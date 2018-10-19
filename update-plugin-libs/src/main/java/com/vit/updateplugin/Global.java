package com.vit.updateplugin;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

/**
 * <p> <p/>
 *
 * @author kewz
 */

public class Global {

    private Context applicationContext;

    private static Global mGlobal = new Global();

    public static Global get() {
        return mGlobal;
    }

    void initApplicationContext(Context context) {
        this.applicationContext = context.getApplicationContext();
    }

    public Context getApplicationContext() {
        return applicationContext;
    }
}
