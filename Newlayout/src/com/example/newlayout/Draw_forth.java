package com.example.newlayout;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Draw_forth extends SurfaceView implements SurfaceHolder.Callback{
	 private int unitX,unitY;
	    int offset=0;
	    private int GB,Ncolor;
	    private boolean isRun ;
		private SurfaceHolder holder;
		private RefreshThread thread;
		public Draw_forth(Context context)
		{
			super(context);
			// TODO Auto-generated constructor stub
			holder=this.getHolder();
		    holder.addCallback(this);
		}
		public Draw_forth(Context context, AttributeSet attrs)
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
			DrawData(Data_RW.CHAlist4,canvas);
			
		}
		/*//每个通道250组数据250x32
		private void Drawevery(ArrayList<Integer> list, Canvas canvas,
				Paint paint) {
			// TODO Auto-generated method stub
			unitX=(int) (4*MyTabActivity.rateX);
	    	unitY=(int) (18*MyTabActivity.rateY);
	    	DrawData(list,canvas,0,0);
		}
		//四个通道同时显示
	    private void Drawfour(Canvas canvas, Paint paint)
	    {
			// TODO Auto-generated method stub
	    	unitX=(int) (2*MyTabActivity.rateX);
	    	unitY=(int) (9*MyTabActivity.rateY);
	    	Rcet_x1=(int) (492*MyTabActivity.rateX);
	    	Rcet_y1=(int) (270*MyTabActivity.rateY);
	    	Rcet_x2=(int) (532*MyTabActivity.rateX);
	    	Rcet_y2=(int) (310*MyTabActivity.rateY);
	    	Txt_x1=(int) (497*MyTabActivity.rateX);
	    	Txt_y1=(int) (285*MyTabActivity.rateY);
	    	Txt_x2=(int) (517*MyTabActivity.rateX);
	    	Txt_y2=(int) (305*MyTabActivity.rateY);
	    	start_x=(int) (262*MyTabActivity.rateX);
	    	start_y=(int) (33*MyTabActivity.rateY);
			DrawData(MyTabActivity.CHAlist1,canvas,0,0);
			DrawData(MyTabActivity.CHAlist2,canvas,start_x,0);
			DrawData(MyTabActivity.CHAlist3,canvas,0,start_y);
			DrawData(MyTabActivity.CHAlist4,canvas,start_x,start_y);
			paint.setColor(0xffffff00);
			canvas.drawArc(new RectF(Rcet_x1, Rcet_y1, Rcet_x2, Rcet_y2), 180, 90, true, paint);
			paint.setColor(0xffff0000);
			canvas.drawText("1", Txt_x1, Txt_y1, paint);
			
			paint.setColor(0xff00ffff);
			canvas.drawArc(new RectF(Rcet_x1, Rcet_y1, Rcet_x2, Rcet_y2), 270, 90, true, paint);
			paint.setColor(0xffff0000);
			canvas.drawText("2", Txt_x2, Txt_y1, paint);
			
			paint.setColor(0xff00f0f0);
			canvas.drawArc(new RectF(Rcet_x1, Rcet_y1, Rcet_x2, Rcet_y2), 90, 90, true, paint);
			paint.setColor(0xffff0000);
			canvas.drawText("3", Txt_x1, Txt_y2, paint);
			
			paint.setColor(0xffffff00);
			canvas.drawArc(new RectF(Rcet_x1, Rcet_y1, Rcet_x2, Rcet_y2), 0, 90, true, paint);
			paint.setColor(0xffff0000);
			canvas.drawText("4", Txt_x2, Txt_y2, paint);
		}
	    */
	    
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
				 int icount=0;//surfaceview的双缓冲机制，第一次绘图要绘制5次才能显示
		         while(isRun){  	        	   
		        	   Canvas canvas = null;  
		        	   synchronized (surfaceHolder){
				           if(Data_RW.bool_draw){ 
				        	   clearDraw();
				        	   while(icount<=4){
						    	   canvas=surfaceHolder.lockCanvas();
						    	   dodraw(canvas); 
						    	   surfaceHolder.unlockCanvasAndPost(canvas);
						    	   icount++;
				        	   }
				        //	   第一次显示完成后，每次都更新，不用画5次
				        	   if(icount>4){
				        		   canvas=surfaceHolder.lockCanvas();
						    	   dodraw(canvas); 
						    	   surfaceHolder.unlockCanvasAndPost(canvas);
				        	   }
				    	   }
				       }
		        	   if(canvas!=null){   
		        		   Data_RW.bool_draw=false;
		        	   }      
		          }  
		     }
		}
}
