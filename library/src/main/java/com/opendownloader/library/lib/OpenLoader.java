package com.opendownloader.library.lib;

import android.os.Message;
import android.view.View;

import com.opendownloader.library.lib.entity.CustomMessage;
import com.opendownloader.library.lib.handlers.ImageDownloadHandler;
import com.opendownloader.library.lib.utils.AppConstants;

public class OpenLoader {

    private static OpenLoader thisInstance;
    private ImageDownloadHandler handler = new ImageDownloadHandler();

    public static OpenLoader get() {
        if(thisInstance == null) {
            thisInstance = new OpenLoader();
        }
        return thisInstance;
    }

    public void loadImage(String URL, View view) {
        Message msg = new Message();
        msg.obj = new CustomMessage(URL, view);
        msg.what = AppConstants.LOAD;
        this.handler.dispatchMessage(msg);
    }
}
