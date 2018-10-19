package com.vit.updateplugdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.vit.updateplugin.UpdatePlug;
import com.vit.updateplugin.check.AbstractApiChecker;
import com.vit.updateplugin.check.DefaultApiCheck;
import com.vit.updateplugin.parse.AbstractParser;
import com.vit.updateplugin.parse.UpdateBean;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
    }

    private void check() {
        new UpdatePlug.Builder("http://api.test3.meiquick.com/?app_id=C2asWPixMdVnCkpaWBDwyZr5W6" +
                "tz2ZK6&nonce=1538193172053&method=app.updateVersion&format=json&sign_method=md5&sign=42B" +
                "1E1239CDFB4A11B23FDFF0E4C8956&os=android&lang=zh-CN&token=87bbda958eed404fe851e9e112ac3657&version=4")
                .asGet()
                .create()
                .check();

        AbstractApiChecker checker = new DefaultApiCheck();
        checker.setCheckInfo("http://api.test3.meiquick.com/?app_id=C2asWPixMdVnCkpaWBDwyZr5W6" +
                "tz2ZK6&nonce=1538193172053&method=app.updateVersion&format=json&sign_method=md5&sign=42B" +
                "1E1239CDFB4A11B23FDFF0E4C8956&os=android&lang=zh-CN&token=87bbda958eed404fe851e9e112ac3657&version=4",
                "GET",
                new HashMap<String, String>(16),
                new HashMap<String, String>(16));
        checker.setParser(new AbstractParser() {
            @Override
            public UpdateBean parse(String response) {
                Log.d("AbstractParser", "parse: " + response);
                UpdateBean bean = new UpdateBean();
                bean.setCode(1);
                return new UpdateBean();
            }
        });
        new Thread(checker).start();

    }

}
