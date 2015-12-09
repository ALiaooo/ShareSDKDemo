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
                ShareHelper shareHelper = new ShareHelper(MainActivity.this);
                shareHelper.setTitle("分享标题");
                shareHelper.setText("分享的内容");
                shareHelper.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");
                shareHelper.setUrl("http://www.mob.com");
                shareHelper.setSite("来自");
                shareHelper.setSiteUrl("http://www.mob.com");
                shareHelper.show();
            }
        });
    }
}
