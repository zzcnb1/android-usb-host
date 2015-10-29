package com.example.newlayout;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Draw_Cha3 extends SurfaceView implements SurfaceHolder.Callback{
	    private int unitX,unitY;
	    int offset=0;
	    private int GB,Ncolor;
	    private boolean isRun ;
	    private SurfaceHolder holder;
	    private RefreshThread thread;
		public Draw_Cha3(Context context)
		{
			super(context);
			// TODO Auto-generated constructor stub
			holder=this.getHolder();
		    holder.addCallback(this);
		}
		public Draw_Cha3(Context context, AttributeSet attrs)
		{
			super(context, attrs);
			holder=this.getHolder();
		    holder.addCallback(this);
		    thread = new  RefreshThread(holder, getContext());  
		}
		public void dodraw(Canvas canvas)
		{	
			unitX=Data_RW.view_width/250;
			unitY=Data_RW.view_height/32; 
//			Paint paint = new Paint();
//			paint.setColor(Color.GREEN);
//			paint.setStrokeWidth(20);
//			canvas.drawLine(0, 0, Data_RW.view_width, Data_RW.view_height, paint);
			DrawData(Data_RW.CHAlist1,canvas);
			
		}
	    
		public void DrawData(ArrayList<Integer> data,Canvas canvas) 
		{
			Paint paint = new Paint();
			int X,Y;
			//确定数组中各个值的范围，只画出0-2239范围内数据
			for (int s = 0; s < 8000; s++) 
			{			
				GB=data.get(s);//数据大小
			    X=s/32;
				Y=s%32;
				//int offset=EtestActivity.Yoffset[X];
				Ncolor=GB/Data_RW.unitcolor;
				paint.setColor(0xfff00000+Ncolor*0X000101);
				//if((Y+offset)>Data_RW.view_height)判断Y轴偏移量，再画图 
				
				canvas.drawRect(new Rect(X * unitX, (Y+offset)* unitY, (X+ 1) *unitX,(Y+offset+ 1) * unitY), paint);
			    
			}
		}
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			isRun = true;
			clearDraw();
			thread.start(); 
		}
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			isRun=false;
		}
		public void clearDraw() {
			// TODO Auto-generated method stub
			Canvas canvas = null;  
	        try {  
	            canvas = holder.lockCanvas(null);  
	            canvas.drawColor(Color.BLACK);  
	        }catch (Exception e) {  
	            // TODO: handle exception  
	        }finally {  
	            if(canvas != null) {  
	                holder.unlockCanvasAndPost(canvas);  
	            }  
	        }  
		}  
		 private class RefreshThread  extends Thread{
			SurfaceHolder surfaceHolder;  
		    Context context; 
			public RefreshThread(SurfaceHolder surfaceHolder,Context context){
				 this.surfaceHolder = surfaceHolder;  
		         this.context = context;  
		         //isRun= false; 
			}
			@Override  
		     public void run() {  
				 int icount=0;//surfaceview的双缓冲机制，第一次绘图要绘制2次才能显示
		         while(isRun){  
		      //  	   clearDraw();
		        	   
		        	   Canvas canvas = null;  
		        	   synchronized (surfaceHolder){
				           if(Data_RW.bool_draw3){ 
				        	  // clearDraw();
				        	   while(icount<=2){
						    	   canvas=surfaceHolder.lockCanvas();
						    	   dodraw(canvas); 
						    	   surfaceHolder.unlockCanvasAndPost(canvas);
						    	   icount++;
				        	   }
				        //	   第一次显示完成后，每次都更新，不用画2次
				        	   if(icount>2){
				        		   canvas=surfaceHolder.lockCanvas();
						    	   dodraw(canvas); 
						    	   surfaceHolder.unlockCanvasAndPost(canvas);
				        	   }
				    	   }
				       }
		        	   if(canvas!=null){   
		        		   Data_RW.bool_draw3=false;
		        	   }      
		          }  
		     }
		}


}
