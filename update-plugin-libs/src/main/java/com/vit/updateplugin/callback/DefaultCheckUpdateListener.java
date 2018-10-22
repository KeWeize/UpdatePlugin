package com.vit.updateplugin.callback;

import android.util.Log;

import com.vit.updateplugin.parse.UpdateBean;

/**
 * <p> <p/>
 *
 * @author kewz
 */

public class DefaultCheckUpdateListener implements OnCheckUpdateListener {

    private static String TAG =
            DefaultCheckUpdateListener.class.getSimpleName();

    @Override
    public void onCheckStart() {
        Log.d(TAG, "onCheckStart: start check...");
    }

    @Override
    public void hasUpdate(UpdateBean mUpdate) {
        Log.d(TAG, "onCheckStart: has update...");
    }

    @Override
    public void noUpdate() {
        Log.d(TAG, "onCheckStart: no update...");
    }

    @Override
    public void onCheckError(Throwable t) {
        Log.d(TAG, "onCheckStart: check error...");
    }
}
