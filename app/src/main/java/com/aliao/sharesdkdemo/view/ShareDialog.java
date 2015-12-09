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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liaolishuang on 15/12/8.
 */
public class ShareDialog extends Dialog {

    private GridView mGridView;
    List<HashMap<String, Object>> mShareList;
    private String[] name = {"微信", "微信朋友圈", "QQ", "QQ空间"};

    private int[] icon = {R.drawable.ssdk_oks_skyblue_logo_qq, R.drawable.ssdk_oks_skyblue_logo_qq,
            R.drawable.ssdk_oks_skyblue_logo_qq_checked, R.drawable.ssdk_oks_skyblue_logo_qq};

    public ShareDialog(Context context) {
        this(context, R.style.dialog_bottom);
    }

    public ShareDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context) {
        View shareView = getLayoutInflater().inflate(R.layout.dialog_share_content, null);
        addContentView(shareView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        initShare();
        SimpleAdapter adapter = new SimpleAdapter(context, mShareList, R.layout.item_dialog_share,
                new String[]{"ItemImage", "ItemText"}, new int[]{R.id.iv_icon, R.id.tv_from});
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

    private void initShare() {
        mShareList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < icon.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", icon[i]);//添加图像资源的ID
            map.put("ItemText", name[i]);//按序号做ItemText
            mShareList.add(map);
        }
    }

}
