package com.example.newlayout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class Fragment_view1 extends BaseFragment{
	private View view;
	
	private SurfaceView view1;
	private GestureDetector gestureDetector;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
	Fragment fragment_v2=new Fragment_view2();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub		
		view=inflater.inflate(R.layout.fragment_view1, container, false);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	
    	super.onActivityCreated(savedInstanceState);
    	view1=(SurfaceView) this.getActivity().findViewById(R.id.view_1);
        ViewTreeObserver vto = view1.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {  
		    public boolean onPreDraw() {  		       
		            Data_RW.view_height = view1.getMeasuredHeight();  
		            Data_RW.view_width=view1.getMeasuredWidth(); 		        
		            return true;  
		    }  
		});  
    	
    }
    
//	接收到activity的通知事件，开始更新各个视图
	@Subscribe
	public void onEvent(MyEvent eventData) {
		// TODO Auto-generated method stub
		int type = eventData.eventType;
        if (type == 1) {
            Data_RW.bool_draw1=true;
        }
	}
	
   @Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Data_RW.bool_draw1=false;
	}
}
