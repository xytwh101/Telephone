package com.hfut.telephone;

import com.hfut.deskclock.DeskClockMainActivity;
import com.hfut.service.ConnectionService;
import com.yzxdemo.action.UIDfineAction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
public class MainActivity extends Activity {
	private String sid="e2c003dbb9ee05648d6b8e1ad194c586";
	private String sid_pwd="549889c420e53ff35545b21f563049b9";
	private String cliend_id="73572030592744";
	private String cliend_pwd="3132e947";
	//73572030592743 e8d4a1d3
	//73572030592744 3132e947
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		startService(new Intent(this, ConnectionService.class));
		SharedPreferences preferences = getSharedPreferences("Role", MODE_PRIVATE);
    	String role=preferences.getString("role", "1");
    	Log.e("角色", role);
	    int a=Integer.valueOf(role);
	    if(a==1){
	    	setContentView(R.layout.activity_main_all);           	    	
	    }
	    if(a==2){
	    	setContentView(R.layout.activity_main_chirld);           	    	
	    }
	    if(a==3){
	    	setContentView(R.layout.activity_main_chirld);     	    	
	    }
	    if(a==4){
	    	setContentView(R.layout.activity_main_parent);   	    	
	    }
	    if(a==5){
	    	setContentView(R.layout.activity_main_parent); 
	    }
	    if(a==6){
	    	setContentView(R.layout.activity_main_doctor);
	    }
	    
	    
	    Intent intent = new Intent(UIDfineAction.ACTION_LOGIN);
		intent.putExtra("sid", sid);
		intent.putExtra("sid_pwd", sid_pwd);
		intent.putExtra("cliend_id", cliend_id);
		intent.putExtra("cliend_pwd", cliend_pwd);
		sendBroadcast(intent);
		
	}
	public void remote(View view){
		
		Intent intent = new Intent(UIDfineAction.ACTION_LOGIN);
		intent.putExtra("sid", sid);
		intent.putExtra("sid_pwd", sid_pwd);
		intent.putExtra("cliend_id", cliend_id);
		intent.putExtra("cliend_pwd", cliend_pwd);
		sendBroadcast(intent);
	}
	//亲情电话
	public void myPhone(View view){
		Intent intent = new Intent(MainActivity.this, AudioConverseActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.putExtra("call_client", "73572030592743");
		intent.putExtra("call_type", 1);
		startActivity(intent);
	}
    //视频通话
	public void videoPhone(View view){
		Intent intent = new Intent(MainActivity.this, VideoConverseActivity.class); 
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.putExtra("phoneNumber", "73572030592743");
		startActivity(intent);
		
	}
	//通讯录
	public void adressBook(View view){
			
	}
	//紧急电话
	public void urgencyPhone(View view){
		Intent intent = new Intent(MainActivity.this, AudioConverseActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intent.putExtra("call_phone", "18256072816");
		intent.putExtra("call_type", 0);
		startActivity(intent);
	}
	//紧急汇报
	public void urgencyReport(View view){
		Intent intent = new Intent(MainActivity.this, CallMainActivity.class);
		startActivity(intent);
	}
	//我的医生
	public void myDoctor(View view){
		Log.e("asdf", "是队服");
		Intent intent = new Intent(MainActivity.this, MyDoctorActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}
	//作息时间
	public void restTime(View view){
		Intent intent = new Intent(MainActivity.this, DeskClockMainActivity.class);
		startActivity(intent);
	}
	//健康档案
	public void healthRecord(View view){
		Intent intent = new Intent(MainActivity.this, HealthRecordActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}
	//我的角色
	public void myRole(View view){
		Intent intent = new Intent(MainActivity.this, MyRoleActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}
	//通用设置
	public void setting(View view){
		Intent intent = new Intent(MainActivity.this, SettingActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}
	//关于软件
	public void about(View view){
		Intent intent = new Intent(MainActivity.this, AboutActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}
	//导航
	public void GPS(View view){
		
	}
	
}
