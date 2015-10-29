package com.example.newlayout;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Fragment_seeting extends Fragment{
	
//	控制窗口自适应后期处理
	public static float   rateX=0,rateY=0; 
	private Button btn_main,btn_pro;
    private TextView usbmessage,txt_PID,txt_P,txt_I,txt_D,txt_point,txt_shift,txt_pga;
    private TextView txt_rbtnP,txt_ss1,txt_ss2,txt_setF,txt_setV;
    private EditText edi_cha1v,edi_cha1f,edi_cha2f,edi_cha2v,edi_cha3f,edi_cha3v,edi_cha4f,edi_cha4v;
    private EditText edi_P,edi_I,edi_D,edi_point,edi_speed;
    private CheckBox che_main,che_pro,che_cha1,che_cha2,che_cha3,che_cha4;
    private Spinner spi_pga;
    private RadioGroup rgroup_SING,rgroup_COSG,rgroup_CHOSE;	
    private RadioButton rbtn_chip1,rbtn_sin,rbtn_chip2,rbtn_cos,rbtn_pro1,rbtn_pro2;
	Fragment_usb fragment_usb=new Fragment_usb();
	
	/*发送数据包部分的参数*/
//	主板数据包
	static byte[] Send_Maindata;
//	探头板数据包
	static byte[] Send_Prodata;
//	发送线程中区分发送哪个板子的数据
	static boolean mainOK,proOK;
//	使能各通道编辑器
	byte enable1, enable2, enable3, enable4;
//	PGA放大倍数
	byte PGAmul;
//	探头板选取激励电流源产生类型
	int setsin,setcos,p1;
//	主板探头板各编辑器使能
    private boolean enableMain,enablePro;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//getActivity().startService(fragment_usb.intent);
		
		return inflater.inflate(R.layout.fragment_setting, container, false);
		
	}
    
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		UI_Init();
		//主板数据总开关
		che_main.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					enableMain=true;
					che_cha1.setEnabled(true);
					che_cha2.setEnabled(true);
					che_cha3.setEnabled(true);
					che_cha4.setEnabled(true);
					btn_main.setEnabled(true);
					btn_main.setBackgroundColor(0xffFFFF00);
				}
				else{
					enableMain=false;
					che_cha1.setEnabled(false);
					che_cha2.setEnabled(false);
					che_cha3.setEnabled(false);
					che_cha4.setEnabled(false);	
					edi_cha1f.setEnabled(false);
					edi_cha1v.setEnabled(false);
					edi_cha2f.setEnabled(false);
					edi_cha2v.setEnabled(false);
					edi_cha3f.setEnabled(false);
					edi_cha3v.setEnabled(false);
					edi_cha4f.setEnabled(false);
					edi_cha4v.setEnabled(false);
					btn_main.setEnabled(false);
					btn_main.setBackgroundColor(0xff708090);
				}
			}
		});
		
		//探头板数据总开关
		che_pro.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					enablePro=true;
					edi_P.setEnabled(true);
					edi_I.setEnabled(true);
					edi_D.setEnabled(true);
					edi_point.setEnabled(true);
					edi_speed.setEnabled(true);
					spi_pga.setEnabled(true);	
					btn_pro.setEnabled(true);
					btn_pro.setBackgroundColor(0xffFFFF00);
				}
				else{
					enablePro=false;
					edi_P.setEnabled(false);
					edi_I.setEnabled(false);
					edi_D.setEnabled(false);
					edi_point.setEnabled(false);
					edi_speed.setEnabled(false);
					spi_pga.setEnabled(false);	
					btn_pro.setEnabled(false);
					btn_pro.setBackgroundColor(0xff708090);
				}
			}
		});
		
		//通道一开关
		che_cha1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					enable1=(byte)0x01;
					edi_cha1f.setEnabled(true);
					edi_cha1v.setEnabled(true);
				}
				else{
					enable1=0;
					edi_cha1f.setEnabled(false);
					edi_cha1v.setEnabled(false);
				}
			}
		});
		
		//通道2开关
		che_cha2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					enable2=(byte)0x01;
					edi_cha2f.setEnabled(true);
					edi_cha2v.setEnabled(true);
				}
				else{
					
					enable2=0;
					edi_cha2f.setEnabled(false);
					edi_cha2v.setEnabled(false);
				}
			}
		});
		//通道3开关
		che_cha3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					enable3=(byte)0x01;
					edi_cha3f.setEnabled(true);
					edi_cha3v.setEnabled(true);
				}
				else{
					
					enable3=0;
					edi_cha3f.setEnabled(false);
					edi_cha3v.setEnabled(false);
				}
			}
		});
		//通道4开关
		che_cha4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					enable4=(byte)0x01;
					edi_cha4f.setEnabled(true);
					edi_cha4v.setEnabled(true);
				}
				else{
					
					enable4=0;
					edi_cha4f.setEnabled(false);
					edi_cha4v.setEnabled(false);
				}
			}
		});
		rgroup_CHOSE.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			 
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				if(checkedId==rbtn_pro1.getId()){
//					rbtn_pro1.setCompoundDrawables(drawableon, null, null, null);
//					rbtn_pro2.setCompoundDrawables(drawableoff, null, null, null);
	 				p1=0x02;
	 			}
	 			if(checkedId==rbtn_pro2.getId()){	 
//	 				rbtn_pro1.setCompoundDrawables(drawableoff, null, null, null);
//					rbtn_pro2.setCompoundDrawables(drawableon, null, null, null);
	 				p1=0x03;
	 			}
			}
			});
		//选择设置哪块板子
		rgroup_SING.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==rbtn_chip1.getId()){
	 				setsin=0x02;	 				
	 			}
	 			if(checkedId==rbtn_sin.getId()){
	 				setsin=0x00;		 				
	 			}
			}
			});
		//二选一激励源通道选择SIN
		rgroup_COSG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==rbtn_chip2.getId()){
	  				setcos=0x01;
	  				/*showlist2.setText(showlist2.getText()
	 			    		 +"\n激励源2：     单片机 ");*/
	  			}
	  			if(checkedId==rbtn_cos.getId()){
	  				setcos=0x00;
	  				/*showlist2.setText(showlist2.getText()
	 			    		 +"\n激励源2：     cos ");*/
	  			}
			}
			});//二选一激励源通道选择cos	
		//发送主板数据按钮
		btn_main.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				mainOK=true;
				proOK=false;
				if(enableMain && fragment_usb.IsOK)//checkbox main是否为真USB串口是否打开
				{
				    Take_MainPacket();
				    NewlayoutActivity.myService.start_Send();
				  
				}
			}
		});
		//发送探头板数据按钮
		
		btn_pro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mainOK=false;
				proOK=true;
				if(enablePro && fragment_usb.IsOK)
				{//checkbox pro是否为真
					Take_ProPacket();
					NewlayoutActivity.myService.start_Send();
				}
			}
		});//发送探头板数据按钮
		//PGA MUL
		spi_pga.setOnItemSelectedListener(new pgachange());
		
		
	}
	 //打包探头版数据
    protected void Take_ProPacket() {
		// TODO Auto-generated method stub
    	String pidkp= edi_P.getText().toString();//比例常数
		/*showlist2.setText(showlist2.getText()
				 +"\n"+"\nThe ratio of PID:  "+" "+pidkp);	*/
		
    	String pidki=edi_I.getText().toString();//积分常数
    /*	showlist2.setText(showlist2.getText()
		    	 +"  PID integral:  "+pidki);	*/
    	
    	String pidkd=edi_D.getText().toString();//微分常数
    	/*showlist2.setText(showlist2.getText()
	    		 +"\nPID differential:  "+pidkd);*/	
    	
    	String pidsp=edi_point.getText().toString();//传感器预设目标	
    	/*showlist2.setText(showlist2.getText()
	    		 +"  PID setpoint:  "+pidsp);	*/
    	
    	String speed=edi_speed.getText().toString();//速度
    	
    	float fpidkp = Float.parseFloat(pidkp);
    	float fpidki = Float.parseFloat(pidki);
    	float fpidkd = Float.parseFloat(pidkd);
    	float fpidsp = Float.parseFloat(pidsp);
    	float fspeed = Float.parseFloat(speed);
    	
    	int ipidkp=(int) (fpidkp*1000);
    	int ipidki=(int) (fpidki*1000);
    	int ipidkd=(int) (fpidkd*1000);
    	int ipidsp=(int) (fpidsp*1000);
    	int ispeed=(int) (fspeed*1000);
    	
    	byte[] bykp=intToByteArray(ipidkp);
    	byte[] byki=intToByteArray(ipidki);
    	byte[] bykd=intToByteArray(ipidkd);
    	byte[] bysp=intToByteArray(ipidsp);
    	byte[] byspeed=intToByteArray(ispeed);
    	Send_Prodata=new byte[25];
    	Send_Prodata[0]=(byte) 0xaa;//传送标志（0）
    	Send_Prodata[1]=(byte) 0x23; //数据包长度35（1）
    	Send_Prodata[2]=(byte) p1; //ID为在另一个函数中判断为0x02或者0x03
        for(int i=0;i<4;i++){
        	Send_Prodata[3+i]=bykp[i];  //比例系数(3到6)(kp)
        }	        
        for(int i=0;i<4;i++){
        	Send_Prodata[7+i]=bykd[i];  //微分系数(7到10)
        }
        for(int i=0;i<4;i++){
        	Send_Prodata[11+i]=byki[i];  //积分系数（11到14）
        }
        for(int i=0;i<4;i++){
        	Send_Prodata[15+i]=bysp[i];  //预设目标(15到18)
        }
        Send_Prodata[19]=PGAmul;
        Send_Prodata[20]=(byte)(setsin+setcos);//选择激励类型（20的后两位）
        for(int i=0;i<4;i++){
        	Send_Prodata[21+i]=byspeed[i];  //LED(21到24)
        }
	}

