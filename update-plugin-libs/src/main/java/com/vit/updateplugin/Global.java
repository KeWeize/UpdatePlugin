package com.vit.updateplugin;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.LinkedList;

/**
 * <p> <p/>
 *
 * @author kewz
 */

public class Global implements Application.ActivityLifecycleCallbacks {

    private Context applicationContext;
    private LinkedList<Activity> stack = new LinkedList<>();

    private static Global mGlobal = new Global();

    public static Global get() {
        return mGlobal;
    }

    void initApplicationContext(Context context) {
        this.applicationContext = context.getApplicationContext();
        ((Application) context.getApplicationContext())
                .registerActivityLifecycleCallbacks(Global.mGlobal);
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public Activity getTopActivity() {
        Activity activity = null;
        if (!stack.isEmpty()) {
            activity = stack.getLast();
        }
        return activity;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (stack.contains(activity)) {
            stack.add(activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (stack.contains(activity)) {
            stack.remove(activity);
        }
    }
}
