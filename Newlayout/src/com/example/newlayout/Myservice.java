package com.example.newlayout;

import java.util.HashMap;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class Myservice extends Service{
	public NewlayoutActivity mActivity;
	Thread Receive_Data=new Thread(new ReceiveData());//接收数据线程
	Thread Send_Data=new Thread(new SendData());//接收数据线程
	
	/*USB设备初始化所需变量*/
    private boolean Isaccept=false;
    static public boolean IsOK=false;//设备是否与vid和pid匹配,线程开启标志
    static public UsbManager usbManager;
    static public UsbDevice usbDevice;
    static public UsbInterface[] usbInterface=null;
    static public UsbEndpoint[][] usbEndpoint = new UsbEndpoint[5][5];
    static public UsbDeviceConnection connection;	
    static public int myvid = 4660, mypid = 17185;
    private PendingIntent pendingIntent;
    
    private int view_count;
    public static  String str;
    private byte[] get_bytes;
    
    static Handler DrawHandler;   
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    Datatransfer Trans=new Datatransfer();
    private UpdateView updateView;  
	
	/*oncreate（）方法里面实现ReceiveData线程的开启*/
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Init_USB();
		
		Receive_Data.start();
		DrawHandler = new Handler()
		{	
	    	
//			Datatransfer Trans=new Datatransfer();
			public void handleMessage(Message msg) {
				switch (msg.what)
				{
				    //通信协议每个包56个Bytes,共11个包，最后一个包只有两条数据，39个BYTE;
				    case 1:	
				    	//str=Trans.bytes2HexString(testSavedata);
//				    	usbmessage.setText(usbmessage.getText()+str);
				    	str="";
				    	view_count++;
				    	for(int b=0;b<32;b++)
						{
				    		Data_RW.CHAlist1.add(8000+b);//保证数据数组长度为2336,先加上32，再删除32，
				    		Data_RW.CHAlist2.add(8000+b);
				    		Data_RW.CHAlist3.add(8000+b);
				    		Data_RW.CHAlist4.add(8000+b);
						}
						for(int a=0;a<11;a++)
						{
							synchronized (ReceiveData.Save_DataList) 
							{				   				            				         				           				
								get_bytes = ReceiveData.Save_DataList.get(0);		            		
								ReceiveData.Save_DataList.remove(0);
								//数据处理部分，将32个数据包分配到通道的Arraylist中
								//添加32组数据
					            Trans.todivide(get_bytes,a);				            
							}
						}	
						for(int b=0;b<32;b++)
						{
							Data_RW.CHAlist1.remove(0);//保证数据在显示时向左移动 
							Data_RW.CHAlist2.remove(0);
							Data_RW.CHAlist3.remove(0);
							Data_RW.CHAlist4.remove(0);
						}
						if(view_count>=10)//收到10包数据开始更新图
						{	
							//进度发生变化通知调用方,与activity的接口  
		                    if(updateView != null){  
		                    	updateView.Onupdateview();  
		                    }  
						    view_count=0;
						}
					    msg.what=0;	
					break;
				    case 2:
				    	/*new AlertDialog.Builder(NewlayoutActivity)
						.setTitle("System Information")//设置对话框标题  
					    .setMessage("The mainboard configuration success！")//设置显示的内容  
				        .setPositiveButton("YES", new DialogInterface.OnClickListener()
				        {//添加确定按钮  		 	         		
					        @Override   
					         public void onClick(DialogInterface dialog, int which) 
						     {//确定按钮的响应事件  		 
				             // TODO Auto-generated method stub  
							 }  
				        }).show();*/
				    	msg.what=0;	
				    break;
				    case 3:
				    	/*new AlertDialog.Builder(this)
						.setTitle("System Information")//设置对话框标题  
					    .setMessage("The probeboard configuration success！")//设置显示的内容  
				        .setPositiveButton("YES", new DialogInterface.OnClickListener()
				        {//添加确定按钮  		 	         		
					        @Override   
					         public void onClick(DialogInterface dialog, int which) 
						     {//确定按钮的响应事件  		 
				             // TODO Auto-generated method stub  
							 }  
				        }).show();*/
				    	msg.what=0;	
				    break;
				default:  msg.what=0;						
					break;
				}								
			}
		};
	}
	
	 /** 
     * 注册回调接口的方法，供外部调用 
     * @param onProgressListener 
     */  
    public void setOnProgressListener(UpdateView updateView) {  
        this.updateView = updateView;  
    }  
	/*绑定activity*/
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return  new MsgBinder();
	} 
	
	/*传递到activity的内部类，以便于调用service的方法*/
	public class MsgBinder extends Binder{
	    /**
	     * 获取当前Service的实例
	     * @return
	     */
	    public Myservice getService(){
	      return Myservice.this;
	    }
	  }
	/*onStartCommand()方法里面实现SendData线程的开启*/
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
	//	Send_Data.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	/*此函数供调用Service的用户调用*/
	public void start_Send()
	{
		Send_Data.start();
		
	}
	
	//USB信息初始化
	private void Init_USB() {
		// TODO Auto-generated method stub
		try
		{
			pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(
					ACTION_USB_PERMISSION), 0);
			IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
			registerReceiver(usbReceiver, filter);

			// 列举usbservice
			usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
			HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
			Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
			while (deviceIterator.hasNext()) 
			{
				usbDevice = deviceIterator.next();
				Log.e("device", "vid: " + usbDevice.getVendorId() + "\t pid: "+ usbDevice.getProductId());
				if (usbDevice.getVendorId() == myvid && usbDevice.getProductId() == mypid) {
					break;
				}
			}	
			if (usbDevice != null && usbDevice.getVendorId() == myvid && usbDevice.getProductId() == mypid) {
			//	usbmessage.setText("找到设备:" + usbDevice.getVendorId() + "\t pid: "+ usbDevice.getProductId());
				while(!Isaccept)
				{
					if (usbManager.hasPermission(usbDevice)) 
					{
						Isaccept=true;
						usbInterface = new UsbInterface[89];
						usbInterface[0] = usbDevice.getInterface(0);
					//	usbmessage.setText(usbmessage.getText() + "\n接口" + "的端点数为："
					//			+ usbInterface[0].getEndpointCount());
						for (int i = 0; i < usbDevice.getInterfaceCount(); i++)
						{
							usbInterface[i] = usbDevice.getInterface(i);
					//		usbmessage.setText(usbmessage.getText() + "\n接口" + i + "的端点数为："
					//				+ usbInterface[i].getEndpointCount());
							for (int j = 0; j < usbInterface[i].getEndpointCount(); j++)
							{
								usbEndpoint[i][j] = usbInterface[i].getEndpoint(j);				
							}
						}
					}
					else 
					{
						Isaccept=false;
						usbManager.requestPermission(usbDevice, pendingIntent);
					}
				}	
			} 
			else 
			{
	//			showlist.setText("未发现支持设备");
				Isaccept=false;
				new AlertDialog.Builder(this)
				.setTitle("System Information")//设置对话框标题  
			    .setMessage("USB connection exception！")//设置显示的内容  
		        .setPositiveButton("YES", new DialogInterface.OnClickListener()
		        {//添加确定按钮  		 	         		
			        @Override   
			         public void onClick(DialogInterface dialog, int which) 
				     {//确定按钮的响应事件  		 
		             // TODO Auto-generated method stub  
					      mActivity.finish();  
					 }  
		        }).show();
			}	
		}
		catch(Exception e)
		{
			
		}
	}
	private final BroadcastReceiver usbReceiver=new BroadcastReceiver()
	{	
		public void onReceive(Context context, Intent intent)
		{
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (ACTION_USB_PERMISSION.equals(action))
			{
				synchronized (this)
				{
					UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
					if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) 
					{
						if (device != null) 
						{
							// call method to set up device communication
							//Isaccept=true;
						}
					} 
					else 
					{
						Log.d("denied", "permission denied for device "+ device);
					}
			   }
		    }
		}
	};
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	    super.unregisterReceiver(usbReceiver);	
	}

}
