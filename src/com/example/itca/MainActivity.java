package com.example.itca;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity{ //implements SurfaceHolder.Callback{

	Camera camera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	TextView debugtxt1;
	TextView debugtxt2;
	TextView debugtxt3;
	TextView debugtxt4;
	TextView debugtxt11;
	TextView debugtxt21;

	private final String tag = "VideoServer";

	Button start, stop;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Debug Buttons XML hook
		start = (Button) findViewById(R.id.btn_start);
		start.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				start_camera();
			}
		});
		stop = (Button) findViewById(R.id.btn_stop);
		stop.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				stop_camera();
			}
		});

		// Debug TextView XML hook
		debugtxt1 = (TextView) findViewById(R.id.textView1);
		debugtxt2 = (TextView) findViewById(R.id.textView2);
		debugtxt3 = (TextView) findViewById(R.id.textView3);
		debugtxt4 = (TextView) findViewById(R.id.textView4);
		debugtxt11 = (TextView) findViewById(R.id.textView5);
		debugtxt21 = (TextView) findViewById(R.id.textView6);
		
		// Debug SurfaceView XML hook
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		surfaceHolder = surfaceView.getHolder();
		//surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	/*
    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    
    @Override
    protected void onPause() {
        super.onPause();
		try{
			Camera.open();
		} catch(RuntimeException e){
	        camera.stopPreview();
			camera.release();
		}
        // Another activity is taking focus (this activity is about to be "paused").
    }
    
    @Override
    protected void onStop() {
        super.onStop();
		try{
			Camera.open();
		} catch(RuntimeException e){
	        camera.stopPreview();
			camera.release();
		}
        // The activity is no longer visible (it is now "stopped")
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
		try{
			Camera.open();
		} catch(RuntimeException e){
	        camera.stopPreview();
			camera.release();
		}
        // The activity is about to be destroyed.
    }
	*/

	private void start_camera() {
		try {
			camera = Camera.open();
		} catch (RuntimeException e) {
			Log.e(tag, "init_camera: " + e);
			return;
		}
		Camera.Parameters param;
		param = camera.getParameters();
		// modify parameter
		//param.setPreviewFpsRange(TRIM_MEMORY_RUNNING_LOW, TRIM_MEMORY_COMPLETE);
		param.setPreviewSize(param.getPreviewSize().width, param.getPreviewSize().height);
		camera.setParameters(param);
		try {
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
		} catch (Exception e) {
			Log.e(tag, "init_camera: " + e);
			return;
		}
	}

	private void stop_camera() {
		camera.stopPreview();
		camera.release();
	}
	
	/*
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}
	*/
}
