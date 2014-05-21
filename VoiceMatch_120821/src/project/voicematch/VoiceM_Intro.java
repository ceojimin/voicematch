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
		// Ad@m sdk �ʱ�ȭ ����
		adView = (AdView) findViewById(R.id.adview);
		// ���� ������ ����
		// 1. ���� Ŭ���� ������ ������
		adView.setOnAdClickedListener(new OnAdClickedListener() {
			public void OnAdClicked() {
				Log.i(LOGTAG, "���� Ŭ���߽��ϴ�.");
			}
		});
		// 2. ���� �����ޱ� �������� ��쿡 ������ ������
		adView.setOnAdFailedListener(new OnAdFailedListener() {
			public void OnAdFailed(AdError error, String message) {
				Log.w(LOGTAG, message);
			}
		});
		// 3. ���� ���������� �����޾��� ��쿡 ������ ������
		adView.setOnAdLoadedListener(new OnAdLoadedListener() {
			public void OnAdLoaded() {
				Log.i(LOGTAG, "���� ���������� �ε��Ǿ����ϴ�.");
			}
		});
		// 4. ���� �ҷ��ö� ������ ������
		adView.setOnAdWillLoadListener(new OnAdWillLoadListener() {
			public void OnAdWillLoad(String url) {
				Log.i(LOGTAG, "���� �ҷ��ɴϴ�. : " + url);
			}
		});

		// ���� ������ ĳ�� ��� ���� : �⺻ ���� true
		adView.setAdCache(false);
		// Animation ȿ�� : �⺻ ���� AnimationType.NONE
		adView.setAnimationType(AnimationType.FLIP_HORIZONTAL);
		adView.setVisibility(View.VISIBLE);
	}*/

	public void onBackPressed() {
		new AlertDialog.Builder(VoiceM_Intro.this).setTitle("����").setIcon(R.drawable.app_icon)
				.setMessage("Voice Match�� �����Ͻðڽ��ϱ�?")
				.setPositiveButton("����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.setNegativeButton("���", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}
}