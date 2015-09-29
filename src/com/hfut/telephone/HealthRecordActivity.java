package com.hfut.telephone;

import com.hfut.constant.Constant;
import com.hfut.fragment.BaseFragment;
import com.hfut.ui.BottomPanelLayout;
import com.hfut.ui.BottomPanelLayout.BottomPanelCallBackProtocal;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;

public class HealthRecordActivity extends Activity implements
BottomPanelCallBackProtocal {
    
	private BottomPanelLayout mBottomPanelLayout;
	

	public FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransaction = null;
	public static String currFagTag = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȡ��ϵͳĬ�ϵı�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_health_record_statistics);
		//��ʼ��UI����
		initUI();
		mFragmentManager = getFragmentManager();
		defaultFirstScreen();
	}
	
	
	
	
	
	
	
	
	//����Ĭ�����������������ҳ��
		private void defaultFirstScreen() {
			mBottomPanelLayout.defaultChecked();
			setTabSection(Constant.STR_FRAGMENT_INDEX);
		}

		private void initUI() {
			mBottomPanelLayout = (BottomPanelLayout) findViewById(R.id.bottom_panel);
			mBottomPanelLayout.initBottomPanel();
			mBottomPanelLayout.setCallBackProtocal(this);
			
		}

		@Override
		public void onClickCallBack(int itemID) {
			String tag = "";
			if ((itemID & Constant.SIGN_FRAGMENT_INDEX) != 0) {
				tag = Constant.STR_FRAGMENT_INDEX;
			} else if ((itemID & Constant.SIGN_FRAGMENT_MODEL) != 0) {
				tag = Constant.STR_FRAGMENT_MODEL;
			} else if ((itemID & Constant.SIGN_FRAGMENT_FIND) != 0) {
				tag = Constant.STR_FRAGMENT_FIND;
			} else if ((itemID & Constant.SIGN_FRAGMENT_ME) != 0) {
				tag = Constant.STR_FRAGMENT_ME;
			}
			
			setTabSection(tag);
		}
	    
		//����ÿ����Ӧ��ť��Fragment����
		public void setTabSection(String tag) {
			if (TextUtils.equals(tag, currFagTag)) {
				return;
			}
			ensureTransaction();
			if (currFagTag != null && !currFagTag.equals("")) {
				detachFragment(getFragment(currFagTag));
			}
			attachFragment(R.id.fragment_panel, getFragment(tag), tag);
			commitTransaction();
		}
		
		
		private void detachFragment(Fragment f) {
			if (f != null && !f.isDetached()) {
				mFragmentTransaction.detach(f);
			}
		}

		private void attachFragment(int layoutId, Fragment f, String tag) {
			if (f != null) {
				if (f.isDetached()) {
					mFragmentTransaction.attach(f);
				} else if (!f.isAdded()) {
					mFragmentTransaction.add(layoutId, f, tag);
				}
			}
		}

		public Fragment getFragment(String tag) {
			BaseFragment fragment = (BaseFragment) mFragmentManager
					.findFragmentByTag(tag);
			if (fragment == null) {
				fragment = BaseFragment.newInstance(tag);
			}
			return fragment;
		}

		/**
		 * ����transaction
		 */
		private void ensureTransaction() {
			if (mFragmentTransaction == null) {
				mFragmentTransaction = mFragmentManager.beginTransaction();
				mFragmentTransaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			}
		}

		/**
		 * �ύsection
		 */
		private void commitTransaction() {
			if (mFragmentTransaction != null) {
				mFragmentTransaction.commit();
				mFragmentTransaction = null;
			}
		}

		@Override
		protected void onStop() {
			super.onStop();
			currFagTag = "";
		}

		@Override
		protected void onSaveInstanceState(Bundle outState) {
		}

}
