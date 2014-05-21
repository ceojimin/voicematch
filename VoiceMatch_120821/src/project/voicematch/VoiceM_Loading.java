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
	boolean check = true;// DB������ �Ǻ��ϴ� ���� true�϶� MatchDB_other
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

		requestWindowFeature(Window.FEATURE_NO_TITLE);// Ÿ��Ʋ�� ���ֱ�
		setContentView(R.layout.loading);

		h = new Handler();
		h.postDelayed(irun, 3000);// 3�� �Ŀ� ����

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

				if (sex1 == sex2) {// ���� ������ ������
					Intent intent = new Intent(VoiceM_Loading.this,
							VoiceM_MatchDB_same.class);// ���������� DB������ ���� ����
					Toast.makeText(VoiceM_Loading.this,
							"�뿪�� �̸��� ��ġ�Ͻø� �ش� �뿪�� ���� ������ ���� �� �ֽ��ϴ�.", 300000000)
							.show();
					startActivity(intent);

				} else {// �����ٸ��� ������
					Intent intent = new Intent(VoiceM_Loading.this,
							VoiceM_MatchDB_other.class);
					Toast.makeText(VoiceM_Loading.this,
							"�뿪�� �̸��� ��ġ�Ͻø� �ش� �뿪�� ���� ������ ���� �� �ֽ��ϴ�.", 300000000)
							.show();
					startActivity(intent);
				}
			} else if (pagenum == 2) {
				Intent intent = new Intent(VoiceM_Loading.this,
						VoiceM_ImageDB.class);// ���������� DB������ ���� ����
				Toast.makeText(VoiceM_Loading.this,
						"�뿪�� �̸��� ��ġ�Ͻø� �ش� �뿪�� ���� ������ ���� �� �ֽ��ϴ�.", 300000000)
						.show();
				startActivity(intent);
			} else if (pagenum == 3) {
				Intent intent = new Intent(VoiceM_Loading.this,
						VoiceM_SimilarDB.class);// ���������� DB������ ���� ����
				Toast.makeText(VoiceM_Loading.this,
						"�뿪�� �̸��� ��ġ�Ͻø� �ش� �뿪�� ���� ������ ���� �� �ֽ��ϴ�.", 300000000)
						.show();
				startActivity(intent);
			}

			finish();

			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
		} // fade in, out ȿ���ֱ�
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		h.removeCallbacks(irun);// ��Ʈ�� �� �ڷΰ��� ������ �� �ڵ鷯�� ���� ���� �ÿ��� ������ �ߴ� �� ����
	}

	class Starter implements Runnable {
		// UI������� Runnable��ü�� �޾Ƽ� �����ϸ� �ִϸ��̼��� ���۵ȴ�
		public void run() {
			animation.start();
		}
	}

	private void startAnimation() {
		// �� �������� ������ ��ü�� �����Ѵ�
		animation = new AnimationDrawable();

		// �� ���������� ����� �̹����� ����Ѵ�
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

		// �ִϸ��̼��� �ѹ��� ������ ������ �ݺ��� ������ �����Ѵ�
		animation.setOneShot(false);

		ImageView imageView = (ImageView) findViewById(R.id.imgview);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.alignWithParent = true;
		params.addRule(RelativeLayout.CENTER_IN_PARENT);

		imageView.setLayoutParams(params);
		// ����� �ִϸ��̼����� �����Ѵ�
		// imageView.setBackgroundDrawable(animation);

		// ��������� ����Ǵ� �ִϸ��̼����� ����� ���
		imageView.setImageDrawable(animation);

		// UI thread�� �ִϸ��̼��� ������ �� �ֵ��� ImageView���� Runnable��ü�� ������.
		imageView.post(new Starter());
	}
}