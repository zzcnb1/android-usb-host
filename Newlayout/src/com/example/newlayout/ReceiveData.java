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
    		   //�����Ѿ�����buffer��
			  int inMax =Myservice.usbEndpoint[0][0].getMaxPacketSize(); 
			  ByteBuffer byteBuffer = ByteBuffer.allocate(inMax); //�����µ��ֽڻ�����
			  UsbRequest usbRequest = new UsbRequest();//�������interrupt endpoints
			  usbRequest.initialize(Myservice.connection, Myservice.usbEndpoint[0][0]);// Initializes the request so it can read or write data on the given endpoint.
			  usbRequest.queue(byteBuffer, inMax);
			  //�жϴ����Ƿ����
			  if( Myservice.connection.requestWait() == usbRequest)
			  {  	
				  pack_count++;						  
				  Savedata=new byte[inMax];//11����Ϊ��������	
				  Savedata = byteBuffer.array();
				  /*MyTabActivity.testSavedata=new byte[inMax];
				  MyTabActivity.testSavedata=byteBuffer.array();*/
				  Myservice.str=Myservice.str+Tran.bytes2HexString(Savedata);
				 /* if(pack_count==10){
					  Message message =MyTabActivity.DrawHandler.obtainMessage();
					  message.what = 1;
					  message.sendToTarget();  //���յ��ײ��requestִ�У���������Ĵ����޷�ִ��
				      pack_count=0;
				  }*/
				  synchronized (Save_DataList)
				  {									   
//					   pack_count++;   //ÿ����һ������1��
//						   String ur2=i+"";
//						   Log.v("tag",ur2);
					   Save_DataList.add(Savedata); 
					  //��packet_num=11,�Ұ��Ŷ����ʱ���ŷ�����Ϣ�����̴߳�������
					  //��pack_count=1ʱ�����°��ţ��������űȽ�
					  //���packet_num�ڡ�0,10��֮�䣬�Ұ��Ų��ԣ���ɾ��֮ǰ�洢����
					   if(pack_count==1)
					   {
						  packet_num=Savedata[4];									 									
					   }								  
					   if((pack_count>1)&&(pack_count<12))
					   {
						   if(Savedata[4]!=packet_num)
						   {     //�����11�����г��ְ����в�ͬ���������������
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
							   message.sendToTarget();  //���յ��ײ��requestִ�У���������Ĵ����޷�ִ��
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
