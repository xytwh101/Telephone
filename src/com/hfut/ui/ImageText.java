package com.hfut.ui;




import com.hfut.constant.Constant;
import com.hfut.telephone.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *	°´Å¥¿Ø¼þ
 *
 * 	@author Lean  @date:2014-8-4  
 */
public class ImageText extends LinearLayout{
	
	private ImageView mImageView;
	private TextView mTextiew;
	private static final int DEFAULT_SIZE_WIDTH=140;
	private static final int DEFAULT_SIZE_HEIGHT=90;
	private static final float TEXT_SIZE=16.0f;
	private int mTextDefaulColor=0xffF3F3F3;
	private int mTextSelectedColor=0xffFF0033;
	
	public ImageText(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.image_text_layout, this,true);
		mTextiew=(TextView) findViewById(R.id.tv_imgae_text_layout);
	}
	

	public void setText(String s)
	{
		if (mTextiew!=null) {
			mTextiew.setText(s);
			setTextColor(mTextDefaulColor);
			mTextiew.setTextSize(TEXT_SIZE);
			setTextSize(DEFAULT_SIZE_WIDTH,DEFAULT_SIZE_HEIGHT);
		}
	}

	public void setTextColor(int color) {
		//mTextiew.setTextColor(color);
		mTextiew.setBackgroundColor(color);
	
	}
	
	private void setTextSize(int defaultSizeWidth, int defaultSizeHeight) {
		LayoutParams params=(LayoutParams) mTextiew.getLayoutParams();
		params.width=defaultSizeWidth;
		params.height=defaultSizeHeight;
		mTextiew.setLayoutParams(params);
	}

	public void setImage(int resId)
	{
		if (mImageView!=null) {
			mImageView.setImageResource(resId);
		}
	}
	
	public void setChecked(int itemId)
	{
		if (mTextiew!=null) {
			setTextColor(mTextSelectedColor);
		}
		switch (itemId) {
		case Constant.SIGN_FRAGMENT_INDEX:
			//mImageView.setImageResource(R.drawable.index_selected);
			break;
		case Constant.SIGN_FRAGMENT_MODEL:
			//mImageView.setImageResource(R.drawable.model_selected);
			break;
		case Constant.SIGN_FRAGMENT_FIND:
			//mImageView.setImageResource(R.drawable.find_selected);
			break;
		case Constant.SIGN_FRAGMENT_ME:
			//mImageView.setImageResource(R.drawable.me_selected);
			break;
		default:
			break;
		}
	}

	
	
}
