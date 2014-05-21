package project.voicematch;

import project.voicematch.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VoiceM_Loading extends Activity {
	Handler h;
	boolean check = true;// DB페이지 판별하는 변수 true일때 MatchDB_other
	TextView pathText;
	AnimationDrawable animation;
	private static int pagenum;

	public static int getPagenum() {
		return pagenum;
	}

	public static void setPagenum(int pagenum) {
		VoiceM_Loading.pagenum = pagenum;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);// 타이틀바 없애기
		setContentView(R.layout.loading);

		h = new Handler();
		h.postDelayed(irun, 3000);// 3초 후에 들어가기

		// TODO Auto-generated method stub
		startAnimation();
	}

	Runnable irun = new Runnable() {
		public void run() {
			if (pagenum == 1) {
				VoiceM_Match vm = new VoiceM_Match();
				int sex1 = vm.getSex1();
				int sex2 = vm.getSex2();
				// pathText.setText(""+ getPagenum());

				if (sex1 == sex2) {// 성별 같은때 페이지
					Intent intent = new Intent(VoiceM_Loading.this,
							VoiceM_MatchDB_same.class);// 성별따져서 DB페이지 따로 들어가기
					Toast.makeText(VoiceM_Loading.this,
							"대역의 이름을 터치하시면 해당 대역에 대한 설명을 보실 수 있습니다.", 300000000)
							.show();
					startActivity(intent);

				} else {// 성별다를때 페이지
					Intent intent = new Intent(VoiceM_Loading.this,
							VoiceM_MatchDB_other.class);
					Toast.makeText(VoiceM_Loading.this,
							"대역의 이름을 터치하시면 해당 대역에 대한 설명을 보실 수 있습니다.", 300000000)
							.show();
					startActivity(intent);
				}
			} else if (pagenum == 2) {
				Intent intent = new Intent(VoiceM_Loading.this,
						VoiceM_ImageDB.class);// 성별따져서 DB페이지 따로 들어가기
				Toast.makeText(VoiceM_Loading.this,
						"대역의 이름을 터치하시면 해당 대역에 대한 설명을 보실 수 있습니다.", 300000000)
						.show();
				startActivity(intent);
			} else if (pagenum == 3) {
				Intent intent = new Intent(VoiceM_Loading.this,
						VoiceM_SimilarDB.class);// 성별따져서 DB페이지 따로 들어가기
				Toast.makeText(VoiceM_Loading.this,
						"대역의 이름을 터치하시면 해당 대역에 대한 설명을 보실 수 있습니다.", 300000000)
						.show();
				startActivity(intent);
			}

			finish();

			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
		} // fade in, out 효과주기
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		h.removeCallbacks(irun);// 인트로 중 뒤로가기 눌렀을 때 핸들러를 끊어 종료 시에도 메인이 뜨는 걸 방지
	}

	class Starter implements Runnable {
		// UI쓰레드는 Runnable객체를 받아서 실행하면 애니메이션이 시작된다
		public void run() {
			animation.start();
		}
	}

	private void startAnimation() {
		// 각 프레임을 저장할 객체를 생성한다
		animation = new AnimationDrawable();

		// 각 프레임으로 사용할 이미지를 등록한다
		animation.addFrame(getResources().getDrawable(R.drawable.f1), 100);
		animation.addFrame(getResources().getDrawable(R.drawable.f2), 100);
		// animation.addFrame(getResources().getDrawable(R.drawable.f3), 100);
		animation.addFrame(getResources().getDrawable(R.drawable.f4), 100);
		animation.addFrame(getResources().getDrawable(R.drawable.f5), 100);
		// animation.addFrame(getResources().getDrawable(R.drawable.f6), 100);
		animation.addFrame(getResources().getDrawable(R.drawable.f7), 100);
		animation.addFrame(getResources().getDrawable(R.drawable.f8), 100);
		// animation.addFrame(getResources().getDrawable(R.drawable.f9), 100);
		animation.addFrame(getResources().getDrawable(R.drawable.f10), 100);
		animation.addFrame(getResources().getDrawable(R.drawable.f11), 100);
		// animation.addFrame(getResources().getDrawable(R.drawable.f12), 100);
		animation.addFrame(getResources().getDrawable(R.drawable.f13), 100);
		animation.addFrame(getResources().getDrawable(R.drawable.f14), 100);
		animation.addFrame(getResources().getDrawable(R.drawable.f15), 100);

		// 애니메이션을 한번만 실행할 것인지 반복할 것인지 설정한다
		animation.setOneShot(false);

		ImageView imageView = (ImageView) findViewById(R.id.imgview);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.alignWithParent = true;
		params.addRule(RelativeLayout.CENTER_IN_PARENT);

		imageView.setLayoutParams(params);
		// 배경을 애니메이션으로 설정한다
		// imageView.setBackgroundDrawable(animation);

		// 배경위에서 실행되는 애니메이션으로 사용할 경우
		imageView.setImageDrawable(animation);

		// UI thread가 애니메이션을 실행할 수 있도록 ImageView에게 Runnable객체를 보낸다.
		imageView.post(new Starter());
	}
}