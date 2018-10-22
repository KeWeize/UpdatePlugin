package com.vit.updateplugdemo;

import android.util.Log;

import com.vit.updateplugin.parse.AbstractParser;
import com.vit.updateplugin.parse.UpdateBean;

/**
 * <p> <p/>
 *
 * @author kewz
 */

public class MUpdateParser implements AbstractParser {
    @Override
    public UpdateBean parse(String response) {
        Log.d("AbstractParser", "parse: " + response);
        UpdateBean bean = new UpdateBean();
        bean.setCode(2);
        bean.setCodeName("2.0.0");
        bean.setContent("更新内容：\n1.新增订单搜索\n2.新增未上传身份证列表\n3.新增注册时用户名的填写");
        bean.setApkUrl("https://static.meiquick.com/android/meiquick_2.0.1.apk");
        return bean;
    }
}
