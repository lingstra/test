package com.example.administrator.fgfsgapplication;



import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.administrator.fgfsgapplication.Helper.FunctionCuxtom;
import com.example.administrator.fgfsgapplication.Model.Data;
import com.example.administrator.fgfsgapplication.ShaerdPreferences.ShaerdPreferencesCity;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends Activity {
    public static String locationCity;
    public static String currentCity;
    public Data cityData;
    public ImageView imageViewLoad;
    public FragmentManager fragmentManager=null;
    public FragmentTransaction fragmentTransaction=null;
    private String JSON;
    private int m=0;
    private String cityName;
    private TextView button;
    private TextView locationButton;
    private  UpdateTextTask task;
    private ImageView refresh_button;
    private ViewPager mViewPager;
    private int[] mImgIds=new int[]{R.drawable.lanping,R.drawable.d4,R.drawable.d6};
    private List mImages=new ArrayList<>();
    private LocationClient mLocationClient = null;
    private  SharedPreferences sharedPreferences=null;
    private FunctionCuxtom functionCuxtom=new FunctionCuxtom();
    private BDLocationListener myListener = new MyLocationListener();
    private final static int NOTIFICATION_ID_ICON = 0x10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        sharedPreferences=getSharedPreferences("city",Context.MODE_PRIVATE);
        mViewPager=(ViewPager)findViewById(R.id.id_viewpage);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container,int position){
                ImageView imageView= new ImageView(MainActivity.this);
                imageView.setImageResource(mImgIds[position]);
                //bianxing
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imageView);
                mImages.add(imageView);
                return imageView;
            }
            @Override
            public int getCount() {
                return mImgIds.length;
            }
            @Override
            public void destroyItem(ViewGroup container,int position,
                                    Object object){
                container.removeView((View) mImages.get(position));
            }
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        });
        imageViewLoad = (ImageView) findViewById(R.id.backgroud_progressbar);
        task = new UpdateTextTask(this);
        if(functionCuxtom.isNetworkConnected(MainActivity.this)
                ||functionCuxtom.isWifiConnected(MainActivity.this)) {
            setLocationCity();
        }else {
            cityName=sharedPreferences.getString("ctiyName1", "");
        }
        try {
            Intent intent = getIntent();
            String intentCity=intent.getStringExtra("cityName");
            if (intentCity != null) {
                cityName = intentCity;
            }else{
                cityName="西安";
            }
            task.execute(cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //跳转城市选择界面按钮
        button = (TextView) findViewById(R.id.city_button);
        TextPaint tp = button.getPaint();
        tp.setFakeBoldText(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, CityActivity.class);
                startActivity(in);
                finish();
            }
        });
        //定位城市按钮
        locationButton=(TextView)findViewById(R.id.location_button);
        locationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(locationCity!=null) {
                    locationButton.setText(locationCity);
                }
            }
        });
        //刷新按钮
        refresh_button = (ImageView) findViewById(R.id.refresh_log);
        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refersh();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    class UpdateTextTask extends AsyncTask<String,Void,Void> {
        private Context context;
        UpdateTextTask(Context context) {
            this.context = context;
        }
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected Void doInBackground(String... params) {
            try {
                int shaerdPreferenceDateHour=0;
                Date currentDate=functionCuxtom.currentDate();
                String cityName = (String) params[0];
                if(m!=0){
                    //二次登陆
                    String shaerdPreferenceDateString=
                            ShaerdPreferencesCity.getDate(MainActivity.this);
                    shaerdPreferenceDateHour=
                            functionCuxtom.StrToDateLongHour(shaerdPreferenceDateString, 0);
                    currentCity=cityName;
                    //判断日期是否更新
                    if(functionCuxtom.StrToDateLongHour(currentDate.toString(), 0)-
                            shaerdPreferenceDateHour>=3){
                        if(cityName!=null) {
                            JSON = functionCuxtom.getString(cityName);
                        }
                    }
                }else{
                    //首次登陆
                    if(functionCuxtom.isNetworkConnected(MainActivity.this)||
                            functionCuxtom.isWifiConnected(MainActivity.this)){
                        JSON = functionCuxtom.getString(cityName);
                    }
                }
                if (JSON==null) {
                    if(ShaerdPreferencesCity.getJson(MainActivity.this)!=null){
                        JSON = ShaerdPreferencesCity.getJson(MainActivity.this);
                        MainActivity.this.cityData = functionCuxtom.jsonToData(JSON);
                    }
                } else {
                        updateCityData(JSON);
                }
                if (cityData != null) {
                    addIconToStatusbar(functionCuxtom.selectPicture(cityData, 0));
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                m++;
                functionCuxtom.cityGo(MainActivity.this);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    //刷新方法
    public void updateCityData(String JSON) throws ParseException {
        Data data = functionCuxtom.jsonToData(JSON);
        cityData = data;
        ShaerdPreferencesCity.setJsonBuffer(MainActivity.this,
            functionCuxtom.currentDate().toString(), JSON, cityName);
    }
    public void refersh(){
        task = new UpdateTextTask(MainActivity.this);
        task.execute(currentCity);
    }
    // 后台运行方法
    private void addIconToStatusbar(int resId) {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = new Notification(
                resId, "我查!", System.currentTimeMillis());
        n.flags |= Notification.FLAG_AUTO_CANCEL;
        n.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
        PendingIntent pi = PendingIntent.getActivity(this, 0, getIntent(), 0);
        n.contentIntent = pi;
        n.setLatestEventInfo(this, currentCity, cityData.listData.get(0).weather_description, pi);
        nm.notify(NOTIFICATION_ID_ICON, n);
    }
    //定位城市建立
    public void setLocationCity() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option=new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }
    //调用百度API异步获取定位城市
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            locationCity=location.getCity();
            mLocationClient.stop();
        }
    }
}
