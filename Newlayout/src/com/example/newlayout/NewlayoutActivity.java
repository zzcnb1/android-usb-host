package com.example.newlayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.greenrobot.event.EventBus;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class NewlayoutActivity extends ActionBarActivity {
  
	 
	/*三个fragment（usb，setting，view）参数设置*/
    public Fragment fragment;
    Fragment_home fragment_home;
    Fragment_usb fragment1;
    Fragment_seeting fragment2;
    Fragment_view fragment3;
    Fragment_view1 fragment_view1;
    Fragment_view2 fragment_view2;
    Fragment_view3 fragment_view3;
    Fragment_view4 fragment_view4;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private int[] imageId ;
	private String[] mPlanetTitles;//标题数组
    private DrawerLayout mDrawerLayout;//主布局
    private ListView mDrawerList;//左侧listview
    private CharSequence mTitle;
    static Myservice myService;
    private FrameLayout framelayout;
//   service与activity的接口
    ServiceConnection conn = new ServiceConnection() {
	    @Override
	    public void onServiceDisconnected(ComponentName name) {
	      
	    }
	    
	    @Override
	    public void onServiceConnected(ComponentName name, IBinder service) {
	     /* 返回一个MsgService对象,binder返回service，也可以返回别的值，这里要调用
	    	service里面的方法，所以返回service*/
	        myService = ((Myservice.MsgBinder)service).getService();
	      
	      //注册回调接口来实现view视图的实时更新，此函数应放在fragment_view,1,2,3,4中
//	          发送Eventbus给各fragment，以此更新view画图
	      myService.setOnProgressListener(new UpdateView() {
			
			@Override
			public void Onupdateview() {
				
				// TODO Auto-generated method stub
				 MyEvent myEvent = new MyEvent();
                 myEvent.eventType = 1;
                 EventBus.getDefault().post(myEvent);//发布消息,若fragment的到myEvent开始更新各个视图
			}
		});      
	    }
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newlayout);
		framelayout=(FrameLayout) findViewById(R.id.content_frame);
		framelayout.setBackgroundResource(R.drawable.background);
		
		getWindow().setFormat(PixelFormat.TRANSLUCENT);  
		Data_RW.bool_draw=false;
		Data_RW.bool_draw1=false;
		Data_RW.bool_draw2=false;
		Data_RW.bool_draw3=false;
		Data_RW.bool_draw4=false;//在未进入fragment之前不允许更新视图
		Data_RW.unitcolor=(int) (Math.sqrt( Math.pow(3300,2)+ Math.pow(3300,2))/256);
		
		fragment_view1=new Fragment_view1();
		fragment_view2=new Fragment_view2();
		fragment_view3=new Fragment_view3();
		fragment_view4=new Fragment_view4();
		
		fragment1 = new Fragment_usb();
		fragment2 = new Fragment_seeting();
		fragment3 = new Fragment_view();
		fragment_home=new Fragment_home();
		imageId = new int[] { R.drawable.usb, R.drawable.setting,R.drawable.view};
		drawlistlayout();//抽屉布局的初始化设置
//		启动服务，开启数据接收和数据发送线程
	    Intent intent =new Intent(this,Myservice.class);
	    bindService(intent, conn, Service.BIND_AUTO_CREATE);    
	   
	  //初始化各个通道list
  		for(int init=0;init<8000;init++){
  			Data_RW.CHAlist1.add(0);
  			Data_RW.CHAlist2.add(0);
  			Data_RW.CHAlist3.add(0);
  			Data_RW.CHAlist4.add(0);
  		}		  		
//		Drawusblist();  
	}
	
	
/*抽屉布局的初始化与listview监听事件*/
	private void drawlistlayout() {
		// TODO Auto-generated method stub
		
	    mPlanetTitles = getResources().getStringArray(R.array.items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        List<Map<String, Object>> list=getData();  
        mDrawerList.setAdapter(new MyAdspter(this, list)); 
        
        /*总listview，ArrayAdapter作为接口，填充listview的列表项*/
        // Set the adapter for the list view
//        
        
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener()); 
      
	}

	public List<Map<String, Object>> getData() {
	// TODO Auto-generated method stub
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();  
        for (int i = 0; i < 3; i++) {  
            Map<String, Object> map=new HashMap<String, Object>();  
            map.put("image", imageId[i]);  
            map.put("title", mPlanetTitles[i]);  
            list.add(map);  
        }  
        return list;  
	
}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.newlayout, menu);
		return true;
	}*/

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
//			Object item;
//			item= parent.getItemAtPosition(position);
			//添加对应的fragment
			switch(position)
			{
			   case 0:   fragment=fragment1; getActionBar().setIcon(R.drawable.usb);      break; //USB information
			   case 1:   fragment=fragment2; getActionBar().setIcon(R.drawable.setting);      break; //setting
			   case 2:   fragment=fragment3; getActionBar().setIcon(R.drawable.view);      break; //view
			   default: break;
			}  
//			framelayout.setBackground(null);
			selectItem(position);			
		}
	}
	/** Swaps fragments in the main content view */
	@SuppressLint("NewApi")
	private void selectItem(int position) {

	    // Insert the fragment by replacing any existing fragment
		framelayout.setBackground(null);
	    fragmentManager = getFragmentManager();
	    fragmentManager.beginTransaction()
	                   .replace(R.id.content_frame, fragment)
	                   .show(fragment)
	                   .hide(fragment_home)
	                   .commit();

	    // Highlight the selected item, update the title, and close the drawer
	    mDrawerList.setItemChecked(position, true);
	    setTitle(mPlanetTitles[position]);
	    mDrawerLayout.closeDrawer(mDrawerList);
	}
	@Override
	public void setTitle(CharSequence title) {
	    mTitle = title;
	    getActionBar().setTitle(mTitle);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unbindService(conn);//断开与service的绑定
	    EventBus.getDefault().unregister(this); //注销Eventbus事件 
	   
	}
	
}
