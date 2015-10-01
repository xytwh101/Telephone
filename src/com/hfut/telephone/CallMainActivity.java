package com.hfut.telephone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CallMainActivity extends Activity implements OnClickListener{

	
	private Button btn_0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_main);
		
		btn_0=(Button) findViewById(R.id.btn0);
		
		btn_0.setOnClickListener(this);
		SharedPreferences preference=getSharedPreferences("Phone", MODE_PRIVATE);
		String phone_1=preference.getString("phone", "18256072816");
		btn_0.setText(phone_1);
		
		
		
	}
	@Override
	public void onClick(View v) {
		
		final EditText inputServer = new EditText(this);
		AlertDialog.Builder builder =  new AlertDialog.Builder(CallMainActivity.this);
		builder.setTitle("提示");
		
	    //显示，设置按钮
	    builder.setView(inputServer).setPositiveButton("是", new DialogInterface.OnClickListener() {
	    	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			    
				String phone = null;
				phone=inputServer.getText().toString();
				dialog.dismiss();
				SharedPreferences preference=getSharedPreferences("Phone", MODE_PRIVATE);
				
				Editor editor = preference.edit();
				editor.putString("phone", phone);
				editor.commit();
				
				
				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));
				startActivity(intent);
			}
			
		});
	    builder.setNegativeButton("取消", null);
	    builder.create().show();
	
	
	}

	
	
	
}
