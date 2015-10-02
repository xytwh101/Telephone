package com.hfut.telephone;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.graphics.Color;
import android.widget.TabHost;

public class AddressBookActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_book);
		
		TabHost th=(TabHost)findViewById(R.id.tabhost);
		th.setup();            //��ʼ��TabHost����
		
		//��TabHost������ǩ��Ȼ�����ã����⣯ͼ�꣯��ǩҳ����
		th.addTab(th.newTabSpec("tab1").setIndicator("��ϵ��",getResources().getDrawable(R.drawable.book_adress)).setContent(R.id.tab1));
		th.addTab(th.newTabSpec("tab2").setIndicator("ͨ��",getResources().getDrawable(R.drawable.book_call)).setContent(R.id.tab2));
		th.addTab(th.newTabSpec("tab3").setIndicator("��Ƶ",getResources().getDrawable(R.drawable.book_videocall)).setContent(R.id.tab3));
		th.addTab(th.newTabSpec("tab3").setIndicator("����",getResources().getDrawable(R.drawable.book_voicecall)).setContent(R.id.tab4));

       //�����null����ΪgetResources().getDrawable(R.drawable.ͼƬ��)����ͼ�� 
		int i;
		for (i = 0; i < th.getTabWidget().getChildCount(); i++) {
		th.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#BCBCBC"));}
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.address_book, menu);
		return true;
	}

}
