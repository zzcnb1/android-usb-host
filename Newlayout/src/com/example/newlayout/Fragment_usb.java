package com.example.newlayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
/*主要处理USB信息，与通信按钮值*/
public class Fragment_usb extends Fragment{
	 private View view;
	 private boolean bool_usb=false;
	/* 表格接收到的USB数据*/
	 private String[]number=new String[]{"1"};
	 private String[]PIDnum=new String[]{"4660"};
	 private String[]VIDnum=new String[]{"17185"};
	 private String[]INTERnum=new String[]{"5"};
	 private String[]ENDPTnum=new String[]{"3"};
	/*service启动意图*/
	
	
	 private int[]imagIDs=new int[]{R.drawable.checkbox_empty,R.drawable.checkbox};
	 Map<String,Object> listitem=new HashMap<String,Object>();
	 List<Map<String,Object>> listItems=new ArrayList<Map<String, Object>>();
	/*打开USB总开关,控制service中的两个线程*/ 
	 public boolean IsOK=false;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    		// TODO Auto-generated method stub
    	view=inflater.inflate(R.layout.fragment_usb, container, false);
        return  view; 
    }
   @Override
    public void onActivityCreated(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onActivityCreated(savedInstanceState); 
        ListView USBlist = (ListView)getView().findViewById(R.id.list_usb); 
   	    for(int i=0;i<number.length;i++)
   	    {   	        	
        	listitem.put("ENDPTnum", ENDPTnum[i]);
            listitem.put("number", number[i]);
            listitem.put("VIDnum", VIDnum[i]);
            listitem.put("PIDnum", PIDnum[i]);
            listitem.put("INTERnum", INTERnum[i]);
            if(bool_usb)
                listitem.put("imgID", imagIDs[i+1]);
            else
            	listitem.put("imgID", imagIDs[i]);
            listItems.add(listitem);
        }
   	    SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity(),listItems,R.layout.simple_item,
   	        		new String[]{"number","PIDnum","VIDnum","INTERnum","ENDPTnum","imgID"},
   	                new int[]{R.id.txt_num,R.id.txt_PIDnum,R.id.txt_VIDnum,R.id.txt_INTERnum,R.id.txt_ENDPTnum,R.id.imgview_usb});
        USBlist.setAdapter(simpleAdapter);
   	    USBlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// imagIDs=new int[]{R.drawable.checkbox};
				ListView USBlist = (ListView)getView().findViewById(R.id.list_usb); 
				if(bool_usb)
				{
					
					bool_usb=false;
					IsOK=false;
					listItems.removeAll(listItems);
					for(int i=0;i<number.length;i++)
		    	    {   	        	
	    	        	listitem.put("ENDPTnum", ENDPTnum[i]);
	    	            listitem.put("number", number[i]);
	    	            listitem.put("VIDnum", VIDnum[i]);
	    	            listitem.put("PIDnum", PIDnum[i]);
	    	            listitem.put("INTERnum", INTERnum[i]);
	    	            listitem.put("imgID", imagIDs[i]);
	    	            listItems.add(listitem);
		    	    }
	    	        SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity(),listItems,R.layout.simple_item,
	    	        		new String[]{"number","PIDnum","VIDnum","INTERnum","ENDPTnum","imgID"},
	    	                new int[]{R.id.txt_num,R.id.txt_PIDnum,R.id.txt_VIDnum,R.id.txt_INTERnum,R.id.txt_ENDPTnum,R.id.imgview_usb});
	    	        
		    	    USBlist.setAdapter(simpleAdapter);
		    	      
				}
				else{
				
				  bool_usb=true;
				  IsOK=true;
				  listItems.removeAll(listItems);
				  for(int i=0;i<number.length;i++)
	    	      {   	        	
	    	        	listitem.put("ENDPTnum", ENDPTnum[i]);
	    	            listitem.put("number", number[i]);
	    	            listitem.put("VIDnum", VIDnum[i]);
	    	            listitem.put("PIDnum", PIDnum[i]);
	    	            listitem.put("INTERnum", INTERnum[i]);
	    	            listitem.put("imgID", imagIDs[i+1]);
	    	            listItems.add(listitem);
	    	      }
	    	       SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity(),listItems,R.layout.simple_item,
	    	        		new String[]{"number","PIDnum","VIDnum","INTERnum","ENDPTnum","imgID"},
	    	                new int[]{R.id.txt_num,R.id.txt_PIDnum,R.id.txt_VIDnum,R.id.txt_INTERnum,R.id.txt_ENDPTnum,R.id.imgview_usb});
	    	        
	    	       USBlist.setAdapter(simpleAdapter);
	    	       
			}
			}
		});
    }
   @Override
   public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	    listItems.removeAll(listItems);
}
}
