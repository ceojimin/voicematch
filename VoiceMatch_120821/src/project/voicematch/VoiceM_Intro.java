package project.voicematch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class VoiceM_Intro extends Activity {
	private static final String LOGTAG = "BannerTypeXML1";
	//private AdView adView = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		//initAdam();
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Intent intent = new Intent(VoiceM_Intro.this, VoiceMActivity.class);
			startActivity(intent);

			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			finish();
			return true;
		}
		return false;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		/*if (adView != null) {
			adView.destroy();
			adView = null;
		}*/
	}

	/*private void initAdam() {
		// Ad@m sdk 초기화 시작
		adView = (AdView) findViewById(R.id.adview);
		// 광고 리스너 설정
		// 1. 광고 클릭시 실행할 리스너
		adView.setOnAdClickedListener(new OnAdClickedListener() {
			public void OnAdClicked() {
				Log.i(LOGTAG, "광고를 클릭했습니다.");
			}
		});
		// 2. 광고 내려받기 실패했을 경우에 실행할 리스너
		adView.setOnAdFailedListener(new OnAdFailedListener() {
			public void OnAdFailed(AdError error, String message) {
				Log.w(LOGTAG, message);
			}
		});
		// 3. 광고를 정상적으로 내려받았을 경우에 실행할 리스너
		adView.setOnAdLoadedListener(new OnAdLoadedListener() {
			public void OnAdLoaded() {
				Log.i(LOGTAG, "광고가 정상적으로 로딩되었습니다.");
			}
		});
		// 4. 광고를 불러올때 실행할 리스너
		adView.setOnAdWillLoadListener(new OnAdWillLoadListener() {
			public void OnAdWillLoad(String url) {
				Log.i(LOGTAG, "광고를 불러옵니다. : " + url);
			}
		});

		// 광고 영역에 캐시 사용 여부 : 기본 값은 true
		adView.setAdCache(false);
		// Animation 효과 : 기본 값은 AnimationType.NONE
		adView.setAnimationType(AnimationType.FLIP_HORIZONTAL);
		adView.setVisibility(View.VISIBLE);
	}*/

	public void onBackPressed() {
		new AlertDialog.Builder(VoiceM_Intro.this).setTitle("종료").setIcon(R.drawable.app_icon)
				.setMessage("Voice Match를 종료하시겠습니까?")
				.setPositiveButton("종료", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.setNegativeButton("취소", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}
}