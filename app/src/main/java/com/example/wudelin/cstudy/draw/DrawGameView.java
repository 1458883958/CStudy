package com.example.wudelin.cstudy.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;

public class DrawGameView extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder holder;
	private SurfaceThread thread;

	
	WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

	int screenW = wm.getDefaultDisplay().getWidth();
	int screenH = wm.getDefaultDisplay().getHeight();
	//private Canvas canvas;

	public SurfaceThread getThread() {
		return thread;
	}


	public DrawGameView(Context context) {
		super(context);
		init();
	}

	public DrawGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		holder = getHolder();
		

		if (!isInEditMode()) {
			// 造成错误的代码段
			setZOrderOnTop(true);// 使surfaceview放到最顶层
		}

		//setZOrderMediaOverlay(true);
		holder.setFormat(PixelFormat.TRANSLUCENT);// 使窗口支持透明度
		holder.addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread = new SurfaceThread();
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	class SurfaceThread extends Thread {


        public boolean isRun = true;

		private Path path;
		private Paint paint;
		//private Canvas canvas;
		private ArrayList<DrawStep> steps = new ArrayList<DrawStep>();
		private ArrayList<Path> paths = new ArrayList<Path>();
		public void reStart(){
			steps.clear();
			paths.clear();
		}

		public SurfaceThread() {
		    //canvas = new Canvas();
			path = new Path();
			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(Color.BLUE);
			paint.setStrokeWidth(10);
			paint.setStyle(Paint.Style.STROKE);

		}
		public Paint getPaint() {
			return paint;
		}
		@Override
		public void run() {
			super.run();
			while (isRun) {
				draw();
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void addStep(DrawStep step) {
			steps.add(step);
		}

		public void draw() {
			Canvas canvas=null;
			try {
				canvas = holder.lockCanvas();// 锁整个画布
				canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);// 绘制透明色
				if (canvas != null) {
					while (steps.size() > 0) {
						DrawStep step = steps.remove(0);
						if (step.getType() == DrawStep.MoveTo) {
							path.moveTo(screenW * ((float) step.getxP() / 10000),
									screenH * ((float) step.getyP() / 10000));
						} else if (step.getType() == DrawStep.LineTo) {
							path.lineTo(screenW * ((float) step.getxP() / 10000),
									screenH * ((float) step.getyP() / 10000));
						} else if (step.getType() == DrawStep.UP) {
							Path path1 = new Path(path);
							paths.add(path1);
						}
					}

					for (int i = 0; i < paths.size(); i++) {
						canvas.drawPath(paths.get(i), paint);
					}
					canvas.drawPath(path, paint);
					holder.unlockCanvasAndPost(canvas);//// 解除锁定，并提交修改内容
				}
			} catch (Exception e) {
			}
		}
	}

}
