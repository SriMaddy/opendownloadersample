package com.opendownloader.library.lib.handlers;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.opendownloader.library.lib.action.DownloadImage;
import com.opendownloader.library.lib.action.FetchCachedImage;
import com.opendownloader.library.lib.action.InformDownloadCompletion;
import com.opendownloader.library.lib.action.ResumePendingAction;
import com.opendownloader.library.lib.cache.LRUCache;
import com.opendownloader.library.lib.entity.CustomMessage;
import com.opendownloader.library.lib.utils.AppConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ImageDownloadHandler extends Handler {
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    HashMap<String, Future<Bitmap>> urlVsFutureMap = new HashMap<>();
    HashMap<String, List<View>> urlVsViews = new HashMap<>();
    List<Object> triggeredActions = Collections.synchronizedList(new ArrayList<>());
    HashMap<String, List<CustomMessage>> pendingActions = new HashMap<>();
    LRUCache cache = LRUCache.getInstance();

    public ImageDownloadHandler() {
        super();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void handleMessage(Message msg) {
        if (msg.what == AppConstants.LOAD) {
            Log.i("loading - ", msg.what + "");
            CustomMessage customMessage = (CustomMessage) msg.obj;
            final String key = customMessage.getURL();

            Future future;
            /*
             *  Maintaining a map of url as key vs FutureTasks for download operation. Once DONE message is triggered, using the URL's future, result
             *  can be fetched. "future.get()"
             * */
            if (cache.getBitmapFromMemCache(key) != null) {
                future = executorService.submit(new FetchCachedImage(key, cache.getBitmapFromMemCache(customMessage.getURL()), this));
            } else {
                future = executorService.submit(new DownloadImage(key, this));
            }
            urlVsFutureMap.put(key, future);

            /*
             *  Maintaining a map of key as URL vs view. So that it can be updated when fetch of image is done
             * */
            if (urlVsViews.get(key) == null) {
                urlVsViews.put(key, Collections.synchronizedList(new ArrayList<>()));
            }


            urlVsViews.get(key).add(customMessage.getView());

            /*
             *  To avoid duplicate http request maintaining a list for already triggered. Once it is done, all the pending actions will be triggered.
             *  That time it will fetch image from cache
             * */
            if (triggeredActions.contains(key)) {
                if (pendingActions.get(key) == null) {
                    pendingActions.put(key, Collections.synchronizedList(new ArrayList<>()));
                }
                pendingActions.get(key).add(customMessage);
            } else {
                triggeredActions.add(key);
            }

        } else if (msg.what == AppConstants.DONE) {
            Log.i("done - ", msg.what + "");
            final CustomMessage customMessage = (CustomMessage) msg.obj;
            final String key = customMessage.getURL();
            Bitmap response = null;
            try {
                response = urlVsFutureMap.get(customMessage.getURL()).get();
                cache.addBitmapToMemoryCache(customMessage.getURL(), response);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            List<View> views = urlVsViews.get(customMessage.getURL());
            views = Collections.synchronizedList(views);
            for (View view : views) {
                ImageView v = (ImageView) view;
                if (v != null) {
                    v.setImageBitmap(response);
                }
            }
            views.clear();
            if (pendingActions.get(key) != null && pendingActions.get(key).size() > 0) {
                pendingActions.get(key).forEach(pendingMessage -> executorService.submit(new ResumePendingAction(customMessage, this)));
                pendingActions.get(key).clear();
            }

        } else if (msg.what == AppConstants.DOWNLOAD_COMPLETE) {
            Log.i("DownloadComplete", msg.what + "");
            CustomMessage customMessage = (CustomMessage) msg.obj;
            executorService.submit(new InformDownloadCompletion(customMessage.getURL(), this));
        }
    }

}
