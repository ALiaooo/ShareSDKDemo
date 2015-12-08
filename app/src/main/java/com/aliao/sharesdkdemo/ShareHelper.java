package com.aliao.sharesdkdemo;

import android.content.Context;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.aliao.sharesdkdemo.view.ShareDialog;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import static cn.sharesdk.framework.utils.ShareSDKR.getStringRes;

/**
 * Created by liaolishuang on 15/12/8.
 */
public class ShareHelper implements PlatformActionListener, Callback {

    private static final String TAG = "Walking";
    private static final int COMPLETE = 1;
    private static final int ERROE = 2;
    private static final int CANCEL = 3;

    private Context mContext;

    public void show(Context context) {
        ShareSDK.initSDK(context);
        mContext = context;
        final ShareDialog shareDialog = new ShareDialog(mContext);
        shareDialog.show();
        shareDialog.setGridViewOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);
                if (item.get("ItemText").equals("QQ")) {
                    shareToQQ();
                } else if (item.get("ItemText").equals("QZone")) {
                    shareToQZone();
                } else if (item.get("ItemText").equals("Wechat")) {
                    shareToWechat();
                } else if (item.get("ItemText").equals("WechatMoments")) {
                    shareToWechatMoments();
                }
                shareDialog.dismiss();
            }
        });


    }


    private void shareToWechat() {
        //2、设置分享内容
        ShareParams sp = new ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
        sp.setTitle("我是分享标题");  //分享标题
        sp.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");   //分享文本
        sp.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul
        sp.setUrl("http://sharesdk.cn");   //网友点进链接后，可以看到分享的详情

        //3、非常重要：获取平台对象
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(ShareHelper.this); // 设置分享事件回调
        // 执行分享
        wechat.share(sp);
    }

    private void shareToWechatMoments() {
        //2、设置分享内容
        ShareParams sp = new ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE); //非常重要：一定要设置分享属性
        sp.setTitle("我是分享标题");  //分享标题
        sp.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");   //分享文本
        sp.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul
        sp.setUrl("http://sharesdk.cn");   //网友点进链接后，可以看到分享的详情

        //3、非常重要：获取平台对象
        Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
        wechatMoments.setPlatformActionListener(ShareHelper.this); // 设置分享事件回调
        // 执行分享
        wechatMoments.share(sp);
    }


    private void shareToQQ() {
        ShareParams sp = new ShareParams();
        sp.setTitle("qq分享标题");
        sp.setText("分享文本");
        sp.setTitleUrl("http://www.baidu.com");
        sp.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(ShareHelper.this);
        qq.share(sp);
    }

    private void shareToQZone() {

    }


    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        Message msg = new Message();
        msg.arg1 = COMPLETE;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onError(Platform platform, int action, Throwable throwable) {
        Message msg = new Message();
        msg.arg1 = ERROE;
        msg.arg2 = action;
        msg.obj = throwable;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onCancel(Platform platform, int action) {
        Message msg = new Message();
        msg.arg1 = CANCEL;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            case COMPLETE: {
                int resId = getStringRes(mContext, "ssdk_oks_share_completed");
                if (resId > 0) {
                    showNotification(mContext.getString(resId));
                }
            }
            break;
            case ERROE: {
                // 失败
                String expName = msg.obj.getClass().getSimpleName();
                if ("WechatClientNotExistException".equals(expName)
                        || "WechatTimelineNotSupportedException".equals(expName)
                        || "WechatFavoriteNotSupportedException".equals(expName)) {
                    int resId = getStringRes(mContext, "ssdk_wechat_client_inavailable");
                    if (resId > 0) {
                        showNotification(mContext.getString(resId));
                    }
                } else if ("QQClientNotExistException".equals(expName)) {
                    int resId = getStringRes(mContext, "ssdk_qq_client_inavailable");
                    if (resId > 0) {
                        showNotification(mContext.getString(resId));
                    }
                } else {
                    int resId = getStringRes(mContext, "ssdk_oks_share_failed");
                    if (resId > 0) {
                        showNotification(mContext.getString(resId));
                    }
                }
            }
            break;
            case CANCEL: {
                Log.d(TAG, "收到取消的通知");
                // 取消
                int resId = getStringRes(mContext, "ssdk_oks_share_canceled");

                if (resId > 0) {
                    showNotification(mContext.getString(resId));
                }
            }
            break;
        }
        return false;
    }

    // 在状态栏提示分享操作
    private void showNotification(String text) {
        Log.d(TAG, "显示通知");
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }
}
