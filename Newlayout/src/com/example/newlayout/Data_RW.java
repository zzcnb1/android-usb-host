package com.example.newlayout;

import java.util.ArrayList;

public class Data_RW {
	 static ArrayList<Integer> CHAlist1=new ArrayList<Integer>(8000);//250代表横坐标接收是第几次数据，32代表探头数据,后面32数据为缓冲存储区 
	 static ArrayList<Integer> CHAlist2=new ArrayList<Integer>(8000);//250代表横坐标接收是第几次数据，32代表探头数据
	 static ArrayList<Integer> CHAlist3=new ArrayList<Integer>(8000);//250代表横坐标接收是第几次数据，32代表探头数据
	 static ArrayList<Integer> CHAlist4=new ArrayList<Integer>(8000);//250代表横坐标接收是第几次数据，32代表探头数据	

	 static byte[] Send_Maindata;//发送主板数据包
	 static byte[] Send_Prodata;//发送探头板数据包
	 
	 static int view_width;//获取view的长度
	 static int view_height;//获取view的高度
	 
	 static boolean bool_draw;//是否进行invalidate函数
	 static boolean bool_draw1;//是否进行invalidate函数
	 static boolean bool_draw2;//是否进行invalidate函数
	 static boolean bool_draw3;//是否进行invalidate函数
	 static boolean bool_draw4;//是否进行invalidate函数
	 
	 static int unitcolor;//电压值范围0——3300*3300*2更号，颜色总共256档
}
