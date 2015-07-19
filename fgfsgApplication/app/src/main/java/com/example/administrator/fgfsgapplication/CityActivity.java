package com.example.administrator.fgfsgapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.administrator.fgfsgapplication.Adapters.ListAdapter;


public class CityActivity extends Activity {

//    FragmentManager manager = null;
//    FragmentTransaction transaction = null;
//    Button button;
    ListView listView;
    ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Resources r = getResources();
        String[] citys = r.getStringArray(R.array.city);
        mListAdapter=new ListAdapter(this,citys);
        listView=(ListView)findViewById(R.id.reflashlist);
        listView.setAdapter(mListAdapter);
        final Intent in = getIntent();
//        manager = this.getFragmentManager();
//        transaction = manager.beginTransaction();
//        for (int j = 0; j < citys.length; j++) {
//            ButtonFragment fragment = new ButtonFragment();
//            fragment.updateButtonText(citys[j]);
//            transaction.add(R.id.gridlayout, fragment);
//        }
//        transaction.commit();
        Button go_button = (Button) findViewById(R.id.go);
        final EditText editText = (EditText) findViewById(R.id.edit_text);
        go_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String editcity = editText.getText().toString();
                    //�ж�editcity�Ƿ��ǳ���
                    in.setClass(CityActivity.this, MainActivity.class);
                    in.putExtra("cityName", editcity);
                    startActivity(in);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
