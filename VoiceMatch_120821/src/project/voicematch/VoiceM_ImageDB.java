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

			for (int j = 0; j <= i; j++) {// 중복있나 확인
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
							.setMessage("주변을 압도하는 마법의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 2) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("속삭이듯 달콤한 부드러운 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 3) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("우아하고 섬세한 평온의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 4) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("차가운 남성적 용기의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 5) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("냉정하고 상쾌한 자유의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 6) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("젊음과 희망을 노래하는 생명의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 7) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("성실하고 열정적인 자신감이 충만한 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 8) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("명랑, 발랄하며 적극적이고 경쾌한 즐거운 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 9) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
							.setMessage("애교와 원숙미 넘치는 유혹의 소리")
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else if (groupno == 10) {
					new AlertDialog.Builder(VoiceM_ImageDB.this)
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

	}// Create

	public void onBackPressed() {
		new AlertDialog.Builder(VoiceM_ImageDB.this)
		.setTitle("목소리 이미지 종료").setIcon(R.drawable.app_icon)
		.setMessage("목소리 이미지를 종료하시겠습니까? 종료하면 메인화면으로 돌아갑니다.")
		.setPositiveButton("네", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
				Toast.makeText(VoiceM_ImageDB.this,
						"메인메뉴로 돌아갑니다.", Toast.LENGTH_SHORT).show();
			}
		})
		.setNeutralButton("다시 녹음",
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
					"목소리 이미지_yyyy년 MM월 dd일 HH시 mm분 ss초");
			String filename = sdf.format(date);
			file = Environment.getExternalStorageDirectory();
			String path = file.getAbsolutePath() + "/Voice Match/" + filename
					+ ".jpg";

			FileOutputStream out = new FileOutputStream(path);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);

			Toast.makeText(VoiceM_ImageDB.this,
					filename + ".jpg로 저장되셨습니다!", Toast.LENGTH_SHORT).show();

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

		db.execSQL("insert into imageTable9 values( 1, '고음불가',  '제비','개성파', '날렵한' ,'스마트','평화주의자','깍쟁이','청아하다','애교쟁이','흥분');");
		db.execSQL("insert into imageTable9 values( 2, '어둔하다' , '매력있는','예술적','과감한','깔끔하다','관대한','열정적','상큼새내기','소프라노','욕쟁이');");
		db.execSQL("insert into imageTable9 values( 3, '귀차니즘','훈남', '이상론자','파격적', '냉정한','민주적인','여시','인기쟁이','꽃뱀','광분');");
		db.execSQL("insert into imageTable9 values( 4, '의욕저하', '바리스타','신비','용기있는', '섹시','예의범절','리더십','사교적','꾀꼬리','희열');");
		db.execSQL("insert into imageTable9 values( 5, '걸그룹덕후','명품보이스', '재치','신뢰','독선적','순수','이기적','생기발랄','간지러움','도발');");
		db.execSQL("insert into imageTable9 values(  6, '우울함', '볼매남','관찰력','의리', '분석적','차분함','근면성실','명랑','고양이','도전적');");
		db.execSQL("insert into imageTable9 values( 7,'블랙홀', '카사노바','창조성','지조','신의','착함','얄미움','쾌활','공주병','강인함');");
		db.execSQL("insert into imageTable9 values(  8,'압도함', '달콤함','허영심','담대함', '신중함','모범','고집센','깨끗함','화려','기쁨');");
		db.execSQL("insert into imageTable9 values(  9,'마력','초콜렛','권위지향','차도남','상쾌','지혜','통통 튀는','유쾌','사치','넌센스');");
		db.execSQL("insert into imageTable9 values(  10,'빅뱅','품위','출세지향','사나이','자유','솔직','고자질꾼','귀가 얇음','도화살','도도함');");
		db.execSQL("insert into imageTable9 values(  11,'저팔계','능글맞다','변덕심','자존심', '논리적','겸손','아부왕','귀요미','유혹','시끄러움');");
		db.execSQL("insert into imageTable9 values(  12,'천하태평','느끼하다','변태','야심적','냉소적','인간적','시원함','활력적인','오두방정','소음');");
		db.execSQL("insert into imageTable9 values(  13,'보스기질','무대뽀','싸이코','장군감','까도남','편안함','외향적','유머러스','백치미','칠판긁는소리');");
		db.execSQL("insert into imageTable9 values(  14,'느려터짐','선수','또라이','강인함','당당함','온화한','일개미','천진난만','무개념','돌고래소리');");
		db.execSQL("insert into imageTable9 values(  15,'사로잡음','왕자병','거지','외강내유','선비','단아함','얌체','깜찍함','가식덩어리','과욕쟁이');");
		db.execSQL("insert into imageTable9 values(  16,'악마', '자뻑','초식동물','박력','까칠','충성심','네가지','말빨좋음','카랑카랑','눈치제로');");
		db.execSQL("insert into imageTable9 values(   17,'끌어들임','바리톤','4차원','허세','오기','쉬운사람','새침데기','사랑스러운','저음불가','용암분출');");
		db.execSQL("insert into imageTable9 values(  18,'마귀','다정다감','커밍아웃','쎈 척','똑똑함','겁쟁이','풍각쟁이','여성돋음','쌔끈하다','발칙함');");
		db.execSQL("insert into imageTable9 values(  19,'듬직함','여유로움','특이한인간','포악함','과학영재','왕소심','끈기','적극적','충동적임','괴짜');");
		db.execSQL("insert into imageTable9 values(  20,'투철함', '바람둥이','센스쟁이','남자다~','똑부러짐','온건파','도전','복덩이','내숭','타짜');");
		db.execSQL("insert into imageTable9 values(  21, '갑갑함' ,'부드러움','말썽꾸러기','헌신적','강직한','도덕교과서','호박씨','청량음료','앙칼진','욕구불만');");
		db.execSQL("insert into imageTable9 values(  22, '알토','애물단지', '히스테릭','거친입담','현명한','범생이','오리발','재롱둥이','짜증남','강열함');");
		db.execSQL("insert into imageTable9 values(  23, '품격','외유내강','소울','마초남','완벽주의자','평판좋은','노력형','앵무새','원기왕성','과격파');");
		db.execSQL("insert into imageTable9 values(  24, '터프가이','전하~','조용한','허풍','얼리어답터','여린마음','건실함','개구쟁이','낙천적','질풍노도');");
		db.execSQL("insert into imageTable9 values(  25, '소','호감형','고요한','배짱','독불장군','지고지순','열의','깝','원숙미','장엄');");
		db.execSQL("insert into imageTable9 values(  26, '무뚝뚝','낭만파','우아함','추진력','고집불통','해바라기','정직','촉새','귀여운척','혼란스러움');");
		db.execSQL("insert into imageTable9 values(  27, '무념무상','포근함','섬세한','패기','자아도취','청렴결백','약삭빠름','에너자이저','자극적','의지');");
		db.execSQL("insert into imageTable9 values(  28,'돌부처', '감미로운','방황','투지', '내성적','신앙의 힘','위풍당당','진상','신상녀','비범');");
		db.execSQL("insert into imageTable9 values(  29,'믿음직','대책없음','일탈','카리스마','황당무계','청순한','욕심쟁이','왈순아지매','매력덩어리','특별한사람');");
		db.execSQL("insert into imageTable9 values(  30,'고리타분','날라리','평온','영리한','일벌레','친절함','자존심','허당','위선','소유욕');");
		db.execSQL("insert into imageTable9 values(  31,'꼰대스타일','잘난척','꽃거지','엣지있는','무미건조','자애','악바리','나대지마라','가증스러운','집착하는');");
		db.execSQL("insert into imageTable9 values(  32,'게으름의 극치','간지작살','센치한','통찰력','지적인','자수성가','억척스럽다','끈질김','이해타산적','배신의 아이콘');");
		db.execSQL("insert into imageTable9 values(  33,'잠만보','넘사벽','외로운영혼','책임감','예민한','인내','알랑방귀','상냥함','정열','질투');");
		db.execSQL("insert into imageTable9 values(  34,'백수','능구렁이','편집증','포용력','결벽증','인자한','교묘한','생기있는','세련되다','신경질적인');");

	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		Log.i("DB OPEN", "DB OPEN OK");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// db
																				// 갈아
																				// 엎음
		db.execSQL("DROP TABLE IF EXISTS imageTable5");
		onCreate(db);
	}
}