/**
 * 
 */
package com.hfut.fragment;


import com.hfut.constant.Constant;
import com.hfut.telephone.HealthRecordActivity;
import com.hfut.telephone.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *		
 *
 * 	@author Lean  @date:2014-8-4  
 */
public class FindFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.find_content,container,false);
	}

	@Override
	public void onResume() {
		super.onResume();
		HealthRecordActivity.currFagTag=Constant.STR_FRAGMENT_FIND;
	}
	
}
