package com.example.newlayout;

import java.util.ArrayList;

public class Data_RW {
	 static ArrayList<Integer> CHAlist1=new ArrayList<Integer>(8000);//250�������������ǵڼ������ݣ�32����̽ͷ����,����32����Ϊ����洢�� 
	 static ArrayList<Integer> CHAlist2=new ArrayList<Integer>(8000);//250�������������ǵڼ������ݣ�32����̽ͷ����
	 static ArrayList<Integer> CHAlist3=new ArrayList<Integer>(8000);//250�������������ǵڼ������ݣ�32����̽ͷ����
	 static ArrayList<Integer> CHAlist4=new ArrayList<Integer>(8000);//250�������������ǵڼ������ݣ�32����̽ͷ����	

	 static byte[] Send_Maindata;//�����������ݰ�
	 static byte[] Send_Prodata;//����̽ͷ�����ݰ�
	 
	 static int view_width;//��ȡview�ĳ���
	 static int view_height;//��ȡview�ĸ߶�
	 
	 static boolean bool_draw;//�Ƿ����invalidate����
	 static boolean bool_draw1;//�Ƿ����invalidate����
	 static boolean bool_draw2;//�Ƿ����invalidate����
	 static boolean bool_draw3;//�Ƿ����invalidate����
	 static boolean bool_draw4;//�Ƿ����invalidate����
	 
	 static int unitcolor;//��ѹֵ��Χ0����3300*3300*2���ţ���ɫ�ܹ�256��
}
