/**
 * 
 */
package com.hfut.fragment;




import com.hfut.constant.Constant;
import com.hfut.telephone.HealthRecordActivity;
import com.hfut.telephone.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *		
 *
 * 	@author Lean  @date:2014-8-4  
 */
public class ModelFragment extends BaseFragment{
	
	//声明一个变量,该变量存储该Fragment所需要的一切参数 当刷新View时手动调用其更新数据
	private ContactFragmentCallBack mContactFragmentCallBack;
	
	//声明该接口
	public interface ContactFragmentCallBack{
		//说明该Fragment更新时需要一个String对象
		public String getContentStr();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.model_content,container,false);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		HealthRecordActivity.currFagTag=Constant.STR_FRAGMENT_MODEL;
		
		//通过取出 存储于上个Fragment中的数据
		Fragment f=((HealthRecordActivity)getActivity()).getFragment(Constant.STR_FRAGMENT_INDEX);
		if (f!=null&&f instanceof ContactFragmentCallBack) {
			mContactFragmentCallBack=(ContactFragmentCallBack)f;
			TextView textView=(TextView) ((HealthRecordActivity)getActivity()).findViewById(R.id.contact_tv);
			textView.setText(mContactFragmentCallBack.getContentStr());
		}
	}
	
}
