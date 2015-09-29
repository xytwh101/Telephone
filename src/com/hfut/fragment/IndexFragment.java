/**
 * 
 */
package com.hfut.fragment;





import com.hfut.constant.Constant;
import com.hfut.fragment.ModelFragment.ContactFragmentCallBack;
import com.hfut.telephone.HealthRecordActivity;
import com.hfut.telephone.R;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 *		
 *
 * 	@author Lean  @date:2014-8-4  
 */
public class IndexFragment extends BaseFragment implements ContactFragmentCallBack{
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.index_content,container,false);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		HealthRecordActivity.currFagTag=Constant.STR_FRAGMENT_INDEX;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().findViewById(R.id.msg_tv).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((HealthRecordActivity)getActivity()).setTabSection(Constant.STR_FRAGMENT_MODEL);
			}
		});
	}

	@Override
	public String getContentStr() {
		return "abc";
	}
	

}
