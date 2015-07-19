package com.example.administrator.fgfsgapplication.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.fgfsgapplication.MainActivity;
import com.example.administrator.fgfsgapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonFragment extends Fragment {

    private View containerView;
    private String btnText;
    private Intent intent = new Intent();
    public ButtonFragment() {
    }

    private void initViews() throws Exception {
        View view = this.containerView;
        Button button1 = (Button) view.findViewById(R.id.city1_button);
        if (this.btnText != null) {
            button1.setText(this.btnText);
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("cityName", btnText);
                intent.setClass(getActivity().getBaseContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        containerView = inflater.inflate(R.layout.fragment_button, container,false);
        try {
            initViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return containerView;
    }

    public void updateButtonText(String txt){
        btnText = txt;
    }


}
