package com.mapgis.phone.zxing;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.mapgis.phone.zxing.camera.CameraManager;
import com.mapgis.phone.zxing.decoding.CaptureActivityHandler;
import com.mapgis.phone.zxing.decoding.InactivityTimer;
import com.mapgis.phone.zxing.decoding.CaptureActivityHandler.State;
import com.mapgis.phone.zxing.view.ViewfinderView;
import com.softa.fscl.R;
import com.softa.fscl.models.Commodity;
import com.softa.fscl.ui.Activity.CommodityDetailActivity;
 

public class CaptureActivity extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private TextView txtResult;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private boolean vibrate;

	/** Called when the activity is first created. */
	@Override	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.zxing_main);
		CameraManager.init(getApplication());

		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		viewfinderView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				handler.setState(State.SUCCESS);
				
				handler.restartPreviewAndDecode();
				System.out.println("CaptureActivity--->ViewfinderView.setOnclickListener()");
			}
		});
		txtResult = (TextView) findViewById(R.id.txtResult);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
//		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
//		viewfinderView.drawResultBitmap(barcode);
		 playBeepSoundAndVibrate();
		// txtResult.setText("扫瞄成功"+obj.getText());
		 System.out.println("扫瞄结果"+obj.getText());
		 String result=obj.getText();
		 Gson gson=new Gson();
		 try {
			 Commodity comm=gson.fromJson(result, Commodity.class);
			 Intent intent=new Intent(this, CommodityDetailActivity.class);
			 intent.putExtra("commodity", comm);
			
			 startActivity(intent);
			  this.finish();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "无法识别的二维码", 3000).show();
			 this.finish();
		}
		// this.onPause();
		// onResume();
		// this.finish();
//		 Intent	intent = new Intent();
//		 intent.setClass(CaptureActivity.this, ZXing.class);
//		 intent.putExtra("result", obj.getText());
//		 setResult(Activity.RESULT_OK, intent);
//		 CaptureActivity.this.finish();
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	@Override
	public void onBackPressed() {
		if(txtResult.getText().equals("")){
			Dialog		dialog = new AlertDialog.Builder(CaptureActivity.this)
			.setMessage("确定要退出吗")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					CaptureActivity.this.finish();
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			})
			.create();
			dialog.show();
		}else{
			txtResult.setText("");
			this.onPause();
			this.onResume();
		}

}

}
	