//打包主板数据
	private void Take_MainPacket()
    {
    	String a1f,a1v,a2f,a2v,a3f,a3v,a4f,a4v;
    	if(edi_cha1f.getText().toString().equals("")){
    		 a1f="0";
    	}else{
    		 a1f=edi_cha1f.getText().toString();
    	}
//    	showlist2.setText("SetMainboard:"+"\n"+"Channel 1 Frequency:"+a1f);
    	
    	if(edi_cha1v.getText().toString().equals("")){
    		 a1v="0";
    	}else{
    		 a1v=edi_cha1v.getText().toString();
    	}
//    	showlist2.setText(showlist2.getText()+"  "+"Channel 1 Voltage:"+a1v);
    	
    	if(edi_cha2f.getText().toString().equals("")){
    		 a2f="0";
    	}else{
    		 a2f=edi_cha2f.getText().toString();
    	}
//    	showlist2.setText(showlist2.getText()+"\n"+"Channel 2 Frequency:"+a2f);
    	
    	if(edi_cha2v.getText().toString().equals("")){
    		 a2v="0";
    	}else{
    		 a2v=edi_cha2v.getText().toString();
    	}
//    	showlist2.setText(showlist2.getText()+"  "+"Channel 2 Voltage:"+a2v);
    	
    	if(edi_cha3f.getText().toString().equals("")){
    		 a3f="0";
    	}else{
    		 a3f=edi_cha3f.getText().toString();
    	}
//    	showlist2.setText(showlist2.getText()+"\n"+"Channel 3 Frequency:"+a3f);
    	
    	if(edi_cha3v.getText().toString().equals("")){
    		 a3v="0";
    	}else{
    		 a3v=edi_cha3v.getText().toString();
    	}
//    	showlist2.setText(showlist2.getText()+"  "+"Channel 3 Voltage:"+a3v);
    	
    	if(edi_cha4f.getText().toString().equals("")){
    		 a4f="0";
    	}else{
    		 a4f=edi_cha4f.getText().toString();
    	}
//    	showlist2.setText(showlist2.getText()+"\n"+"Channel 4 Frequency:"+a4f);
    	
    	if(edi_cha4v.getText().toString().equals("")){
    		 a4v="0";
    	}else{
    		 a4v=edi_cha4f.getText().toString();
    	}
//    	showlist2.setText(showlist2.getText()+"  "+"Channel 4 Voltage:"+a4v);
    	float f1 = Float.parseFloat(a1f);
        float a1 = Float.parseFloat(a1v);
        float f2 = Float.parseFloat(a2f);
        float a2 = Float.parseFloat(a2v);
        float f3 = Float.parseFloat(a3f);
        float a3 = Float.parseFloat(a3v);
        float f4 = Float.parseFloat(a4f);
        float a4 = Float.parseFloat(a4v);
        
        int if1=(int) (f1*1000);
        int ia1=(int) (a1*1000);
        int if2=(int) (f2*1000);
        int ia2=(int) (a2*1000);
        int if3=(int) (f3*1000);
        int ia3=(int) (a3*1000);
        int if4=(int) (f4*1000);
        int ia4=(int) (a4*1000);
        
        byte[] byf1=intToByteArray(if1);
        byte[] byv1=intToByteArray(ia1);
        byte[] byf2=intToByteArray(if2);
        byte[] byv2=intToByteArray(ia2);
        byte[] byf3=intToByteArray(if3);
        byte[] byv3=intToByteArray(ia3);
        byte[] byf4=intToByteArray(if4);
        byte[] byv4=intToByteArray(ia4);
        Send_Maindata=new byte[31];
        Send_Maindata[0]=(byte) 0xaa;//传送标志
        Send_Maindata[1]=(byte) 0x23; //数据包长度35
        Send_Maindata[2]=(byte) 0x01; //ID为0x01代表SetMainboard
        Send_Maindata[3] =enable1;    //是否使能通道1
        Send_Maindata[10]=enable2;
        Send_Maindata[17]=enable3;    //是否使能通道3
        Send_Maindata[24]=enable4;
        for(int i=0;i<4;i++){
        	Send_Maindata[4+i]=byf1[i];  //通道1频率
        }
        for(int i=0;i<2;i++){
        	Send_Maindata[8+i]=byv1[i+2];  //通道1电压
        }
        
        for(int i=0;i<4;i++){
        	Send_Maindata[11+i]=byf2[i];  //通道2频率
        }
        for(int i=0;i<2;i++){
        	Send_Maindata[15+i]=byv2[i+2];  //通道2电压
        }
        
        for(int i=0;i<4;i++){
        	Send_Maindata[18+i]=byf3[i];  //通道3频率
        }
        for(int i=0;i<2;i++){
        	Send_Maindata[22+i]=byv3[i+2];  //通道3电压
        }
        
        for(int i=0;i<4;i++){
        	Send_Maindata[25+i]=byf4[i];  //通道4频率
        }
        for(int i=0;i<2;i++){
        	Send_Maindata[29+i]=byv4[i+2];  //通道4电压
        }
    }
	private byte[] intToByteArray(int i) 
	{
		// TODO Auto-generated method stub 
        byte[] result = new byte[4];   
        //由高位到低位
        result[0] = (byte)((i >> 24) & 0xFF);
        result[1] = (byte)((i >> 16) & 0xFF);
        result[2] = (byte)((i >> 8) & 0xFF); 
        result[3] = (byte)(i & 0xFF);
        return result;
	}
	
	//PGA放大倍数
    private class pgachange implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			String chosedata= (String) spi_pga.getItemAtPosition(position);
			float chosedataf = Float.parseFloat(chosedata);
			int chosedatai=(int) (chosedataf*100);
			switch(chosedatai)
			{
			    case 8:
			    	PGAmul=(byte)0x00;     break;
			    case 16:
			    	PGAmul=(byte)0x01;     break;
			    case 32:
			    	PGAmul=(byte)0x02;     break;
			    case 63:
			    	PGAmul=(byte)0x03;     break;
			    case 126:
			    	PGAmul=(byte)0x04;     break;
			    case 252:
			    	PGAmul=(byte)0x05;     break;
			    case 501:
			    	PGAmul=(byte)0x06;     break;
			    case 1000:
			    	PGAmul=(byte)0x07;     break;
			    default:break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
		} 	 
    }		
	private void UI_Init() {
		
		// TODO Auto-generated method stub
		Context context = getActivity().getApplicationContext();
		getSizeRate(context);     //得到各控件的显示比例
		
//		swi_usb=(Switch) findViewById(R.id.switch_usb);
		
		che_main=(CheckBox) getView().findViewById(R.id.set_main);
		che_main.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*45);
		che_main.setTextScaleX((float) rateX);
		
		che_pro=(CheckBox)getView().findViewById(R.id.set_pro);
		che_pro.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*45);
		che_pro.setTextScaleX((float) rateX);
		
		btn_main=(Button)  getView().findViewById(R.id.btn_sendmain);
		btn_pro=(Button)  getView().findViewById(R.id.btn_sendpro);
		txt_setF=(TextView)  getView().findViewById(R.id.channel1fview);
		txt_setF.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
		txt_setF.setTextScaleX((float) rateX);
		txt_setV=(TextView)  getView().findViewById(R.id.channel1vview);
		txt_setV.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
		txt_setV.setTextScaleX((float) rateX);
			
		edi_cha1v=(EditText)  getView().findViewById(R.id.edit_Cha1V);
		edi_cha1v.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*30);
		edi_cha1v.setTextScaleX((float) rateX);
		
		edi_cha1f=(EditText)  getView().findViewById(R.id.edit_Cha1F);
		edi_cha1f.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*30);
		edi_cha1f.setTextScaleX((float) rateX);
		
		edi_cha2f=(EditText)  getView().findViewById(R.id.edit_Cha2F);
		edi_cha2f.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*30);
		edi_cha2f.setTextScaleX((float) rateX);
		
		edi_cha2v=(EditText)  getView().findViewById(R.id.edit_Cha2V);
		edi_cha2v.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*30);
		edi_cha2v.setTextScaleX((float) rateX);
		
		edi_cha3v=(EditText)  getView().findViewById(R.id.edit_Cha3V);
		edi_cha3v.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*30);
		edi_cha3v.setTextScaleX((float) rateX);
		
		edi_cha3f=(EditText)  getView().findViewById(R.id.edit_Cha3F);
		edi_cha3f.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*30);
		edi_cha3f.setTextScaleX((float) rateX);
		
		edi_cha4f=(EditText)  getView().findViewById(R.id.edit_Cha4F);
		edi_cha4f.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*30);
		edi_cha4f.setTextScaleX((float) rateX);
		
		edi_cha4v=(EditText)  getView().findViewById(R.id.edit_Cha4V);
		edi_cha4v.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*30);
		edi_cha4v.setTextScaleX((float) rateX);
			
		che_cha1=(CheckBox)  getView().findViewById(R.id.set_chan1);
		che_cha1.setWidth((int) (150*rateX));
		che_cha1.setHeight((int) (40*rateY));
		che_cha1.setTextSize(TypedValue.COMPLEX_UNIT_PX , 20*rateY);
