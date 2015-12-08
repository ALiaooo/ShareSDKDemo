package com.aliao.sharesdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        Button showShare = (Button) findViewById(R.id.btn_show_share);
        showShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开分享选择界面
                ShareHelper shareHelper = new ShareHelper();
                shareHelper.show(MainActivity.this);
            }
        });
    }
}
