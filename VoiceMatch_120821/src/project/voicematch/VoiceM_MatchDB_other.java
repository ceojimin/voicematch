package project.voicematch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import project.voicematch.ui.CustomText;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VoiceM_MatchDB_other extends Activity {
	CustomText textsex_1, textsex_2, textname_1, textname_2;
	CustomText textresult1, textresult2, ffttext1, ffttext2;
	ImageView grouptext1, grouptext2;
	Cursor cursor;
	Random rand = new Random();
	Random randt = new Random();// 테이블 돌리기 위해
	int randid, randtn;
	int matchresult;
	String message;
	Bitmap bm;
	RelativeLayout layout;

	int mgroupno = 0;
	int ugroupno = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.voice_matchdb);

		VoiceMatchOtherDBHelper oHelper = new VoiceMatchOtherDBHelper(this);
		SQLiteDatabase db = oHelper.getReadableDatabase();// 읽는 db 열어줌

		textsex_1 = (CustomText) findViewById(R.id.textsex1);
		textsex_2 = (CustomText) findViewById(R.id.textsex2);
		textname_1 = (CustomText) findViewById(R.id.textname1);
		textname_2 = (CustomText) findViewById(R.id.textname2);
		textresult1 = (CustomText) findViewById(R.id.textresult1);
		layout = (RelativeLayout) findViewById(R.id.resultlayout);
		textresult2 = (CustomText) findViewById(R.id.textresult2);

		ffttext1 = (CustomText) findViewById(R.id.ffttext_1);
		ffttext2 = (CustomText) findViewById(R.id.ffttext_2);
		textsex_1.setTextSize(24F);
		textsex_2.setTextSize(24F);
		textname_1.setTextSize(24F);
		textname_2.setTextSize(24F);
		textresult1.setTextSize(80F);
		textresult2.setGravity(Gravity.CENTER);
		ffttext1.setTextSize(24F);
		ffttext2.setTextSize(24F);

		grouptext1 = (ImageView) findViewById(R.id.group);
		grouptext2 = (ImageView) findViewById(R.id.group2);

		// 이름과 성별받음
		VoiceM_Match vm = new VoiceM_Match();

		String s1 = vm.getname1();
		String s2 = vm.getname2();

		double fft_1 = VoiceM_Match2.getFfttext_1();
		double fft_2 = VoiceM_Match2.getFfttext_2();

		// textresult1.setText(""+fft_1+""+fft_2);
		textname_1.setText(s1);
		textname_2.setText(s2);

		if (vm.getSex1() == 0)
			textsex_1.setText("남자");
		else
			textsex_1.setText("여자");

		if (vm.getSex2() == 0)
			textsex_2.setText("남자");
		else
			textsex_2.setText("여자");// 이름과 성별 출력

		ffttext1.setText(" " + fft_1 + " hz");
		ffttext2.setText(" " + fft_2 + " hz");

		int group1 = rander(fft_1);
		int group2 = rander(fft_2);
		// Math.abs(group1-group2);
		// randtn = randt.nextInt(10);//테이블 임시로 돌려줌
		randid = rand.nextInt(5) + 1;
		String tablename = "matchTableOther";
		tablename = tablename + Math.abs(group1 - group2);
		cursor = db.rawQuery("SELECT * FROM '" + tablename + "' WHERE id= '"
				+ randid + "'", null);
		cursor.moveToFirst();
		textresult1.setText("" + cursor.getString(1));
		textresult2.setText(cursor.getString(2));

		db.close();

		if (fft_1 >= 0 && fft_1 < 82.407) {
			grouptext1.setImageResource(R.drawable.btn_mulberry);
			mgroupno = 1;
		} else if (fft_1 >= 82.407 && fft_1 < 130.8128) {
			grouptext1.setImageResource(R.drawable.btn_coffee);
			mgroupno = 2;
		} else if (fft_1 >= 130.8128 && fft_1 < 146.8324) {
			grouptext1.setImageResource(R.drawable.btn_grape);
			mgroupno = 3;
		} else if (fft_1 >= 146.8324 && fft_1 < 164.8138) {
			grouptext1.setImageResource(R.drawable.btn_blackcurrant);
			mgroupno = 4;
		} else if (fft_1 >= 164.8138 && fft_1 < 174.6141) {
			grouptext1.setImageResource(R.drawable.btn_blueberry);
			mgroupno = 5;
		} else if (fft_1 >= 174.6141 && fft_1 < 195.9977) {
			grouptext1.setImageResource(R.drawable.btn_olive);
			mgroupno = 6;
		} else if (fft_1 >= 195.9977 && fft_1 < 220) {
			grouptext1.setImageResource(R.drawable.btn_lemon);
			mgroupno = 7;
		} else if (fft_1 >= 220 && fft_1 < 246.9417) {
			grouptext1.setImageResource(R.drawable.btn_ginger);
			mgroupno = 8;
		} else if (fft_1 >= 246.9417 && fft_1 < 261.6256) {
			grouptext1.setImageResource(R.drawable.btn_sukru);
			mgroupno = 9;
		} else {
			grouptext1.setImageResource(R.drawable.btn_blackrose);
			mgroupno = 10;
		}
		if (fft_2 >= 0 && fft_2 < 82.407) {
			grouptext2.setImageResource(R.drawable.btn_mulberry);
			ugroupno = 1;
		} else if (fft_2 >= 82.407 && fft_2 < 130.8128) {
			grouptext2.setImageResource(R.drawable.btn_coffee);
			ugroupno = 2;
		} else if (fft_2 >= 130.8128 && fft_2 < 146.8324) {
			grouptext2.setImageResource(R.drawable.btn_grape);
			ugroupno = 3;
		} else if (fft_2 >= 146.8324 && fft_2 < 164.8138) {
			grouptext2.setImageResource(R.drawable.btn_blackcurrant);
			ugroupno = 4;
		} else if (fft_2 >= 164.8138 && fft_2 < 174.6141) {
			grouptext2.setImageResource(R.drawable.btn_blueberry);
			ugroupno = 5;
		} else if (fft_2 >= 174.6141 && fft_2 < 195.9977) {
			grouptext2.setImageResource(R.drawable.btn_olive);
			ugroupno = 6;
		} else if (fft_2 >= 195.9977 && fft_2 < 220) {
			grouptext2.setImageResource(R.drawable.btn_lemon);
			ugroupno = 7;
		} else if (fft_2 >= 220 && fft_2 < 246.9417) {
			grouptext2.setImageResource(R.drawable.btn_ginger);
			ugroupno = 8;
		} else if (fft_2 >= 246.9417 && fft_2 < 261.6256) {
			grouptext2.setImageResource(R.drawable.btn_sukru);
			ugroupno = 9;
		} else {
			grouptext2.setImageResource(R.drawable.btn_blackrose);
			ugroupno = 10;
		}

		findViewById(R.id.gallery).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				layout.buildDrawingCache();
				bm = layout.getDrawingCache();
				try {
					screenshot(bm);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		findViewById(R.id.group).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mgroupno == 1) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("주변을 압도하는 마법의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 2) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("속삭이듯 달콤한 부드러운 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 3) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("우아하고 섬세한 평온의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 4) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("차가운 남성적 용기의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 5) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("냉정하고 상쾌한 자유의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 6) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("젊음과 희망을 노래하는 생명의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 7) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("성실하고 열정적인 자신감이 충만한 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 8) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("명랑, 발랄하며 적극적이고 경쾌한 즐거운 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 9) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("애교와 원숙미 넘치는 유혹의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 10) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("강인하며 도전적인 기쁨의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				}
			}
		});

		findViewById(R.id.group2).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (ugroupno == 1) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("주변을 압도하는 마법의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 2) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("속삭이듯 달콤한 부드러운 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 3) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("우아하고 섬세한 평온의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 4) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("차가운 남성적 용기의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 5) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("냉정하고 상쾌한 자유의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 6) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("젊음과 희망을 노래하는 생명의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 7) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("성실하고 열정적인 자신감이 충만한 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 8) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("명랑, 발랄하며 적극적이고 경쾌한 즐거운 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 9) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("애교와 원숙미 넘치는 유혹의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 10) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("강인하며 도전적인 기쁨의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				}
			}
		});

	}

	public void onBackPressed() {
		new AlertDialog.Builder(VoiceM_MatchDB_other.this)
				.setTitle("목소리 궁합 종료")
				.setIcon(R.drawable.app_icon)
				.setMessage("목소리 궁합을 종료하시겠습니까? 종료하면 메인화면으로 돌아갑니다.")
				.setPositiveButton("네", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
						overridePendingTransition(android.R.anim.fade_in,
								android.R.anim.fade_out);
						Toast.makeText(VoiceM_MatchDB_other.this,
								"메인메뉴로 돌아갑니다.", Toast.LENGTH_SHORT).show();
					}
				})
				.setNeutralButton("다시 녹음",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(
										VoiceM_MatchDB_other.this,
										VoiceM_Match2.class);
								startActivity(intent);
								overridePendingTransition(
										android.R.anim.fade_in,
										android.R.anim.fade_out);
								finish();
							}
						})
				.setNegativeButton("취소", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	public void screenshot(Bitmap bm) {
		try {
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath(), "Voice Match");
			if (!file.exists()) {
				file.mkdirs();
			}

			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"목소리 궁합_yyyy년 MM월 dd일 HH시 mm분 ss초");
			String filename = sdf.format(date);
			file = Environment.getExternalStorageDirectory();
			String path = file.getAbsolutePath() + "/Voice Match/" + filename
					+ ".jpg";

			FileOutputStream out = new FileOutputStream(path);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);

			Toast.makeText(VoiceM_MatchDB_other.this,
					filename + ".jpg로 저장되셨습니다!", Toast.LENGTH_SHORT).show();

			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.parse("file://" + path);
			intent.setData(uri);
			sendBroadcast(intent);

		} catch (FileNotFoundException e) {
			Log.d("FileNotFoundException:", e.getMessage());
		}
	}

	private int rander(double fft) {
		int group = 0;
		// TODO Auto-generated method stub
		if (fft >= 0 && fft < 43.6535) {
			group = 1;
		} else if (fft >= 43.6535 && fft < 65.4064) {
			group = 2;
		} else if (fft >= 65.4064 && fft < 87.3071) {
			group = 3;
		} else if (fft >= 87.3071 & fft < 116.5409) {
			group = 4;
		} else if (fft >= 116.5409 && fft < 130.8128) {
			group = 5;
		} else if (fft >= 130.8128 && fft < 146.8324) {
			group = 6;
		} else if (fft >= 146.8324 && fft < 164.8138) {
			group = 7;
		} else if (fft >= 164.8138 && fft < 174.6141) {
			group = 8;
		} else if (fft >= 174.6141 && fft < 195.9977) {
			group = 9;
		} else if (fft >= 195.9977 && fft < 220) {
			group = 10;
		} else if (fft >= 220 && fft < 246.9417) {
			group = 11;
		} else if (fft >= 246.9417 && fft < 261.6256) {
			group = 12;
		} else if (fft >= 261.6256 && fft < 293.6648) {
			group = 13;
		} else if (fft >= 293.6648 && fft < 329.6276) {
			group = 14;
		} else if (fft >= 329.6276 && fft < 391.9954) {
			group = 15;
		} else if (fft >= 391.9954 && fft < 440) {
			group = 16;
		} else if (fft >= 440 && fft < 493.8833) {
			group = 17;
		} else if (fft >= 493.8833 && fft < 622.2540) {
			group = 18;
		} else if (fft >= 622.2540 && fft < 783.9909) {
			group = 19;
		} else if (fft >= 783.9909 && fft < 987.7666) {
			group = 20;
		}
		return group;
	}
}

