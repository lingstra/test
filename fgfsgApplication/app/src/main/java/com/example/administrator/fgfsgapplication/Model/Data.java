package com.example.administrator.fgfsgapplication.Model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2015/7/2.
 */
public class Data {
    int cod1;
    double message1;
    int city_id;//���б��
    public Date date;
    public String city_name;//��������
    public double city_coord_lon;//���о���
    public double city_coord_lat;//����γ��
    public String city_country;//��������
    int city_population;//�����˿�
    int cnt1;//
    public ArrayList<ListData> listData=new ArrayList<ListData>();
}
