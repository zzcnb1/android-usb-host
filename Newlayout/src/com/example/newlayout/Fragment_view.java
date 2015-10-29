package com.example.newlayout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class Fragment_view extends BaseFragment{
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    View view2,view3,view4;
    SurfaceView view1;
    LinearLayout layout2;
	Fragment fragment_v1=new Fragment_view1();
	Fragment fragment_v2=new Fragment_view2();
	Fragment fragment_v3=new Fragment_view3();
	Fragment fragment_v4=new Fragment_view4();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub	
		 return inflater.inflate(R.layout.fragment_view, container, false);  
	
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	
		view1=(Draw_first) this.getActivity().findViewById(R.id.view_chan1);
		view2=(Draw_second)this.getActivity().findViewById(R.id.view_chan2);
		view3=(Draw_third)this.getActivity().findViewById(R.id.view_chan3);
		view4=(Draw_forth)this.getActivity().findViewById(R.id.view_chan4);
		layout2=(LinearLayout) getView().findViewById(R.id.layout_chan2);
		ViewTreeObserver vto = view1.getViewTreeObserver();
		
		//获取当前view的高度和宽度
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {  
		    public boolean onPreDraw() {  		       
		            Data_RW.view_height = view1.getMeasuredHeight();  
		            Data_RW.view_width=view1.getMeasuredWidth(); 		        
		            return true;  
		    }  
		});  
//		Data_RW.view_width=view1.getWidth();
//		Data_RW.view_height=view1.getHeight();
		view1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 fragmentManager = getFragmentManager();
				 fragmentManager.beginTransaction()
				                   .replace(R.id.content_frame, fragment_v1)
				                   .commit();
			}
		});
		view2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 fragmentManager = getFragmentManager();
				 fragmentManager.beginTransaction()
				                   .replace(R.id.content_frame, fragment_v2)
				                   .commit();
			}
		});
        view3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 fragmentManager = getFragmentManager();
				 fragmentManager.beginTransaction()
				                   .replace(R.id.content_frame, fragment_v3)
				                   .commit();
			}
		});
        view4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 fragmentManager = getFragmentManager();
				 fragmentManager.beginTransaction()
				                   .replace(R.id.content_frame, fragment_v4)
				                   .commit();
			}
		});
	}


//	接收到activity的通知事件，开始更新各个视图
	
	@Subscribe
	public void onEvent(MyEvent eventData) {
		// TODO Auto-generated method stub
		int type = eventData.eventType;
        if (type == 1) {
            Data_RW.bool_draw=true;
        }
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Data_RW.bool_draw=false;
	}
}