//		che_cha1.setTextScaleX(rateX);
		
		che_cha2=(CheckBox)  getView().findViewById(R.id.set_chan2);
		che_cha2.setWidth((int) (150*rateX));
		che_cha2.setHeight((int) (40*rateY));
		che_cha2.setTextSize(TypedValue.COMPLEX_UNIT_PX , 20*rateY);
//		che_cha2.setTextScaleX(rateX);
		
		che_cha3=(CheckBox)  getView().findViewById(R.id.set_chan3);
		che_cha3.setWidth((int) (150*rateX));
		che_cha3.setHeight((int) (40*rateY));
		che_cha3.setTextSize(TypedValue.COMPLEX_UNIT_PX , 20*rateY);
//		che_cha3.setTextScaleX(rateX);
		
		che_cha4=(CheckBox)  getView().findViewById(R.id.set_chan4);
		che_cha4.setWidth((int) (150*rateX));
		che_cha4.setHeight((int) (40*rateY));
		che_cha4.setTextSize(TypedValue.COMPLEX_UNIT_PX , 20*rateY);
//		che_cha4.setTextScaleX(rateX);
		
		txt_PID=(TextView)  getView().findViewById(R.id.set_PID);
		txt_PID.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
		txt_PID.setTextScaleX((float) rateX);
		
		
		txt_rbtnP=(TextView)  getView().findViewById(R.id.text_rbtnP);
		txt_rbtnP.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
		txt_rbtnP.setTextScaleX((float) rateX);
		rgroup_CHOSE=(RadioGroup)  getView().findViewById(R.id.RadioGroup3);
		
		txt_ss1=(TextView)  getView().findViewById(R.id.text_ss1);
		txt_ss1.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
		txt_ss1.setTextScaleX((float) rateX);
		rgroup_SING =(RadioGroup)  getView().findViewById(R.id.RadioGroup1);
		txt_ss2=(TextView) getView().findViewById(R.id.text_ss2);
		txt_ss2.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
		txt_ss2.setTextScaleX((float) rateX);
		rgroup_COSG =(RadioGroup) getView(). findViewById(R.id.RadioGroup2);
		
		
    	rbtn_sin=(RadioButton) getView(). findViewById(R.id.rb1_sin);
    	rbtn_sin.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
    	rbtn_sin.setTextScaleX((float) rateX);
    	rbtn_cos=(RadioButton)  getView().findViewById(R.id.rb2_cos);  
    	rbtn_cos.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
    	rbtn_cos.setTextScaleX((float) rateX);
    	rbtn_chip1=(RadioButton)  getView().findViewById(R.id.rb1_chip);
    	rbtn_chip1.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
    	rbtn_chip1.setTextScaleX((float) rateX);
    	rbtn_chip2=(RadioButton)  getView().findViewById(R.id.rb2_chip);
    	rbtn_chip2.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
    	rbtn_chip2.setTextScaleX((float) rateX);
    	rbtn_pro1=(RadioButton)  getView().findViewById(R.id.set_pro1);
    	rbtn_pro1.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
    	rbtn_pro1.setTextScaleX((float) rateX);
    	rbtn_pro2=(RadioButton)  getView().findViewById(R.id.set_pro2);
    	rbtn_pro2.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
    	rbtn_pro2.setTextScaleX((float) rateX);
		
		txt_P=(TextView) getView(). findViewById(R.id.text_kp1);
		txt_P.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
		txt_P.setTextScaleX((float) rateX);
		edi_P=(EditText)  getView().findViewById(R.id.edit_P);
		edi_P.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*30);
		edi_P.setTextScaleX((float) rateX);
		txt_I=(TextView)  getView().findViewById(R.id.text_ki1);
		txt_I.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
		txt_I.setTextScaleX((float) rateX);
		edi_I=(EditText)  getView().findViewById(R.id.edit_I);
		edi_I.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*30);
		edi_I.setTextScaleX((float) rateX);
		txt_D=(TextView)  getView().findViewById(R.id.text_kd1);
		txt_D.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
		txt_D.setTextScaleX((float) rateX);
		edi_D=(EditText)  getView().findViewById(R.id.edit_D);
		edi_D.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*30);
		edi_D.setTextScaleX((float) rateX);
		txt_point=(TextView)  getView().findViewById(R.id.text_setp);
		txt_point.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
		txt_point.setTextScaleX((float) rateX);
		edi_point=(EditText)  getView().findViewById(R.id.edit_point);
		edi_point.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*30);
		edi_point.setTextScaleX((float) rateX);
		txt_shift=(TextView)  getView().findViewById(R.id.LED);
		txt_shift.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
		txt_shift.setTextScaleX((float) rateX);
		edi_speed=(EditText)  getView().findViewById(R.id.edit_Shiftspeed);
		edi_speed.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*30);
		edi_speed.setTextScaleX((float) rateX);
		txt_pga=(TextView)  getView().findViewById(R.id.text_pga1);
		txt_pga.setTextSize(TypedValue.COMPLEX_UNIT_PX , rateY*20);
		txt_pga.setTextScaleX((float) rateX);
		spi_pga=(Spinner)  getView().findViewById(R.id.Spi_pga);
		
		
				
	   
	}
    //按照与标准分辨率1024x700进行缩放
	public  void getSizeRate(Context context) {
		
       DisplayMetrics dm = new DisplayMetrics();
       WindowManager windowManager = (WindowManager) context
               .getSystemService(Context.WINDOW_SERVICE);
       windowManager.getDefaultDisplay().getMetrics(dm);
       int screenHeight = dm.heightPixels;  //目前真机可用编辑的屏幕高度
       int screenwidth = dm.widthPixels;    //目前真机可用编辑的屏幕宽度
       // screenWidth = screenWidth > screenHeight ? screenWidth :
       // screenHeight;
       rateY =  (float) screenHeight / 700;
       rateX =  (float) screenwidth / 1024;          
   }
}
