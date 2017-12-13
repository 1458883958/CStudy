package com.example.wudelin.cstudy.draw;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


import com.example.wudelin.cstudy.R;

public class YouDrawIGuessActivity extends Activity {
    private static DrawGameView drawGameView;

    private int whichColor = 0;
    private SurfaceView surfaceView;
    DisplayMetrics dm = new DisplayMetrics();
    int screenW;
    int screenH;
    private final static int RESULT = 0;
    // 画布
    private Canvas canvas;
    // 缩放后的图片
    private Bitmap bitmap;
    private Paint paint;
    // 缩放后的图片副本
    private Bitmap copyBitmap;
    private Button clean, scanle, hongse;
    //private ImageView imageView;

    public YouDrawIGuessActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sur_layout);

        NetWorkMessage.get_instaance().setActivity(this);
        drawGameView = this.findViewById(R.id.surfa);

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenW = dm.widthPixels;
        screenH = dm.heightPixels;
        clean = this.findViewById(R.id.sclean);
        hongse =  this.findViewById(R.id.hongse);
        scanle =  this.findViewById(R.id.scanle);
        surfaceView =  this.findViewById(R.id.surfa);
        //imageView = (ImageView) this.findViewById(R.id.im);

        scanle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method
                NetWorkMessage.get_instaance().closeNetWork();
                YouDrawIGuessActivity.this.finish();

            }
        });

        clean.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                drawGameView.getThread().isRun = false;
                drawGameView.getThread().reStart();
                drawGameView.surfaceCreated(drawGameView.getHolder());

            }
        });
        hongse.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Paint paint = drawGameView.getThread().getPaint();
                Dialog mDialog = new AlertDialog.Builder(YouDrawIGuessActivity.this).setTitle("颜色设置")
                        .setSingleChoiceItems(new String[]{"红色", "绿色", "蓝色"}, whichColor,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        switch (which) {
                                            case 0: {
                                                // 画笔的颜色
                                                paint.setColor(Color.RED);
                                                whichColor = 0;
                                                break;
                                            }
                                            case 1: {
                                                // 画笔的颜色
                                                paint.setColor(Color.GREEN);
                                                whichColor = 1;
                                                break;
                                            }
                                            case 2: {
                                                // 画笔的颜色106
                                                paint.setColor(Color.BLUE);
                                                whichColor = 2;
                                                break;
                                            }
                                        }
                                    }
                                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        }).create();
                mDialog.show();
            }

        });
        drawGameView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (NetWorkMessage.get_instaance().isDrawer()) {
                    // if (true) {
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            addStep(DrawStep.MoveTo, (int) (event.getX() / screenW * 10000),
                                    (int) (event.getY() / screenH * 10000));
                            break;
                        case MotionEvent.ACTION_MOVE:
                            addStep(DrawStep.LineTo, (int) (event.getX() / screenW * 10000),
                                    (int) (event.getY() / screenH * 10000));
                            break;
                        case MotionEvent.ACTION_UP:
                            addStep(DrawStep.UP, 0, 0);
                            break;
                    }
                    return true;
                } else {
                    return false;
                }
            }

        });

    }

    public void addStep(byte type, int xp, int yp) {
        DrawStep step = new DrawStep();
        step.setType(type);
        step.setxP(xp);
        step.setyP(yp);

        NetWorkMessage.get_instaance().sendDrawMsgToServer(step);
        addOneStep(step);
    }

    public void addOneStep(DrawStep step) {
        drawGameView.getThread().addStep(step);
    }

}
