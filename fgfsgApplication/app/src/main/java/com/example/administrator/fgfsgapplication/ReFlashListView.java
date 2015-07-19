package com.example.administrator.fgfsgapplication;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Administrator on 2015/7/10.
 */
public class ReFlashListView extends ListView {
    View header;

    public ReFlashListView(Context context) {
        super(context );
        initView(context);
    }
    public ReFlashListView(Context context, AttributeSet attrs ) {
        super(context, attrs);
        initView(context);
    }
    public ReFlashListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    public void initView(Context context){
        LayoutInflater inflater=LayoutInflater.from(context);
        header= inflater.inflate(R.layout.header_listview,null);
        this.addHeaderView(header);

    }
}
