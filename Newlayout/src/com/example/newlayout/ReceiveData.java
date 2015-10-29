package com.example.newlayout;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import android.hardware.usb.UsbRequest;
import android.os.Message;

public class ReceiveData implements Runnable {
    Fragment_usb fragment_usb=new Fragment_usb();
	Datatransfer Tran=new Datatransfer();
	static public  ArrayList<byte[]> Save_DataList=new ArrayList<byte[]> ();
	private byte[] Savedata;
	private int pack_count,packet_num;
	@Override
	public void run() {
		// TODO Auto-generated method stub
       while(fragment_usb.IsOK)
       {
    	   try
    	   {
    		  if(Myservice.connection!=null)
			  { 
    			  Myservice.connection.close(); 
			  }			 
    		  Myservice.connection = Myservice.usbManager.openDevice(Myservice.usbDevice);
    		  Myservice.connection.claimInterface(Myservice.usbInterface[0], true);
    		   //数据已经存入buffer中
			  int inMax =Myservice.usbEndpoint[0][0].getMaxPacketSize(); 
			  ByteBuffer byteBuffer = ByteBuffer.allocate(inMax); //创建新的字节缓冲区
			  UsbRequest usbRequest = new UsbRequest();//传输可以interrupt endpoints
			  usbRequest.initialize(Myservice.connection, Myservice.usbEndpoint[0][0]);// Initializes the request so it can read or write data on the given endpoint.
			  usbRequest.queue(byteBuffer, inMax);
			  //判断传输是否完成
			  if( Myservice.connection.requestWait() == usbRequest)
			  {  	
				  pack_count++;						  
				  Savedata=new byte[inMax];//11个包为完整数据	
				  Savedata = byteBuffer.array();
				  /*MyTabActivity.testSavedata=new byte[inMax];
				  MyTabActivity.testSavedata=byteBuffer.array();*/
				  Myservice.str=Myservice.str+Tran.bytes2HexString(Savedata);
				 /* if(pack_count==10){
					  Message message =MyTabActivity.DrawHandler.obtainMessage();
					  message.what = 1;
					  message.sendToTarget();  //接收到底层的request执行，否则下面的代码无法执行
				      pack_count=0;
				  }*/
				  synchronized (Save_DataList)
				  {									   
//					   pack_count++;   //每处理一个包加1；
//						   String ur2=i+"";
//						   Log.v("tag",ur2);
					   Save_DataList.add(Savedata); 
					  //当packet_num=11,且包号都相等时，才发送信息给主线程处理数据
					  //在pack_count=1时，记下包号，与后面包号比较
					  //如果packet_num在【0,10】之间，且包号不对，则删除之前存储数据
					   if(pack_count==1)
					   {
						  packet_num=Savedata[4];									 									
					   }								  
					   if((pack_count>1)&&(pack_count<12))
					   {
						   if(Savedata[4]!=packet_num)
						   {     //如果这11个包中出现包号有不同，则清除所有数据
							   for(int k=0;k<pack_count;k++){
								   Save_DataList.remove(0);
							   }
							   pack_count=0;		
						   }	
						   
						   if(((pack_count==11)&&Savedata[4]==packet_num))
						   {
							   pack_count=0;
							   Message message =Myservice.DrawHandler.obtainMessage();
							   message.what = 1;
							   message.sendToTarget();  //接收到底层的request执行，否则下面的代码无法执行
						   }  								   									   								   									   
					   }
					   if(pack_count>=12){
						   pack_count=0;
					   }
				   }
			  }							 
			//sleep(1);   
    	   }
    	   catch(Exception e)
    	   {
    		   
    	   }    	   	  
       }
       
	}

}
