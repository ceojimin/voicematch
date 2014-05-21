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

		if (fft < 70 || fft > 304.6875) {// �������� �ذ��� �κ� ó�� ���
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
							.setMessage("�ֺ��� �е��ϴ� ������ �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 2) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("�ӻ��̵� ������ �ε巯�� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 3) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("����ϰ� ������ ����� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 4) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("������ ������ ����� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 5) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("�����ϰ� ������ ������ �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 6) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("������ ����� �뷡�ϴ� ������ �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 7) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("�����ϰ� �������� �ڽŰ��� �游�� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 8) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("���, �߶��ϸ� �������̰� ������ ��ſ� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 9) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("�ֱ��� ������ ��ġ�� ��Ȥ�� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 10) {
					new AlertDialog.Builder(VoiceM_SimilarDB.this)
							.setMessage("�����ϸ� �������� ����� �Ҹ�")
							.setPositiveButton("Ȯ��",
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
				.setTitle("��Ҹ� ������ ����").setIcon(R.drawable.app_icon)
				.setMessage("��Ҹ� �������� �����Ͻðڽ��ϱ�? �����ϸ� ����ȭ������ ���ư��ϴ�.")
				.setPositiveButton("��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
						overridePendingTransition(android.R.anim.fade_in,
								android.R.anim.fade_out);
						Toast.makeText(VoiceM_SimilarDB.this, "���θ޴��� ���ư��ϴ�.",
								Toast.LENGTH_SHORT).show();
					}
				})
				.setNeutralButton("�ٽ� ����",
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
				.setNegativeButton("���", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	// �̹��� �׸��� �޼���
	private void drawImage() {
		mProgress.setTitle("Voice Match");
		VoiceM_Similar vs = new VoiceM_Similar();
		mProgress.setMessage(vs.getName() + "���� ��Ҹ� ���� ���� �м� ���Դϴ�..");
		mProgress.show();

		DrawThread drawThread = new DrawThread(mainHandler);
		Thread thread = new Thread(drawThread);
		thread.setDaemon(true);
		thread.start();
	}

	// �ڵ鷯 ó��
	Handler mainHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// ������ �̹��� �ٷ� �����ֱ�.
			case 0:
				Bitmap bm1 = (Bitmap) msg.obj;
				if (bm1 == null) {// 1 == null||bm2 == null||bm3 == null){
					Toast.makeText(VoiceM_SimilarDB.this,
							"�̹��� ǥ�� ����:���ͳ��� Ȯ�����ּ���", Toast.LENGTH_SHORT).show();
				} else {
					image1.setImageBitmap(bm1);

				}
				break;

			case 1:
				Bitmap bm2 = (Bitmap) msg.obj;
				if (bm2 == null) {// 1 == null||bm2 == null||bm3 == null){
					Toast.makeText(VoiceM_SimilarDB.this,
							"�̹��� ǥ�� ����:���ͳ��� Ȯ�����ּ���", Toast.LENGTH_SHORT).show();
				} else {
					image2.setImageBitmap(bm2);
				}
				break;
			case 2:
				Bitmap bm3 = (Bitmap) msg.obj;
				if (bm3 == null) {// 1 == null||bm2 == null||bm3 == null){
					Toast.makeText(VoiceM_SimilarDB.this,
							"�̹��� ǥ�� ����:���ͳ��� Ȯ�����ּ���", Toast.LENGTH_SHORT).show();
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
					"��Ҹ� ������_yyyy�� MM�� dd�� HH�� mm�� ss��");
			String filename = sdf.format(date);
			file = Environment.getExternalStorageDirectory();
			String path = file.getAbsolutePath() + "/Voice Match/" + filename
					+ ".jpg";

			FileOutputStream out = new FileOutputStream(path);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);

			Toast.makeText(VoiceM_SimilarDB.this,
					filename + ".jpg�� ����Ǽ̽��ϴ�!", Toast.LENGTH_SHORT).show();

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

		db.execSQL("insert into similarTable1 values(1,	52	,	'c59'	,	'	����	'	);");
		db.execSQL("insert into similarTable1 values(2,	60	,	'c57'	,	'	����	'	);");
		db.execSQL("insert into similarTable1 values(3,	64	,	'c58'	,	'	�㿰	'	);");
		db.execSQL("insert into similarTable1 values(4,	319.2	,	'c46'	,	'	����	'	);");
		db.execSQL("insert into similarTable1 values(5,	349.65	,	'c47'	,	'	���ո�	'	);");
		db.execSQL("insert into similarTable1 values(6,	367	,	'c48'	,	'	Ƽ��	'	);");
		db.execSQL("insert into similarTable1 values(7,	413	,	'a40'	,	'	�Ż���  	'	);");
		db.execSQL("insert into similarTable1 values(8,	450.79	,	'c49'	,	'	������	'	);");
		db.execSQL("insert into similarTable1 values(9,	479	,	'c50'	,	'	��ī��	'	);");
		db.execSQL("insert into similarTable1 values(10,	521	,	'c51'	,	'	������7�ܰ���	'	);");
		db.execSQL("insert into similarTable1 values(11,	549.716	,	'c52'	,	'	������	'	);");
		db.execSQL("insert into similarTable1 values(12,	580.56	,	'c53'	,	'	�Ǳ۷�	'	);");
		db.execSQL("insert into similarTable1 values(13,	628.2	,	'c54'	,	'	���¡��	'	);");
		db.execSQL("insert into similarTable1 values(14,	656	,	'a42'	,	'	IU 1�� �ν���	'	);");
		db.execSQL("insert into similarTable1 values(15,	699	,	'a43'	,	'	IU 2�� �ν���	'	);");
		db.execSQL("insert into similarTable1 values(16,	742	,	'a46'	,	'	IU 3�� �ν���	'	);");
		db.execSQL("insert into similarTable1 values(17,	762	,	'c55'	,	'	����������	'	);");
		db.execSQL("insert into similarTable1 values(18,	780.062	,	'c56'	,	'	�嵿��12��Ÿ���	'	);");

		db.execSQL("CREATE TABLE similarRank1Table ( id INTEGER ,"
				+ " dbfft DOUBLE ," + " addr TEXT , " + " image TEXT , "
				+ "  fft DOUBLE);");

		db.execSQL("insert into similarRank1Table values(1,	71.36	,	'c60'	,	'	�����Ʈ	'	,	70.3125	);");
		db.execSQL("insert into similarRank1Table values(2,	71.36	,	'c61'	,	'	����	'	,	70.3125	);");
		db.execSQL("insert into similarRank1Table values(3,	71.36	,	'c62'	,	'	��ũ	'	,	70.3125	);");
		db.execSQL("insert into similarRank1Table values(4,	71.36	,	'c63'	,	'	��ͳ�	'	,	70.3125	);");
		db.execSQL("insert into similarRank1Table values(5,	71.36	,	'c64'	,	'	������	'	,	70.3125	);");
		db.execSQL("insert into similarRank1Table values(6,	78.24	,	'b118'	,	'	����	'	,	78.125	);");
		db.execSQL("insert into similarRank1Table values(7,	78.24	,	'b119'	,	'	�ӵ�	'	,	78.125	);");
		db.execSQL("insert into similarRank1Table values(8,	78.24	,	'b2'	,	'	������	'	,	78.125	);");
		db.execSQL("insert into similarRank1Table values(9,	78.24	,	'b120'	,	'	������	'	,	78.125	);");
		db.execSQL("insert into similarRank1Table values(10,	78.24	,	'b3'	,	'	���汸	'	,	78.125	);");
		db.execSQL("insert into similarRank1Table values(11,	85.93	,	'b123'	,	'	�����	'	,	85.9375	);");
		db.execSQL("insert into similarRank1Table values(12,	85.93	,	'b312'	,	'	����	'	,	85.9375	);");
		db.execSQL("insert into similarRank1Table values(13,	85.93	,	'b5'	,	'	�ڽ���	'	,	85.9375	);");
		db.execSQL("insert into similarRank1Table values(14,	85.93	,	'b215'	,	'	�̺���	'	,	85.9375	);");
		db.execSQL("insert into similarRank1Table values(15,	85.93	,	'b193'	,	'	���±�	'	,	85.9375	);");
		db.execSQL("insert into similarRank1Table values(16,	93.24	,	'b9'	,	'	����ȣ	'	,	93.75	);");
		db.execSQL("insert into similarRank1Table values(17,	93.24	,	'b10'	,	'	����	'	,	93.75	);");
		db.execSQL("insert into similarRank1Table values(18,	93.24	,	'b226'	,	'	�ֹν�	'	,	93.75	);");
		db.execSQL("insert into similarRank1Table values(19,	93.24	,	'b11'	,	'	ȯ��	'	,	93.75	);");
		db.execSQL("insert into similarRank1Table values(20,	93.24	,	'b366'	,	'	�����	'	,	93.75	);");
		db.execSQL("insert into similarRank1Table values(21,	101.56	,	'b15'	,	'	�ڰ���	'	,	101.5625	);");
		db.execSQL("insert into similarRank1Table values(22,	101.56	,	'b126'	,	'	���ػ�	'	,	101.5625	);");
		db.execSQL("insert into similarRank1Table values(23,	101.56	,	'b16'	,	'	������	'	,	101.5625	);");
		db.execSQL("insert into similarRank1Table values(24,	101.56	,	'b274'	,	'	���ϼ�	'	,	101.5625	);");
		db.execSQL("insert into similarRank1Table values(25,	101.56	,	'b196'	,	'	������	'	,	101.5625	);");
		db.execSQL("insert into similarRank1Table values(26,	110	,	'a6'	,	'	������	'	,	109.375	);");
		db.execSQL("insert into similarRank1Table values(27,	110	,	'b361'	,	'	���°�	'	,	109.375	);");
		db.execSQL("insert into similarRank1Table values(28,	110	,	'b128'	,	'	�̹���	'	,	109.375	);");
		db.execSQL("insert into similarRank1Table values(29,	110	,	'b18'	,	'	������	'	,	109.375	);");
		db.execSQL("insert into similarRank1Table values(30,	110	,	'b290'	,	'	�̴�ȣ	'	,	109.375	);");
		db.execSQL("insert into similarRank1Table values(31,	117.2	,	'b131'	,	'	�迵��	'	,	117.1875	);");
		db.execSQL("insert into similarRank1Table values(32,	117.2	,	'b195'	,	'	���¹�	'	,	117.1875	);");
		db.execSQL("insert into similarRank1Table values(33,	117.2	,	'b192'	,	'	������	'	,	117.1875	);");
		db.execSQL("insert into similarRank1Table values(34,	117.2	,	'b132'	,	'	���μ�	'	,	117.1875	);");
		db.execSQL("insert into similarRank1Table values(35,	117.2	,	'b360'	,	'	�����	'	,	117.1875	);");
		db.execSQL("insert into similarRank1Table values(36,	125	,	'b133'	,	'	���	'	,	125	);");
		db.execSQL("insert into similarRank1Table values(37,	125	,	'b134'	,	'	���ּ�	'	,	125	);");
		db.execSQL("insert into similarRank1Table values(38,	125	,	'b22'	,	'	������	'	,	125	);");
		db.execSQL("insert into similarRank1Table values(39,	125	,	'b265'	,	'	�輮��	'	,	125	);");
		db.execSQL("insert into similarRank1Table values(40,	125	,	'a10'	,	'	�Ѽ���	'	,	125	);");
		db.execSQL("insert into similarRank1Table values(41,	132.24	,	'b276'	,	'	���߱�	'	,	132.8125	);");
		db.execSQL("insert into similarRank1Table values(42,	132.24	,	'b24'	,	'	������	'	,	132.8125	);");
		db.execSQL("insert into similarRank1Table values(43,	132.24	,	'b206'	,	'	�ÿ�	'	,	132.8125	);");
		db.execSQL("insert into similarRank1Table values(44,	132.24	,	'b223'	,	'	������	'	,	132.8125	);");
		db.execSQL("insert into similarRank1Table values(45,	132.24	,	'b199'	,	'	������	'	,	132.8125	);");
		db.execSQL("insert into similarRank1Table values(46,	140.6	,	'b217'	,	'	�̽±�	'	,	140.625	);");
		db.execSQL("insert into similarRank1Table values(47,	140.6	,	'b26'	,	'	õ����	'	,	140.625	);");
		db.execSQL("insert into similarRank1Table values(48,	140.6	,	'b205'	,	'	�ۻ���	'	,	140.625	);");
		db.execSQL("insert into similarRank1Table values(49,	140.6	,	'b296'	,	'	�̽¿�	'	,	140.625	);");
		db.execSQL("insert into similarRank1Table values(50,	140.6	,	'b273'	,	'	���¿�	'	,	140.625	);");
		db.execSQL("insert into similarRank1Table values(51,	148.4	,	'b219'	,	'	������	'	,	148.4375	);");
		db.execSQL("insert into similarRank1Table values(52,	148.4	,	'b298'	,	'	���ؼ�	'	,	148.4375	);");
		db.execSQL("insert into similarRank1Table values(53,	148.4	,	'b214'	,	'	������	'	,	148.4375	);");
		db.execSQL("insert into similarRank1Table values(54,	148.4	,	'b268'	,	'	��븸	'	,	148.4375	);");
		db.execSQL("insert into similarRank1Table values(55,	148.4	,	'b299'	,	'	������	'	,	148.4375	);");
		db.execSQL("insert into similarRank1Table values(56,	156	,	'b31'	,	'	�����	'	,	156.25	);");
		db.execSQL("insert into similarRank1Table values(57,	156	,	'b32'	,	'	������	'	,	156.25	);");
		db.execSQL("insert into similarRank1Table values(58,	156	,	'b33'	,	'	���ð�	'	,	156.25	);");
		db.execSQL("insert into similarRank1Table values(59,	156	,	'b201'	,	'	�����	'	,	156.25	);");
		db.execSQL("insert into similarRank1Table values(60,	156	,	'a20'	,	'	������	'	,	156.25	);");
		db.execSQL("insert into similarRank1Table values(61,	164	,	'b40'	,	'	�¸�	'	,	164.0625	);");
		db.execSQL("insert into similarRank1Table values(62,	164	,	'b58'	,	'	��ȫö	'	,	164.0625	);");
		db.execSQL("insert into similarRank1Table values(63,	164	,	'b292'	,	'	�̹���	'	,	164.0625	);");
		db.execSQL("insert into similarRank1Table values(64,	164	,	'b149'	,	'	������	'	,	164.0625	);");
		db.execSQL("insert into similarRank1Table values(65,	164	,	'b311'	,	'	������	'	,	164.0625	);");
		db.execSQL("insert into similarRank1Table values(66,	172.5	,	'b42'	,	'	������	'	,	171.875	);");
		db.execSQL("insert into similarRank1Table values(67,	172.5	,	'b137'	,	'	���	'	,	171.875	);");
		db.execSQL("insert into similarRank1Table values(68,	172.5	,	'a22'	,	'	���¿�	'	,	171.875	);");
		db.execSQL("insert into similarRank1Table values(69,	172.5	,	'a23'	,	'	��C	'	,	171.875	);");
		db.execSQL("insert into similarRank1Table values(70,	172.5	,	'b43'	,	'	������	'	,	171.875	);");
		db.execSQL("insert into similarRank1Table values(71,	180	,	'b139'	,	'	������	'	,	179.6875	);");
		db.execSQL("insert into similarRank1Table values(72,	180	,	'b259'	,	'	��ȳ�	'	,	179.6875	);");
		db.execSQL("insert into similarRank1Table values(73,	180	,	'b267'	,	'	�迬��	'	,	179.6875	);");
		db.execSQL("insert into similarRank1Table values(74,	180	,	'b48'	,	'	��	'	,	179.6875	);");
		db.execSQL("insert into similarRank1Table values(75,	180	,	'b141'	,	'	�豹��	'	,	179.6875	);");
		db.execSQL("insert into similarRank1Table values(76,	187.3	,	'b211'	,	'	����	'	,	187.5	);");
		db.execSQL("insert into similarRank1Table values(77,	187.3	,	'b248'	,	'	������	'	,	187.5	);");
		db.execSQL("insert into similarRank1Table values(78,	187.3	,	'b53'	,	'	������	'	,	187.5	);");
		db.execSQL("insert into similarRank1Table values(79,	187.3	,	'b333'	,	'	����	'	,	187.5	);");
		db.execSQL("insert into similarRank1Table values(80,	187.3	,	'b328'	,	'	�մ��	'	,	187.5	);");
		db.execSQL("insert into similarRank1Table values(81,	194	,	'a25'	,	'	������ ����	'	,	195.3125	);");
		db.execSQL("insert into similarRank1Table values(82,	194	,	'b148'	,	'	���ؼ�	'	,	195.3125	);");
		db.execSQL("insert into similarRank1Table values(83,	194	,	'b229'	,	'	�����	'	,	195.3125	);");
		db.execSQL("insert into similarRank1Table values(84,	194	,	'b59'	,	'	������	'	,	195.3125	);");
		db.execSQL("insert into similarRank1Table values(85,	194	,	'b324'	,	'	�ڱ���	'	,	195.3125	);");
		db.execSQL("insert into similarRank1Table values(86,	203.68	,	'b352'	,	'	����	'	,	203.125	);");
		db.execSQL("insert into similarRank1Table values(87,	203.68	,	'b158'	,	'	������	'	,	203.125	);");
		db.execSQL("insert into similarRank1Table values(88,	203.68	,	'b159'	,	'	�̽�ȯ	'	,	203.125	);");
		db.execSQL("insert into similarRank1Table values(89,	203.68	,	'b160'	,	'	�����	'	,	203.125	);");
		db.execSQL("insert into similarRank1Table values(90,	203.68	,	'b68'	,	'	������	'	,	203.125	);");
		db.execSQL("insert into similarRank1Table values(91,	211.3	,	'b71'	,	'	������	'	,	210.9375	);");
		db.execSQL("insert into similarRank1Table values(92,	211.3	,	'b166'	,	'	���ϼ�	'	,	210.9375	);");
		db.execSQL("insert into similarRank1Table values(93,	211.3	,	'b355'	,	'	�¿�	'	,	210.9375	);");
		db.execSQL("insert into similarRank1Table values(94,	211.3	,	'b72'	,	'	���ع�	'	,	210.9375	);");
		db.execSQL("insert into similarRank1Table values(95,	211.3	,	'b73'	,	'	���ָ�	'	,	210.9375	);");
		db.execSQL("insert into similarRank1Table values(96,	220	,	'b78'	,	'	�̿���	'	,	218.75	);");
		db.execSQL("insert into similarRank1Table values(97,	220	,	'b245'	,	'	�ȼ���	'	,	218.75	);");
		db.execSQL("insert into similarRank1Table values(98,	220	,	'b325'	,	'	�ڼ���	'	,	218.75	);");
		db.execSQL("insert into similarRank1Table values(99,	220	,	'a29'	,	'	������ ��Ŀ	'	,	218.75	);");
		db.execSQL("insert into similarRank1Table values(100,	220	,	'b174'	,	'	������	'	,	218.75	);");
		db.execSQL("insert into similarRank1Table values(101,	227.4	,	'b85'	,	'	���ϴ�	'	,	226.5625	);");
		db.execSQL("insert into similarRank1Table values(102,	227.4	,	'b346'	,	'	�̿��	'	,	226.5625	);");
		db.execSQL("insert into similarRank1Table values(103,	227.4	,	'b320'	,	'	�����	'	,	226.5625	);");
		db.execSQL("insert into similarRank1Table values(104,	227.4	,	'b239'	,	'	���ٿ�	'	,	226.5625	);");
		db.execSQL("insert into similarRank1Table values(105,	227.4	,	'b86'	,	'	������	'	,	226.5625	);");
		db.execSQL("insert into similarRank1Table values(106,	235	,	'b92'	,	'	�Ѱ���	'	,	234.375	);");
		db.execSQL("insert into similarRank1Table values(107,	235	,	'a31'	,	'	���ٸ�	'	,	234.375	);");
		db.execSQL("insert into similarRank1Table values(108,	235	,	'b235'	,	'	�輱��	'	,	234.375	);");
		db.execSQL("insert into similarRank1Table values(109,	235	,	'b357'	,	'	����ȭ	'	,	234.375	);");
		db.execSQL("insert into similarRank1Table values(110,	235	,	'b93'	,	'	����	'	,	234.375	);");
		db.execSQL("insert into similarRank1Table values(111,	242.4	,	'b98'	,	'	����	'	,	242.1875	);");
		db.execSQL("insert into similarRank1Table values(112,	242.4	,	'b99'	,	'	������	'	,	242.1875	);");
		db.execSQL("insert into similarRank1Table values(113,	242.4	,	'b234'	,	'	�躸��	'	,	242.1875	);");
		db.execSQL("insert into similarRank1Table values(114,	242.4	,	'b252'	,	'	�念��	'	,	242.1875	);");
		db.execSQL("insert into similarRank1Table values(115,	242.4	,	'b100'	,	'	������	'	,	242.1875	);");
		db.execSQL("insert into similarRank1Table values(116,	249	,	'a33'	,	'	�迬��	'	,	250	);");
		db.execSQL("insert into similarRank1Table values(117,	249	,	'b329'	,	'	�տ���	'	,	250	);");
		db.execSQL("insert into similarRank1Table values(118,	249	,	'b343'	,	'	������	'	,	250	);");
		db.execSQL("insert into similarRank1Table values(119,	249	,	'b256'	,	'	�ְ���	'	,	250	);");
		db.execSQL("insert into similarRank1Table values(120,	249	,	'b103'	,	'	������	'	,	250	);");
		db.execSQL("insert into similarRank1Table values(121,	257.81	,	'b179'	,	'	������	'	,	257.8125	);");
		db.execSQL("insert into similarRank1Table values(122,	257.81	,	'b180'	,	'	��ٷ�	'	,	257.8125	);");
		db.execSQL("insert into similarRank1Table values(123,	257.81	,	'b181'	,	'	����	'	,	257.8125	);");
		db.execSQL("insert into similarRank1Table values(124,	257.81	,	'b107'	,	'	������	'	,	257.8125	);");
		db.execSQL("insert into similarRank1Table values(125,	257.81	,	'b182'	,	'	�����	'	,	257.8125	);");
		db.execSQL("insert into similarRank1Table values(126,	249	,	'b237'	,	'	�迵��	'	,	265.625	);");
		db.execSQL("insert into similarRank1Table values(127,	249	,	'b162'	,	'	������	'	,	265.625	);");
		db.execSQL("insert into similarRank1Table values(128,	249	,	'b318'	,	'	����	'	,	265.625	);");
		db.execSQL("insert into similarRank1Table values(129,	249	,	'b336'	,	'	�žֶ�	'	,	265.625	);");
		db.execSQL("insert into similarRank1Table values(130,	249	,	'b232'	,	'	�賲��	'	,	265.625	);");
		db.execSQL("insert into similarRank1Table values(131,	273.4	,	'b114'	,	'	������	'	,	273.4375	);");
		db.execSQL("insert into similarRank1Table values(132,	273.4	,	'b241'	,	'	������	'	,	273.4375	);");
		db.execSQL("insert into similarRank1Table values(133,	273.4	,	'b236'	,	'	�迵��	'	,	273.4375	);");
		db.execSQL("insert into similarRank1Table values(134,	273.4	,	'b326'	,	'	�ڿ���	'	,	273.4375	);");
		db.execSQL("insert into similarRank1Table values(135,	273.4	,	'b260'	,	'	ȫ����	'	,	273.4375	);");
		db.execSQL("insert into similarRank1Table values(136,	282.362	,	'c1'	,	'	������	'	,	281.25	);");
		db.execSQL("insert into similarRank1Table values(137,	282.362	,	'c2'	,	'	�����	'	,	281.25	);");
		db.execSQL("insert into similarRank1Table values(138,	282.362	,	'c3'	,	'	�����	'	,	281.25	);");
		db.execSQL("insert into similarRank1Table values(139,	282.362	,	'c4'	,	'	�̼�����	'	,	281.25	);");
		db.execSQL("insert into similarRank1Table values(140,	282.362	,	'c5'	,	'	������	'	,	281.25	);");
		db.execSQL("insert into similarRank1Table values(141,	290.7	,	'c16'	,	'	�ҹ̳�	'	,	289.0625	);");
		db.execSQL("insert into similarRank1Table values(142,	290.7	,	'c17'	,	'	�ϴ�	'	,	289.0625	);");
		db.execSQL("insert into similarRank1Table values(143,	290.7	,	'c18'	,	'	�Ƿη�	'	,	289.0625	);");
		db.execSQL("insert into similarRank1Table values(144,	290.7	,	'c19'	,	'	���ָ�	'	,	289.0625	);");
		db.execSQL("insert into similarRank1Table values(145,	290.7	,	'c20'	,	'	�̼�	'	,	289.0625	);");
		db.execSQL("insert into similarRank1Table values(146,	298.05	,	'c31'	,	'	�����ɽ�	'	,	296.875	);");
		db.execSQL("insert into similarRank1Table values(147,	298.05	,	'c32'	,	'	�鿩ġ	'	,	296.875	);");
		db.execSQL("insert into similarRank1Table values(148,	298.05	,	'c33'	,	'	������	'	,	296.875	);");
		db.execSQL("insert into similarRank1Table values(149,	298.05	,	'c34'	,	'	��ĥ��	'	,	296.875	);");
		db.execSQL("insert into similarRank1Table values(150,	298.05	,	'c35'	,	'	õ��ȣ	'	,	296.875	);");

		db.execSQL("CREATE TABLE similarRank2Table ( id INTEGER ,"
				+ " dbfft DOUBLE ," + " addr TEXT , " + " image TEXT , "
				+ "  fft DOUBLE);");

		db.execSQL("insert into similarRank2Table values(1,	73.8	,	'c65'	,	'	�ǰ���	'	,	70.3125	);");
		db.execSQL("insert into similarRank2Table values(2,	73.8	,	'c66'	,	'	���α�	'	,	70.3125	);");
		db.execSQL("insert into similarRank2Table values(3,	73.8	,	'c67'	,	'	�̻��ؾ�	'	,	70.3125	);");
		db.execSQL("insert into similarRank2Table values(4,	73.8	,	'c68'	,	'	������	'	,	70.3125	);");
		db.execSQL("insert into similarRank2Table values(5,	73.8	,	'c69'	,	'	������	'	,	70.3125	);");
		db.execSQL("insert into similarRank2Table values(6,	80	,	'b275'	,	'	���ϱ�	'	,	78.125	);");
		db.execSQL("insert into similarRank2Table values(7,	80	,	'b4'	,	'	�ڽž�	'	,	78.125	);");
		db.execSQL("insert into similarRank2Table values(8,	80	,	'b121'	,	'	�ڸ��	'	,	78.125	);");
		db.execSQL("insert into similarRank2Table values(9,	80	,	'b289'	,	'	�̰���	'	,	78.125	);");
		db.execSQL("insert into similarRank2Table values(10,	80	,	'b122'	,	'	�蹫��	'	,	78.125	);");
		db.execSQL("insert into similarRank2Table values(11,	87.1	,	'b6'	,	'	���켺	'	,	85.9375	);");
		db.execSQL("insert into similarRank2Table values(12,	87.1	,	'b191'	,	'	������	'	,	85.9375	);");
		db.execSQL("insert into similarRank2Table values(13,	87.1	,	'b278'	,	'	��	'	,	85.9375	);");
		db.execSQL("insert into similarRank2Table values(14,	87.1	,	'b7'	,	'	�嵿��	'	,	85.9375	);");
		db.execSQL("insert into similarRank2Table values(15,	87.1	,	'b8'	,	'	�۰�ȣ	'	,	85.9375	);");
		db.execSQL("insert into similarRank2Table values(16,	97.1	,	'b12'	,	'	������	'	,	93.75	);");
		db.execSQL("insert into similarRank2Table values(17,	97.1	,	'b124'	,	'	�豸��	'	,	93.75	);");
		db.execSQL("insert into similarRank2Table values(18,	97.1	,	'b295'	,	'	�̼���	'	,	93.75	);");
		db.execSQL("insert into similarRank2Table values(19,	97.1	,	'b294'	,	'	�̼���	'	,	93.75	);");
		db.execSQL("insert into similarRank2Table values(20,	97.1	,	'b218'	,	'	��õ��	'	,	93.75	);");
		db.execSQL("insert into similarRank2Table values(21,	105.2	,	'b370'	,	'	������	'	,	101.5625	);");
		db.execSQL("insert into similarRank2Table values(22,	105.2	,	'b17'	,	'	�̼���	'	,	101.5625	);");
		db.execSQL("insert into similarRank2Table values(23,	105.2	,	'b224'	,	'	������	'	,	101.5625	);");
		db.execSQL("insert into similarRank2Table values(24,	105.2	,	'b291'	,	'	�̴�ȭ	'	,	101.5625	);");
		db.execSQL("insert into similarRank2Table values(25,	105.2	,	'b207'	,	'	�絿��	'	,	101.5625	);");
		db.execSQL("insert into similarRank2Table values(26,	112.35	,	'b364'	,	'	������	'	,	109.375	);");
		db.execSQL("insert into similarRank2Table values(27,	112.35	,	'b190'	,	'	�躴��	'	,	109.375	);");
		db.execSQL("insert into similarRank2Table values(28,	112.35	,	'b19'	,	'	�۽���	'	,	109.375	);");
		db.execSQL("insert into similarRank2Table values(29,	112.35	,	'b365'	,	'	����ȣ	'	,	109.375	);");
		db.execSQL("insert into similarRank2Table values(30,	112.35	,	'b129'	,	'	�̹�ȣ	'	,	109.375	);");
		db.execSQL("insert into similarRank2Table values(31,	118.96	,	'b21'	,	'	�ִٴϿ�	'	,	117.1875	);");
		db.execSQL("insert into similarRank2Table values(32,	118.96	,	'b208'	,	'	���޼�	'	,	117.1875	);");
		db.execSQL("insert into similarRank2Table values(33,	118.96	,	'b263'	,	'	�跡��	'	,	117.1875	);");
		db.execSQL("insert into similarRank2Table values(34,	118.96	,	'b261'	,	'	����ȣ	'	,	117.1875	);");
		db.execSQL("insert into similarRank2Table values(35,	118.96	,	'b293'	,	'	�̻���	'	,	117.1875	);");
		db.execSQL("insert into similarRank2Table values(36,	127	,	'b305'	,	'	�ֻ��	'	,	125	);");
		db.execSQL("insert into similarRank2Table values(37,	127	,	'b209'	,	'	������	'	,	125	);");
		db.execSQL("insert into similarRank2Table values(38,	127	,	'b54'	,	'	�����	'	,	125	);");
		db.execSQL("insert into similarRank2Table values(39,	127	,	'b213'	,	'	������	'	,	125	);");
		db.execSQL("insert into similarRank2Table values(40,	127	,	'a12'	,	'	��ȸ��	'	,	125	);");
		db.execSQL("insert into similarRank2Table values(41,	130	,	'b220'	,	'	������	'	,	132.8125	);");
		db.execSQL("insert into similarRank2Table values(42,	130	,	'a15'	,	'	����Ŭ���	'	,	132.8125	);");
		db.execSQL("insert into similarRank2Table values(43,	130	,	'a13'	,	'	�賲��	'	,	132.8125	);");
		db.execSQL("insert into similarRank2Table values(44,	130	,	'a17'	,	'	�����	'	,	132.8125	);");
		db.execSQL("insert into similarRank2Table values(45,	130	,	'b23'	,	'	�ȼ���	'	,	132.8125	);");
		db.execSQL("insert into similarRank2Table values(46,	137	,	'b264'	,	'	�����	'	,	140.625	);");
		db.execSQL("insert into similarRank2Table values(47,	137	,	'b303'	,	'	������	'	,	140.625	);");
		db.execSQL("insert into similarRank2Table values(48,	137	,	'b363'	,	'	����ȣ	'	,	140.625	);");
		db.execSQL("insert into similarRank2Table values(49,	137	,	'a18'	,	'	������	'	,	140.625	);");
		db.execSQL("insert into similarRank2Table values(50,	137	,	'a19'	,	'	�������	'	,	140.625	);");
		db.execSQL("insert into similarRank2Table values(51,	151	,	'b310'	,	'	�ּ���	'	,	148.4375	);");
		db.execSQL("insert into similarRank2Table values(52,	151	,	'b29'	,	'	�̺���	'	,	148.4375	);");
		db.execSQL("insert into similarRank2Table values(53,	151	,	'b189'	,	'	����ȿ	'	,	148.4375	);");
		db.execSQL("insert into similarRank2Table values(54,	151	,	'b30'	,	'	�̰��	'	,	148.4375	);");
		db.execSQL("insert into similarRank2Table values(55,	151	,	'b66'	,	'	���ϴ�	'	,	148.4375	);");
		db.execSQL("insert into similarRank2Table values(56,	160.95	,	'b34'	,	'	�����	'	,	156.25	);");
		db.execSQL("insert into similarRank2Table values(57,	160.95	,	'b35'	,	'	�����	'	,	156.25	);");
		db.execSQL("insert into similarRank2Table values(58,	160.95	,	'b36'	,	'	������	'	,	156.25	);");
		db.execSQL("insert into similarRank2Table values(59,	160.95	,	'b286'	,	'	������	'	,	156.25	);");
		db.execSQL("insert into similarRank2Table values(60,	160.95	,	'b313'	,	'	����	'	,	156.25	);");
		db.execSQL("insert into similarRank2Table values(61,	168	,	'b135'	,	'	����ȯ	'	,	164.0625	);");
		db.execSQL("insert into similarRank2Table values(62,	168	,	'b39'	,	'	��ȿ��	'	,	164.0625	);");
		db.execSQL("insert into similarRank2Table values(63,	168	,	'b136'	,	'	���±�	'	,	164.0625	);");
		db.execSQL("insert into similarRank2Table values(64,	168	,	'a24'	,	'	�ּ�	'	,	164.0625	);");
		db.execSQL("insert into similarRank2Table values(65,	168	,	'b202'	,	'	����	'	,	164.0625	);");
		db.execSQL("insert into similarRank2Table values(66,	176.45	,	'b44'	,	'	�嵿��	'	,	171.875	);");
		db.execSQL("insert into similarRank2Table values(67,	176.45	,	'b304'	,	'	������	'	,	171.875	);");
		db.execSQL("insert into similarRank2Table values(68,	176.45	,	'b323'	,	'	�ڰ渲	'	,	171.875	);");
		db.execSQL("insert into similarRank2Table values(69,	176.45	,	'b45'	,	'	õ����	'	,	171.875	);");
		db.execSQL("insert into similarRank2Table values(70,	176.45	,	'b287'	,	'	����	'	,	171.875	);");
		db.execSQL("insert into similarRank2Table values(71,	182.5	,	'b142'	,	'	ȫ��õ	'	,	179.6875	);");
		db.execSQL("insert into similarRank2Table values(72,	182.5	,	'b67'	,	'	������	'	,	179.6875	);");
		db.execSQL("insert into similarRank2Table values(73,	182.5	,	'b262'	,	'	���ȣ	'	,	179.6875	);");
		db.execSQL("insert into similarRank2Table values(74,	182.5	,	'b49'	,	'	���漮	'	,	179.6875	);");
		db.execSQL("insert into similarRank2Table values(75,	182.5	,	'b50'	,	'	���缮	'	,	179.6875	);");
		db.execSQL("insert into similarRank2Table values(76,	190	,	'b315'	,	'	�輭��	'	,	187.5	);");
		db.execSQL("insert into similarRank2Table values(77,	190	,	'b145'	,	'	���̷�	'	,	187.5	);");
		db.execSQL("insert into similarRank2Table values(78,	190	,	'b55'	,	'	�����Ͼ�Ŀ	'	,	187.5	);");
		db.execSQL("insert into similarRank2Table values(79,	190	,	'b200'	,	'	������	'	,	187.5	);");
		db.execSQL("insert into similarRank2Table values(80,	190	,	'b212'	,	'	������	'	,	187.5	);");
		db.execSQL("insert into similarRank2Table values(81,	197.8	,	'b150'	,	'	�˸�	'	,	195.3125	);");
		db.execSQL("insert into similarRank2Table values(82,	197.8	,	'b344'	,	'	�̼���	'	,	195.3125	);");
		db.execSQL("insert into similarRank2Table values(83,	197.8	,	'b61'	,	'	�����	'	,	195.3125	);");
		db.execSQL("insert into similarRank2Table values(84,	197.8	,	'b151'	,	'	����	'	,	195.3125	);");
		db.execSQL("insert into similarRank2Table values(85,	197.8	,	'b154'	,	'	������	'	,	195.3125	);");
		db.execSQL("insert into similarRank2Table values(86,	207.89	,	'b367'	,	'	����ȭ	'	,	203.125	);");
		db.execSQL("insert into similarRank2Table values(87,	207.89	,	'b164'	,	'	�ڽÿ�	'	,	203.125	);");
		db.execSQL("insert into similarRank2Table values(88,	207.89	,	'b253'	,	'	���̼�	'	,	203.125	);");
		db.execSQL("insert into similarRank2Table values(89,	207.89	,	'b372'	,	'	��ҿ�	'	,	203.125	);");
		db.execSQL("insert into similarRank2Table values(90,	207.89	,	'b165'	,	'	��Į��	'	,	203.125	);");
		db.execSQL("insert into similarRank2Table values(91,	215	,	'b168'	,	'	������	'	,	210.9375	);");
		db.execSQL("insert into similarRank2Table values(92,	215	,	'b251'	,	'	������	'	,	210.9375	);");
		db.execSQL("insert into similarRank2Table values(93,	215	,	'a27'	,	'	�̸��	'	,	210.9375	);");
		db.execSQL("insert into similarRank2Table values(94,	215	,	'b75'	,	'	�ź���	'	,	210.9375	);");
		db.execSQL("insert into similarRank2Table values(95,	215	,	'b171'	,	'	�ź���	'	,	210.9375	);");
		db.execSQL("insert into similarRank2Table values(96,	222.56	,	'b79'	,	'	�̹���	'	,	218.75	);");
		db.execSQL("insert into similarRank2Table values(97,	222.56	,	'b175'	,	'	�ڹο�	'	,	218.75	);");
		db.execSQL("insert into similarRank2Table values(98,	222.56	,	'b80'	,	'	������	'	,	218.75	);");
		db.execSQL("insert into similarRank2Table values(99,	222.56	,	'b337'	,	'	������	'	,	218.75	);");
		db.execSQL("insert into similarRank2Table values(100,	222.56	,	'b81'	,	'	������	'	,	218.75	);");
		db.execSQL("insert into similarRank2Table values(101,	230	,	'b87'	,	'	������	'	,	226.5625	);");
		db.execSQL("insert into similarRank2Table values(102,	230	,	'b233'	,	'	��ο�	'	,	226.5625	);");
		db.execSQL("insert into similarRank2Table values(103,	230	,	'b88'	,	'	��ҿ���Ŀ	'	,	226.5625	);");
		db.execSQL("insert into similarRank2Table values(104,	230	,	'b89'	,	'	������	'	,	226.5625	);");
		db.execSQL("insert into similarRank2Table values(105,	230	,	'b91'	,	'	����	'	,	226.5625	);");
		db.execSQL("insert into similarRank2Table values(106,	237	,	'a32'	,	'	���¿�	'	,	234.375	);");
		db.execSQL("insert into similarRank2Table values(107,	237	,	'b258'	,	'	ũ����Ż	'	,	234.375	);");
		db.execSQL("insert into similarRank2Table values(108,	237	,	'b95'	,	'	������	'	,	234.375	);");
		db.execSQL("insert into similarRank2Table values(109,	237	,	'b254'	,	'	�����	'	,	234.375	);");
		db.execSQL("insert into similarRank2Table values(110,	237	,	'b96'	,	'	�ڹ̼�	'	,	234.375	);");
		db.execSQL("insert into similarRank2Table values(111,	244.18	,	'b349'	,	'	������	'	,	242.1875	);");
		db.execSQL("insert into similarRank2Table values(112,	244.18	,	'b347'	,	'	��ȿ��	'	,	242.1875	);");
		db.execSQL("insert into similarRank2Table values(113,	244.18	,	'b342'	,	'	����	'	,	242.1875	);");
		db.execSQL("insert into similarRank2Table values(114,	244.18	,	'b340'	,	'	����	'	,	242.1875	);");
		db.execSQL("insert into similarRank2Table values(115,	244.18	,	'b62'	,	'	�ֿ���	'	,	242.1875	);");
		db.execSQL("insert into similarRank2Table values(116,	247.87	,	'b101'	,	'	�ٴ�	'	,	250	);");
		db.execSQL("insert into similarRank2Table values(117,	247.87	,	'b255'	,	'	���	'	,	250	);");
		db.execSQL("insert into similarRank2Table values(118,	247.87	,	'b338'	,	'	���޶�	'	,	250	);");
		db.execSQL("insert into similarRank2Table values(119,	247.87	,	'b368'	,	'	�峪��	'	,	250	);");
		db.execSQL("insert into similarRank2Table values(120,	247.87	,	'b102'	,	'	������	'	,	250	);");
		db.execSQL("insert into similarRank2Table values(121,	260	,	'a36'	,	'	������	'	,	257.8125	);");
		db.execSQL("insert into similarRank2Table values(122,	260	,	'b183'	,	'	������	'	,	257.8125	);");
		db.execSQL("insert into similarRank2Table values(123,	260	,	'b185'	,	'	������	'	,	257.8125	);");
		db.execSQL("insert into similarRank2Table values(124,	260	,	'b108'	,	'	���γ�	'	,	257.8125	);");
		db.execSQL("insert into similarRank2Table values(125,	260	,	'b109'	,	'	����	'	,	257.8125	);");
		db.execSQL("insert into similarRank2Table values(126,	267	,	'b184'	,	'	���ϸ�	'	,	265.625	);");
		db.execSQL("insert into similarRank2Table values(127,	267	,	'b177'	,	'	���ڿ�	'	,	265.625	);");
		db.execSQL("insert into similarRank2Table values(128,	267	,	'b330'	,	'	�տ���	'	,	265.625	);");
		db.execSQL("insert into similarRank2Table values(129,	267	,	'b83'	,	'	���ΰ�	'	,	265.625	);");
		db.execSQL("insert into similarRank2Table values(130,	267	,	'b332'	,	'	������	'	,	265.625	);");
		db.execSQL("insert into similarRank2Table values(131,	270	,	'b240'	,	'	���ر�	'	,	273.4375	);");
		db.execSQL("insert into similarRank2Table values(132,	270	,	'b112'	,	'	�ѿ���	'	,	273.4375	);");
		db.execSQL("insert into similarRank2Table values(133,	270	,	'a35'	,	'	����	'	,	273.4375	);");
		db.execSQL("insert into similarRank2Table values(134,	270	,	'a37'	,	'	������	'	,	273.4375	);");
		db.execSQL("insert into similarRank2Table values(135,	270	,	'b113'	,	'	�����	'	,	273.4375	);");
		db.execSQL("insert into similarRank2Table values(136,	285.713	,	'c6'	,	'	�����	'	,	281.25	);");
		db.execSQL("insert into similarRank2Table values(137,	285.713	,	'c7'	,	'	���ޱ�	'	,	281.25	);");
		db.execSQL("insert into similarRank2Table values(138,	285.713	,	'c8'	,	'	�ڰ���	'	,	281.25	);");
		db.execSQL("insert into similarRank2Table values(139,	285.713	,	'c9'	,	'	������	'	,	281.25	);");
		db.execSQL("insert into similarRank2Table values(140,	285.713	,	'c10'	,	'	ȫ����	'	,	281.25	);");
		db.execSQL("insert into similarRank2Table values(141,	293.062	,	'c21'	,	'	���Ϸ���	'	,	289.0625	);");
		db.execSQL("insert into similarRank2Table values(142,	293.062	,	'c22'	,	'	��Ƽ	'	,	289.0625	);");
		db.execSQL("insert into similarRank2Table values(143,	293.062	,	'c23'	,	'	��ְ�	'	,	289.0625	);");
		db.execSQL("insert into similarRank2Table values(144,	293.062	,	'c24'	,	'	�̴���	'	,	289.0625	);");
		db.execSQL("insert into similarRank2Table values(145,	293.062	,	'c25'	,	'	�ٸ���	'	,	289.0625	);");
		db.execSQL("insert into similarRank2Table values(146,	301.4	,	'c36'	,	'	����	'	,	296.875	);");
		db.execSQL("insert into similarRank2Table values(147,	301.4	,	'c37'	,	'	�μ���	'	,	296.875	);");
		db.execSQL("insert into similarRank2Table values(148,	301.4	,	'c38'	,	'	����	'	,	296.875	);");
		db.execSQL("insert into similarRank2Table values(149,	301.4	,	'c39'	,	'	���ܵ�	'	,	296.875	);");
		db.execSQL("insert into similarRank2Table values(150,	301.4	,	'c40'	,	'	������	'	,	296.875	);");

		db.execSQL("CREATE TABLE similarRank3Table ( id INTEGER ,"
				+ " dbfft DOUBLE ," + " addr TEXT , " + " image TEXT , "
				+ "  fft DOUBLE);");

		db.execSQL("insert into similarRank3Table values(1,	75	,	'a3'	,	'	�̺���	'	,	70.3125	);");
		db.execSQL("insert into similarRank3Table values(2,	75	,	'b1'	,	'	ž	'	,	70.3125	);");
		db.execSQL("insert into similarRank3Table values(3,	75	,	'b115'	,	'	�����	'	,	70.3125	);");
		db.execSQL("insert into similarRank3Table values(4,	75	,	'b116'	,	'	JK�赿��	'	,	70.3125	);");
		db.execSQL("insert into similarRank3Table values(5,	75	,	'b117'	,	'	�����	'	,	70.3125	);");
		db.execSQL("insert into similarRank3Table values(6,	81.762	,	'b194'	,	'	���·�	'	,	78.125	);");
		db.execSQL("insert into similarRank3Table values(7,	81.762	,	'b362'	,	'	����ȣ	'	,	78.125	);");
		db.execSQL("insert into similarRank3Table values(8,	81.762	,	'c70'	,	'	������	'	,	78.125	);");
		db.execSQL("insert into similarRank3Table values(9,	81.762	,	'c71'	,	'	���ϴϽ�	'	,	78.125	);");
		db.execSQL("insert into similarRank3Table values(10,	81.762	,	'c72'	,	'	�˶�����	'	,	78.125	);");
		db.execSQL("insert into similarRank3Table values(11,	88.96	,	'b270'	,	'	������	'	,	85.9375	);");
		db.execSQL("insert into similarRank3Table values(12,	88.96	,	'c73'	,	'	����ǥ	'	,	85.9375	);");
		db.execSQL("insert into similarRank3Table values(13,	88.96	,	'c74'	,	'	������	'	,	85.9375	);");
		db.execSQL("insert into similarRank3Table values(14,	88.96	,	'c75'	,	'	������	'	,	85.9375	);");
		db.execSQL("insert into similarRank3Table values(15,	88.96	,	'c76'	,	'	���»�	'	,	85.9375	);");
		db.execSQL("insert into similarRank3Table values(16,	98	,	'b221'	,	'	��ȣ��	'	,	93.75	);");
		db.execSQL("insert into similarRank3Table values(17,	98	,	'b125'	,	'	��ȣ��	'	,	93.75	);");
		db.execSQL("insert into similarRank3Table values(18,	98	,	'b13'	,	'	������	'	,	93.75	);");
		db.execSQL("insert into similarRank3Table values(19,	98	,	'b14'	,	'	�ֹμ�	'	,	93.75	);");
		db.execSQL("insert into similarRank3Table values(20,	98	,	'b269'	,	'	������	'	,	93.75	);");
		db.execSQL("insert into similarRank3Table values(21,	108	,	'a5'	,	'	�ν�	'	,	101.5625	);");
		db.execSQL("insert into similarRank3Table values(22,	108	,	'b281'	,	'	�ż���	'	,	101.5625	);");
		db.execSQL("insert into similarRank3Table values(23,	108	,	'b203'	,	'	������	'	,	101.5625	);");
		db.execSQL("insert into similarRank3Table values(24,	108	,	'b127'	,	'	ȫ����	'	,	101.5625	);");
		db.execSQL("insert into similarRank3Table values(25,	108	,	'b309'	,	'	�ֺҾ�	'	,	101.5625	);");
		db.execSQL("insert into similarRank3Table values(26,	114.17	,	'b20'	,	'	������	'	,	109.375	);");
		db.execSQL("insert into similarRank3Table values(27,	114.17	,	'b308'	,	'	���¿�	'	,	109.375	);");
		db.execSQL("insert into similarRank3Table values(28,	114.17	,	'b130'	,	'	������	'	,	109.375	);");
		db.execSQL("insert into similarRank3Table values(29,	114.17	,	'b204'	,	'	�պ�ȣ	'	,	109.375	);");
		db.execSQL("insert into similarRank3Table values(30,	114.17	,	'b216'	,	'	�̼���	'	,	109.375	);");
		db.execSQL("insert into similarRank3Table values(31,	120	,	'b186'	,	'	��â��	'	,	117.1875	);");
		db.execSQL("insert into similarRank3Table values(32,	120	,	'a9'	,	'	����õ	'	,	117.1875	);");
		db.execSQL("insert into similarRank3Table values(33,	120	,	'a8'	,	'	������	'	,	117.1875	);");
		db.execSQL("insert into similarRank3Table values(34,	120	,	'b306'	,	'	�ֿ�	'	,	117.1875	);");
		db.execSQL("insert into similarRank3Table values(35,	120	,	'b188'	,	'	������	'	,	117.1875	);");
		db.execSQL("insert into similarRank3Table values(36,	122.175	,	'b225'	,	'	������	'	,	125	);");
		db.execSQL("insert into similarRank3Table values(37,	122.175	,	'b187'	,	'	����	'	,	125	);");
		db.execSQL("insert into similarRank3Table values(38,	122.175	,	'b300'	,	'	����	'	,	125	);");
		db.execSQL("insert into similarRank3Table values(39,	122.175	,	'b356'	,	'	�ϸ���	'	,	125	);");
		db.execSQL("insert into similarRank3Table values(40,	122.175	,	'b60'	,	'	ȫ��	'	,	125	);");
		db.execSQL("insert into similarRank3Table values(41,	129	,	'b307'	,	'	�����	'	,	132.8125	);");
		db.execSQL("insert into similarRank3Table values(42,	129	,	'b266'	,	'	�輼ȯ	'	,	132.8125	);");
		db.execSQL("insert into similarRank3Table values(43,	129	,	'a14'	,	'	������ ����	'	,	132.8125	);");
		db.execSQL("insert into similarRank3Table values(44,	129	,	'b197'	,	'	������	'	,	132.8125	);");
		db.execSQL("insert into similarRank3Table values(45,	129	,	'b282'	,	'	�ȱ氭	'	,	132.8125	);");
		db.execSQL("insert into similarRank3Table values(46,	134	,	'a16'	,	'	������	'	,	140.625	);");
		db.execSQL("insert into similarRank3Table values(47,	134	,	'b301'	,	'	������	'	,	140.625	);");
		db.execSQL("insert into similarRank3Table values(48,	134	,	'b25'	,	'	�̹α�	'	,	140.625	);");
		db.execSQL("insert into similarRank3Table values(49,	134	,	'b371'	,	'	������	'	,	140.625	);");
		db.execSQL("insert into similarRank3Table values(50,	134	,	'b302'	,	'	������	'	,	140.625	);");
		db.execSQL("insert into similarRank3Table values(51,	144.2	,	'b198'	,	'	��ö��	'	,	148.4375	);");
		db.execSQL("insert into similarRank3Table values(52,	144.2	,	'b27'	,	'	������	'	,	148.4375	);");
		db.execSQL("insert into similarRank3Table values(53,	144.2	,	'b285'	,	'	���¿�	'	,	148.4375	);");
		db.execSQL("insert into similarRank3Table values(54,	144.2	,	'b28'	,	'	������	'	,	148.4375	);");
		db.execSQL("insert into similarRank3Table values(55,	144.2	,	'b210'	,	'	�쿵	'	,	148.4375	);");
		db.execSQL("insert into similarRank3Table values(56,	164	,	'b283'	,	'	�ȼ�ȯ	'	,	156.25	);");
		db.execSQL("insert into similarRank3Table values(57,	164	,	'b37'	,	'	�㰢	'	,	156.25	);");
		db.execSQL("insert into similarRank3Table values(58,	164	,	'b222'	,	'	���¿�	'	,	156.25	);");
		db.execSQL("insert into similarRank3Table values(59,	164	,	'b38'	,	'	����	'	,	156.25	);");
		db.execSQL("insert into similarRank3Table values(60,	164	,	'a21'	,	'	õ����	'	,	156.25	);");
		db.execSQL("insert into similarRank3Table values(61,	170.3	,	'b280'	,	'	�ŵ���	'	,	164.0625	);");
		db.execSQL("insert into similarRank3Table values(62,	170.3	,	'b155'	,	'	�缼��	'	,	164.0625	);");
		db.execSQL("insert into similarRank3Table values(63,	170.3	,	'b272'	,	'	������	'	,	164.0625	);");
		db.execSQL("insert into similarRank3Table values(64,	170.3	,	'b156'	,	'	��ö��	'	,	164.0625	);");
		db.execSQL("insert into similarRank3Table values(65,	170.3	,	'b153'	,	'	G�巡��	'	,	164.0625	);");
		db.execSQL("insert into similarRank3Table values(66,	178	,	'b46'	,	'	�ȳ���	'	,	171.875	);");
		db.execSQL("insert into similarRank3Table values(67,	178	,	'b47'	,	'	������	'	,	171.875	);");
		db.execSQL("insert into similarRank3Table values(68,	178	,	'b138'	,	'	�ƿ����̴�	'	,	171.875	);");
		db.execSQL("insert into similarRank3Table values(69,	178	,	'b297'	,	'	������	'	,	171.875	);");
		db.execSQL("insert into similarRank3Table values(70,	178	,	'b284'	,	'	�����	'	,	171.875	);");
		db.execSQL("insert into similarRank3Table values(71,	185.629	,	'b277'	,	'	��â��	'	,	179.6875	);");
		db.execSQL("insert into similarRank3Table values(72,	185.629	,	'b143'	,	'	������	'	,	179.6875	);");
		db.execSQL("insert into similarRank3Table values(73,	185.629	,	'b144'	,	'	�ٸ�����	'	,	179.6875	);");
		db.execSQL("insert into similarRank3Table values(74,	185.629	,	'b51'	,	'	����	'	,	179.6875	);");
		db.execSQL("insert into similarRank3Table values(75,	185.629	,	'b52'	,	'	��Ư	'	,	179.6875	);");
		db.execSQL("insert into similarRank3Table values(76,	193.4	,	'b56'	,	'	Ȳ��	'	,	187.5	);");
		db.execSQL("insert into similarRank3Table values(77,	193.4	,	'b146'	,	'	���	'	,	187.5	);");
		db.execSQL("insert into similarRank3Table values(78,	193.4	,	'b147'	,	'	��̿�	'	,	187.5	);");
		db.execSQL("insert into similarRank3Table values(79,	193.4	,	'b57'	,	'	����ö	'	,	187.5	);");
		db.execSQL("insert into similarRank3Table values(80,	193.4	,	'b242'	,	'	������	'	,	187.5	);");
		db.execSQL("insert into similarRank3Table values(81,	200.75	,	'b317'	,	'	������	'	,	195.3125	);");
		db.execSQL("insert into similarRank3Table values(82,	200.75	,	'b63'	,	'	�ȿ���	'	,	195.3125	);");
		db.execSQL("insert into similarRank3Table values(83,	200.75	,	'b227'	,	'	Ȳ����	'	,	195.3125	);");
		db.execSQL("insert into similarRank3Table values(84,	200.75	,	'b244'	,	'	�ȹ���	'	,	195.3125	);");
		db.execSQL("insert into similarRank3Table values(85,	200.75	,	'b157'	,	'	������	'	,	195.3125	);");
		db.execSQL("insert into similarRank3Table values(86,	209	,	'b161'	,	'	������	'	,	203.125	);");
		db.execSQL("insert into similarRank3Table values(87,	209	,	'b69'	,	'	������	'	,	203.125	);");
		db.execSQL("insert into similarRank3Table values(88,	209	,	'b70'	,	'	������	'	,	203.125	);");
		db.execSQL("insert into similarRank3Table values(89,	209	,	'b163'	,	'	�����	'	,	203.125	);");
		db.execSQL("insert into similarRank3Table values(90,	209	,	'a26'	,	'	�����	'	,	203.125	);");
		db.execSQL("insert into similarRank3Table values(91,	217	,	'b250'	,	'	������	'	,	210.9375	);");
		db.execSQL("insert into similarRank3Table values(92,	217	,	'a28'	,	'	�Ѹ��	'	,	210.9375	);");
		db.execSQL("insert into similarRank3Table values(93,	217	,	'b172'	,	'	����	'	,	210.9375	);");
		db.execSQL("insert into similarRank3Table values(94,	217	,	'b359'	,	'	������	'	,	210.9375	);");
		db.execSQL("insert into similarRank3Table values(95,	217	,	'b76'	,	'	������	'	,	210.9375	);");
		db.execSQL("insert into similarRank3Table values(96,	224.8	,	'b319'	,	'	�����	'	,	218.75	);");
		db.execSQL("insert into similarRank3Table values(97,	224.8	,	'b176'	,	'	������	'	,	218.75	);");
		db.execSQL("insert into similarRank3Table values(98,	224.8	,	'b249'	,	'	������	'	,	218.75	);");
		db.execSQL("insert into similarRank3Table values(99,	224.8	,	'b257'	,	'	����	'	,	218.75	);");
		db.execSQL("insert into similarRank3Table values(100,	224.8	,	'b231'	,	'	��ȿ��	'	,	218.75	);");
		db.execSQL("insert into similarRank3Table values(101,	232.45	,	'b228'	,	'	��ν�	'	,	226.5625	);");
		db.execSQL("insert into similarRank3Table values(102,	232.45	,	'b335'	,	'	�ż���	'	,	226.5625	);");
		db.execSQL("insert into similarRank3Table values(103,	232.45	,	'b331'	,	'	����ȿ	'	,	226.5625	);");
		db.execSQL("insert into similarRank3Table values(104,	232.45	,	'b334'	,	'	�Źξ�	'	,	226.5625	);");
		db.execSQL("insert into similarRank3Table values(105,	232.45	,	'b84'	,	'	����	'	,	226.5625	);");
		db.execSQL("insert into similarRank3Table values(106,	240.7	,	'b97'	,	'	������	'	,	234.375	);");
		db.execSQL("insert into similarRank3Table values(107,	240.7	,	'b246'	,	'	�����	'	,	234.375	);");
		db.execSQL("insert into similarRank3Table values(108,	240.7	,	'b230'	,	'	������	'	,	234.375	);");
		db.execSQL("insert into similarRank3Table values(109,	240.7	,	'b350'	,	'	������	'	,	234.375	);");
		db.execSQL("insert into similarRank3Table values(110,	240.7	,	'b247'	,	'	����	'	,	234.375	);");
		db.execSQL("insert into similarRank3Table values(111,	246.85	,	'b314'	,	'	������	'	,	242.1875	);");
		db.execSQL("insert into similarRank3Table values(112,	246.85	,	'b65'	,	'	��ſ�	'	,	242.1875	);");
		db.execSQL("insert into similarRank3Table values(113,	246.85	,	'b77'	,	'	������	'	,	242.1875	);");
		db.execSQL("insert into similarRank3Table values(114,	246.85	,	'b82'	,	'	������	'	,	242.1875	);");
		db.execSQL("insert into similarRank3Table values(115,	246.85	,	'b351'	,	'	����	'	,	242.1875	);");
		db.execSQL("insert into similarRank3Table values(116,	255.73	,	'b104'	,	'	����ȭ	'	,	250	);");
		db.execSQL("insert into similarRank3Table values(117,	255.73	,	'b354'	,	'	��ȭ��	'	,	250	);");
		db.execSQL("insert into similarRank3Table values(118,	255.73	,	'b105'	,	'	�質��	'	,	250	);");
		db.execSQL("insert into similarRank3Table values(119,	255.73	,	'b106'	,	'	��ȿ��	'	,	250	);");
		db.execSQL("insert into similarRank3Table values(120,	255.73	,	'b243'	,	'	������	'	,	250	);");
		db.execSQL("insert into similarRank3Table values(121,	262	,	'b90'	,	'	�̿���	'	,	257.8125	);");
		db.execSQL("insert into similarRank3Table values(122,	262	,	'b170'	,	'	������	'	,	257.8125	);");
		db.execSQL("insert into similarRank3Table values(123,	262	,	'b94'	,	'	���ظ�	'	,	257.8125	);");
		db.execSQL("insert into similarRank3Table values(124,	262	,	'b322'	,	'	��ä��	'	,	257.8125	);");
		db.execSQL("insert into similarRank3Table values(125,	262	,	'b173'	,	'	������	'	,	257.8125	);");
		db.execSQL("insert into similarRank3Table values(126,	269.71	,	'b110'	,	'	������	'	,	265.625	);");
		db.execSQL("insert into similarRank3Table values(127,	269.71	,	'b178'	,	'	Ȳ����	'	,	265.625	);");
		db.execSQL("insert into similarRank3Table values(128,	269.71	,	'b339'	,	'	������	'	,	265.625	);");
		db.execSQL("insert into similarRank3Table values(129,	269.71	,	'b169'	,	'	����	'	,	265.625	);");
		db.execSQL("insert into similarRank3Table values(130,	269.71	,	'b111'	,	'	�麸��	'	,	265.625	);");
		db.execSQL("insert into similarRank3Table values(131,	279.1	,	'c77'	,	'	���̸�	'	,	273.4375	);");
		db.execSQL("insert into similarRank3Table values(132,	279.1	,	'c78'	,	'	¯������	'	,	273.4375	);");
		db.execSQL("insert into similarRank3Table values(133,	279.1	,	'c79'	,	'	¯��	'	,	273.4375	);");
		db.execSQL("insert into similarRank3Table values(134,	279.1	,	'c80'	,	'	�λ�	'	,	273.4375	);");
		db.execSQL("insert into similarRank3Table values(135,	279.1	,	'c81'	,	'	������ rolling in the deep	'	,	273.4375	);");
		db.execSQL("insert into similarRank3Table values(136,	288.66	,	'c11'	,	'	��ĥ��	'	,	281.25	);");
		db.execSQL("insert into similarRank3Table values(137,	288.66	,	'c12'	,	'	������	'	,	281.25	);");
		db.execSQL("insert into similarRank3Table values(138,	288.66	,	'c13'	,	'	����	'	,	281.25	);");
		db.execSQL("insert into similarRank3Table values(139,	288.66	,	'c14'	,	'	�ȼ���	'	,	281.25	);");
		db.execSQL("insert into similarRank3Table values(140,	288.66	,	'c15'	,	'	���	'	,	281.25	);");
		db.execSQL("insert into similarRank3Table values(141,	296.2	,	'c26'	,	'	�����	'	,	289.0625	);");
		db.execSQL("insert into similarRank3Table values(142,	296.2	,	'c27'	,	'	��ġ	'	,	289.0625	);");
		db.execSQL("insert into similarRank3Table values(143,	296.2	,	'c28'	,	'	���ٶ˲�	'	,	289.0625	);");
		db.execSQL("insert into similarRank3Table values(144,	296.2	,	'c29'	,	'	�žָ�	'	,	289.0625	);");
		db.execSQL("insert into similarRank3Table values(145,	296.2	,	'c30'	,	'	ũ����Ƽ��	'	,	289.0625	);");
		db.execSQL("insert into similarRank3Table values(146,	304.57	,	'c41'	,	'	��������	'	,	296.875	);");
		db.execSQL("insert into similarRank3Table values(147,	304.57	,	'c42'	,	'	���������½ĸ�	'	,	296.875	);");
		db.execSQL("insert into similarRank3Table values(148,	304.57	,	'c43'	,	'	���θ�	'	,	296.875	);");
		db.execSQL("insert into similarRank3Table values(149,	304.57	,	'c44'	,	'	Ÿ¥������	'	,	296.875	);");
		db.execSQL("insert into similarRank3Table values(150,	304.57	,	'c45'	,	'	�Ӹ޾Ƹ�	'	,	296.875	);");
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		Log.i("DB OPEN", "DB OPEN OK");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// db
																				// ����
																				// ����
		db.execSQL("DROP TABLE IF EXISTS similarTable1");
		db.execSQL("DROP TABLE IF EXISTS similarRank1Table");
		db.execSQL("DROP TABLE IF EXISTS similarRank2Table");
		db.execSQL("DROP TABLE IF EXISTS similarRank3Table");
		onCreate(db);
	}
}