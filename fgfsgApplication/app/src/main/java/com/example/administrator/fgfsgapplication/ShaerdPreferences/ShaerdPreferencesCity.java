package com.example.administrator.fgfsgapplication.ShaerdPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.fgfsgapplication.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/7/2.
 */
public class ShaerdPreferencesCity {
    private static String Json;
    private static String Date;
    private static SharedPreferences sharedPreferences=null;
    private static SharedPreferences.Editor editor;
    public static List cityNames(Context context) {
        List<String> buffer_city =new ArrayList<>();
        sharedPreferences=context.getSharedPreferences("city",
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String N1=sharedPreferences.getString("ctiyName1", "");
        String N2=sharedPreferences.getString("ctiyName2", "");
        String N3=sharedPreferences.getString("ctiyName3", "");
        boolean CN1=true;
        boolean CN2=true;
        if(N1!=null){
            buffer_city.add( N1);
            if(N1.equals(N2)) {
                editor.putString("cityName2", "");
                editor.putString("Json2", "");
                CN1=false;
            }
            if(N2!=null||CN1){
                buffer_city.add( N2);
                if(N2.equals(N3)) {
                    editor.putString("cityName3", "");
                    editor.putString("Json3", "");
                    CN2=false;
                }
                if(N3!=null||CN2){
                    if(N3.equals(N1)){
                        editor.putString("cityName3", "");
                        editor.putString("Json3", "");
                    }else {
                        buffer_city.add(N3);
                    }
                }
            }
        }

        editor.commit();
        return buffer_city;
    }
    public static void setJsonBuffer(Context context, String mDate,
                                     String currentJson,String cityName) {
        sharedPreferences=context.getSharedPreferences("city",
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String cityNames1 = sharedPreferences.getString("ctiyName1", "");
        String cityNames2 = sharedPreferences.getString("ctiyName2", "");
        String cityNames3 = sharedPreferences.getString("ctiyName3", "");
        String Json1 = sharedPreferences.getString("Json1", "");
        String Json2 = sharedPreferences.getString("Json2", "");
        if (buffer_cityAddTure(cityName, cityNames1,cityNames2,cityNames3)) {
            if(cityNames1==null) {
                editor.putString("ctiyName1", cityName);
                editor.putString("Json1", currentJson);
                editor.putString("Time1", mDate);
            }else if(cityNames2==null){
                editor.putString("ctiyName2", cityName);
                editor.putString("Json2", currentJson);
                editor.putString("Time2", mDate);
            }else if(cityNames3==null){
                editor.putString("ctiyName3", cityName);
                editor.putString("Json3", currentJson);
                editor.putString("Time3", mDate);
            }else {
                editor.putString("ctiyName1", cityName);
                editor.putString("Json1", currentJson);
                editor.putString("Time1", mDate);
                editor.putString("ctiyName2", cityNames1);
                editor.putString("Json2", Json1);
                editor.putString("Time2", mDate);
                editor.putString("ctiyName3", cityNames2);
                editor.putString("Json3", Json2);
                editor.putString("Time3", mDate);
            }
        }
        Date = sharedPreferences.getString("Date", "");
        if (mDate.equals(Date)) {
        }else{
            editor.putString("JSON", currentJson);
            editor.putString("city0", cityName);
            editor.putString("Date", mDate);
        }
        editor.commit();
        Date = sharedPreferences.getString("Date", "");
        Json = sharedPreferences.getString("JSON", "");
    }
    public static String getJson(Context context){
        sharedPreferences=context.getSharedPreferences("city",
                Context.MODE_PRIVATE);
        Json = sharedPreferences.getString("JSON", "");
        return Json;
    }
    public static String getDate(Context context){
        sharedPreferences=context.getSharedPreferences("city",
                Context.MODE_PRIVATE);
        Date = sharedPreferences.getString("Date", "");
        return Date;
    }

    private static boolean buffer_cityAddTure(String s, String s1,String s2 ,String s3) {
        if(s.equals(s1)||s.equals(s2)||s.equals(s3)){
            return false;
        }
        if(s.equals(MainActivity.locationCity)){
            return false;
        }
        return true;
    }
}
