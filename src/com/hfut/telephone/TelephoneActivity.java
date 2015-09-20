package com.hfut.telephone;

import com.hfut.service.ConnectionService;
import com.yzxdemo.action.UIDfineAction;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TelephoneActivity extends Activity {
	
	public Button btn;
	public Button btn_derect;
	private String sid="e2c003dbb9ee05648d6b8e1ad194c586";
	private String sid_pwd="549889c420e53ff35545b21f563049b9";
	private String cliend_id="73572030592744";
	private String cliend_pwd="3132e947";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_telephone);
		startService(new Intent(this, ConnectionService.class));
		btn_derect=(Button) findViewById(R.id.btn_derect);
		btn=(Button) findViewById(R.id.btn_call);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UIDfineAction.ACTION_LOGIN);
				intent.putExtra("sid", sid);
				intent.putExtra("sid_pwd", sid_pwd);
				intent.putExtra("cliend_id", cliend_id);
				intent.putExtra("cliend_pwd", cliend_pwd);
				sendBroadcast(intent);
			}
		});
		btn_derect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TelephoneActivity.this, AudioConverseActivity.class);
            	intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				intent.putExtra("call_phone", "18256072816");
				intent.putExtra("call_type", 0);
				startActivity(intent);
			}
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.telephone, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
