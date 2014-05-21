package project.voicematch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VoiceM_ImageDB extends Activity {
	VoiceImageDBHelper mHelper;
	TextView view1, view2, view3, view4, view5, view6, view7, view8, view9,
			nameview, ffttext;
	ImageView group;
	int[] randid = new int[9];
	int buttonchk;
	String imagetext = "";
	Cursor cursor;
	Bitmap bm;
	RelativeLayout layout;
	
	int groupno = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_imagedb);

		view1 = (TextView) findViewById(R.id.dbstring1);
		view2 = (TextView) findViewById(R.id.dbstring2);
		view3 = (TextView) findViewById(R.id.dbstring3);
		view4 = (TextView) findViewById(R.id.dbstring4);
		view5 = (TextView) findViewById(R.id.dbstring5);
		view6 = (TextView) findViewById(R.id.dbstring6);
		view7 = (TextView) findViewById(R.id.dbstring7);
		view8 = (TextView) findViewById(R.id.dbstring8);
		view9 = (TextView) findViewById(R.id.dbstring9);
		nameview = (TextView) findViewById(R.id.textname);
		ffttext = (TextView) findViewById(R.id.fftvalue);
		
		group = (ImageView) findViewById(R.id.group);

		layout = (RelativeLayout) findViewById(R.id.resultlayout);

		mHelper = new VoiceImageDBHelper(this);

		Random rand = new Random();
		SQLiteDatabase db = mHelper.getReadableDatabase();

		nameview = (TextView) findViewById(R.id.textname);
		VoiceM_Image vi = new VoiceM_Image();
		double vmifft = vi.getStopfft();
		String sname = vi.getName();
		nameview.setText(sname);
		ffttext.setText(vmifft + "hz");

		for (int i = 0; i < 9; i++) {
			randid[i] = rand.nextInt(34) + 1;

			for (int j = 0; j <= i; j++) {// �ߺ��ֳ� Ȯ��
				if (randid[i] == randid[j] && j != i) {
					i = i - 1;
				}
			}
		}// for-ij

		if (32.7032 <= vmifft && vmifft < 82.407) {
			for (int x = 0; x < 9; x++) {
				cursor = db.rawQuery(
						"SELECT id, range1 FROM imageTable9 WHERE id='"
								+ randid[x] + "'", null);
				cursor.moveToFirst();
				imagetext = cursor.getString(1);
				if (x == 0)
					view1.setText(imagetext);
				else if (x == 1)
					view2.setText(imagetext);
				else if (x == 2)
					view3.setText(imagetext);
				else if (x == 3)
					view4.setText(imagetext);
				else if (x == 4)
					view5.setText(imagetext);
				else if (x == 5)
					view6.setText(imagetext);
				else if (x == 6)
					view7.setText(imagetext);
				else if (x == 7)
					view8.setText(imagetext);
				else if (x == 8)
					view9.setText(imagetext);
			}
			group.setImageResource(R.drawable.btn_mulberry);
			groupno = 1;
		}

		else if (82.407 <= vmifft && vmifft < 130.8128) {
			// randimgtxt(*);
			for (int x = 0; x < 9; x++) {
				cursor = db.rawQuery(
						"SELECT id, range2 FROM imageTable9 WHERE id = '"
								+ randid[x] + "'", null);
				cursor.moveToFirst();
				imagetext = cursor.getString(1);
				if (x == 0)
					view1.setText(imagetext);
				else if (x == 1)
					view2.setText(imagetext);
				else if (x == 2)
					view3.setText(imagetext);
				else if (x == 3)
					view4.setText(imagetext);
				else if (x == 4)
					view5.setText(imagetext);
				else if (x == 5)
					view6.setText(imagetext);
				else if (x == 6)
					view7.setText(imagetext);
				else if (x == 7)
					view8.setText(imagetext);
				else if (x == 8)
					view9.setText(imagetext);
			}
			group.setImageResource(R.drawable.btn_coffee);
			groupno = 2;
		}

		else if (130.8128 <= vmifft && vmifft < 146.8324) {
			// randimgtxt(*);
			for (int x = 0; x < 9; x++) {
				cursor = db.rawQuery(
						"SELECT id, range3 FROM imageTable9 WHERE id = '"
								+ randid[x] + "'", null);
				cursor.moveToFirst();
				imagetext = cursor.getString(1);
				if (x == 0)
					view1.setText(imagetext);
				else if (x == 1)
					view2.setText(imagetext);
				else if (x == 2)
					view3.setText(imagetext);
				else if (x == 3)
					view4.setText(imagetext);
				else if (x == 4)
					view5.setText(imagetext);
				else if (x == 5)
					view6.setText(imagetext);
				else if (x == 6)
					view7.setText(imagetext);
				else if (x == 7)
					view8.setText(imagetext);
				else if (x == 8)
					view9.setText(imagetext);
			}
			group.setImageResource(R.drawable.btn_grape);
			groupno = 3;
		} else if (146.8324 <= vmifft && vmifft < 164.8138) {
			// randimgtxt(*);
			for (int x = 0; x < 9; x++) {
				cursor = db.rawQuery(
						"SELECT id, range4 FROM imageTable9 WHERE id = '"
								+ randid[x] + "'", null);
				cursor.moveToFirst();
				imagetext = cursor.getString(1);
				if (x == 0)
					view1.setText(imagetext);
				else if (x == 1)
					view2.setText(imagetext);
				else if (x == 2)
					view3.setText(imagetext);
				else if (x == 3)
					view4.setText(imagetext);
				else if (x == 4)
					view5.setText(imagetext);
				else if (x == 5)
					view6.setText(imagetext);
				else if (x == 6)
					view7.setText(imagetext);
				else if (x == 7)
					view8.setText(imagetext);
				else if (x == 8)
					view9.setText(imagetext);
			}
			group.setImageResource(R.drawable.btn_blackcurrant);
			groupno = 4;
		} else if (164.8138 <= vmifft && vmifft < 174.6141) {
			// randimgtxt(*);
			for (int x = 0; x < 9; x++) {
				cursor = db.rawQuery(
						"SELECT id, range5 FROM imageTable9 WHERE id = '"
								+ randid[x] + "'", null);
				cursor.moveToFirst();
				imagetext = cursor.getString(1);
				if (x == 0)
					view1.setText(imagetext);
				else if (x == 1)
					view2.setText(imagetext);
				else if (x == 2)
					view3.setText(imagetext);
				else if (x == 3)
					view4.setText(imagetext);
				else if (x == 4)
					view5.setText(imagetext);
				else if (x == 5)
					view6.setText(imagetext);
				else if (x == 6)
					view7.setText(imagetext);
				else if (x == 7)
					view8.setText(imagetext);
				else if (x == 8)
					view9.setText(imagetext);
			}
			group.setImageResource(R.drawable.btn_blueberry);
			groupno = 5;
			
		} else if (174.6141 <= vmifft && vmifft < 195.9977) {
			// randimgtxt(range6);
			for (int x = 0; x < 9; x++) {
				cursor = db.rawQuery(
						"SELECT id, range6 FROM imageTable9 WHERE id = '"
								+ randid[x] + "'", null);
				cursor.moveToFirst();
				imagetext = cursor.getString(1);
				if (x == 0)
					view1.setText(imagetext);
				else if (x == 1)
					view2.setText(imagetext);
				else if (x == 2)
					view3.setText(imagetext);
				else if (x == 3)
					view4.setText(imagetext);
				else if (x == 4)
					view5.setText(imagetext);
				else if (x == 5)
					view6.setText(imagetext);
				else if (x == 6)
					view7.setText(imagetext);
				else if (x == 7)
					view8.setText(imagetext);
				else if (x == 8)
					view9.setText(imagetext);
			}
			group.setImageResource(R.drawable.btn_olive);
			groupno = 6;
			
		} else if (195.9977 <= vmifft && vmifft < 220) {
			// randimgtxt(*);
			for (int x = 0; x < 9; x++) {
				cursor = db.rawQuery(
						"SELECT id, range7 FROM imageTable9 WHERE id = '"
								+ randid[x] + "'", null);
				cursor.moveToFirst();
				imagetext = cursor.getString(1);
				if (x == 0)
					view1.setText(imagetext);
				else if (x == 1)
					view2.setText(imagetext);
				else if (x == 2)
					view3.setText(imagetext);
				else if (x == 3)
					view4.setText(imagetext);
				else if (x == 4)
					view5.setText(imagetext);
				else if (x == 5)
					view6.setText(imagetext);
				else if (x == 6)
					view7.setText(imagetext);
				else if (x == 7)
					view8.setText(imagetext);
				else if (x == 8)
					view9.setText(imagetext);
			}
			group.setImageResource(R.drawable.btn_lemon);
			groupno = 7;
			
		} else if (220 <= vmifft && vmifft < 246.9417) {
			// randimgtxt(*);
			for (int x = 0; x < 9; x++) {
				cursor = db.rawQuery(
						"SELECT id, range8 FROM imageTable9 WHERE id = '"
								+ randid[x] + "'", null);
				cursor.moveToFirst();
				imagetext = cursor.getString(1);
				if (x == 0)
					view1.setText(imagetext);
				else if (x == 1)
					view2.setText(imagetext);
				else if (x == 2)
					view3.setText(imagetext);
				else if (x == 3)
					view4.setText(imagetext);
				else if (x == 4)
					view5.setText(imagetext);
				else if (x == 5)
					view6.setText(imagetext);
				else if (x == 6)
					view7.setText(imagetext);
				else if (x == 7)
					view8.setText(imagetext);
				else if (x == 8)
					view9.setText(imagetext);
			}
			group.setImageResource(R.drawable.btn_ginger);
			groupno = 8;
			
		} else if (246.9417 <= vmifft && vmifft < 261.6256) {
			// randimgtxt(*);
			for (int x = 0; x < 9; x++) {
				cursor = db.rawQuery(
						"SELECT id,range9 FROM imageTable9 WHERE id = '"
								+ randid[x] + "'", null);
				cursor.moveToFirst();
				imagetext = cursor.getString(1);
				if (x == 0)
					view1.setText(imagetext);
				else if (x == 1)
					view2.setText(imagetext);
				else if (x == 2)
					view3.setText(imagetext);
				else if (x == 3)
					view4.setText(imagetext);
				else if (x == 4)
					view5.setText(imagetext);
				else if (x == 5)
					view6.setText(imagetext);
				else if (x == 6)
					view7.setText(imagetext);
				else if (x == 7)
					view8.setText(imagetext);
				else if (x == 8)
					view9.setText(imagetext);
			}
			group.setImageResource(R.drawable.btn_sukru);
			groupno = 9;
			
		} else if (261.6256 <= vmifft) {
			// randimgtxt(*);
			for (int x = 0; x < 9; x++) {
				cursor = db.rawQuery(
						"SELECT id,range10 FROM imageTable9 WHERE id = '"
								+ randid[x] + "'", null);
				cursor.moveToFirst();
				imagetext = cursor.getString(1);
				if (x == 0)
					view1.setText(imagetext);
				else if (x == 1)
					view2.setText(imagetext);
				else if (x == 2)
					view3.setText(imagetext);
				else if (x == 3)
					view4.setText(imagetext);
				else if (x == 4)
					view5.setText(imagetext);
				else if (x == 5)
					view6.setText(imagetext);
				else if (x == 6)
					view7.setText(imagetext);
				else if (x == 7)
					view8.setText(imagetext);
				else if (x == 8)
					view9.setText(imagetext);
			}
			group.setImageResource(R.drawable.btn_blackrose);
			groupno = 10;
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

		group.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (groupno == 1) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("�ֺ��� �е��ϴ� ������ �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 2) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("�ӻ��̵� ������ �ε巯�� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 3) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("����ϰ� ������ ����� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 4) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("������ ������ ����� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 5) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("�����ϰ� ������ ������ �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 6) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("������ ����� �뷡�ϴ� ������ �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 7) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("�����ϰ� �������� �ڽŰ��� �游�� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 8) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("���, �߶��ϸ� �������̰� ������ ��ſ� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 9) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("�ֱ��� ������ ��ġ�� ��Ȥ�� �Ҹ�")
							.setPositiveButton("Ȯ��",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 10) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
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

	}// Create

	public void onBackPressed() {
		new AlertDialog.Builder(VoiceM_ImageDB.this)
		.setTitle("��Ҹ� �̹��� ����").setIcon(R.drawable.app_icon)
		.setMessage("��Ҹ� �̹����� �����Ͻðڽ��ϱ�? �����ϸ� ����ȭ������ ���ư��ϴ�.")
		.setPositiveButton("��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
				Toast.makeText(VoiceM_ImageDB.this,
						"���θ޴��� ���ư��ϴ�.", Toast.LENGTH_SHORT).show();
			}
		})
		.setNeutralButton("�ٽ� ����",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						Intent intent = new Intent(VoiceM_ImageDB.this,
								VoiceM_Image.class);
						startActivity(intent);
						overridePendingTransition(android.R.anim.fade_in,
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
					"��Ҹ� �̹���_yyyy�� MM�� dd�� HH�� mm�� ss��");
			String filename = sdf.format(date);
			file = Environment.getExternalStorageDirectory();
			String path = file.getAbsolutePath() + "/Voice Match/" + filename
					+ ".jpg";

			FileOutputStream out = new FileOutputStream(path);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);

			Toast.makeText(VoiceM_ImageDB.this,
					filename + ".jpg�� ����Ǽ̽��ϴ�!", Toast.LENGTH_SHORT).show();

			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.parse("file://" + path);
			intent.setData(uri);
			sendBroadcast(intent);

		} catch (FileNotFoundException e) {
			Log.d("FileNotFoundException:", e.getMessage());
		}
	}

}// Activity

