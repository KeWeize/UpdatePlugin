package com.vit.updateplugin.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.vit.updateplugin.Global;

import java.util.HashSet;
import java.util.Set;

/**
 * <p> 缓存忽略版本 <p/>
 *
 * @author kewz
 */

public class UpdateVersionPreference {

    private static final String PREF_NAME = "update_pref";

    public static void saveIgnoreVersion(int versionCode) {
        Set<String> ignoreVersions = getIgnoreVersions();
        if (!ignoreVersions.contains(String.valueOf(versionCode))) {
            ignoreVersions.add(String.valueOf(versionCode));
            getUpdatePref().edit().putStringSet("ignoreVersions",ignoreVersions).apply();
        }
    }

    public static Set<String> getIgnoreVersions () {
        return getUpdatePref().getStringSet("ignoreVersions", new HashSet<String>());
    }

    private static SharedPreferences getUpdatePref () {
        return Global.get().getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

}
