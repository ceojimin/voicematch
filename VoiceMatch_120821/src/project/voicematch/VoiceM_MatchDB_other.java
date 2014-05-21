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
	Random randt = new Random();// ���̺� ������ ����
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
		SQLiteDatabase db = oHelper.getReadableDatabase();// �д� db ������

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

		// �̸��� ��������
		VoiceM_Match vm = new VoiceM_Match();

		String s1 = vm.getname1();
		String s2 = vm.getname2();

		double fft_1 = VoiceM_Match2.getFfttext_1();
		double fft_2 = VoiceM_Match2.getFfttext_2();

		// textresult1.setText(""+fft_1+""+fft_2);
		textname_1.setText(s1);
		textname_2.setText(s2);

		if (vm.getSex1() == 0)
			textsex_1.setText("����");
		else
			textsex_1.setText("����");

		if (vm.getSex2() == 0)
			textsex_2.setText("����");
		else
			textsex_2.setText("����");// �̸��� ���� ���

		ffttext1.setText(" " + fft_1 + " hz");
		ffttext2.setText(" " + fft_2 + " hz");

		int group1 = rander(fft_1);
		int group2 = rander(fft_2);
		// Math.abs(group1-group2);
		// randtn = randt.nextInt(10);//���̺� �ӽ÷� ������
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
							.setMessage("�ֺ��� �е��ϴ� ������ �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 2) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("�ӻ��̵� ������ �ε巯�� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 3) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("����ϰ� ������ ����� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 4) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("������ ������ ����� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 5) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("�����ϰ� ������ ������ �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 6) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("������ ����� �뷡�ϴ� ������ �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 7) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("�����ϰ� �������� �ڽŰ��� �游�� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 8) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("���, �߶��ϸ� �������̰� ������ ��ſ� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 9) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("�ֱ��� ������ ��ġ�� ��Ȥ�� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (mgroupno == 10) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
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

		findViewById(R.id.group2).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (ugroupno == 1) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("�ֺ��� �е��ϴ� ������ �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 2) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("�ӻ��̵� ������ �ε巯�� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 3) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("����ϰ� ������ ����� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 4) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("������ ������ ����� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 5) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("�����ϰ� ������ ������ �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 6) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("������ ����� �뷡�ϴ� ������ �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 7) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("�����ϰ� �������� �ڽŰ��� �游�� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 8) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("���, �߶��ϸ� �������̰� ������ ��ſ� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 9) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
							.setMessage("�ֱ��� ������ ��ġ�� ��Ȥ�� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (ugroupno == 10) {
					new AlertDialog.Builder(VoiceM_MatchDB_other.this)
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

	}

	public void onBackPressed() {
		new AlertDialog.Builder(VoiceM_MatchDB_other.this)
				.setTitle("��Ҹ� ���� ����")
				.setIcon(R.drawable.app_icon)
				.setMessage("��Ҹ� ������ �����Ͻðڽ��ϱ�? �����ϸ� ����ȭ������ ���ư��ϴ�.")
				.setPositiveButton("��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
						overridePendingTransition(android.R.anim.fade_in,
								android.R.anim.fade_out);
						Toast.makeText(VoiceM_MatchDB_other.this,
								"���θ޴��� ���ư��ϴ�.", Toast.LENGTH_SHORT).show();
					}
				})
				.setNeutralButton("�ٽ� ����",
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
				.setNegativeButton("���", new DialogInterface.OnClickListener() {
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
					"��Ҹ� ����_yyyy�� MM�� dd�� HH�� mm�� ss��");
			String filename = sdf.format(date);
			file = Environment.getExternalStorageDirectory();
			String path = file.getAbsolutePath() + "/Voice Match/" + filename
					+ ".jpg";

			FileOutputStream out = new FileOutputStream(path);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);

			Toast.makeText(VoiceM_MatchDB_other.this,
					filename + ".jpg�� ����Ǽ̽��ϴ�!", Toast.LENGTH_SHORT).show();

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
		 * �׷� �� ���̸� �̿��ؼ� ��Ī���� ��Ÿ�� DB - �̼�, ���� ��� �׷��� ���� �������� ��Ī���� ���� (�Ҹ������� �����
		 * ���� �� ���� : ��Ҹ��� ����Ҽ��� ������ ���ٴ� ����) ���� matchTableOther0�� ���� ��Ī�� ���� DB,
		 * matchTableOther19�� ���� ��Ī�� ���� DB
		 */
		db.execSQL("CREATE TABLE matchTableOther0 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");
		db.execSQL("CREATE TABLE matchTableOther1 ( id INTEGER ,"
				+ " match TEXT, " + " message TEXT);");// db ����
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
					+ x + "', '���� ��, �ʴ� ����.. �Ϸ絵 ����ϴ�.' );");
		}
		for (int x = 6; x <= 10; x++) {
			y = x - 5;
			db.execSQL("insert into matchTableOther18 values('" + y + "', '"
					+ x + "', '������� ���� ��ó�� �� ��! �׸� �����..' );");
		}
		for (int x = 11; x <= 15; x++) {
			y = x - 10;
			db.execSQL("insert into matchTableOther17 values('" + y + "', '"
					+ x + "', '������ �ι̿��� �ٸ���. �̷������ ������~' );");
		}
		for (int x = 16; x <= 20; x++) {
			y = x - 15;
			db.execSQL("insert into matchTableOther16 values('" + y + "', '"
					+ x + "', '��~ �ʳ� �� �ž�~~' );");
		}
		for (int x = 21; x <= 25; x++) {
			y = x - 20;
			db.execSQL("insert into matchTableOther15 values('" + y + "', '"
					+ x + "', '�� ������ �鸮��? �� ������ �� ���..' );");
		}
		for (int x = 26; x <= 30; x++) {
			y = x - 25;
			db.execSQL("insert into matchTableOther14 values('" + y + "', '"
					+ x + "', '�η��� ť��Ʈ�� ȭ��...' );");
		}

		for (int x = 31; x <= 35; x++) {
			y = x - 30;
			db.execSQL("insert into matchTableOther13 values('" + y + "', '"
					+ x + "', '��ȭ�� �ʿ��� ��' );");
		}
		for (int x = 36; x <= 40; x++) {
			y = x - 35;
			db.execSQL("insert into matchTableOther12 values('" + y + "', '"
					+ x + "', '�� �ȿ� �� �ִ�. �ٵ� �ʴ�..?' );");
		}
		for (int x = 41; x <= 45; x++) {
			y = x - 40;
			db.execSQL("insert into matchTableOther11 values('" + y + "', '"
					+ x + "', '������� �� �������ٴ� �����..��' );");
		}
		for (int x = 46; x <= 50; x++) {
			y = x - 45;
			db.execSQL("insert into matchTableOther10 values('" + y + "', '"
					+ x + "', '�»꾾! ���̽���ġ�� ���� Ŀ�� �ֹ��ϴ��~' );");
		}
		for (int x = 51; x <= 55; x++) {
			y = x - 50;
			db.execSQL("insert into matchTableOther9 values('" + y + "', '" + x
					+ "', '�츮 ����.. �� ����ϸ� ���簡�� �ɷ�~' );");
		}
		for (int x = 56; x <= 60; x++) {
			y = x - 55;
			db.execSQL("insert into matchTableOther8 values('" + y + "', '" + x
					+ "', '�� ���� �ݶ� ���ƿ�. Ư������ �ʿ��մϴ�!' );");
		}
		for (int x = 61; x <= 65; x++) {
			y = x - 60;
			db.execSQL("insert into matchTableOther7 values('" + y + "', '" + x
					+ "', '���ο��� �����ּ���. Step by step!' );");
		}
		for (int x = 66; x <= 70; x++) {
			y = x - 65;
			db.execSQL("insert into matchTableOther6 values('" + y + "', '" + x
					+ "', '���� �� �� �ϼ���!' );");
		}
		for (int x = 71; x <= 75; x++) {
			y = x - 70;
			db.execSQL("insert into matchTableOther5 values('" + y + "', '" + x
					+ "', '���ڴ� �����ϱ� �����̿���~' );");
		}
		for (int x = 76; x <= 80; x++) {
			y = x - 75;
			db.execSQL("insert into matchTableOther4 values('" + y + "', '" + x
					+ "', '�츰 ���� �� ������ ��' );");
		}
		for (int x = 81; x <= 85; x++) {
			y = x - 80;
			db.execSQL("insert into matchTableOther3 values('" + y + "', '" + x
					+ "', '�츮 ���� �丸ŭ�� �� ������ ��' );");
		}
		for (int x = 86; x <= 90; x++) {
			y = x - 85;
			db.execSQL("insert into matchTableOther2 values('" + y + "', '" + x
					+ "', '�ʸ� ǰ�� ��, ���� ǰ�� ��.. ��������!' );");
		}
		for (int x = 91; x <= 95; x++) {
			y = x - 90;
			db.execSQL("insert into matchTableOther1 values('" + y + "', '" + x
					+ "', '����ǵ� ������ ȯ���� Ŀ��' );");
		}
		for (int x = 96; x <= 100; x++) {
			y = x - 95;
			db.execSQL("insert into matchTableOther0 values('" + y + "', '" + x
					+ "', '�������� �������� ��� �� �ְ��� ����' );");
		}

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		Log.i("DB OPEN", "DB OPEN OK");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// db
																				// ������Ʈ

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