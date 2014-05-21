package project.voicematch.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.widget.EditText;

public class CustomEditText extends EditText {
	
	private Context mContext;

	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
	}

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setTextSize(22F);
	}

	public CustomEditText(Context context) {
		super(context);
		mContext = context;
	}
	
	@Override
	public void setTextSize(float size) {
		Display d = ((Activity) mContext).getWindowManager()
				.getDefaultDisplay();
		int width = d.getWidth();
		float outputSize = ((float)width / (float)480) * (float)size;
		super.setTextSize(0 , outputSize);
	}

}
