package com.hfut.telephone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyDoctorActivity extends Activity implements OnClickListener{

	public Button btn_myDoctor_list,btn_myDoctor_medicalRemind,btn_myDoctor_careRemind,btn_myDoctor_call,btn_myDoctor_setting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_doctor);
		btn_myDoctor_list=(Button) findViewById(R.id.btn_myDoctor_list);
		btn_myDoctor_medicalRemind=(Button) findViewById(R.id.btn_myDoctor_medicalRemind);
		btn_myDoctor_careRemind=(Button) findViewById(R.id.btn_myDoctor_careRemind);
		btn_myDoctor_call=(Button) findViewById(R.id.btn_myDoctor_call);
		btn_myDoctor_setting=(Button) findViewById(R.id.btn_myDoctor_setting);
		btn_myDoctor_list.setOnClickListener(this);
		btn_myDoctor_medicalRemind.setOnClickListener(this);
		btn_myDoctor_careRemind.setOnClickListener(this);
		btn_myDoctor_call.setOnClickListener(this);
		btn_myDoctor_setting.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_myDoctor_list:
			 startActivity(new Intent(MyDoctorActivity.this,MyDoctorListActivity.class));
	         break;
		case R.id.btn_myDoctor_medicalRemind:
			 startActivity(new Intent(MyDoctorActivity.this,MyDoctorMedicalRemindActivity.class));	 	
			 break;
		case R.id.btn_myDoctor_careRemind:
			 startActivity(new Intent(MyDoctorActivity.this,MyDoctorCareRemindActivity.class));	
	         break;
		case R.id.btn_myDoctor_call:
			 
	         break;
		case R.id.btn_myDoctor_setting:
			 startActivity(new Intent(MyDoctorActivity.this,MyDoctorSettingActivity.class));	
	         break;
		
		
		
		}
	}
	
	
}
