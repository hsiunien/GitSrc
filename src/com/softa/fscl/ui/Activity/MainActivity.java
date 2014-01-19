package com.softa.fscl.ui.Activity;

import com.softa.fscl.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private ImageView imageView1;
	ImageButton imageButton2,imageButton3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 
	     imageView1=(ImageView)findViewById(R.id.imageview1);
		 imageButton2=(ImageButton)findViewById(R.id.imageview2);
		 imageButton2.setOnClickListener(this);
		 imageButton3=(ImageButton)findViewById(R.id.imageview3);
		 imageButton3.setOnClickListener(this);
		
	}



	@Override
	public void onClick(View v) {
		Intent intent=null;
		switch (v.getId()) {
		case R.id.imageview2:   
			intent=new Intent(this,HomeActivity.class);
			startActivity(intent);
			break;
		case R.id.imageview3:   
			intent=new Intent(this,HomeActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}

}
