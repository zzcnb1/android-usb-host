package com.example.newlayout;

//数据转换类，将采集到的数据分装在chalist中，实现画图
public class Datatransfer {
	
    public Datatransfer(){    	
    };
    
    //将接收到的byte数组根据a^2+b^2再开方，
    public  int channeldataT(int d1,int d2)
    {
		int D;
    	D= (int) Math.sqrt( Math.pow(d1,2)+ Math.pow(d2,2));
    	return D;   	
    }
    
    //分配到各个画图list中
    //senID:探头序号
    //start:从哪个位置开始读有效数据
    //byte[] b:要分配的数组
    //采样结果以mv为单位，所以为整型数据0-3300mv
    private void Distribution(int senID,int start,byte[] b)
    {
    	if(senID<32 &&senID >= 0)
    	{
	    	int s1 = (b[start+1] << 8) & 0xFF00;  
	        s1 |= b[start] & 0xFF;         //通道1的sin	    	
	        int c1 = (b[start+9] << 8) & 0xFF00;  
	        c1 |= b[start+8] & 0xFF;         //通道1的cos
	        int d1=channeldataT(s1,c1);		      
	        Data_RW.CHAlist1.set(8000+senID,d1);//senID从1开始
	     
	    	
	    	int s2 = (b[start+3] << 8) & 0xFF00;  
	        s2 |= b[start+2] & 0xFF;         //通道2的sin	    	
	        int c2 = (b[start+11] << 8) & 0xFF00;  
	        c2 |= b[start+10] & 0xFF;         //通道2的cos
	        int d2=channeldataT(s2,c2);
	        Data_RW.CHAlist2.set(8000+senID,d2);
	       
	    	
	    	int s3 = (b[start+5] << 8) & 0xFF00;  
	        s3 |= b[start+4] & 0xFF;         //通道3的sin	    	
	        int c3 = (b[start+13] << 8) & 0xFF00;  
	        c3 |= b[start+12] & 0xFF;         //通道3的cos
	        int d3=channeldataT(s3,c3);
	        Data_RW.CHAlist3.set(8000+senID,d3);
	       	        		       
	    	
	    	int s4 = (b[start+7] << 8) & 0xFF00;  
	        s4 |= b[start+6] & 0xFF;         //通道4的sin	    	
	        int c4 = (b[start+15] << 8) & 0xFF00;  
	        c4 |= b[start+14] & 0xFF;         //通道4的cos
	        int d4=channeldataT(s4,c4);
	        Data_RW.CHAlist4.set(8000+senID,d4);   
    	}
	}	
    
	//pts[]={0xaa,LENGTH=55(0X37),0x81,y0ffset,sensorID,5_20,sensorID,21_36,sensorID,37_54} ,outvalue为包号  （0——10）
		public void todivide(byte[] byt,int outvalue) 
		{				
			// TODO Auto-generated method stub
//			int  y1 = byt[4] & 0xFF;         //Y轴偏移量   	           
//	        int sensor=(byt[5] & 0xFF);    //探头序号
			int sensor=(outvalue*3);    //探头序号
			Distribution(sensor, 6, byt);  
//			sensor=(byt[22] & 0xFF);
			sensor=(outvalue*3+1);
			Distribution(sensor, 23, byt);
			
			if(outvalue<10)
			{
//				sensor=(byt[39] & 0xFF);      //第11个包中最后一个探头数为无效数据
				sensor=(outvalue*3+2);      //第11个包中最后一个探头数为无效数据
				Distribution(sensor, 40, byt);
			}			
		}
		
		 //字节数组转化为十六进制字符，检验显示用
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