class VoiceMatchOtherDBHelper extends SQLiteOpenHelper {
	int y;

	public VoiceMatchOtherDBHelper(Context context) {
		super(context, "voicematch_other.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/*
		 * 그룹 간 차이를 이용해서 매칭도를 나타낸 DB - 이성, 동성 모두 그룹의 차가 적을수록 매칭도가 높다 (소리연구가 배명진
		 * 교수 논문 참조 : 목소리가 비슷할수록 궁합이 좋다는 내용) 따라서 matchTableOther0이 가장 매칭도 높은 DB,
		 * matchTableOther19가 가장 매칭도 낮은 DB
		 */
		db.execSQL("CREATE TABLE matchTableOther0 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther1 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");// db 생성
		db.execSQL("CREATE TABLE matchTableOther2 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther3 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther4 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther5 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther6 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther7 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther8 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther9 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther10 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther11 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther12 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther13 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther14 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther15 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther16 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther17 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther18 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther19 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");

		for (int x = 1; x <= 5; x++) {
			db.execSQL("insert into matchTableOther19 values('" + x + "', '"
					+ x + "', '나는 톰, 너는 제리.. 하루도 힘듭니다.' );");
		}
		for (int x = 6; x <= 10; x++) {
			y = x - 5;
			db.execSQL("insert into matchTableOther18 values('" + y + "', '"
					+ x + "', '사랑보다 깊은 상처만 준 너! 그만 헤어져..' );");
		}
		for (int x = 11; x <= 15; x++) {
			y = x - 10;
			db.execSQL("insert into matchTableOther17 values('" + y + "', '"
					+ x + "', '현대판 로미오와 줄리엣. 이루어지기 힘들어요~' );");
		}
		for (int x = 16; x <= 20; x++) {
			y = x - 15;
			db.execSQL("insert into matchTableOther16 values('" + y + "', '"
					+ x + "', '야~ 너네 안 돼애~~' );");
		}
		for (int x = 21; x <= 25; x++) {
			y = x - 20;
			db.execSQL("insert into matchTableOther15 values('" + y + "', '"
					+ x + "', '내 마음이 들리니? 네 마음은 안 들려..' );");
		}
		for (int x = 26; x <= 30; x++) {
			y = x - 25;
			db.execSQL("insert into matchTableOther14 values('" + y + "', '"
					+ x + "', '부러진 큐피트의 화살...' );");
		}

		for (int x = 31; x <= 35; x++) {
			y = x - 30;
			db.execSQL("insert into matchTableOther13 values('" + y + "', '"
					+ x + "', '대화가 필요해 ♬' );");
		}
		for (int x = 36; x <= 40; x++) {
			y = x - 35;
			db.execSQL("insert into matchTableOther12 values('" + y + "', '"
					+ x + "', '내 안에 너 있다. 근데 너는..?' );");
		}
		for (int x = 41; x <= 45; x++) {
			y = x - 40;
			db.execSQL("insert into matchTableOther11 values('" + y + "', '"
					+ x + "', '사랑보다 먼 우정보다는 가까운..♬' );");
		}
		for (int x = 46; x <= 50; x++) {
			y = x - 45;
			db.execSQL("insert into matchTableOther10 values('" + y + "', '"
					+ x + "', '태산씨! 보이스매치가 저희 커플 밍밍하대요~' );");
		}
		for (int x = 51; x <= 55; x++) {
			y = x - 50;
			db.execSQL("insert into matchTableOther9 values('" + y + "', '" + x
					+ "', '우리 사이.. 더 노력하며 맞춰가는 걸로~' );");
		}
		for (int x = 56; x <= 60; x++) {
			y = x - 55;
			db.execSQL("insert into matchTableOther8 values('" + y + "', '" + x
					+ "', '김 빠진 콜라 같아요. 특별함이 필요합니다!' );");
		}
		for (int x = 61; x <= 65; x++) {
			y = x - 60;
			db.execSQL("insert into matchTableOther7 values('" + y + "', '" + x
					+ "', '서로에게 맞춰주세요. Step by step!' );");
		}
		for (int x = 66; x <= 70; x++) {
			y = x - 65;
			db.execSQL("insert into matchTableOther6 values('" + y + "', '" + x
					+ "', '있을 때 잘 하세요!' );");
		}
		for (int x = 71; x <= 75; x++) {
			y = x - 70;
			db.execSQL("insert into matchTableOther5 values('" + y + "', '" + x
					+ "', '남자는 여자하기 나름이에요~' );");
		}
		for (int x = 76; x <= 80; x++) {
			y = x - 75;
			db.execSQL("insert into matchTableOther4 values('" + y + "', '" + x
					+ "', '우린 제법 잘 어울려요 ♬' );");
		}
		for (int x = 81; x <= 85; x++) {
			y = x - 80;
			db.execSQL("insert into matchTableOther3 values('" + y + "', '" + x
					+ "', '우리 사이 요만큼만 더 가까이 ♬' );");
		}
		for (int x = 86; x <= 90; x++) {
			y = x - 85;
			db.execSQL("insert into matchTableOther2 values('" + y + "', '" + x
					+ "', '너를 품은 나, 나를 품은 너.. 찰떡궁합!' );");
		}
		for (int x = 91; x <= 95; x++) {
			y = x - 90;
			db.execSQL("insert into matchTableOther1 values('" + y + "', '" + x
					+ "', '나상실도 인정한 환상의 커플' );");
		}
		for (int x = 96; x <= 100; x++) {
			y = x - 95;
			db.execSQL("insert into matchTableOther0 values('" + y + "', '" + x
					+ "', '독고진과 구애정도 울고 갈 최고의 궁합' );");
		}

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		Log.i("DB OPEN", "DB OPEN OK");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// db
																				// 업데이트

		db.execSQL("DROP TABLE IF EXISTS matchTableOther0");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther1");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther2");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther3");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther4");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther5");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther6");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther7");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther8");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther9");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther10");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther11");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther12");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther13");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther14");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther15");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther16");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther17");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther18");
		db.execSQL("DROP TABLE IF EXISTS matchTableOther19");

		onCreate(db);
	}
}