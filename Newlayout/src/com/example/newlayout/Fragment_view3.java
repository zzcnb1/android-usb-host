package com.example.newlayout;

import de.greenrobot.event.Subscribe;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class Fragment_view3 extends Fragment{
	private View view;
	private SurfaceView view3;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment_view3, container, false);
        return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		view3=(SurfaceView) this.getActivity().findViewById(R.id.view_3);
		ViewTreeObserver vto = view3.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {  
		    public boolean onPreDraw() {  		       
		            Data_RW.view_height = view3.getMeasuredHeight();  
		            Data_RW.view_width=view3.getMeasuredWidth(); 		        
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
            Data_RW.bool_draw3=true;
        }
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Data_RW.bool_draw3=false;
	}
}
