package com.eleme.mars.sharesdk;

/**
 * Created by liaolishuang on 15/12/10.
 */
public interface ShareItem {
    interface Text {
        String WECHAT = "微信";
        String WECHATMOMENTS = "朋友圈";
        String QQ = "QQ";
        String QZONE = "QQ空间";
    }

    interface Image {
        int WECHAT = R.drawable.share_wechat_selector;
        int WECHATMOMENTS = R.drawable.share_wechatmoments_selector;
        int QQ = R.drawable.share_qq_selector;
        int QZONE = R.drawable.share_qzone_selector;
    }

}
