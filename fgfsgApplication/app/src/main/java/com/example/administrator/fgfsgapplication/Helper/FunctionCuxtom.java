package com.example.administrator.fgfsgapplication.Helper;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.fgfsgapplication.Fragments.TextFragment;
import com.example.administrator.fgfsgapplication.Fragments.ViewFragment;
import com.example.administrator.fgfsgapplication.MainActivity;
import com.example.administrator.fgfsgapplication.Model.Data;
import com.example.administrator.fgfsgapplication.Model.ListData;
import com.example.administrator.fgfsgapplication.R;
import com.example.administrator.fgfsgapplication.ShaerdPreferences.ShaerdPreferencesCity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.String.*;

/**
 * Created by Administrator on 2015/7/2.
 */
public class FunctionCuxtom {
    private  Context mContext;
    private Date currentDate;
    public List<String> comm_city = new ArrayList<>();
    public int dayNamber=3;
    //选择图片方法
    public int selectPicture(Data data,int k) {
        int drawable;
        String weather = data.listData.get(k).weather_main.toString();
        if (weather.equals("Clouds")) {
            drawable = R.drawable.qingzhuangduoyun;
        } else if (weather.equals("Sunny")) {
            drawable = R.drawable.qing;
        } else if (weather.equals("Overcast")) {
            drawable =R.drawable.yin;
        } else if (weather.equals("Rain")) {
            drawable = R.drawable.xiaoyu;
        } else if (weather.equals("heavy rain")) {
            drawable = R.drawable.dayu;
        } else if (weather.equals("heave snow")) {
            drawable = R.drawable.daxue;
        } else if (weather.equals("Clear")) {
            drawable = R.drawable.qing;
        }else {
            drawable= Integer.parseInt(null);
        }
        return drawable;
    }
    //返回当前时间
    public Date currentDate() throws ParseException {
        Date curDate = new Date(System.currentTimeMillis());
        return curDate;
    }
    //时间格式
    public String formatDate(Date date ){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 \nHH时");
        String dateString = formatter.format(date);
        return dateString;
    }
    //时间加天数方法
    public  String StrToDateLong(Date strDate,int d) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(strDate);
        cal.add(Calendar.DATE, d);
        Date date = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        String dateString = formatter.format(date);
        return dateString;
    }
    //时间加小时方法
    public  int StrToDateLongHour(String strDate,int hour) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 \nHH时");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        long myTime=(strtodate.getTime()/1000)+60*60*hour;
        int mHour= (int) (myTime%(60*60*24))/60*60;
        strtodate.setTime(myTime*1000);
        return mHour;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void cityGo( MainActivity mainActivity) throws ParseException {
        mContext=mainActivity;
        Data data=mainActivity.cityData;
        Resources r=mainActivity.getResources();
        FragmentManager fragmentManager=mainActivity.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String mDate ;
        currentDate=currentDate();
            for (int j = 0; j < dayNamber; j++) {
                ViewFragment fragment = new ViewFragment();
                if (j == 0) {
                    mDate = "明天";
                } else {
                    mDate = StrToDateLong(data.date, j + 1);
                }
                if (data != null) {
                    Drawable drawable = r.getDrawable(selectPicture(data, j + 1));
                    String weathermassage = (
                            data.listData.get(j + 1).weather_description +
                                    "\n" + data.listData.get(j + 1).temp_day + "°" +
                                    "\n" + data.listData.get(j + 1).temp_max + "°" +
                                    "~" + data.listData.get(j + 1).temp_min) + "°";
                    fragment.setData(weathermassage, drawable, mDate);
                    transaction.add(R.id.view_fragment, fragment);
                }
            }

            TextView main_text1 = (TextView) mainActivity.findViewById(R.id.main_textview1);
            main_text1.setText(data.listData.get(0).temp_day + "°C");
            TextView main_text2 = (TextView) mainActivity.findViewById(R.id.main_textview2);
            main_text2.setText(data.listData.get(0).temp_max + "°" +
                            "~" + data.listData.get(0).temp_min + "°C" +
                            "\n" + data.listData.get(0).weather_description +
                            "\n" + data.city_name +
                            "\n" + data.city_country +
                            "\n" + formatDate(currentDate)
            );
              comm_city = ShaerdPreferencesCity.cityNames(mainActivity);
                for (int k = 0; k < comm_city.size(); k++) {
                    String c = comm_city.get(k);
                    if (c != null) {
                        TextFragment textFragment = new TextFragment();
                        textFragment.setCity(c);
                        textFragment.mainActivity = mainActivity;
                        transaction.add(R.id.middle_layout, textFragment);
                    }
                }

            if(!transaction.isEmpty()) {
                transaction.commit();
            }
            LinearLayout taoday = (LinearLayout) mainActivity.findViewById(R.id.today);
            Drawable drawableToday = r.getDrawable(selectPicture(data, 0));
            taoday.setBackground(drawableToday);
            mainActivity.imageViewLoad.setImageDrawable(null);
            if (isNetworkConnected(mainActivity) || isWifiConnected(mainActivity)) {
                Toast.makeText(mainActivity, "当前城市："
                        + data.city_name + "天气", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mainActivity, "\"网络未连接\"",
                        Toast.LENGTH_LONG).show();
            }
    }
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    public  String getString(String CityName)  {
        String c = null;
        HttpURLConnection conn = null;
        try {
            c = URLEncoder.encode(CityName, "utf-8");
            String Sky = "http://api.openweathermap.org/data/2.5/forecast/daily?q=\"" +
                    c + "\"&mode=json&units=metric&cnt=7&lang=zh_CN";
            URL url = new URL(Sky);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(6 * 1000);
            if (conn.getResponseCode() == 200) {
                InputStream inStream = null;
                inStream = conn.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                byte[] data = outStream.toByteArray();
                outStream.close();
                inStream.close();
                return new String(data, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Data jsonToData(String json){
        Data data=new Data();
        if(json.startsWith("error")) {
            return null;
        }
        try{
            JSONObject jsonObject=new JSONObject(json);
            JSONObject city1=jsonObject.getJSONObject("city");
            data.city_country=city1.getString("country");
            JSONObject coord1=city1.getJSONObject("coord");
            data.city_coord_lon=coord1.getDouble("lon");
            data.city_coord_lat=coord1.getDouble("lat");
            data.city_name=city1.getString("name");
            JSONArray list1=jsonObject.getJSONArray("list");
            for(int i=0;i<list1.length();i++) {
                ListData listData=new ListData();
                JSONObject listObject = list1.getJSONObject(i);
                JSONArray weather1 = listObject.getJSONArray("weather");
                JSONObject weatherObject = weather1.getJSONObject(0);
                listData.weather_main = weatherObject.getString("main");
                listData.weather_description=weatherObject.getString("description");
                listData.weather_icon=weatherObject.getString("icon");
                listData.weather_id=weatherObject.getInt("id");
                JSONObject temp1=listObject.getJSONObject("temp");
                listData.temp_day= (int) Math.round(temp1.getDouble("day"));
                listData.temp_max=(int) Math.round(temp1.getDouble("max"));
                listData.temp_min=(int) Math.round(temp1.getDouble("min"));
                listData.temp_night=temp1.getDouble("night");
                listData.temp_morn=temp1.getDouble("morn");
                listData.temp_eve=temp1.getDouble("eve");
                listData.pressure=listObject.getDouble("pressure");
                listData.deg=listObject.getInt("deg");
                listData.clouds=listObject.getInt("clouds");
                listData.humidity=listObject.getInt("humidity");
                listData.speed = listObject.getDouble("speed");
                data.listData.add(listData);
            }
            data.date=currentDate();
        }catch(JSONException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }
}
