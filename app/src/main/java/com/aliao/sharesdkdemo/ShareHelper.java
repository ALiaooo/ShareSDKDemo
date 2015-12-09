package com.aliao.sharesdkdemo;

import android.content.Context;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.aliao.sharesdkdemo.view.ShareDialog;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import static cn.sharesdk.framework.utils.ShareSDKR.getStringRes;

/**
 * Created by liaolishuang on 15/12/8.
 */
public class ShareHelper implements PlatformActionListener, Callback {

    private static final String TAG = "Walking";
    private Context mContext;
    private HashMap<String, Object> shareParamsMap;

    public ShareHelper(Context mContext) {
        this.mContext = mContext;
        shareParamsMap = new HashMap<String, Object>();
    }

    /**
     * title标题，微信（包括好友、朋友圈和收藏）、QQ空间使用，否则可以不提供
     */
    public void setTitle(String title) {
        shareParamsMap.put("title", title);
    }

    public String getTitle() {
        return shareParamsMap.containsKey("title") ? String.valueOf(shareParamsMap.get("title")) : null;
    }

    /**
     * titleUrl是标题的网络链接，仅在QQ空间使用，否则可以不提供
     */
    public void setTitleUrl(String titleUrl) {
        shareParamsMap.put("titleUrl", titleUrl);
    }

    public String getTitleUrl() {
        return shareParamsMap.containsKey("titleUrl") ? String.valueOf(shareParamsMap.get("titleUrl")) : null;
    }

    /**
     * text是分享文本，所有平台都需要这个字段
     */
    public void setText(String text) {
        shareParamsMap.put("text", text);
    }

    /**
     * 获取text字段的值
     */
    public String getText() {
        return shareParamsMap.containsKey("text") ? String.valueOf(shareParamsMap.get("text")) : null;
    }

    /**
     * imagePath是本地的图片路径，除Linked-In外的所有平台都支持这个字段
     */
    public void setImagePath(String imagePath) {
        if (!TextUtils.isEmpty(imagePath))
            shareParamsMap.put("imagePath", imagePath);
    }

    public String getImagePath() {
        return shareParamsMap.containsKey("imagePath") ? String.valueOf(shareParamsMap.get("imagePath")) : null;
    }

    /**
     * imageUrl是图片的网络路径，QQ空间支持此字段
     */
    public void setImageUrl(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl))
            shareParamsMap.put("imageUrl", imageUrl);
    }

    public String getImageUrl() {
        return shareParamsMap.containsKey("imageUrl") ? String.valueOf(shareParamsMap.get("imageUrl")) : null;
    }

    /**
     * url在微信（包括好友、朋友圈收藏）中使用，否则可以不提供
     */
    public void setUrl(String url) {
        shareParamsMap.put("url", url);
    }

    public String getUrl() {
        return shareParamsMap.containsKey("url") ? String.valueOf(shareParamsMap.get("url")) : null;
    }

    /**
     * site是分享此内容的网站名称，仅在QQ空间使用，否则可以不提供
     */
    public void setSite(String site) {
        shareParamsMap.put("site", site);
    }

    public String getSite() {
        return shareParamsMap.containsKey("url") ? String.valueOf(shareParamsMap.get("url")) : null;
    }

    /**
     * siteUrl是分享此内容的网站地址，仅在QQ空间使用，否则可以不提供
     */
    public void setSiteUrl(String siteUrl) {
        shareParamsMap.put("siteUrl", siteUrl);
    }

    public String getSiteUrl() {
        return shareParamsMap.containsKey("siteUrl") ? String.valueOf(shareParamsMap.get("siteUrl")) : null;
    }


    public void show() {
        ShareSDK.initSDK(mContext);
        final ShareDialog shareDialog = new ShareDialog(mContext);
        shareDialog.show();
        shareDialog.setGridViewOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);
                if (item.get("ItemText").equals("QQ")) {
                    shareToQQ();
                } else if (item.get("ItemText").equals("QQ空间")) {
                    shareToQZone();
                } else if (item.get("ItemText").equals("微信")) {
                    shareToWechat();
                } else if (item.get("ItemText").equals("微信朋友圈")) {
                    shareToWechatMoments();
                }
                shareDialog.dismiss();
            }
        });


    }


    /**
     * 分享给微信好友
     */
    private void shareToWechat() {
        //2、设置分享内容
        ShareParams sp = new ShareParams();
        sp.setShareType(Platform.SHARE_TEXT);//非常重要：一定要设置分享属性
        sp.setTitle(getTitle());//分享标题
        sp.setText(getText());//分享文本
//        sp.setUrl(getUrl());//用户点进链接后，可以看到分享的详情
//        sp.setImageUrl(getImageUrl());//网络图片rul

        //3、非常重要：获取平台对象
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(ShareHelper.this); // 设置分享事件回调
        // 执行分享
        wechat.share(sp);
    }

    /**
     * 分享到朋友圈
     */
    private void shareToWechatMoments() {
        //2、设置分享内容
        ShareParams sp = new ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE); //非常重要：一定要设置分享属性
        sp.setTitle(getTitle());  //分享标题
        sp.setText(getText());//分享文本
        sp.setImageUrl(getImageUrl());//网络图片rul
        sp.setUrl(getUrl());   //网友点进链接后，可以看到分享的详情
        Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
        wechatMoments.setPlatformActionListener(ShareHelper.this); // 设置分享事件回调
        // 执行分享
        wechatMoments.share(sp);
    }


    /**
     * 分享给qq好友
     */
    private void shareToQQ() {
        ShareParams sp = new ShareParams();
        sp.setTitle(getTitle());  //分享标题
        sp.setText(getText());//分享文本
        sp.setTitleUrl(getTitleUrl());
        sp.setImageUrl(getImageUrl());//网络图片rul
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(ShareHelper.this);
        qq.share(sp);
    }

    /**
     * 分享到qq空间
     */
    private void shareToQZone() {
        ShareParams sp = new ShareParams();
        sp.setTitle(getTitle());
        sp.setTitleUrl(getTitleUrl());
        sp.setText(getText());
        sp.setImageUrl(getImageUrl());
        sp.setSite(getSite());
        sp.setSiteUrl(getSiteUrl());
        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        qzone.setPlatformActionListener(ShareHelper.this);
        qzone.share(sp);
    }


    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        Log.d(TAG, "onComplete");
        Message msg = new Message();
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onError(Platform platform, int action, Throwable throwable) {
        Log.d(TAG, "onError");
        throwable.printStackTrace();
        Message msg = new Message();
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = throwable;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onCancel(Platform platform, int action) {
        Log.d(TAG, "onCancel");
        Message msg = new Message();
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            case 1: {
                //成功
                Platform plat = (Platform) msg.obj;
                String text = plat.getName() + " completed at ";
                Log.d("Walking", "onComplete - "+text);
                int resId = getStringRes(mContext, "ssdk_oks_share_completed");
                if (resId > 0) {
                    showNotification(mContext.getString(resId));
                }
            }
            break;
            case 2: {
                // 失败
                String expName = msg.obj.getClass().getSimpleName();
                Log.d(TAG, "onError = "+expName);
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
            case 3: {
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
//        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }
}
