/**
 * 
 */
package com.hfut.fragment;



import com.hfut.constant.Constant;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *	»ù´¡Fragment
 *
 * 	@author Lean  @date:2014-8-4  
 */
public abstract class BaseFragment extends Fragment {
	
	public static final String TAG="zftphone";

//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//		Log.v(TAG, ">>onAttach");
//	}
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		Log.v(TAG, ">>onCreateView");
//		return super.onCreateView(inflater, container, savedInstanceState);
//	}
//	
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		Log.v(TAG, ">>onActivityCreated");
//	}
//	
//	@Override
//	public void onStart() {
//		super.onStart();
//		Log.v(TAG, ">>onStart");
//	}
//	
//	@Override
//	public void onResume() {
//		super.onResume();
//		Log.v(TAG, ">>onResume");
//	}
//	
//	@Override
//	public void onPause() {
//		super.onPause();
//		Log.v(TAG, ">>onPause");
//	}
//	
//	@Override
//	public void onStop() {
//		super.onStop();
//		Log.v(TAG, ">>onStop");
//	}
//	
//	@Override
//	public void onDestroyView() {
//		super.onDestroyView();
//		Log.v(TAG, ">>onDestroyView");
//	}
//	
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		Log.v(TAG, ">>onDestroy");
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		Log.v(TAG, ">>onCreate");
//	}
//
//	@Override
//	public void onDetach() {
//		super.onDetach();
//		Log.v(TAG, ">>onDetach");
//	}
	
	public static BaseFragment newInstance(String tag) {
		BaseFragment fragment=null;
		if (TextUtils.equals(tag,Constant.STR_FRAGMENT_INDEX)) {
			fragment=new IndexFragment();
		}/*else if (TextUtils.equals(tag,Constant.STR_FRAGMENT_MODEL)) {
			fragment=new ModelFragment();
		}else if (TextUtils.equals(tag,Constant.STR_FRAGMENT_FIND)) {
			fragment=new FindFragment();
		}else if (TextUtils.equals(tag,Constant.STR_FRAGMENT_ME)) {
			fragment=new MeFragment();
		}*/
		return fragment;
	}

	
}
