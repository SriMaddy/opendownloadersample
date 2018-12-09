package com.opendownloader.library.lib.action;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.opendownloader.library.lib.entity.CustomMessage;
import com.opendownloader.library.lib.utils.AppConstants;

import java.util.concurrent.Callable;

public class FetchCachedImage implements Callable<Bitmap> {
    private final Bitmap bitmap;
    private final String url;
    private final Handler handler;

    public FetchCachedImage(String url, Bitmap bitmap, Handler handler) {
        this.bitmap = bitmap;
        this.url = url;
        this.handler = handler;
    }

    @Override
    public Bitmap call() throws Exception {
        Message msg = new Message();
        msg.what = AppConstants.DOWNLOAD_COMPLETE;
        msg.obj = new CustomMessage(url, null);
        handler.dispatchMessage(msg);
        return this.bitmap;
    }
}
