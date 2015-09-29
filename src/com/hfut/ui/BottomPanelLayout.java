package com.hfut.ui;




import com.hfut.constant.Constant;
import com.hfut.telephone.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

/**
 *	底部栏
 *
 * 	@author Lean  @date:2014-8-4  
 */
public class BottomPanelLayout extends RelativeLayout implements OnClickListener{
	
	private ImageText mIndexBtn;
	private ImageText mModelBtn;
	private ImageText mFindBtn;
	private ImageText mMeBtn;
	private BottomPanelCallBackProtocal mCallBackProtocal;
	
	//代理协议
	public void setCallBackProtocal(BottomPanelCallBackProtocal callBackProtocal) {
		this.mCallBackProtocal = callBackProtocal;
	}

	public BottomPanelLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public interface BottomPanelCallBackProtocal{
		public void onClickCallBack(int itemID);
	}
	
	public void initBottomPanel(){
		initItemBtn(mIndexBtn,Constant.STR_FRAGMENT_INDEX,R.drawable.index_unselected);
		initItemBtn(mModelBtn,Constant.STR_FRAGMENT_MODEL,R.drawable.model_unselected);
		initItemBtn(mFindBtn,Constant.STR_FRAGMENT_FIND,R.drawable.find_unselected);
		initItemBtn(mMeBtn,Constant.STR_FRAGMENT_ME,R.drawable.me_unselected);
	}

	private void initItemBtn(ImageText itv,String text,int resId) {
		if (itv!=null) {
			itv.setText(text);
			itv.setImage(resId);
		}
	}
	
	public void defaultChecked(){
		mIndexBtn.setChecked(Constant.SIGN_FRAGMENT_INDEX);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mIndexBtn=(ImageText) findViewById(R.id.index_btn);
		mModelBtn=(ImageText) findViewById(R.id.model_btn);
		mFindBtn=(ImageText) findViewById(R.id.find_btn);
		mMeBtn=(ImageText) findViewById(R.id.me_btn);
		initClickEvent();
	}

	private void initClickEvent() {
		int n=getChildCount();
		if (n==0) {
			return;
		}
		for (int i = 0; i < n; i++) {
			getChildAt(i).setOnClickListener(this);
		}
	}

	/**  
	 * 1.修改本身样式 
	 * 2.对外声明事件
	 */
	@Override
	public void onClick(View v) {
		initBottomPanel();
		int index=-1;
		switch (v.getId()) {
			case R.id.index_btn:
				index=Constant.SIGN_FRAGMENT_INDEX;
				mIndexBtn.setChecked(index);
				break;
			case R.id.model_btn:
				index=Constant.SIGN_FRAGMENT_MODEL;
				mModelBtn.setChecked(index);
				break;
			case R.id.find_btn:
				index=Constant.SIGN_FRAGMENT_FIND;
				mFindBtn.setChecked(index);
				break;
			case R.id.me_btn:
				index=Constant.SIGN_FRAGMENT_ME;
				mMeBtn.setChecked(index);
				break;
			default:
				break;
		}
		if (mCallBackProtocal!=null) {
			mCallBackProtocal.onClickCallBack(index);
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		layoutItem(l, t, r, b);
	}

	private void layoutItem(int left, int top, int right, int bottom) {
		int allChildWidth=0;
		int num=getChildCount();
		for (int i = 0; i < num; i++) {
			allChildWidth+=getChildAt(i).getWidth();
		}
		int absoluteWidth=right-left-getPaddingLeft()-getPaddingRight();
		int blankWidth=(absoluteWidth-allChildWidth)/(num-1);
		//设置第2 3个按钮的间距
		LayoutParams params1=(LayoutParams) mModelBtn.getLayoutParams();
		params1.leftMargin=blankWidth;
		mModelBtn.setLayoutParams(params1);
		LayoutParams params2=(LayoutParams) mFindBtn.getLayoutParams();
		params2.leftMargin=blankWidth;
		mFindBtn.setLayoutParams(params2);
	}
	

}
