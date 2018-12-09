package com.opendownloader.library.lib.action;

import android.os.Handler;
import android.os.Message;

import com.opendownloader.library.lib.entity.CustomMessage;
import com.opendownloader.library.lib.utils.AppConstants;

import java.util.concurrent.Callable;

public class ResumePendingAction implements Callable<String> {
    private final CustomMessage customMessage;
    private final Handler handler;

    public ResumePendingAction(CustomMessage customMessage, Handler handler) {
        this.handler = handler;
        this.customMessage = customMessage;
    }

    @Override
    public String call() throws Exception {
        Message msg = new Message();
        msg.what = AppConstants.LOAD;
        msg.obj = customMessage;
        this.handler.dispatchMessage(msg);
        return "";
    }
}
