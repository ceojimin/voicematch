package project.voicematch;

import java.io.InputStream;
import java.net.URL;

//import kidsbear.downimage.DownImageActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DrawThread implements Runnable {

	final String TAG = "DrawThread";

	Handler mainHandler;

	public DrawThread(Handler h) {
		this.mainHandler = h;
	}

	public void run() {
		InputStream is1 = null;
		InputStream is2 = null;
		InputStream is3 = null;

		Bitmap bm1 = null;
		Bitmap bm2 = null;
		Bitmap bm3 = null;

		try {
			is1 = new URL(VoiceM_SimilarDB.imageUrl1).openStream();
			bm1 = BitmapFactory.decodeStream(is1);

			is2 = new URL(VoiceM_SimilarDB.imageUrl2).openStream();
			bm2 = BitmapFactory.decodeStream(is2);

			is3 = new URL(VoiceM_SimilarDB.imageUrl3).openStream();
			bm3 = BitmapFactory.decodeStream(is3);
			is1.close();
			is2.close();
			is3.close();

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		} finally {
			Message msg1 = Message.obtain();
			msg1.what = 0;
			msg1.obj = bm1;
			Message msg2 = Message.obtain();
			msg2.what = 1;
			msg2.obj = bm2;
			Message msg3 = Message.obtain();
			msg3.what = 2;
			msg3.obj = bm3;

			mainHandler.sendMessage(msg1);
			mainHandler.sendMessage(msg2);
			mainHandler.sendMessage(msg3);
		}
	}
}