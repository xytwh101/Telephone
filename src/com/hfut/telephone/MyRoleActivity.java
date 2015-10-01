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
		
		//Log.e("ѡ��ֵ", myrole);
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
			Log.e("ѡ��ֵ", myrole);
			SharedPreferences preferences=getSharedPreferences("Role", MODE_PRIVATE);
			String role=preferences.getString("role", "1");
			Editor editor = preferences.edit();
		  /*1   ȫ��
			2   ����
			3   Ů��
			4   ����
			5   ĸ��
			6   ҽ��
			*/
			if(myrole.equals("Ĭ��")){
				role="1";
			}
			if(myrole.equals("����")){
				role="2";
			}
			if(myrole.equals("Ů��")){
				role="3";			
			}
			if(myrole.equals("����")){
				role="4";
			}
			if(myrole.equals("ĸ��")){
				role="5";
			}
			if(myrole.equals("ҽ��")){
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
		builder.setTitle("��ʾ");
		builder.setMessage("�Ƿ�ȷ������ɫ�޸�Ϊ:"+myrole);
	    //��ʾ�����ð�ť
	    builder.setPositiveButton("��", new DialogInterface.OnClickListener() {
	    	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			    
				
				dialog.dismiss();
				Intent intent=new Intent(MyRoleActivity.this,MainActivity.class);
				startActivity(intent);
			}
			
		});
	    builder.setNegativeButton("ȡ��", null);
	    builder.create().show();
	}
	
}
