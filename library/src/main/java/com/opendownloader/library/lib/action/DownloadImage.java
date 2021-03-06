package com.opendownloader.library.lib.action;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.opendownloader.library.lib.bitmaputils.BitmapUtils;
import com.opendownloader.library.lib.entity.CustomMessage;
import com.opendownloader.library.lib.utils.AppConstants;

import java.util.concurrent.Callable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadImage implements Callable<Bitmap> {
    private final Handler handler;
    OkHttpClient client = new OkHttpClient();
    private final String url;


    public DownloadImage(String url, Handler handler) {
        this.url = url;
        this.handler = handler;
    }

    @Override
    public Bitmap call() throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        byte[] responseText = response.body().bytes();
        Bitmap myBitmap = BitmapUtils.resizeTo(responseText, 250);
        Message msg = new Message();
        msg.what = AppConstants.DOWNLOAD_COMPLETE;
        msg.obj = new CustomMessage(url, null);
        handler.dispatchMessage(msg);
        return myBitmap;
    }
}
