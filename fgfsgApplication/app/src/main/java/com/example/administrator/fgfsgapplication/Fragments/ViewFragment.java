package com.example.administrator.fgfsgapplication.Fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.fgfsgapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFragment extends Fragment {


    private TextView textView1;
    private ImageView imageView1;
    private TextView textView2;
    private View rootView;
    private String weatherMessage;
    private String mDate;
    private Drawable drawable;
    private boolean hasData = false;


    public ViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedIstanceState){
        super.onCreate(savedIstanceState);
//        this.hasData = false;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_view, container, false);
        if(this.hasData){
            textView1=(TextView)this.rootView.findViewById(R.id.lie01);
            textView2=(TextView)this.rootView.findViewById(R.id.lie03);
            imageView1=(ImageView)this.rootView.findViewById(R.id.lie02);
            this.update(this.weatherMessage, this.drawable, this.mDate);
        }
        return rootView;
    }
    public void update(String weathermassage,Drawable pid,String date){
        textView1.setText(weathermassage);
        imageView1.setImageDrawable(pid);
        textView2.setText(date);
    }


    public void setData(String weathermassage, Drawable drawable, String mDate) {
        this.hasData = true;
        this.weatherMessage = weathermassage;
        this.drawable = drawable;
        this.mDate = mDate;
    }
}
