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
import android.app.ProgressDialog;
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
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VoiceM_SimilarDB extends Activity {

	final String TAG = "DownImageActivity";

	ProgressDialog mProgress;
	ImageView image1, image2, image3, group, gallery;
	CustomText ffttext, fft1, fft2, fft3;
	CustomText myname, nametext1, nametext2, nametext3;
	Bitmap bm;
	RelativeLayout layout;
	
	int groupno = 0;

	int[] randid = new int[3];

	public final static String imageUrl = "http://voicet.googlecode.com/files/";// a5.jpg";
	public static String imageUrl1 = "";
	public static String imageUrl2 = "";
	public static String imageUrl3 = "";
	public static String searchUrl = "http://search.naver.com/search.naver?where=nexearch&query=";

	Cursor cursor;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem item = menu.add(0, 1, 0, "");
		item.setIcon(R.drawable.refresh);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:
			drawImage();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_similarresult);

		image1 = (ImageView) findViewById(R.id.img1);
		image2 = (ImageView) findViewById(R.id.img2);
		image3 = (ImageView) findViewById(R.id.img3);
		mProgress = new ProgressDialog(VoiceM_SimilarDB.this);
		ffttext = (CustomText) findViewById(R.id.fftvalue);
		fft1 = (CustomText) findViewById(R.id.fft1);
		fft2 = (CustomText) findViewById(R.id.fft2);
		fft3 = (CustomText) findViewById(R.id.fft3);
		myname = (CustomText) findViewById(R.id.textname);
		nametext1 = (CustomText) findViewById(R.id.name1);
		nametext2 = (CustomText) findViewById(R.id.name2);
		nametext3 = (CustomText) findViewById(R.id.name3);
		ffttext.setTextSize(24F);
		ffttext.setGravity(Gravity.CENTER);
		ffttext.setGravity(Gravity.BOTTOM);
		fft1.setTextSize(24F);
		fft1.setGravity(Gravity.CENTER);
		fft2.setTextSize(24F);
		fft2.setGravity(Gravity.CENTER);
		fft3.setTextSize(24F);
		fft3.setGravity(Gravity.CENTER);
		myname.setTextSize(28F);
		myname.setGravity(Gravity.CENTER);
		myname.setGravity(Gravity.BOTTOM);
		nametext1.setTextSize(24F);
		nametext1.setGravity(Gravity.CENTER);
		nametext2.setTextSize(24F);
		nametext2.setGravity(Gravity.CENTER);
		nametext3.setTextSize(24F);
		nametext3.setGravity(Gravity.CENTER);
		
		group = (ImageView) findViewById(R.id.group);
		gallery = (ImageView) findViewById(R.id.gallery);
		
		layout = (RelativeLayout) findViewById(R.id.resultlayout);

		VoiceSimilarDBHelper siHelper = new VoiceSimilarDBHelper(this);
		SQLiteDatabase db = siHelper.getReadableDatabase();

		Random rand = new Random();

		VoiceM_Similar vs = new VoiceM_Similar();
		double fft = vs.getStopfft();
		ffttext.setText(fft + " hz,");
		myname.setText(vs.getName());

		if (fft >= 0 && fft < 82.407) {
			group.setImageResource(R.drawable.btn_mulberry);
			groupno = 1;
		} else if (fft >= 82.407 && fft < 130.8128) {
			group.setImageResource(R.drawable.btn_coffee);
			groupno = 2;
		} else if (fft >= 130.8128 && fft < 146.8324) {
			group.setImageResource(R.drawable.btn_grape);
			groupno = 3;
		} else if (fft >= 146.8324 && fft < 164.8138) {
			group.setImageResource(R.drawable.btn_blackcurrant);
			groupno = 4;
		} else if (fft >= 164.8138 && fft < 174.6141) {
			group.setImageResource(R.drawable.btn_blueberry);
			groupno = 5;
		} else if (fft >= 174.6141 && fft < 195.9977) {
			group.setImageResource(R.drawable.btn_olive);
			groupno = 6;
		} else if (fft >= 195.9977 && fft < 220) {
			group.setImageResource(R.drawable.btn_lemon);
			groupno = 7;
		} else if (fft >= 220 && fft < 246.9417) {
			group.setImageResource(R.drawable.btn_ginger);
			groupno = 8;
		} else if (fft >= 246.9417 && fft < 261.6256) {
			group.setImageResource(R.drawable.btn_sukru);
			groupno = 9;
		} else {
			group.setImageResource(R.drawable.btn_blackrose);
			groupno = 10;
		}

		cursor = db.rawQuery("SELECT * FROM similarTable1", null);
		cursor.moveToFirst();
		int recordcount = cursor.getCount();// cursor.getString(1);

		double dbfft = 0;
		double s, s1 = 1000, s2 = 1000, s3 = 1000;
		double s1fft = 0, s2fft = 0, s3fft = 0;

		if (fft < 70 || fft > 304.6875) {// 극저음과 극고음 부분 처리 방식
			for (int i = recordcount; i >= 1; i--) {
				cursor = db.rawQuery("SELECT * FROM similarTable1 where id= '"
						+ i + "'", null);
				cursor.moveToFirst();
				if (cursor != null && cursor.moveToFirst()) {
					try {
						do {
							dbfft = cursor.getDouble(1);
							s = Math.abs(dbfft - fft);
							if (s <= s1 || s <= s2 || s <= s3) {
								if (s < s1) {
									s3 = s2;
									s3fft = s2fft;
									s2 = s1;
									s2fft = s1fft;
									s1 = s;
									s1fft = dbfft;
								} else if (s == s1 || s < s2) {
									s3 = s2;
									s3fft = s2fft;
									s2 = s;
									s2fft = dbfft;
								} else if (s == s2 || s < s3) {
									s3 = s;
									s3fft = dbfft;
								}
							}
							Log.e("s1, s2, s3", "s1:" + s1fft + " s2:" + s2fft
									+ " s3:" + s3fft);
						} while (cursor.moveToNext());
					} finally {
						cursor.close();
						cursor = null;
					}
				} else {
					if (cursor != null) {
						cursor.close();
					}
				}
			}
			cursor = db.rawQuery("SELECT * FROM similarTable1 where fft= '"
					+ s1fft + "'", null);
			cursor.moveToFirst();
			nametext1.setText(cursor.getString(3));
			fft1.setText(cursor.getString(1) + "hz");
			imageUrl1 = imageUrl + cursor.getString(2) + ".jpg";

			drawImage();

			cursor = db.rawQuery("SELECT * FROM similarTable1 where fft= '"
					+ s2fft + "'", null);
			cursor.moveToFirst();
			nametext2.setText(cursor.getString(3));
			fft2.setText(cursor.getString(1) + "hz");
			imageUrl2 = imageUrl + cursor.getString(2) + ".jpg";

			drawImage();

			cursor = db.rawQuery("SELECT * FROM similarTable1 where fft= '"
					+ s3fft + "'", null);
			cursor.moveToFirst();
			nametext3.setText(cursor.getString(3));
			fft3.setText(cursor.getString(1) + "hz");
			imageUrl3 = imageUrl + cursor.getString(2) + ".jpg";

			drawImage();
		}

		for (int i = 0; i < 3; i++) {
			randid[i] = rand.nextInt(5) + 1;
		}

		double no1 = randid[0] + 5 * (fft / 7.8125 - 9);
		Log.e("no1", "" + no1);

		cursor = db.rawQuery("SELECT * FROM similarRank1Table where id = "
				+ no1 + " and fft = '" + fft + "'", null);
		cursor.moveToFirst();
		// cursor.moveToPosition(randid[0]);

		if (cursor.moveToFirst()) {
			try {
				do {
					nametext1.setText(cursor.getString(3));
					fft1.setText(cursor.getString(1) + "hz");
					imageUrl1 = imageUrl + cursor.getString(2) + ".jpg";

					drawImage();
				} while (cursor.moveToNext());
			} finally {
				cursor.close();
				cursor = null;
			}
		} else {
			if (cursor != null) {
				cursor.close();
			}
		}

		double no2 = randid[1] + 5 * (fft / 7.8125 - 9);
		Log.e("no2", "" + no2);

		cursor = db.rawQuery("SELECT * FROM similarRank2Table where id = "
				+ no2 + " and fft = '" + fft + "'", null);
		cursor.moveToFirst();
		// cursor.moveToPosition(randid[1]);

		if (cursor.moveToFirst()) {
			try {
				do {
					nametext2.setText(cursor.getString(3));
					fft2.setText(cursor.getString(1) + "hz");
					imageUrl2 = imageUrl + cursor.getString(2) + ".jpg";

					drawImage();
				} while (cursor.moveToNext());
			} finally {
				cursor.close();
				cursor = null;
			}
		} else {
			if (cursor != null) {
				cursor.close();
			}
		}

		double no3 = randid[2] + 5 * (fft / 7.8125 - 9);
		Log.e("no3", "" + no3);

		cursor = db.rawQuery("SELECT * FROM similarRank3Table where id = "
				+ no3 + " and fft = '" + fft + "'", null);
		cursor.moveToFirst();
		// cursor.moveToPosition(randid[2]);

		if (cursor.moveToFirst()) {
			try {
				do {
					nametext3.setText(cursor.getString(3));
					fft3.setText(cursor.getString(1) + "hz");
					imageUrl3 = imageUrl + cursor.getString(2) + ".jpg";

					drawImage();
				} while (cursor.moveToNext());
			} finally {
				cursor.close();
				cursor = null;
			}
		} else {
			if (cursor != null) {
				cursor.close();
			}
		}

		gallery.setOnClickListener(new OnClickListener() {
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

		group.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (groupno == 1) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("주변을 압도하는 마법의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 2) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("속삭이듯 달콤한 부드러운 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 3) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("우아하고 섬세한 평온의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 4) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("차가운 남성적 용기의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 5) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("냉정하고 상쾌한 자유의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 6) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("젊음과 희망을 노래하는 생명의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 7) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("성실하고 열정적인 자신감이 충만한 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 8) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("명랑, 발랄하며 적극적이고 경쾌한 즐거운 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 9) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("애교와 원숙미 넘치는 유혹의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 10) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
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

		image1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final Intent search = new Intent(Intent.ACTION_VIEW, Uri
						.parse(searchUrl + nametext1.getText().toString()));
				startActivity(search);
			}
		});
		image2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final Intent search = new Intent(Intent.ACTION_VIEW, Uri
						.parse(searchUrl + nametext2.getText().toString()));
				startActivity(search);
			}
		});
		image3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final Intent search = new Intent(Intent.ACTION_VIEW, Uri
						.parse(searchUrl + nametext3.getText().toString()));
				startActivity(search);
			}
		});

	}

	public void onBackPressed() {
		new AlertDialog.Builder(VoiceM_SimilarDB.this)
				.setTitle("목소리 닮은꼴 종료").setIcon(R.drawable.app_icon)
				.setMessage("목소리 닮은꼴을 종료하시겠습니까? 종료하면 메인화면으로 돌아갑니다.")
				.setPositiveButton("네", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
						overridePendingTransition(android.R.anim.fade_in,
								android.R.anim.fade_out);
						Toast.makeText(VoiceM_SimilarDB.this, "메인메뉴로 돌아갑니다.",
								Toast.LENGTH_SHORT).show();
					}
				})
				.setNeutralButton("다시 녹음",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(
										VoiceM_SimilarDB.this,
										VoiceM_Similar.class);
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

	// 이미지 그리기 메서드
	private void drawImage() {
		mProgress.setTitle("Voice Match");
		VoiceM_Similar vs = new VoiceM_Similar();
		mProgress.setMessage(vs.getName() + "님의 목소리 닮은 꼴을 분석 중입니다..");
		mProgress.show();

		DrawThread drawThread = new DrawThread(mainHandler);
		Thread thread = new Thread(drawThread);
		thread.setDaemon(true);
		thread.start();
	}

	// 핸들러 처리
	Handler mainHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 웹상의 이미지 바로 보여주기.
			case 0:
				Bitmap bm1 = (Bitmap) msg.obj;
				if (bm1 == null) {// 1 == null||bm2 == null||bm3 == null){
					Toast.makeText(VoiceM_SimilarDB.this,
							"이미지 표시 실패:인터넷을 확인해주세요", Toast.LENGTH_SHORT).show();
				} else {
					image1.setImageBitmap(bm1);

				}
				break;

			case 1:
				Bitmap bm2 = (Bitmap) msg.obj;
				if (bm2 == null) {// 1 == null||bm2 == null||bm3 == null){
					Toast.makeText(VoiceM_SimilarDB.this,
							"이미지 표시 실패:인터넷을 확인해주세요", Toast.LENGTH_SHORT).show();
				} else {
					image2.setImageBitmap(bm2);
				}
				break;
			case 2:
				Bitmap bm3 = (Bitmap) msg.obj;
				if (bm3 == null) {// 1 == null||bm2 == null||bm3 == null){
					Toast.makeText(VoiceM_SimilarDB.this,
							"이미지 표시 실패:인터넷을 확인해주세요", Toast.LENGTH_SHORT).show();
				} else {

					image3.setImageBitmap(bm3);
				}
				break;
			}

			if (mProgress.isShowing()) {
				mProgress.dismiss();
			}
		}
	};

	public void screenshot(Bitmap bm) {
		try {
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath(), "Voice Match");
			if (!file.exists()) {
				file.mkdirs();
			}

			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"목소리 닮은꼴_yyyy년 MM월 dd일 HH시 mm분 ss초");
			String filename = sdf.format(date);
			file = Environment.getExternalStorageDirectory();
			String path = file.getAbsolutePath() + "/Voice Match/" + filename
					+ ".jpg";

			FileOutputStream out = new FileOutputStream(path);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);

			Toast.makeText(VoiceM_SimilarDB.this,
					filename + ".jpg로 저장되셨습니다!", Toast.LENGTH_SHORT).show();

			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.parse("file://" + path);
			intent.setData(uri);
			sendBroadcast(intent);

		} catch (FileNotFoundException e) {
			Log.d("FileNotFoundException:", e.getMessage());
		}
	}

}

