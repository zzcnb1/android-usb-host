package com.example.newlayout;

//����ת���࣬���ɼ��������ݷ�װ��chalist�У�ʵ�ֻ�ͼ
public class Datatransfer {
	
    public Datatransfer(){    	
    };
    
    //�����յ���byte�������a^2+b^2�ٿ�����
    public  int channeldataT(int d1,int d2)
    {
		int D;
    	D= (int) Math.sqrt( Math.pow(d1,2)+ Math.pow(d2,2));
    	return D;   	
    }
    
    //���䵽������ͼlist��
    //senID:̽ͷ���
    //start:���ĸ�λ�ÿ�ʼ����Ч����
    //byte[] b:Ҫ���������
    //���������mvΪ��λ������Ϊ��������0-3300mv
    private void Distribution(int senID,int start,byte[] b)
    {
    	if(senID<32 &&senID >= 0)
    	{
	    	int s1 = (b[start+1] << 8) & 0xFF00;  
	        s1 |= b[start] & 0xFF;         //ͨ��1��sin	    	
	        int c1 = (b[start+9] << 8) & 0xFF00;  
	        c1 |= b[start+8] & 0xFF;         //ͨ��1��cos
	        int d1=channeldataT(s1,c1);		      
	        Data_RW.CHAlist1.set(8000+senID,d1);//senID��1��ʼ
	     
	    	
	    	int s2 = (b[start+3] << 8) & 0xFF00;  
	        s2 |= b[start+2] & 0xFF;         //ͨ��2��sin	    	
	        int c2 = (b[start+11] << 8) & 0xFF00;  
	        c2 |= b[start+10] & 0xFF;         //ͨ��2��cos
	        int d2=channeldataT(s2,c2);
	        Data_RW.CHAlist2.set(8000+senID,d2);
	       
	    	
	    	int s3 = (b[start+5] << 8) & 0xFF00;  
	        s3 |= b[start+4] & 0xFF;         //ͨ��3��sin	    	
	        int c3 = (b[start+13] << 8) & 0xFF00;  
	        c3 |= b[start+12] & 0xFF;         //ͨ��3��cos
	        int d3=channeldataT(s3,c3);
	        Data_RW.CHAlist3.set(8000+senID,d3);
	       	        		       
	    	
	    	int s4 = (b[start+7] << 8) & 0xFF00;  
	        s4 |= b[start+6] & 0xFF;         //ͨ��4��sin	    	
	        int c4 = (b[start+15] << 8) & 0xFF00;  
	        c4 |= b[start+14] & 0xFF;         //ͨ��4��cos
	        int d4=channeldataT(s4,c4);
	        Data_RW.CHAlist4.set(8000+senID,d4);   
    	}
	}	
    
	//pts[]={0xaa,LENGTH=55(0X37),0x81,y0ffset,sensorID,5_20,sensorID,21_36,sensorID,37_54} ,outvalueΪ����  ��0����10��
		public void todivide(byte[] byt,int outvalue) 
		{				
			// TODO Auto-generated method stub
//			int  y1 = byt[4] & 0xFF;         //Y��ƫ����   	           
//	        int sensor=(byt[5] & 0xFF);    //̽ͷ���
			int sensor=(outvalue*3);    //̽ͷ���
			Distribution(sensor, 6, byt);  
//			sensor=(byt[22] & 0xFF);
			sensor=(outvalue*3+1);
			Distribution(sensor, 23, byt);
			
			if(outvalue<10)
			{
//				sensor=(byt[39] & 0xFF);      //��11���������һ��̽ͷ��Ϊ��Ч����
				sensor=(outvalue*3+2);      //��11���������һ��̽ͷ��Ϊ��Ч����
				Distribution(sensor, 40, byt);
			}			
		}
		
		 //�ֽ�����ת��Ϊʮ�������ַ���������ʾ��
		public  String bytes2HexString(byte[] b) {
			  String ret = "";
			  for (int i = 0; i < b.length; i++) {
			   String hex = Integer.toHexString(b[ i ] & 0xFF);
			   if (hex.length() == 1) {
			    hex = '0' + hex;
			   }
			   ret += hex.toUpperCase();
			  }
			  return ret;
			}
}
