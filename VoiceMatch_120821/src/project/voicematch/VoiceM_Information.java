package project.voicematch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class VoiceM_Information extends Activity implements OnClickListener {
	private ViewFlipper m_viewFlipper, m_viewFlipper2;
	private int m_nPreTouchPosX = 0;

	ImageView infobtn1, infobtn2, infobtn3, infobtn4, infobtn5, infobtn6;
	ImageView index1, index2, index3, index4, index5, index6;
	ImageView fbbtn, mailbtn;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_information);

		infobtn1 = (ImageView) findViewById(R.id.btn1);
		infobtn1.setOnClickListener(this);
		infobtn2 = (ImageView) findViewById(R.id.btn2);
		infobtn2.setOnClickListener(this);
		infobtn3 = (ImageView) findViewById(R.id.btn3);
		infobtn3.setOnClickListener(this);
		infobtn4 = (ImageView) findViewById(R.id.btn4);
		infobtn4.setOnClickListener(this);
		infobtn5 = (ImageView) findViewById(R.id.btn5);
		infobtn5.setOnClickListener(this);
		infobtn6 = (ImageView) findViewById(R.id.btn6);
		infobtn6.setOnClickListener(this);
		
		index1 = (ImageView) findViewById(R.id.indexbtn);
		index1.setOnClickListener(this);
		index2 = (ImageView) findViewById(R.id.indexbtn2);
		index2.setOnClickListener(this);
		index3 = (ImageView) findViewById(R.id.indexbtn3);
		index3.setOnClickListener(this);
		index4 = (ImageView) findViewById(R.id.indexbtn4);
		index4.setOnClickListener(this);
		index5 = (ImageView) findViewById(R.id.indexbtn5);
		index5.setOnClickListener(this);
		index6 = (ImageView) findViewById(R.id.indexbtn6);
		index6.setOnClickListener(this);
		
		fbbtn = (ImageView) findViewById(R.id.facebookbtn);
		fbbtn.setOnClickListener(this);
		mailbtn = (ImageView) findViewById(R.id.mailbtn);
		mailbtn.setOnClickListener(this);

		m_viewFlipper = (ViewFlipper) findViewById(R.id.vf);
		m_viewFlipper2 = (ViewFlipper) findViewById(R.id.vf2);
		m_viewFlipper.setOnTouchListener(MyTouchListener);
	}

	public void onClick(View v) {
		if (v == infobtn1) {
			m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.appear_from_right));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in));
			m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.disappear_to_left));
			m_viewFlipper2.setOutAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out));
			m_viewFlipper.setDisplayedChild(1);
			m_viewFlipper2.setDisplayedChild(1);
		}
		else if (v == infobtn2) {
			m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.appear_from_right));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in));
			m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.disappear_to_left));
			m_viewFlipper2.setOutAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out));
			m_viewFlipper.setDisplayedChild(2);
			m_viewFlipper2.setDisplayedChild(2);
		}
		else if (v == infobtn3) {
			m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.appear_from_right));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in));
			m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.disappear_to_left));
			m_viewFlipper2.setOutAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out));
			m_viewFlipper.setDisplayedChild(3);
			m_viewFlipper2.setDisplayedChild(3);
		}
		else if (v == infobtn4) {
			m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.appear_from_right));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in));
			m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.disappear_to_left));
			m_viewFlipper2.setOutAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out));
			m_viewFlipper.setDisplayedChild(4);
			m_viewFlipper2.setDisplayedChild(4);
		}
		else if (v == infobtn5) {
			m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.appear_from_right));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in));
			m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.disappear_to_left));
			m_viewFlipper2.setOutAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out));
			m_viewFlipper.setDisplayedChild(5);
			m_viewFlipper2.setDisplayedChild(5);
		}
		else if (v == infobtn6) {
			m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.appear_from_right));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in));
			m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.disappear_to_left));
			m_viewFlipper2.setOutAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out));
			m_viewFlipper.setDisplayedChild(6);
			m_viewFlipper2.setDisplayedChild(6);
		}
		else if (v == index1) {
			m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.appear_from_left));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out));
			m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.disappear_to_right));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in));
			m_viewFlipper.setDisplayedChild(0);
			m_viewFlipper2.setDisplayedChild(0);
		}
		else if (v == index2) {
			m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.appear_from_left));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out));
			m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.disappear_to_right));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in));
			m_viewFlipper.setDisplayedChild(0);
			m_viewFlipper2.setDisplayedChild(0);
		}
		else if (v == index3) {
			m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.appear_from_left));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out));
			m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.disappear_to_right));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in));
			m_viewFlipper.setDisplayedChild(0);
			m_viewFlipper2.setDisplayedChild(0);
		}
		else if (v == index4) {
			m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.appear_from_left));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out));
			m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.disappear_to_right));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in));
			m_viewFlipper.setDisplayedChild(0);
			m_viewFlipper2.setDisplayedChild(0);
		}
		else if (v == index5) {
			m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.appear_from_left));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out));
			m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.disappear_to_right));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in));
			m_viewFlipper.setDisplayedChild(0);
			m_viewFlipper2.setDisplayedChild(0);
		}
		else if (v == index6) {
			m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.appear_from_left));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out));
			m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.disappear_to_right));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in));
			m_viewFlipper.setDisplayedChild(0);
			m_viewFlipper2.setDisplayedChild(0);
		}
		
		final Intent fbintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/voicematch"));
		Intent mailintent = new Intent(Intent.ACTION_SEND);
		mailintent.setType("text/email");
		mailintent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"voicematch@hanmail.net"});
		
		if(v == fbbtn){
			startActivity(fbintent);
		} else if(v == mailbtn){
			startActivity(mailintent);
		}
	}

	private void MoveNextView() {
		if (m_viewFlipper.getDisplayedChild() != 6) {
			m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.appear_from_right));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in));
			m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.disappear_to_left));
			m_viewFlipper2.setOutAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out));
			m_viewFlipper.showNext();
			m_viewFlipper2.showNext();
		} else if (m_viewFlipper.getDisplayedChild() == 6) {
			new AlertDialog.Builder(VoiceM_Information.this)
					.setMessage("마지막 페이지입니다.")
					.setPositiveButton("확인",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();
		}
	}

	private void MovewPreviousView() {
		if (m_viewFlipper.getDisplayedChild() != 0) {
			m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.appear_from_left));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out));
			m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.disappear_to_right));
			m_viewFlipper2.setInAnimation(AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in));
			m_viewFlipper.showPrevious();
			m_viewFlipper2.showPrevious();
		} else if (m_viewFlipper.getDisplayedChild() == 0) {
			new AlertDialog.Builder(VoiceM_Information.this)
					.setMessage("첫 번째 페이지입니다.")
					.setPositiveButton("확인",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();
		}
	}

	View.OnTouchListener MyTouchListener = new View.OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				m_nPreTouchPosX = (int) event.getX();
			}

			if (event.getAction() == MotionEvent.ACTION_UP) {
				int nTouchPosX = (int) event.getX();

				if (nTouchPosX < m_nPreTouchPosX) {
					MoveNextView();
				} else if (nTouchPosX > m_nPreTouchPosX) {
					MovewPreviousView();
				}
				m_nPreTouchPosX = nTouchPosX;
			}
			return true;
		}
	};

	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}
}