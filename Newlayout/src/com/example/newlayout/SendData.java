package com.example.newlayout;
import android.os.Message;

public class SendData implements Runnable {
    int SendMsucc=-1,SendPsucc=-1;
    Fragment_usb fragment_usb=new Fragment_usb();
    Fragment_seeting fragment_seeting=new Fragment_seeting();
	@Override
	public void run() {
		// TODO Auto-generated method stub
        if(fragment_usb.IsOK)
        {
        	try
        	{
        		if (Myservice.connection != null)
        		{
        			Myservice.connection.close();
    			}
        		Myservice.connection =Myservice. usbManager.openDevice(Myservice.usbDevice);
        		Myservice.connection.claimInterface(Myservice.usbInterface[0], true);
        		if(Fragment_seeting.mainOK)
        		{
        			while(SendMsucc<0)//发送主板数据，若没有成功，SendMsucc<0
        			{
            			SendMsucc=Myservice.connection.bulkTransfer(Myservice.usbEndpoint[0][1], Fragment_seeting.Send_Maindata, 31, 1000);
        			}  
        			Fragment_seeting.mainOK=false;
        			Message message =Myservice.DrawHandler.obtainMessage();
				    message.what = 2;
				    message.sendToTarget(); //发送成功，则通过handle给主activity返回数据
        		}
        		else if(Fragment_seeting.proOK)
        		{
        			while(SendPsucc<0)//发送主板数据，若没有成功，SendPsucc<0
        			{
            			SendPsucc=Myservice.connection.bulkTransfer(Myservice.usbEndpoint[0][1], Fragment_seeting.Send_Prodata, 25, 1000);
        			}
        			Fragment_seeting.proOK=false;
        			Message message =Myservice.DrawHandler.obtainMessage();
				    message.what = 3;
				    message.sendToTarget();  
        		}
        	}
        	catch(Exception ex)
        	{
        		
        	}
        	
        }
        else
        {
        	
        }
	}

}
