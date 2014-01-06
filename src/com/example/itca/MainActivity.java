//TEST
package com.example.itca;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Camera camera;
	TextView debugtxt1;
	TextView debugtxt2;
	TextView debugtxt11;
	TextView debugtxt21;
	Button captureButton, start;
	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	Context app_context = this;
	
	File temp_picture_path = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
	File temp_picture_filename;
	
	private static final int CAPTURE_IMAGE_REQUEST_CODE = 133;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		start = (Button) findViewById(R.id.btn_start);

		// Start and Stop button function
		start.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				temp_picture_filename = new File(temp_picture_path, "test.jpg");
				intent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(temp_picture_filename));

				startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Image saved to:\n" + Uri.fromFile(temp_picture_filename),
						Toast.LENGTH_LONG).show();
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
				Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
			} else {
				// Image capture failed, advise user
				Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
			}
		}

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

	@Override
	protected void onPause() {
		super.onPause();
		// Another activity is taking focus (this activity is about to be
		// "paused").
	}

	@Override
	protected void onStop() {
		super.onStop();
		// The activity is no longer visible (it is now "stopped")
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// The activity is about to be destroyed.
	}

}
