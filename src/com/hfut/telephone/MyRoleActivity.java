package com.hfut.telephone;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;

public class MyRoleActivity extends Activity implements OnClickListener{
	
	private Spinner spinner_myrole;
	private String  myrole;
	private TextView myRole_saved;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_role);
		spinner_myrole=(Spinner) findViewById(R.id.spinner_myrole);
		myRole_saved=(TextView) findViewById(R.id.myRole_saved);
		
		//Log.e("选中值", myrole);
		myRole_saved.setOnClickListener(this);
	}
	
	
	public void back(View view){
		/*Intent intent = new Intent(MyRoleActivity.this, MainActivity.class); 
		startActivity(intent);*/
		finish();
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.myRole_saved:
			myrole=(String) spinner_myrole.getSelectedItem();
			Log.e("选中值", myrole);
			SharedPreferences preferences=getSharedPreferences("Role", MODE_PRIVATE);
			String role=preferences.getString("role", "1");
			Editor editor = preferences.edit();
		  /*1   全部
			2   儿子
			3   女儿
			4   父亲
			5   母亲
			6   医生
			*/
			if(myrole.equals("默认")){
				role="1";
			}
			if(myrole.equals("儿子")){
				role="2";
			}
			if(myrole.equals("女儿")){
				role="3";			
			}
			if(myrole.equals("父亲")){
				role="4";
			}
			if(myrole.equals("母亲")){
				role="5";
			}
			if(myrole.equals("医生")){
				role="6";
			}
			editor.putString("role", role);
			editor.commit();
			builder();
			break;
		}
		
	}
	
	public void builder(){
		AlertDialog.Builder builder =  new AlertDialog.Builder(MyRoleActivity.this);
		builder.setTitle("提示");
		builder.setMessage("是否确定将角色修改为:"+myrole);
	    //显示，设置按钮
	    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
	    	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			    
				
				dialog.dismiss();
				Intent intent=new Intent(MyRoleActivity.this,MainActivity.class);
				startActivity(intent);
			}
			
		});
	    builder.setNegativeButton("取消", null);
	    builder.create().show();
	}
	
}
