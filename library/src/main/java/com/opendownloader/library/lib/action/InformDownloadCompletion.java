package com.opendownloader.library.lib.action;

import android.os.Message;

import com.opendownloader.library.lib.entity.CustomMessage;
import com.opendownloader.library.lib.handlers.ImageDownloadHandler;
import com.opendownloader.library.lib.utils.AppConstants;

import java.util.concurrent.Callable;

public class InformDownloadCompletion implements Callable<String> {
    private final ImageDownloadHandler handler;
    private String url;

    public InformDownloadCompletion(String url, ImageDownloadHandler handler) {
        this.url = url;
        this.handler = handler;
    }

    @Override
    public String call() throws Exception {
        CustomMessage customMessage = new CustomMessage(url, null);
        final Message msg = new Message();
        msg.what = AppConstants.DONE;
        msg.obj = customMessage;
        final InformDownloadCompletion that = this;
        handler.dispatchMessage(msg);
        return "";
    }
}