class VoiceImageDBHelper extends SQLiteOpenHelper {
	public VoiceImageDBHelper(Context context) {
		super(context, "voiceimage.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE imageTable9 ( id INTEGER ," + " range1 TEXT,"
				+ " range2 TEXT," + " range3 TEXT," + " range4 TEXT,"
				+ " range5 TEXT," + " range6 TEXT," + " range7 TEXT,"
				+ " range8 TEXT," + " range9 TEXT," + " range10 TEXT);");

		db.execSQL("insert into imageTable9 values( 1, '�����Ұ�',  '����','������', '������' ,'����Ʈ','��ȭ������','������','û���ϴ�','�ֱ�����','���');");
		db.execSQL("insert into imageTable9 values( 2, '����ϴ�' , '�ŷ��ִ�','������','������','����ϴ�','������','������','��ŭ������','�������','������');");
		db.execSQL("insert into imageTable9 values( 3, '��������','�Ƴ�', '�̻����','�İ���', '������','��������','����','�α�����','�ɹ�','����');");
		db.execSQL("insert into imageTable9 values( 4, '�ǿ�����', '�ٸ���Ÿ','�ź�','����ִ�', '����','���ǹ���','������','�米��','�Ҳ���','��');");
		db.execSQL("insert into imageTable9 values( 5, '�ɱ׷����','��ǰ���̽�', '��ġ','�ŷ�','������','����','�̱���','����߶�','��������','����');");
		db.execSQL("insert into imageTable9 values(  6, '�����', '���ų�','������','�Ǹ�', '�м���','������','�ٸ鼺��','���','�����','������');");
		db.execSQL("insert into imageTable9 values( 7,'��Ȧ', 'ī����','â����','����','����','����','��̿�','��Ȱ','���ֺ�','������');");
		db.execSQL("insert into imageTable9 values(  8,'�е���', '������','�㿵��','�����', '������','���','������','������','ȭ��','���');");
		db.execSQL("insert into imageTable9 values(  9,'����','���ݷ�','��������','������','����','����','���� Ƣ��','����','��ġ','�ͼ���');");
		db.execSQL("insert into imageTable9 values(  10,'���','ǰ��','�⼼����','�糪��','����','����','��������','�Ͱ� ����','��ȭ��','������');");
		db.execSQL("insert into imageTable9 values(  11,'���Ȱ�','�ɱ۸´�','������','������', '����','���','�ƺο�','�Ϳ��','��Ȥ','�ò�����');");
		db.execSQL("insert into imageTable9 values(  12,'õ������','�����ϴ�','����','�߽���','�ü���','�ΰ���','�ÿ���','Ȱ������','���ι���','����');");
		db.execSQL("insert into imageTable9 values(  13,'��������','�����','������','�屺��','���','�����','������','���ӷ���','��ġ��','ĥ�Ǳܴ¼Ҹ�');");
		db.execSQL("insert into imageTable9 values(  14,'��������','����','�Ƕ���','������','�����','��ȭ��','�ϰ���','õ������','������','�����Ҹ�');");
		db.execSQL("insert into imageTable9 values(  15,'�������','���ں�','����','�ܰ�����','����','�ܾ���','��ü','������','���ĵ��','��������');");
		db.execSQL("insert into imageTable9 values(  16,'�Ǹ�', '�ڻ�','�ʽĵ���','�ڷ�','��ĥ','�漺��','�װ���','��������','ī��ī��','��ġ����');");
		db.execSQL("insert into imageTable9 values(   17,'�������','�ٸ���','4����','�㼼','����','������','��ħ����','���������','�����Ұ�','��Ϻ���');");
		db.execSQL("insert into imageTable9 values(  18,'����','�����ٰ�','Ŀ�־ƿ�','�� ô','�ȶ���','������','ǳ������','��������','�ز��ϴ�','��Ģ��');");
		db.execSQL("insert into imageTable9 values(  19,'������','�����ο�','Ư�����ΰ�','������','���п���','�ռҽ�','����','������','�浿����','��¥');");
		db.execSQL("insert into imageTable9 values(  20,'��ö��', '�ٶ�����','��������','���ڴ�~','�Ⱥη���','�°���','����','������','����','Ÿ¥');");
		db.execSQL("insert into imageTable9 values(  21, '������' ,'�ε巯��','����ٷ���','�����','������','����������','ȣ�ھ�','û������','��Į��','�屸�Ҹ�');");
		db.execSQL("insert into imageTable9 values(  22, '����','�ֹ�����', '�����׸�','��ģ�Դ�','������','������','������','��յ���','¥����','������');");
		db.execSQL("insert into imageTable9 values(  23, 'ǰ��','��������','�ҿ�','���ʳ�','�Ϻ�������','��������','�����','�޹���','����ռ�','������');");
		db.execSQL("insert into imageTable9 values(  24, '��������','����~','������','��ǳ','�󸮾����','��������','�ǽ���','��������','��õ��','��ǳ�뵵');");
		db.execSQL("insert into imageTable9 values(  25, '��','ȣ����','�����','��¯','�����屺','��������','����','��','������','���');");
		db.execSQL("insert into imageTable9 values(  26, '���Ҷ�','������','�����','������','��������','�عٶ��','����','�˻�','�Ϳ���ô','ȥ��������');");
		db.execSQL("insert into imageTable9 values(  27, '���乫��','������','������','�б�','�ھƵ���','û�Ű��','������','����������','�ڱ���','����');");
		db.execSQL("insert into imageTable9 values(  28,'����ó', '���̷ο�','��Ȳ','����', '������','�ž��� ��','��ǳ���','����','�Ż��','���');");
		db.execSQL("insert into imageTable9 values(  29,'������','��å����','��Ż','ī������','Ȳ�繫��','û����','�������','�м�������','�ŷµ��','Ư���ѻ��');");
		db.execSQL("insert into imageTable9 values(  30,'��Ÿ��','����','���','������','�Ϲ���','ģ����','������','���','����','������');");
		db.execSQL("insert into imageTable9 values(  31,'���뽺Ÿ��','�߳�ô','�ɰ���','�����ִ�','���̰���','�ھ�','�ǹٸ�','����������','����������','�����ϴ�');");
		db.execSQL("insert into imageTable9 values(  32,'�������� ��ġ','�����ۻ�','��ġ��','������','������','�ڼ�����','��ô������','������','����Ÿ����','����� ������');");
		db.execSQL("insert into imageTable9 values(  33,'�Ḹ��','�ѻ纮','�ܷοȥ','å�Ӱ�','������','�γ�','�˶����','�����','����','����');");
		db.execSQL("insert into imageTable9 values(  34,'���','�ɱ�����','������','�����','�Ắ��','������','������','�����ִ�','���õǴ�','�Ű�������');");

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		Log.i("DB OPEN", "DB OPEN OK");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// db
																				// ����
																				// ����
		db.execSQL("DROP TABLE IF EXISTS imageTable5");
		onCreate(db);
	}
}