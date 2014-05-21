package project.voicematch;

import project.voicematch.R;
import project.voicematch.ui.CustomEditText;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class VoiceM_Match extends Activity {

	CustomEditText edit1;
	CustomEditText edit2;
	private static String name1;
	private static String name2;
	private RadioGroup radio1;
	private RadioGroup radio2;

	private static int sex1 = 0;// 초기값 0으로 셋팅
	private static int sex2 = 0;

	private int pagenum;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_match);
		edit1 = (CustomEditText) findViewById(R.id.nameedit);
		edit2 = (CustomEditText) findViewById(R.id.nameedit2);
		radio1 = (RadioGroup) findViewById(R.id.radiogroup);
		radio2 = (RadioGroup) findViewById(R.id.radiogroup2);
		// VoiceM_Loading vl = new VoiceM_Loading();
		pagenum = 1;
		VoiceM_Loading.setPagenum(pagenum);

		radio1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// if (group == radio1) {
				if (checkedId == R.id.man1) {
					sex1 = 0;
					// return;
				} else if (checkedId == R.id.woman1) {
					sex1 = 1;
				}
			}
		});

		radio2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// if (group == radio2) {
				if (checkedId == R.id.man2) {
					sex2 = 0;
				} else if (checkedId == R.id.woman2) {
					sex2 = 1;
				}
			}
		});

		findViewById(R.id.go).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CustomEditText nameEdit = (CustomEditText) findViewById(R.id.nameedit);
				CustomEditText unameEdit = (CustomEditText) findViewById(R.id.nameedit2);

				name1 = nameEdit.getText().toString();
				name2 = unameEdit.getText().toString();

				name1 = name1.trim();
				name2 = name2.trim();

				if (name1.getBytes().length <= 0) {
					Toast.makeText(VoiceM_Match.this, "본인의 이름을 입력해주세요!",
							Toast.LENGTH_SHORT).show();
				} else if (name2.getBytes().length <= 0) {
					Toast.makeText(VoiceM_Match.this, "상대방의 이름을 입력해주세요!",
							Toast.LENGTH_SHORT).show();
				} else if (name1 != null && name2 != null) {
					Toast.makeText(
							VoiceM_Match.this,
							nameEdit.getText().toString() + "님과 "
									+ unameEdit.getText().toString()
									+ "님의 매칭도를 알려드립니다!", Toast.LENGTH_SHORT)
							.show();
					Intent intent = new Intent(VoiceM_Match.this,
							VoiceM_Match2.class);
					startActivity(intent);
					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
					finish();
				}
			}
		});
	}
	
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public int getSex1() {
		return sex1;
	}

	public int getSex2() {
		return sex2;
	}

	public String getname1() {
		return name1;
	}

	public String getname2() {
		return name2;
	}

}
