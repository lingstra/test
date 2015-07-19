package com.example.administrator.fgfsgapplication.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.fgfsgapplication.MainActivity;
import com.example.administrator.fgfsgapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment {


    private View containerText;
    private String city;
    public TextView textView;
    public TextView dectory_text;
    private Intent intent=new Intent();
    public MainActivity mainActivity;
    public void setCity(String city){
        this.city=city;
    }

    public TextFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        containerText=inflater.inflate(R.layout.fragment_text, container, false);
        textView=(TextView)containerText.findViewById(R.id.common_city_text);
        TextPaint tp1 = textView.getPaint();
        tp1.setFakeBoldText(true);
        if(city!=null) {
            textView.setText(city);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.currentCity=city;
                mainActivity.refersh();
                intent.putExtra("cityName", city);
                intent.setClass(getActivity().getBaseContext(), MainActivity.class);
                startActivity(intent);
                mainActivity.finish();
            }
        });
        dectory_text=(TextView)containerText.findViewById(R.id.dectory_text);
        TextPaint tp2 = dectory_text.getPaint();
        tp2.setFakeBoldText(true);
        dectory_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentManager=mainActivity.getFragmentManager();
                mainActivity.fragmentTransaction=mainActivity.fragmentManager.beginTransaction();
                mainActivity.fragmentTransaction.hide(TextFragment.this);
                mainActivity.fragmentTransaction.commit();
                onDestroy();
            }
        });
        return containerText;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onPause(){
        super.onPause();
    }


}
