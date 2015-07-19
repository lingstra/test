package com.example.administrator.fgfsgapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.fgfsgapplication.R;

/**
 * Created by Administrator on 2015/7/12.
 */
public class ListAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private String[] ss;

    public ListAdapter(Context context,String[] s){
        mInflater=LayoutInflater.from(context);
        ss=s;
    }


    @Override
    public int getCount() {
        return ss.length;
    }


    @Override
    public Object getItem(int position) {
        return ss[position];
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.header_listview, parent,false);
            holder=new ViewHolder();
            holder.cityText=(TextView)convertView.findViewById(R.id.textdd);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.cityText.setText(ss[position]);
        return convertView;
    }

    private  class ViewHolder{
        TextView cityText;

    }
}
