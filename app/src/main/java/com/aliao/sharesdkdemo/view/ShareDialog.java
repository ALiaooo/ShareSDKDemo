package com.aliao.sharesdkdemo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.aliao.sharesdkdemo.R;
import com.eleme.mars.sharesdk.ShareItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liaolishuang on 15/12/8.
 */
public class ShareDialog extends Dialog {

    private GridView mGridView;
    private List<HashMap<String, Object>> mShareItemList;
    private static final String SHAREITEM_ICON = "shareitem_icon";
    public static final String SHAREITEM_TEXT = "shareitem_text";

    String[] name = {
            ShareItem.Text.WECHAT,
            ShareItem.Text.WECHATMOMENTS,
            ShareItem.Text.QQ,
            ShareItem.Text.QZONE
    };
    private int[] image = {
            ShareItem.Image.WECHAT,
            ShareItem.Image.WECHATMOMENTS,
            ShareItem.Image.QQ,
            ShareItem.Image.QZONE};


    public ShareDialog(Context context) {
        this(context, R.style.dialog_bottom);
    }

    public ShareDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context) {
        initShareItemData();
        View shareView = getLayoutInflater().inflate(R.layout.dialog_share_content, null);
        addContentView(shareView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        SimpleAdapter adapter = new SimpleAdapter(context, mShareItemList, R.layout.item_dialog_share,
                new String[]{SHAREITEM_ICON, SHAREITEM_TEXT}, new int[]{R.id.iv_icon, R.id.tv_from});
        mGridView = (GridView) shareView.findViewById(R.id.gv_share);
        mGridView.setAdapter(adapter);
        shareView.findViewById(R.id.btn_dimiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(shareView);
    }

    private void initShareItemData() {
        mShareItemList = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(SHAREITEM_ICON, image[i]);
            map.put(SHAREITEM_TEXT, name[i]);
            mShareItemList.add(map);
        }
    }


    public void setGridViewOnItemClickListener(AdapterView.OnItemClickListener l) {
        mGridView.setOnItemClickListener(l);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth();
        getWindow().setAttributes(p);
    }


}