class VoiceSimilarDBHelper extends SQLiteOpenHelper {
	public VoiceSimilarDBHelper(Context context) {
		super(context, "voicesimilar.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE similarTable1 ( id INTEGER ,"
				+ " fft DOUBLE ," + " addr TEXT , " + " image TEXT);");

		db.execSQL("insert into similarTable1 values(1,	52	,	'c59'	,	'	운이	'	);");
		db.execSQL("insert into similarTable1 values(2,	60	,	'c57'	,	'	이훤	'	);");
		db.execSQL("insert into similarTable1 values(3,	64	,	'c58'	,	'	허염	'	);");
		db.execSQL("insert into similarTable1 values(4,	319.2	,	'c46'	,	'	희동이	'	);");
		db.execSQL("insert into similarTable1 values(5,	349.65	,	'c47'	,	'	세균맨	'	);");
		db.execSQL("insert into similarTable1 values(6,	367	,	'c48'	,	'	티몬	'	);");
		db.execSQL("insert into similarTable1 values(7,	413	,	'a40'	,	'	신생아  	'	);");
		db.execSQL("insert into similarTable1 values(8,	450.79	,	'c49'	,	'	공실이	'	);");
		db.execSQL("insert into similarTable1 values(9,	479	,	'c50'	,	'	피카츄	'	);");
		db.execSQL("insert into similarTable1 values(10,	521	,	'c51'	,	'	전현무7단고음	'	);");
		db.execSQL("insert into similarTable1 values(11,	549.716	,	'c52'	,	'	스머프	'	);");
		db.execSQL("insert into similarTable1 values(12,	580.56	,	'c53'	,	'	피글렛	'	);");
		db.execSQL("insert into similarTable1 values(13,	628.2	,	'c54'	,	'	깐깐징어	'	);");
		db.execSQL("insert into similarTable1 values(14,	656	,	'a42'	,	'	IU 1단 부스터	'	);");
		db.execSQL("insert into similarTable1 values(15,	699	,	'a43'	,	'	IU 2단 부스터	'	);");
		db.execSQL("insert into similarTable1 values(16,	742	,	'a46'	,	'	IU 3단 부스터	'	);");
		db.execSQL("insert into similarTable1 values(17,	762	,	'c55'	,	'	박지선돌고래	'	);");
		db.execSQL("insert into similarTable1 values(18,	780.062	,	'c56'	,	'	장동민12옥타브미	'	);");

		db.execSQL("CREATE TABLE similarRank1Table ( id INTEGER ,"
				+ " dbfft DOUBLE ," + " addr TEXT , " + " image TEXT , "
				+ "  fft DOUBLE);");

		db.execSQL("insert into similarRank1Table values(1,	71.36	,	'c60'	,	'	볼드모트	'	,	70.3125	);");
		db.execSQL("insert into similarRank1Table values(2,	71.36	,	'c61'	,	'	진구	'	,	70.3125	);");
		db.execSQL("insert into similarRank1Table values(3,	71.36	,	'c62'	,	'	헐크	'	,	70.3125	);");
		db.execSQL("insert into similarRank1Table values(4,	71.36	,	'c63'	,	'	방귀남	'	,	70.3125	);");
		db.execSQL("insert into similarRank1Table values(5,	71.36	,	'c64'	,	'	덤블도어	'	,	70.3125	);");
		db.execSQL("insert into similarRank1Table values(6,	78.24	,	'b118'	,	'	김명민	'	,	78.125	);");
		db.execSQL("insert into similarRank1Table values(7,	78.24	,	'b119'	,	'	쌈디	'	,	78.125	);");
		db.execSQL("insert into similarRank1Table values(8,	78.24	,	'b2'	,	'	강동원	'	,	78.125	);");
		db.execSQL("insert into similarRank1Table values(9,	78.24	,	'b120'	,	'	이종혁	'	,	78.125	);");
		db.execSQL("insert into similarRank1Table values(10,	78.24	,	'b3'	,	'	설경구	'	,	78.125	);");
		db.execSQL("insert into similarRank1Table values(11,	85.93	,	'b123'	,	'	배용준	'	,	85.9375	);");
		db.execSQL("insert into similarRank1Table values(12,	85.93	,	'b312'	,	'	하하	'	,	85.9375	);");
		db.execSQL("insert into similarRank1Table values(13,	85.93	,	'b5'	,	'	박시후	'	,	85.9375	);");
		db.execSQL("insert into similarRank1Table values(14,	85.93	,	'b215'	,	'	이병준	'	,	85.9375	);");
		db.execSQL("insert into similarRank1Table values(15,	85.93	,	'b193'	,	'	김태균	'	,	85.9375	);");
		db.execSQL("insert into similarRank1Table values(16,	93.24	,	'b9'	,	'	유승호	'	,	93.75	);");
		db.execSQL("insert into similarRank1Table values(17,	93.24	,	'b10'	,	'	원빈	'	,	93.75	);");
		db.execSQL("insert into similarRank1Table values(18,	93.24	,	'b226'	,	'	최민식	'	,	93.75	);");
		db.execSQL("insert into similarRank1Table values(19,	93.24	,	'b11'	,	'	환희	'	,	93.75	);");
		db.execSQL("insert into similarRank1Table values(20,	93.24	,	'b366'	,	'	김재원	'	,	93.75	);");
		db.execSQL("insert into similarRank1Table values(21,	101.56	,	'b15'	,	'	박건형	'	,	101.5625	);");
		db.execSQL("insert into similarRank1Table values(22,	101.56	,	'b126'	,	'	유준상	'	,	101.5625	);");
		db.execSQL("insert into similarRank1Table values(23,	101.56	,	'b16'	,	'	이현우	'	,	101.5625	);");
		db.execSQL("insert into similarRank1Table values(24,	101.56	,	'b274'	,	'	백일섭	'	,	101.5625	);");
		db.execSQL("insert into similarRank1Table values(25,	101.56	,	'b196'	,	'	마동석	'	,	101.5625	);");
		db.execSQL("insert into similarRank1Table values(26,	110	,	'a6'	,	'	유동근	'	,	109.375	);");
		db.execSQL("insert into similarRank1Table values(27,	110	,	'b361'	,	'	이태곤	'	,	109.375	);");
		db.execSQL("insert into similarRank1Table values(28,	110	,	'b128'	,	'	이범수	'	,	109.375	);");
		db.execSQL("insert into similarRank1Table values(29,	110	,	'b18'	,	'	지현우	'	,	109.375	);");
		db.execSQL("insert into similarRank1Table values(30,	110	,	'b290'	,	'	이대호	'	,	109.375	);");
		db.execSQL("insert into similarRank1Table values(31,	117.2	,	'b131'	,	'	김영광	'	,	117.1875	);");
		db.execSQL("insert into similarRank1Table values(32,	117.2	,	'b195'	,	'	류승범	'	,	117.1875	);");
		db.execSQL("insert into similarRank1Table values(33,	117.2	,	'b192'	,	'	김정태	'	,	117.1875	);");
		db.execSQL("insert into similarRank1Table values(34,	117.2	,	'b132'	,	'	조인성	'	,	117.1875	);");
		db.execSQL("insert into similarRank1Table values(35,	117.2	,	'b360'	,	'	이재룡	'	,	117.1875	);");
		db.execSQL("insert into similarRank1Table values(36,	125	,	'b133'	,	'	설운도	'	,	125	);");
		db.execSQL("insert into similarRank1Table values(37,	125	,	'b134'	,	'	박휘순	'	,	125	);");
		db.execSQL("insert into similarRank1Table values(38,	125	,	'b22'	,	'	엄기준	'	,	125	);");
		db.execSQL("insert into similarRank1Table values(39,	125	,	'b265'	,	'	김석훈	'	,	125	);");
		db.execSQL("insert into similarRank1Table values(40,	125	,	'a10'	,	'	한석규	'	,	125	);");
		db.execSQL("insert into similarRank1Table values(41,	132.24	,	'b276'	,	'	송중기	'	,	132.8125	);");
		db.execSQL("insert into similarRank1Table values(42,	132.24	,	'b24'	,	'	양현석	'	,	132.8125	);");
		db.execSQL("insert into similarRank1Table values(43,	132.24	,	'b206'	,	'	시완	'	,	132.8125	);");
		db.execSQL("insert into similarRank1Table values(44,	132.24	,	'b223'	,	'	조정석	'	,	132.8125	);");
		db.execSQL("insert into similarRank1Table values(45,	132.24	,	'b199'	,	'	박해일	'	,	132.8125	);");
		db.execSQL("insert into similarRank1Table values(46,	140.6	,	'b217'	,	'	이승기	'	,	140.625	);");
		db.execSQL("insert into similarRank1Table values(47,	140.6	,	'b26'	,	'	천정명	'	,	140.625	);");
		db.execSQL("insert into similarRank1Table values(48,	140.6	,	'b205'	,	'	송새벽	'	,	140.625	);");
		db.execSQL("insert into similarRank1Table values(49,	140.6	,	'b296'	,	'	이승엽	'	,	140.625	);");
		db.execSQL("insert into similarRank1Table values(50,	140.6	,	'b273'	,	'	김태우	'	,	140.625	);");
		db.execSQL("insert into similarRank1Table values(51,	148.4	,	'b219'	,	'	이한위	'	,	148.4375	);");
		db.execSQL("insert into similarRank1Table values(52,	148.4	,	'b298'	,	'	이준석	'	,	148.4375	);");
		db.execSQL("insert into similarRank1Table values(53,	148.4	,	'b214'	,	'	윤제문	'	,	148.4375	);");
		db.execSQL("insert into similarRank1Table values(54,	148.4	,	'b268'	,	'	김용만	'	,	148.4375	);");
		db.execSQL("insert into similarRank1Table values(55,	148.4	,	'b299'	,	'	이희준	'	,	148.4375	);");
		db.execSQL("insert into similarRank1Table values(56,	156	,	'b31'	,	'	김민종	'	,	156.25	);");
		db.execSQL("insert into similarRank1Table values(57,	156	,	'b32'	,	'	은지원	'	,	156.25	);");
		db.execSQL("insert into similarRank1Table values(58,	156	,	'b33'	,	'	성시경	'	,	156.25	);");
		db.execSQL("insert into similarRank1Table values(59,	156	,	'b201'	,	'	변희봉	'	,	156.25	);");
		db.execSQL("insert into similarRank1Table values(60,	156	,	'a20'	,	'	오세훈	'	,	156.25	);");
		db.execSQL("insert into similarRank1Table values(61,	164	,	'b40'	,	'	승리	'	,	164.0625	);");
		db.execSQL("insert into similarRank1Table values(62,	164	,	'b58'	,	'	노홍철	'	,	164.0625	);");
		db.execSQL("insert into similarRank1Table values(63,	164	,	'b292'	,	'	이문세	'	,	164.0625	);");
		db.execSQL("insert into similarRank1Table values(64,	164	,	'b149'	,	'	윤문식	'	,	164.0625	);");
		db.execSQL("insert into similarRank1Table values(65,	164	,	'b311'	,	'	케이윌	'	,	164.0625	);");
		db.execSQL("insert into similarRank1Table values(66,	172.5	,	'b42'	,	'	강승윤	'	,	171.875	);");
		db.execSQL("insert into similarRank1Table values(67,	172.5	,	'b137'	,	'	재범	'	,	171.875	);");
		db.execSQL("insert into similarRank1Table values(68,	172.5	,	'a22'	,	'	김태원	'	,	171.875	);");
		db.execSQL("insert into similarRank1Table values(69,	172.5	,	'a23'	,	'	김C	'	,	171.875	);");
		db.execSQL("insert into similarRank1Table values(70,	172.5	,	'b43'	,	'	서지석	'	,	171.875	);");
		db.execSQL("insert into similarRank1Table values(71,	180	,	'b139'	,	'	차태현	'	,	179.6875	);");
		db.execSQL("insert into similarRank1Table values(72,	180	,	'b259'	,	'	허안나	'	,	179.6875	);");
		db.execSQL("insert into similarRank1Table values(73,	180	,	'b267'	,	'	김연우	'	,	179.6875	);");
		db.execSQL("insert into similarRank1Table values(74,	180	,	'b48'	,	'	붐	'	,	179.6875	);");
		db.execSQL("insert into similarRank1Table values(75,	180	,	'b141'	,	'	김국진	'	,	179.6875	);");
		db.execSQL("insert into similarRank1Table values(76,	187.3	,	'b211'	,	'	우현	'	,	187.5	);");
		db.execSQL("insert into similarRank1Table values(77,	187.3	,	'b248'	,	'	윤여정	'	,	187.5	);");
		db.execSQL("insert into similarRank1Table values(78,	187.3	,	'b53'	,	'	정형돈	'	,	187.5	);");
		db.execSQL("insert into similarRank1Table values(79,	187.3	,	'b333'	,	'	수애	'	,	187.5	);");
		db.execSQL("insert into similarRank1Table values(80,	187.3	,	'b328'	,	'	손담비	'	,	187.5	);");
		db.execSQL("insert into similarRank1Table values(81,	194	,	'a25'	,	'	김정일 위원	'	,	195.3125	);");
		db.execSQL("insert into similarRank1Table values(82,	194	,	'b148'	,	'	김해숙	'	,	195.3125	);");
		db.execSQL("insert into similarRank1Table values(83,	194	,	'b229'	,	'	고수희	'	,	195.3125	);");
		db.execSQL("insert into similarRank1Table values(84,	194	,	'b59'	,	'	이하이	'	,	195.3125	);");
		db.execSQL("insert into similarRank1Table values(85,	194	,	'b324'	,	'	박근혜	'	,	195.3125	);");
		db.execSQL("insert into similarRank1Table values(86,	203.68	,	'b352'	,	'	지연	'	,	203.125	);");
		db.execSQL("insert into similarRank1Table values(87,	203.68	,	'b158'	,	'	전원주	'	,	203.125	);");
		db.execSQL("insert into similarRank1Table values(88,	203.68	,	'b159'	,	'	이승환	'	,	203.125	);");
		db.execSQL("insert into similarRank1Table values(89,	203.68	,	'b160'	,	'	김경진	'	,	203.125	);");
		db.execSQL("insert into similarRank1Table values(90,	203.68	,	'b68'	,	'	최지우	'	,	203.125	);");
		db.execSQL("insert into similarRank1Table values(91,	211.3	,	'b71'	,	'	강유미	'	,	210.9375	);");
		db.execSQL("insert into similarRank1Table values(92,	211.3	,	'b166'	,	'	박하선	'	,	210.9375	);");
		db.execSQL("insert into similarRank1Table values(93,	211.3	,	'b355'	,	'	태연	'	,	210.9375	);");
		db.execSQL("insert into similarRank1Table values(94,	211.3	,	'b72'	,	'	박해미	'	,	210.9375	);");
		db.execSQL("insert into similarRank1Table values(95,	211.3	,	'b73'	,	'	정주리	'	,	210.9375	);");
		db.execSQL("insert into similarRank1Table values(96,	220	,	'b78'	,	'	이연희	'	,	218.75	);");
		db.execSQL("insert into similarRank1Table values(97,	220	,	'b245'	,	'	안선영	'	,	218.75	);");
		db.execSQL("insert into similarRank1Table values(98,	220	,	'b325'	,	'	박소현	'	,	218.75	);");
		db.execSQL("insert into similarRank1Table values(99,	220	,	'a29'	,	'	정세진 앵커	'	,	218.75	);");
		db.execSQL("insert into similarRank1Table values(100,	220	,	'b174'	,	'	오나미	'	,	218.75	);");
		db.execSQL("insert into similarRank1Table values(101,	227.4	,	'b85'	,	'	김하늘	'	,	226.5625	);");
		db.execSQL("insert into similarRank1Table values(102,	227.4	,	'b346'	,	'	이요원	'	,	226.5625	);");
		db.execSQL("insert into similarRank1Table values(103,	227.4	,	'b320'	,	'	남상미	'	,	226.5625	);");
		db.execSQL("insert into similarRank1Table values(104,	227.4	,	'b239'	,	'	문근영	'	,	226.5625	);");
		db.execSQL("insert into similarRank1Table values(105,	227.4	,	'b86'	,	'	김현주	'	,	226.5625	);");
		db.execSQL("insert into similarRank1Table values(106,	235	,	'b92'	,	'	한가인	'	,	234.375	);");
		db.execSQL("insert into similarRank1Table values(107,	235	,	'a31'	,	'	오바마	'	,	234.375	);");
		db.execSQL("insert into similarRank1Table values(108,	235	,	'b235'	,	'	김선경	'	,	234.375	);");
		db.execSQL("insert into similarRank1Table values(109,	235	,	'b357'	,	'	하춘화	'	,	234.375	);");
		db.execSQL("insert into similarRank1Table values(110,	235	,	'b93'	,	'	수지	'	,	234.375	);");
		db.execSQL("insert into similarRank1Table values(111,	242.4	,	'b98'	,	'	소희	'	,	242.1875	);");
		db.execSQL("insert into similarRank1Table values(112,	242.4	,	'b99'	,	'	송윤아	'	,	242.1875	);");
		db.execSQL("insert into similarRank1Table values(113,	242.4	,	'b234'	,	'	김보미	'	,	242.1875	);");
		db.execSQL("insert into similarRank1Table values(114,	242.4	,	'b252'	,	'	장영남	'	,	242.1875	);");
		db.execSQL("insert into similarRank1Table values(115,	242.4	,	'b100'	,	'	하지원	'	,	242.1875	);");
		db.execSQL("insert into similarRank1Table values(116,	249	,	'a33'	,	'	김연아	'	,	250	);");
		db.execSQL("insert into similarRank1Table values(117,	249	,	'b329'	,	'	손연재	'	,	250	);");
		db.execSQL("insert into similarRank1Table values(118,	249	,	'b343'	,	'	윤진이	'	,	250	);");
		db.execSQL("insert into similarRank1Table values(119,	249	,	'b256'	,	'	최강희	'	,	250	);");
		db.execSQL("insert into similarRank1Table values(120,	249	,	'b103'	,	'	안혜경	'	,	250	);");
		db.execSQL("insert into similarRank1Table values(121,	257.81	,	'b179'	,	'	사유리	'	,	257.8125	);");
		db.execSQL("insert into similarRank1Table values(122,	257.81	,	'b180'	,	'	김다래	'	,	257.8125	);");
		db.execSQL("insert into similarRank1Table values(123,	257.81	,	'b181'	,	'	가인	'	,	257.8125	);");
		db.execSQL("insert into similarRank1Table values(124,	257.81	,	'b107'	,	'	장윤주	'	,	257.8125	);");
		db.execSQL("insert into similarRank1Table values(125,	257.81	,	'b182'	,	'	배다혜	'	,	257.8125	);");
		db.execSQL("insert into similarRank1Table values(126,	249	,	'b237'	,	'	김영희	'	,	265.625	);");
		db.execSQL("insert into similarRank1Table values(127,	249	,	'b162'	,	'	박희진	'	,	265.625	);");
		db.execSQL("insert into similarRank1Table values(128,	249	,	'b318'	,	'	김희선	'	,	265.625	);");
		db.execSQL("insert into similarRank1Table values(129,	249	,	'b336'	,	'	신애라	'	,	265.625	);");
		db.execSQL("insert into similarRank1Table values(130,	249	,	'b232'	,	'	김남주	'	,	265.625	);");
		db.execSQL("insert into similarRank1Table values(131,	273.4	,	'b114'	,	'	전도연	'	,	273.4375	);");
		db.execSQL("insert into similarRank1Table values(132,	273.4	,	'b241'	,	'	박진주	'	,	273.4375	);");
		db.execSQL("insert into similarRank1Table values(133,	273.4	,	'b236'	,	'	김영옥	'	,	273.4375	);");
		db.execSQL("insert into similarRank1Table values(134,	273.4	,	'b326'	,	'	박원숙	'	,	273.4375	);");
		db.execSQL("insert into similarRank1Table values(135,	273.4	,	'b260'	,	'	홍진희	'	,	273.4375	);");
		db.execSQL("insert into similarRank1Table values(136,	282.362	,	'c1'	,	'	다중이	'	,	281.25	);");
		db.execSQL("insert into similarRank1Table values(137,	282.362	,	'c2'	,	'	기봉이	'	,	281.25	);");
		db.execSQL("insert into similarRank1Table values(138,	282.362	,	'c3'	,	'	삼순이	'	,	281.25	);");
		db.execSQL("insert into similarRank1Table values(139,	282.362	,	'c4'	,	'	미소지나	'	,	281.25	);");
		db.execSQL("insert into similarRank1Table values(140,	282.362	,	'c5'	,	'	노은설	'	,	281.25	);");
		db.execSQL("insert into similarRank1Table values(141,	290.7	,	'c16'	,	'	할미넴	'	,	289.0625	);");
		db.execSQL("insert into similarRank1Table values(142,	290.7	,	'c17'	,	'	하니	'	,	289.0625	);");
		db.execSQL("insert into similarRank1Table values(143,	290.7	,	'c18'	,	'	뽀로로	'	,	289.0625	);");
		db.execSQL("insert into similarRank1Table values(144,	290.7	,	'c19'	,	'	나애리	'	,	289.0625	);");
		db.execSQL("insert into similarRank1Table values(145,	290.7	,	'c20'	,	'	이숙	'	,	289.0625	);");
		db.execSQL("insert into similarRank1Table values(146,	298.05	,	'c31'	,	'	마지심슨	'	,	296.875	);");
		db.execSQL("insert into similarRank1Table values(147,	298.05	,	'c32'	,	'	백여치	'	,	296.875	);");
		db.execSQL("insert into similarRank1Table values(148,	298.05	,	'c33'	,	'	영심이	'	,	296.875	);");
		db.execSQL("insert into similarRank1Table values(149,	298.05	,	'c34'	,	'	땡칠이	'	,	296.875	);");
		db.execSQL("insert into similarRank1Table values(150,	298.05	,	'c35'	,	'	천지호	'	,	296.875	);");

		db.execSQL("CREATE TABLE similarRank2Table ( id INTEGER ,"
				+ " dbfft DOUBLE ," + " addr TEXT , " + " image TEXT , "
				+ "  fft DOUBLE);");

		db.execSQL("insert into similarRank2Table values(1,	73.8	,	'c65'	,	'	또가스	'	,	70.3125	);");
		db.execSQL("insert into similarRank2Table values(2,	73.8	,	'c66'	,	'	꼬부기	'	,	70.3125	);");
		db.execSQL("insert into similarRank2Table values(3,	73.8	,	'c67'	,	'	이상해씨	'	,	70.3125	);");
		db.execSQL("insert into similarRank2Table values(4,	73.8	,	'c68'	,	'	서정학	'	,	70.3125	);");
		db.execSQL("insert into similarRank2Table values(5,	73.8	,	'c69'	,	'	김형일	'	,	70.3125	);");
		db.execSQL("insert into similarRank2Table values(6,	80	,	'b275'	,	'	송일국	'	,	78.125	);");
		db.execSQL("insert into similarRank2Table values(7,	80	,	'b4'	,	'	박신양	'	,	78.125	);");
		db.execSQL("insert into similarRank2Table values(8,	80	,	'b121'	,	'	박명수	'	,	78.125	);");
		db.execSQL("insert into similarRank2Table values(9,	80	,	'b289'	,	'	이계인	'	,	78.125	);");
		db.execSQL("insert into similarRank2Table values(10,	80	,	'b122'	,	'	김무열	'	,	78.125	);");
		db.execSQL("insert into similarRank2Table values(11,	87.1	,	'b6'	,	'	정우성	'	,	85.9375	);");
		db.execSQL("insert into similarRank2Table values(12,	87.1	,	'b191'	,	'	김윤석	'	,	85.9375	);");
		db.execSQL("insert into similarRank2Table values(13,	87.1	,	'b278'	,	'	숀리	'	,	85.9375	);");
		db.execSQL("insert into similarRank2Table values(14,	87.1	,	'b7'	,	'	장동건	'	,	85.9375	);");
		db.execSQL("insert into similarRank2Table values(15,	87.1	,	'b8'	,	'	송강호	'	,	85.9375	);");
		db.execSQL("insert into similarRank2Table values(16,	97.1	,	'b12'	,	'	최진혁	'	,	93.75	);");
		db.execSQL("insert into similarRank2Table values(17,	97.1	,	'b124'	,	'	김구라	'	,	93.75	);");
		db.execSQL("insert into similarRank2Table values(18,	97.1	,	'b295'	,	'	이수근	'	,	93.75	);");
		db.execSQL("insert into similarRank2Table values(19,	97.1	,	'b294'	,	'	이선균	'	,	93.75	);");
		db.execSQL("insert into similarRank2Table values(20,	97.1	,	'b218'	,	'	이천희	'	,	93.75	);");
		db.execSQL("insert into similarRank2Table values(21,	105.2	,	'b370'	,	'	지진희	'	,	101.5625	);");
		db.execSQL("insert into similarRank2Table values(22,	105.2	,	'b17'	,	'	이순재	'	,	101.5625	);");
		db.execSQL("insert into similarRank2Table values(23,	105.2	,	'b224'	,	'	조진웅	'	,	101.5625	);");
		db.execSQL("insert into similarRank2Table values(24,	105.2	,	'b291'	,	'	이덕화	'	,	101.5625	);");
		db.execSQL("insert into similarRank2Table values(25,	105.2	,	'b207'	,	'	양동근	'	,	101.5625	);");
		db.execSQL("insert into similarRank2Table values(26,	112.35	,	'b364'	,	'	유인촌	'	,	109.375	);");
		db.execSQL("insert into similarRank2Table values(27,	112.35	,	'b190'	,	'	김병옥	'	,	109.375	);");
		db.execSQL("insert into similarRank2Table values(28,	112.35	,	'b19'	,	'	송승헌	'	,	109.375	);");
		db.execSQL("insert into similarRank2Table values(29,	112.35	,	'b365'	,	'	정준호	'	,	109.375	);");
		db.execSQL("insert into similarRank2Table values(30,	112.35	,	'b129'	,	'	이민호	'	,	109.375	);");
		db.execSQL("insert into similarRank2Table values(31,	118.96	,	'b21'	,	'	최다니엘	'	,	117.1875	);");
		db.execSQL("insert into similarRank2Table values(32,	118.96	,	'b208'	,	'	오달수	'	,	117.1875	);");
		db.execSQL("insert into similarRank2Table values(33,	118.96	,	'b263'	,	'	김래원	'	,	117.1875	);");
		db.execSQL("insert into similarRank2Table values(34,	118.96	,	'b261'	,	'	강민호	'	,	117.1875	);");
		db.execSQL("insert into similarRank2Table values(35,	118.96	,	'b293'	,	'	이상윤	'	,	117.1875	);");
		db.execSQL("insert into similarRank2Table values(36,	127	,	'b305'	,	'	주상욱	'	,	125	);");
		db.execSQL("insert into similarRank2Table values(37,	127	,	'b209'	,	'	오정세	'	,	125	);");
		db.execSQL("insert into similarRank2Table values(38,	127	,	'b54'	,	'	김수로	'	,	125	);");
		db.execSQL("insert into similarRank2Table values(39,	127	,	'b213'	,	'	윤시윤	'	,	125	);");
		db.execSQL("insert into similarRank2Table values(40,	127	,	'a12'	,	'	노회찬	'	,	125	);");
		db.execSQL("insert into similarRank2Table values(41,	130	,	'b220'	,	'	정찬우	'	,	132.8125	);");
		db.execSQL("insert into similarRank2Table values(42,	130	,	'a15'	,	'	조지클루니	'	,	132.8125	);");
		db.execSQL("insert into similarRank2Table values(43,	130	,	'a13'	,	'	김남길	'	,	132.8125	);");
		db.execSQL("insert into similarRank2Table values(44,	130	,	'a17'	,	'	김수현	'	,	132.8125	);");
		db.execSQL("insert into similarRank2Table values(45,	130	,	'b23'	,	'	안성기	'	,	132.8125	);");
		db.execSQL("insert into similarRank2Table values(46,	137	,	'b264'	,	'	김상중	'	,	140.625	);");
		db.execSQL("insert into similarRank2Table values(47,	137	,	'b303'	,	'	조영남	'	,	140.625	);");
		db.execSQL("insert into similarRank2Table values(48,	137	,	'b363'	,	'	박찬호	'	,	140.625	);");
		db.execSQL("insert into similarRank2Table values(49,	137	,	'a18'	,	'	정동영	'	,	140.625	);");
		db.execSQL("insert into similarRank2Table values(50,	137	,	'a19'	,	'	삼장법사	'	,	140.625	);");
		db.execSQL("insert into similarRank2Table values(51,	151	,	'b310'	,	'	최수종	'	,	148.4375	);");
		db.execSQL("insert into similarRank2Table values(52,	151	,	'b29'	,	'	이봉원	'	,	148.4375	);");
		db.execSQL("insert into similarRank2Table values(53,	151	,	'b189'	,	'	권해효	'	,	148.4375	);");
		db.execSQL("insert into similarRank2Table values(54,	151	,	'b30'	,	'	이경규	'	,	148.4375	);");
		db.execSQL("insert into similarRank2Table values(55,	151	,	'b66'	,	'	이하늘	'	,	148.4375	);");
		db.execSQL("insert into similarRank2Table values(56,	160.95	,	'b34'	,	'	김범수	'	,	156.25	);");
		db.execSQL("insert into similarRank2Table values(57,	160.95	,	'b35'	,	'	이장우	'	,	156.25	);");
		db.execSQL("insert into similarRank2Table values(58,	160.95	,	'b36'	,	'	소지섭	'	,	156.25	);");
		db.execSQL("insert into similarRank2Table values(59,	160.95	,	'b286'	,	'	연정훈	'	,	156.25	);");
		db.execSQL("insert into similarRank2Table values(60,	160.95	,	'b313'	,	'	현빈	'	,	156.25	);");
		db.execSQL("insert into similarRank2Table values(61,	168	,	'b135'	,	'	강지환	'	,	164.0625	);");
		db.execSQL("insert into similarRank2Table values(62,	168	,	'b39'	,	'	최효종	'	,	164.0625	);");
		db.execSQL("insert into similarRank2Table values(63,	168	,	'b136'	,	'	봉태규	'	,	164.0625	);");
		db.execSQL("insert into similarRank2Table values(64,	168	,	'a24'	,	'	휘성	'	,	164.0625	);");
		db.execSQL("insert into similarRank2Table values(65,	168	,	'b202'	,	'	산이	'	,	164.0625	);");
		db.execSQL("insert into similarRank2Table values(66,	176.45	,	'b44'	,	'	장동민	'	,	171.875	);");
		db.execSQL("insert into similarRank2Table values(67,	176.45	,	'b304'	,	'	조형기	'	,	171.875	);");
		db.execSQL("insert into similarRank2Table values(68,	176.45	,	'b323'	,	'	박경림	'	,	171.875	);");
		db.execSQL("insert into similarRank2Table values(69,	176.45	,	'b45'	,	'	천명훈	'	,	171.875	);");
		db.execSQL("insert into similarRank2Table values(70,	176.45	,	'b287'	,	'	온유	'	,	171.875	);");
		db.execSQL("insert into similarRank2Table values(71,	182.5	,	'b142'	,	'	홍석천	'	,	179.6875	);");
		db.execSQL("insert into similarRank2Table values(72,	182.5	,	'b67'	,	'	우종완	'	,	179.6875	);");
		db.execSQL("insert into similarRank2Table values(73,	182.5	,	'b262'	,	'	김경호	'	,	179.6875	);");
		db.execSQL("insert into similarRank2Table values(74,	182.5	,	'b49'	,	'	서경석	'	,	179.6875	);");
		db.execSQL("insert into similarRank2Table values(75,	182.5	,	'b50'	,	'	유재석	'	,	179.6875	);");
		db.execSQL("insert into similarRank2Table values(76,	190	,	'b315'	,	'	김서형	'	,	187.5	);");
		db.execSQL("insert into similarRank2Table values(77,	190	,	'b145'	,	'	윤미래	'	,	187.5	);");
		db.execSQL("insert into similarRank2Table values(78,	190	,	'b55'	,	'	김주하앵커	'	,	187.5	);");
		db.execSQL("insert into similarRank2Table values(79,	190	,	'b200'	,	'	박현빈	'	,	187.5	);");
		db.execSQL("insert into similarRank2Table values(80,	190	,	'b212'	,	'	유해진	'	,	187.5	);");
		db.execSQL("insert into similarRank2Table values(81,	197.8	,	'b150'	,	'	알리	'	,	195.3125	);");
		db.execSQL("insert into similarRank2Table values(82,	197.8	,	'b344'	,	'	이수나	'	,	195.3125	);");
		db.execSQL("insert into similarRank2Table values(83,	197.8	,	'b61'	,	'	김수미	'	,	195.3125	);");
		db.execSQL("insert into similarRank2Table values(84,	197.8	,	'b151'	,	'	씨엘	'	,	195.3125	);");
		db.execSQL("insert into similarRank2Table values(85,	197.8	,	'b154'	,	'	신은경	'	,	195.3125	);");
		db.execSQL("insert into similarRank2Table values(86,	207.89	,	'b367'	,	'	전인화	'	,	203.125	);");
		db.execSQL("insert into similarRank2Table values(87,	207.89	,	'b164'	,	'	박시연	'	,	203.125	);");
		db.execSQL("insert into similarRank2Table values(88,	207.89	,	'b253'	,	'	전미선	'	,	203.125	);");
		db.execSQL("insert into similarRank2Table values(89,	207.89	,	'b372'	,	'	고소영	'	,	203.125	);");
		db.execSQL("insert into similarRank2Table values(90,	207.89	,	'b165'	,	'	박칼린	'	,	203.125	);");
		db.execSQL("insert into similarRank2Table values(91,	215	,	'b168'	,	'	배현진	'	,	210.9375	);");
		db.execSQL("insert into similarRank2Table values(92,	215	,	'b251'	,	'	이윤지	'	,	210.9375	);");
		db.execSQL("insert into similarRank2Table values(93,	215	,	'a27'	,	'	이명박	'	,	210.9375	);");
		db.execSQL("insert into similarRank2Table values(94,	215	,	'b75'	,	'	신봉선	'	,	210.9375	);");
		db.execSQL("insert into similarRank2Table values(95,	215	,	'b171'	,	'	신보라	'	,	210.9375	);");
		db.execSQL("insert into similarRank2Table values(96,	222.56	,	'b79'	,	'	이민정	'	,	218.75	);");
		db.execSQL("insert into similarRank2Table values(97,	222.56	,	'b175'	,	'	박민영	'	,	218.75	);");
		db.execSQL("insert into similarRank2Table values(98,	222.56	,	'b80'	,	'	구혜선	'	,	218.75	);");
		db.execSQL("insert into similarRank2Table values(99,	222.56	,	'b337'	,	'	심혜진	'	,	218.75	);");
		db.execSQL("insert into similarRank2Table values(100,	222.56	,	'b81'	,	'	고현정	'	,	218.75	);");
		db.execSQL("insert into similarRank2Table values(101,	230	,	'b87'	,	'	손은서	'	,	226.5625	);");
		db.execSQL("insert into similarRank2Table values(102,	230	,	'b233'	,	'	김민영	'	,	226.5625	);");
		db.execSQL("insert into similarRank2Table values(103,	230	,	'b88'	,	'	김소원앵커	'	,	226.5625	);");
		db.execSQL("insert into similarRank2Table values(104,	230	,	'b89'	,	'	박지민	'	,	226.5625	);");
		db.execSQL("insert into similarRank2Table values(105,	230	,	'b91'	,	'	조권	'	,	226.5625	);");
		db.execSQL("insert into similarRank2Table values(106,	237	,	'a32'	,	'	나승연	'	,	234.375	);");
		db.execSQL("insert into similarRank2Table values(107,	237	,	'b258'	,	'	크리스탈	'	,	234.375	);");
		db.execSQL("insert into similarRank2Table values(108,	237	,	'b95'	,	'	오영실	'	,	234.375	);");
		db.execSQL("insert into similarRank2Table values(109,	237	,	'b254'	,	'	정경미	'	,	234.375	);");
		db.execSQL("insert into similarRank2Table values(110,	237	,	'b96'	,	'	박미선	'	,	234.375	);");
		db.execSQL("insert into similarRank2Table values(111,	244.18	,	'b349'	,	'	전지현	'	,	242.1875	);");
		db.execSQL("insert into similarRank2Table values(112,	244.18	,	'b347'	,	'	이효리	'	,	242.1875	);");
		db.execSQL("insert into similarRank2Table values(113,	244.18	,	'b342'	,	'	윤아	'	,	242.1875	);");
		db.execSQL("insert into similarRank2Table values(114,	244.18	,	'b340'	,	'	유이	'	,	242.1875	);");
		db.execSQL("insert into similarRank2Table values(115,	244.18	,	'b62'	,	'	최여진	'	,	242.1875	);");
		db.execSQL("insert into similarRank2Table values(116,	247.87	,	'b101'	,	'	바다	'	,	250	);");
		db.execSQL("insert into similarRank2Table values(117,	247.87	,	'b255'	,	'	쥬니	'	,	250	);");
		db.execSQL("insert into similarRank2Table values(118,	247.87	,	'b338'	,	'	엄앵란	'	,	250	);");
		db.execSQL("insert into similarRank2Table values(119,	247.87	,	'b368'	,	'	장나라	'	,	250	);");
		db.execSQL("insert into similarRank2Table values(120,	247.87	,	'b102'	,	'	백진희	'	,	250	);");
		db.execSQL("insert into similarRank2Table values(121,	260	,	'a36'	,	'	박정현	'	,	257.8125	);");
		db.execSQL("insert into similarRank2Table values(122,	260	,	'b183'	,	'	윤세아	'	,	257.8125	);");
		db.execSQL("insert into similarRank2Table values(123,	260	,	'b185'	,	'	서민정	'	,	257.8125	);");
		db.execSQL("insert into similarRank2Table values(124,	260	,	'b108'	,	'	유인나	'	,	257.8125	);");
		db.execSQL("insert into similarRank2Table values(125,	260	,	'b109'	,	'	은정	'	,	257.8125	);");
		db.execSQL("insert into similarRank2Table values(126,	267	,	'b184'	,	'	에일리	'	,	265.625	);");
		db.execSQL("insert into similarRank2Table values(127,	267	,	'b177'	,	'	김자옥	'	,	265.625	);");
		db.execSQL("insert into similarRank2Table values(128,	267	,	'b330'	,	'	손예진	'	,	265.625	);");
		db.execSQL("insert into similarRank2Table values(129,	267	,	'b83'	,	'	강민경	'	,	265.625	);");
		db.execSQL("insert into similarRank2Table values(130,	267	,	'b332'	,	'	송혜교	'	,	265.625	);");
		db.execSQL("insert into similarRank2Table values(131,	270	,	'b240'	,	'	박준금	'	,	273.4375	);");
		db.execSQL("insert into similarRank2Table values(132,	270	,	'b112'	,	'	한예슬	'	,	273.4375	);");
		db.execSQL("insert into similarRank2Table values(133,	270	,	'a35'	,	'	현영	'	,	273.4375	);");
		db.execSQL("insert into similarRank2Table values(134,	270	,	'a37'	,	'	힐러리	'	,	273.4375	);");
		db.execSQL("insert into similarRank2Table values(135,	270	,	'b113'	,	'	김새롬	'	,	273.4375	);");
		db.execSQL("insert into similarRank2Table values(136,	285.713	,	'c6'	,	'	나상실	'	,	281.25	);");
		db.execSQL("insert into similarRank2Table values(137,	285.713	,	'c7'	,	'	남달구	'	,	281.25	);");
		db.execSQL("insert into similarRank2Table values(138,	285.713	,	'c8'	,	'	박개인	'	,	281.25	);");
		db.execSQL("insert into similarRank2Table values(139,	285.713	,	'c9'	,	'	오들희	'	,	281.25	);");
		db.execSQL("insert into similarRank2Table values(140,	285.713	,	'c10'	,	'	홍세라	'	,	281.25	);");
		db.execSQL("insert into similarRank2Table values(141,	293.062	,	'c21'	,	'	세일러문	'	,	289.0625	);");
		db.execSQL("insert into similarRank2Table values(142,	293.062	,	'c22'	,	'	네티	'	,	289.0625	);");
		db.execSQL("insert into similarRank2Table values(143,	293.062	,	'c23'	,	'	김애경	'	,	289.0625	);");
		db.execSQL("insert into similarRank2Table values(144,	293.062	,	'c24'	,	'	미달이	'	,	289.0625	);");
		db.execSQL("insert into similarRank2Table values(145,	293.062	,	'c25'	,	'	다름이	'	,	289.0625	);");
		db.execSQL("insert into similarRank2Table values(146,	301.4	,	'c36'	,	'	옥히	'	,	296.875	);");
		db.execSQL("insert into similarRank2Table values(147,	301.4	,	'c37'	,	'	민소희	'	,	296.875	);");
		db.execSQL("insert into similarRank2Table values(148,	301.4	,	'c38'	,	'	하쿠	'	,	296.875	);");
		db.execSQL("insert into similarRank2Table values(149,	301.4	,	'c39'	,	'	금잔디	'	,	296.875	);");
		db.execSQL("insert into similarRank2Table values(150,	301.4	,	'c40'	,	'	구애정	'	,	296.875	);");

		db.execSQL("CREATE TABLE similarRank3Table ( id INTEGER ,"
				+ " dbfft DOUBLE ," + " addr TEXT , " + " image TEXT , "
				+ "  fft DOUBLE);");

		db.execSQL("insert into similarRank3Table values(1,	75	,	'a3'	,	'	이병헌	'	,	70.3125	);");
		db.execSQL("insert into similarRank3Table values(2,	75	,	'b1'	,	'	탑	'	,	70.3125	);");
		db.execSQL("insert into similarRank3Table values(3,	75	,	'b115'	,	'	김기현	'	,	70.3125	);");
		db.execSQL("insert into similarRank3Table values(4,	75	,	'b116'	,	'	JK김동욱	'	,	70.3125	);");
		db.execSQL("insert into similarRank3Table values(5,	75	,	'b117'	,	'	임재범	'	,	70.3125	);");
		db.execSQL("insert into similarRank3Table values(6,	81.762	,	'b194'	,	'	류승룡	'	,	78.125	);");
		db.execSQL("insert into similarRank3Table values(7,	81.762	,	'b362'	,	'	김준호	'	,	78.125	);");
		db.execSQL("insert into similarRank3Table values(8,	81.762	,	'c70'	,	'	이종현	'	,	78.125	);");
		db.execSQL("insert into similarRank3Table values(9,	81.762	,	'c71'	,	'	리암니슨	'	,	78.125	);");
		db.execSQL("insert into similarRank3Table values(10,	81.762	,	'c72'	,	'	알란릭맨	'	,	78.125	);");
		db.execSQL("insert into similarRank3Table values(11,	88.96	,	'b270'	,	'	김장훈	'	,	85.9375	);");
		db.execSQL("insert into similarRank3Table values(12,	88.96	,	'c73'	,	'	구준표	'	,	85.9375	);");
		db.execSQL("insert into similarRank3Table values(13,	88.96	,	'c74'	,	'	이정록	'	,	85.9375	);");
		db.execSQL("insert into similarRank3Table values(14,	88.96	,	'c75'	,	'	독고진	'	,	85.9375	);");
		db.execSQL("insert into similarRank3Table values(15,	88.96	,	'c76'	,	'	임태산	'	,	85.9375	);");
		db.execSQL("insert into similarRank3Table values(16,	98	,	'b221'	,	'	정호빈	'	,	93.75	);");
		db.execSQL("insert into similarRank3Table values(17,	98	,	'b125'	,	'	강호동	'	,	93.75	);");
		db.execSQL("insert into similarRank3Table values(18,	98	,	'b13'	,	'	여진구	'	,	93.75	);");
		db.execSQL("insert into similarRank3Table values(19,	98	,	'b14'	,	'	최민수	'	,	93.75	);");
		db.execSQL("insert into similarRank3Table values(20,	98	,	'b269'	,	'	김응수	'	,	93.75	);");
		db.execSQL("insert into similarRank3Table values(21,	108	,	'a5'	,	'	부시	'	,	101.5625	);");
		db.execSQL("insert into similarRank3Table values(22,	108	,	'b281'	,	'	신성일	'	,	101.5625	);");
		db.execSQL("insert into similarRank3Table values(23,	108	,	'b203'	,	'	성동일	'	,	101.5625	);");
		db.execSQL("insert into similarRank3Table values(24,	108	,	'b127'	,	'	홍성흔	'	,	101.5625	);");
		db.execSQL("insert into similarRank3Table values(25,	108	,	'b309'	,	'	최불암	'	,	101.5625	);");
		db.execSQL("insert into similarRank3Table values(26,	114.17	,	'b20'	,	'	김현중	'	,	109.375	);");
		db.execSQL("insert into similarRank3Table values(27,	114.17	,	'b308'	,	'	차승원	'	,	109.375	);");
		db.execSQL("insert into similarRank3Table values(28,	114.17	,	'b130'	,	'	유세윤	'	,	109.375	);");
		db.execSQL("insert into similarRank3Table values(29,	114.17	,	'b204'	,	'	손병호	'	,	109.375	);");
		db.execSQL("insert into similarRank3Table values(30,	114.17	,	'b216'	,	'	이성민	'	,	109.375	);");
		db.execSQL("insert into similarRank3Table values(31,	120	,	'b186'	,	'	고창석	'	,	117.1875	);");
		db.execSQL("insert into similarRank3Table values(32,	120	,	'a9'	,	'	박유천	'	,	117.1875	);");
		db.execSQL("insert into similarRank3Table values(33,	120	,	'a8'	,	'	김재중	'	,	117.1875	);");
		db.execSQL("insert into similarRank3Table values(34,	120	,	'b306'	,	'	주원	'	,	117.1875	);");
		db.execSQL("insert into similarRank3Table values(35,	120	,	'b188'	,	'	곽도원	'	,	117.1875	);");
		db.execSQL("insert into similarRank3Table values(36,	122.175	,	'b225'	,	'	주지훈	'	,	125	);");
		db.execSQL("insert into similarRank3Table values(37,	122.175	,	'b187'	,	'	공유	'	,	125	);");
		db.execSQL("insert into similarRank3Table values(38,	122.175	,	'b300'	,	'	장혁	'	,	125	);");
		db.execSQL("insert into similarRank3Table values(39,	122.175	,	'b356'	,	'	하리수	'	,	125	);");
		db.execSQL("insert into similarRank3Table values(40,	122.175	,	'b60'	,	'	홍명보	'	,	125	);");
		db.execSQL("insert into similarRank3Table values(41,	129	,	'b307'	,	'	지상렬	'	,	132.8125	);");
		db.execSQL("insert into similarRank3Table values(42,	129	,	'b266'	,	'	김세환	'	,	132.8125	);");
		db.execSQL("insert into similarRank3Table values(43,	129	,	'a14'	,	'	김정은 위원	'	,	132.8125	);");
		db.execSQL("insert into similarRank3Table values(44,	129	,	'b197'	,	'	박중훈	'	,	132.8125	);");
		db.execSQL("insert into similarRank3Table values(45,	129	,	'b282'	,	'	안길강	'	,	132.8125	);");
		db.execSQL("insert into similarRank3Table values(46,	134	,	'a16'	,	'	문국현	'	,	140.625	);");
		db.execSQL("insert into similarRank3Table values(47,	134	,	'b301'	,	'	전광렬	'	,	140.625	);");
		db.execSQL("insert into similarRank3Table values(48,	134	,	'b25'	,	'	이민기	'	,	140.625	);");
		db.execSQL("insert into similarRank3Table values(49,	134	,	'b371'	,	'	이종원	'	,	140.625	);");
		db.execSQL("insert into similarRank3Table values(50,	134	,	'b302'	,	'	전현무	'	,	140.625	);");
		db.execSQL("insert into similarRank3Table values(51,	144.2	,	'b198'	,	'	박철민	'	,	148.4375	);");
		db.execSQL("insert into similarRank3Table values(52,	144.2	,	'b27'	,	'	양희은	'	,	148.4375	);");
		db.execSQL("insert into similarRank3Table values(53,	144.2	,	'b285'	,	'	엄태웅	'	,	148.4375	);");
		db.execSQL("insert into similarRank3Table values(54,	144.2	,	'b28'	,	'	이휘재	'	,	148.4375	);");
		db.execSQL("insert into similarRank3Table values(55,	144.2	,	'b210'	,	'	우영	'	,	148.4375	);");
		db.execSQL("insert into similarRank3Table values(56,	164	,	'b283'	,	'	안석환	'	,	156.25	);");
		db.execSQL("insert into similarRank3Table values(57,	164	,	'b37'	,	'	허각	'	,	156.25	);");
		db.execSQL("insert into similarRank3Table values(58,	164	,	'b222'	,	'	조승우	'	,	156.25	);");
		db.execSQL("insert into similarRank3Table values(59,	164	,	'b38'	,	'	유상무	'	,	156.25	);");
		db.execSQL("insert into similarRank3Table values(60,	164	,	'a21'	,	'	천정배	'	,	156.25	);");
		db.execSQL("insert into similarRank3Table values(61,	170.3	,	'b280'	,	'	신동엽	'	,	164.0625	);");
		db.execSQL("insert into similarRank3Table values(62,	170.3	,	'b155'	,	'	양세형	'	,	164.0625	);");
		db.execSQL("insert into similarRank3Table values(63,	170.3	,	'b272'	,	'	김종민	'	,	164.0625	);");
		db.execSQL("insert into similarRank3Table values(64,	170.3	,	'b156'	,	'	안철수	'	,	164.0625	);");
		db.execSQL("insert into similarRank3Table values(65,	170.3	,	'b153'	,	'	G드래곤	'	,	164.0625	);");
		db.execSQL("insert into similarRank3Table values(66,	178	,	'b46'	,	'	안내상	'	,	171.875	);");
		db.execSQL("insert into similarRank3Table values(67,	178	,	'b47'	,	'	박진영	'	,	171.875	);");
		db.execSQL("insert into similarRank3Table values(68,	178	,	'b138'	,	'	아웃사이더	'	,	171.875	);");
		db.execSQL("insert into similarRank3Table values(69,	178	,	'b297'	,	'	이윤석	'	,	171.875	);");
		db.execSQL("insert into similarRank3Table values(70,	178	,	'b284'	,	'	안재욱	'	,	171.875	);");
		db.execSQL("insert into similarRank3Table values(71,	185.629	,	'b277'	,	'	송창식	'	,	179.6875	);");
		db.execSQL("insert into similarRank3Table values(72,	185.629	,	'b143'	,	'	이제훈	'	,	179.6875	);");
		db.execSQL("insert into similarRank3Table values(73,	185.629	,	'b144'	,	'	줄리엔강	'	,	179.6875	);");
		db.execSQL("insert into similarRank3Table values(74,	185.629	,	'b51'	,	'	유빈	'	,	179.6875	);");
		db.execSQL("insert into similarRank3Table values(75,	185.629	,	'b52'	,	'	이특	'	,	179.6875	);");
		db.execSQL("insert into similarRank3Table values(76,	193.4	,	'b56'	,	'	황보	'	,	187.5	);");
		db.execSQL("insert into similarRank3Table values(77,	193.4	,	'b146'	,	'	길미	'	,	187.5	);");
		db.execSQL("insert into similarRank3Table values(78,	193.4	,	'b147'	,	'	김미연	'	,	187.5	);");
		db.execSQL("insert into similarRank3Table values(79,	193.4	,	'b57'	,	'	김희철	'	,	187.5	);");
		db.execSQL("insert into similarRank3Table values(80,	193.4	,	'b242'	,	'	백지연	'	,	187.5	);");
		db.execSQL("insert into similarRank3Table values(81,	200.75	,	'b317'	,	'	김태희	'	,	195.3125	);");
		db.execSQL("insert into similarRank3Table values(82,	200.75	,	'b63'	,	'	안영미	'	,	195.3125	);");
		db.execSQL("insert into similarRank3Table values(83,	200.75	,	'b227'	,	'	황광희	'	,	195.3125	);");
		db.execSQL("insert into similarRank3Table values(84,	200.75	,	'b244'	,	'	안문숙	'	,	195.3125	);");
		db.execSQL("insert into similarRank3Table values(85,	200.75	,	'b157'	,	'	나문희	'	,	195.3125	);");
		db.execSQL("insert into similarRank3Table values(86,	209	,	'b161'	,	'	백지영	'	,	203.125	);");
		db.execSQL("insert into similarRank3Table values(87,	209	,	'b69'	,	'	윤진서	'	,	203.125	);");
		db.execSQL("insert into similarRank3Table values(88,	209	,	'b70'	,	'	이혜영	'	,	203.125	);");
		db.execSQL("insert into similarRank3Table values(89,	209	,	'b163'	,	'	김원희	'	,	203.125	);");
		db.execSQL("insert into similarRank3Table values(90,	209	,	'a26'	,	'	나경원	'	,	203.125	);");
		db.execSQL("insert into similarRank3Table values(91,	217	,	'b250'	,	'	윤은혜	'	,	210.9375	);");
		db.execSQL("insert into similarRank3Table values(92,	217	,	'a28'	,	'	한명숙	'	,	210.9375	);");
		db.execSQL("insert into similarRank3Table values(93,	217	,	'b172'	,	'	보아	'	,	210.9375	);");
		db.execSQL("insert into similarRank3Table values(94,	217	,	'b359'	,	'	한지민	'	,	210.9375	);");
		db.execSQL("insert into similarRank3Table values(95,	217	,	'b76'	,	'	송은이	'	,	210.9375	);");
		db.execSQL("insert into similarRank3Table values(96,	224.8	,	'b319'	,	'	김희애	'	,	218.75	);");
		db.execSQL("insert into similarRank3Table values(97,	224.8	,	'b176'	,	'	진지희	'	,	218.75	);");
		db.execSQL("insert into similarRank3Table values(98,	224.8	,	'b249'	,	'	윤유선	'	,	218.75	);");
		db.execSQL("insert into similarRank3Table values(99,	224.8	,	'b257'	,	'	최희	'	,	218.75	);");
		db.execSQL("insert into similarRank3Table values(100,	224.8	,	'b231'	,	'	공효진	'	,	218.75	);");
		db.execSQL("insert into similarRank3Table values(101,	232.45	,	'b228'	,	'	고두심	'	,	226.5625	);");
		db.execSQL("insert into similarRank3Table values(102,	232.45	,	'b335'	,	'	신세경	'	,	226.5625	);");
		db.execSQL("insert into similarRank3Table values(103,	232.45	,	'b331'	,	'	송지효	'	,	226.5625	);");
		db.execSQL("insert into similarRank3Table values(104,	232.45	,	'b334'	,	'	신민아	'	,	226.5625	);");
		db.execSQL("insert into similarRank3Table values(105,	232.45	,	'b84'	,	'	려원	'	,	226.5625	);");
		db.execSQL("insert into similarRank3Table values(106,	240.7	,	'b97'	,	'	한지혜	'	,	234.375	);");
		db.execSQL("insert into similarRank3Table values(107,	240.7	,	'b246'	,	'	양희경	'	,	234.375	);");
		db.execSQL("insert into similarRank3Table values(108,	240.7	,	'b230'	,	'	공서영	'	,	234.375	);");
		db.execSQL("insert into similarRank3Table values(109,	240.7	,	'b350'	,	'	조윤희	'	,	234.375	);");
		db.execSQL("insert into similarRank3Table values(110,	240.7	,	'b247'	,	'	예원	'	,	234.375	);");
		db.execSQL("insert into similarRank3Table values(111,	246.85	,	'b314'	,	'	강수지	'	,	242.1875	);");
		db.execSQL("insert into similarRank3Table values(112,	246.85	,	'b65'	,	'	김신영	'	,	242.1875	);");
		db.execSQL("insert into similarRank3Table values(113,	246.85	,	'b77'	,	'	이인혜	'	,	242.1875	);");
		db.execSQL("insert into similarRank3Table values(114,	246.85	,	'b82'	,	'	옥주현	'	,	242.1875	);");
		db.execSQL("insert into similarRank3Table values(115,	246.85	,	'b351'	,	'	지나	'	,	242.1875	);");
		db.execSQL("insert into similarRank3Table values(116,	255.73	,	'b104'	,	'	엄정화	'	,	250	);");
		db.execSQL("insert into similarRank3Table values(117,	255.73	,	'b354'	,	'	최화정	'	,	250	);");
		db.execSQL("insert into similarRank3Table values(118,	255.73	,	'b105'	,	'	김나영	'	,	250	);");
		db.execSQL("insert into similarRank3Table values(119,	255.73	,	'b106'	,	'	서효림	'	,	250	);");
		db.execSQL("insert into similarRank3Table values(120,	255.73	,	'b243'	,	'	심은경	'	,	250	);");
		db.execSQL("insert into similarRank3Table values(121,	262	,	'b90'	,	'	이영자	'	,	257.8125	);");
		db.execSQL("insert into similarRank3Table values(122,	262	,	'b170'	,	'	박지선	'	,	257.8125	);");
		db.execSQL("insert into similarRank3Table values(123,	262	,	'b94'	,	'	이해리	'	,	257.8125	);");
		db.execSQL("insert into similarRank3Table values(124,	262	,	'b322'	,	'	문채원	'	,	257.8125	);");
		db.execSQL("insert into similarRank3Table values(125,	262	,	'b173'	,	'	왕지혜	'	,	257.8125	);");
		db.execSQL("insert into similarRank3Table values(126,	269.71	,	'b110'	,	'	김정민	'	,	265.625	);");
		db.execSQL("insert into similarRank3Table values(127,	269.71	,	'b178'	,	'	황정음	'	,	265.625	);");
		db.execSQL("insert into similarRank3Table values(128,	269.71	,	'b339'	,	'	오연서	'	,	265.625	);");
		db.execSQL("insert into similarRank3Table values(129,	269.71	,	'b169'	,	'	리지	'	,	265.625	);");
		db.execSQL("insert into similarRank3Table values(130,	269.71	,	'b111'	,	'	백보람	'	,	265.625	);");
		db.execSQL("insert into similarRank3Table values(131,	279.1	,	'c77'	,	'	파이리	'	,	273.4375	);");
		db.execSQL("insert into similarRank3Table values(132,	279.1	,	'c78'	,	'	짱구엄마	'	,	273.4375	);");
		db.execSQL("insert into similarRank3Table values(133,	279.1	,	'c79'	,	'	짱아	'	,	273.4375	);");
		db.execSQL("insert into similarRank3Table values(134,	279.1	,	'c80'	,	'	로사	'	,	273.4375	);");
		db.execSQL("insert into similarRank3Table values(135,	279.1	,	'c81'	,	'	박지민 rolling in the deep	'	,	273.4375	);");
		db.execSQL("insert into similarRank3Table values(136,	288.66	,	'c11'	,	'	미칠이	'	,	281.25	);");
		db.execSQL("insert into similarRank3Table values(137,	288.66	,	'c12'	,	'	말숙이	'	,	281.25	);");
		db.execSQL("insert into similarRank3Table values(138,	288.66	,	'c13'	,	'	강자	'	,	281.25	);");
		db.execSQL("insert into similarRank3Table values(139,	288.66	,	'c14'	,	'	안성댁	'	,	281.25	);");
		db.execSQL("insert into similarRank3Table values(140,	288.66	,	'c15'	,	'	골룸	'	,	281.25	);");
		db.execSQL("insert into similarRank3Table values(141,	296.2	,	'c26'	,	'	도우너	'	,	289.0625	);");
		db.execSQL("insert into similarRank3Table values(142,	296.2	,	'c27'	,	'	또치	'	,	289.0625	);");
		db.execSQL("insert into similarRank3Table values(143,	296.2	,	'c28'	,	'	빵꾸똥꾸	'	,	289.0625	);");
		db.execSQL("insert into similarRank3Table values(144,	296.2	,	'c29'	,	'	신애리	'	,	289.0625	);");
		db.execSQL("insert into similarRank3Table values(145,	296.2	,	'c30'	,	'	크리스티나	'	,	289.0625	);");
		db.execSQL("insert into similarRank3Table values(146,	304.57	,	'c41'	,	'	스펀지밥	'	,	296.875	);");
		db.execSQL("insert into similarRank3Table values(147,	304.57	,	'c42'	,	'	지옥에서온식모	'	,	296.875	);");
		db.execSQL("insert into similarRank3Table values(148,	304.57	,	'c43'	,	'	포로리	'	,	296.875	);");
		db.execSQL("insert into similarRank3Table values(149,	304.57	,	'c44'	,	'	타짜정마담	'	,	296.875	);");
		db.execSQL("insert into similarRank3Table values(150,	304.57	,	'c45'	,	'	임메아리	'	,	296.875	);");
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		Log.i("DB OPEN", "DB OPEN OK");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// db
																				// 갈아
																				// 엎음
		db.execSQL("DROP TABLE IF EXISTS similarTable1");
		db.execSQL("DROP TABLE IF EXISTS similarRank1Table");
		db.execSQL("DROP TABLE IF EXISTS similarRank2Table");
		db.execSQL("DROP TABLE IF EXISTS similarRank3Table");
		onCreate(db);
	}
}