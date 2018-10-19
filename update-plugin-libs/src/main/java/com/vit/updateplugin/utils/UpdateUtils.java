package com.vit.updateplugin.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.vit.updateplugin.Global;
import com.vit.updateplugin.cache.UpdateVersionPreference;
import com.vit.updateplugin.parse.UpdateBean;

/**
 * <p> <p/>
 *
 * @author kewz
 */

public class UpdateUtils {

    /**
     * 对比版本是否需要更新，需要更新的情况如下：
     * 1、安装版本小于更新版本 && 更新版本没被忽略
     * 2、安装版本小于更新版本 && 更新版本为强制
     *
     * @param mUpdate
     * @return
     * @throws Exception
     */
    public static boolean checkShouldUpdate(UpdateBean mUpdate) throws Exception {
        int curVersion = getApkVersion(Global.get().getApplicationContext());
        boolean isVersionByIgnore = UpdateVersionPreference.getIgnoreVersions()
                .contains(String.valueOf(mUpdate.getCode()));
        boolean isVersionForced = mUpdate.isForced();
        return curVersion < mUpdate.getCode() && (!isVersionByIgnore || isVersionForced);
    }

    /**
     * 获取当前Apk版本号
     *
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static int getApkVersion(Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionCode;
    }

}
