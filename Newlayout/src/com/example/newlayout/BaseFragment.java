package com.example.newlayout;

import android.app.Fragment;

public abstract class BaseFragment extends Fragment{
	 public BaseFragment() {
	    
	 }

	 public abstract  void onEvent(MyEvent eventData);

}
