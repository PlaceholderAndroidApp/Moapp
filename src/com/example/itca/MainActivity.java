//TEST
package com.example.itca;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	Camera camera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	TextView debugtxt1;
	TextView debugtxt2;
	TextView debugtxt11;
	TextView debugtxt21;
	Button captureButton, start;
	Context app_context;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		app_context = this;
		
		start = (Button) findViewById(R.id.btn_start);
		
		// Start and Stop button function
		start.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				if(safeCameraOpen()){
					Toast.makeText(app_context, "Success", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(app_context, "Failed", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		// Debug Buttons XML hook
		captureButton = (Button) findViewById(R.id.btn_capture);

		// Capture button function
		captureButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				camera.takePicture(new ShutterCallback() {

					@Override
					public void onShutter() {
						//toast.setText("Toast showed successfully");
						//toast.show();
					}
				}, new PictureCallback() {

					// RAW DATA leave it blank for now
					@Override
					public void onPictureTaken(byte[] data, Camera camera) {
						// TODO Auto-generated method stub
					}
				}, null, new PictureCallback() {

					// JPEG DATA
					@Override
					public void onPictureTaken(byte[] data, Camera camera) {
						
						File temp_picture_dir = new File(Environment.DIRECTORY_DOWNLOADS, "test");
						try{
							FileOutputStream fos = new FileOutputStream(temp_picture_dir);
							fos.write(data);
							fos.close();
						} catch(Exception e){
							//toast.setText("Toast showed successfully, but something is wrong with the picture");
							//toast.show();
						}
						
					}
				});
				camera.startPreview();
			}
		});
		
		
		// Debug TextView XML hook
		debugtxt1 = (TextView) findViewById(R.id.textView1);
		debugtxt2 = (TextView) findViewById(R.id.textView2);
		debugtxt11 = (TextView) findViewById(R.id.textView5);
		debugtxt21 = (TextView) findViewById(R.id.textView6);

		// Debug SurfaceView XML hook
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);

		// SurfaceHolder stuffs
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceHolder.setFixedSize(540, 960);

		/*
		if(safeCameraOpen()){
			Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
		}
		*/
	}
	
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
	
	/*
	@Override
	protected void onPause() {
		super.onPause();
		stop_camera_secure();
		// Another activity is taking focus (this activity is about to be
		// "paused").
	}

	@Override
	protected void onStop() {
		super.onStop();
		stop_camera_secure();
		// The activity is no longer visible (it is now "stopped")
	}
	*/
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (camera!=null)
	    {
	        camera.stopPreview();
	        camera.release();
	        camera=null;
	    }
		// The activity is about to be destroyed.
	}
	
	private boolean safeCameraOpen() {
	    boolean qOpened = false;
	    try {
	        releaseCameraAndPreview();
	        camera = Camera.open();
	        qOpened = (camera != null);
	    } catch (Exception e) {
	        Log.e(getString(R.string.app_name), "failed to open Camera");
	        e.printStackTrace();
	    }

		
		camera.setDisplayOrientation(90);
		Camera.Parameters param;
		param = camera.getParameters();
		// modify parameter
		// param.setPreviewFpsRange(TRIM_MEMORY_RUNNING_LOW,
		// TRIM_MEMORY_COMPLETE);
		param.setPreviewSize(param.getPreviewSize().width,
				param.getPreviewSize().height);
		camera.setParameters(param);
		
		try {
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
		} catch (IOException e) {
			Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
		}
		
		// Debug stuff
		debugtxt1.setText("Width: ");
		debugtxt11.setText("" + param.getPreviewSize().width);
		debugtxt2.setText("Height: ");
		debugtxt21.setText("" + param.getPreviewSize().height);

	    return qOpened;    
	}

	private void releaseCameraAndPreview() {
	    if (camera != null) {
	        camera.release();
	        camera = null;
	    }
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}

}
