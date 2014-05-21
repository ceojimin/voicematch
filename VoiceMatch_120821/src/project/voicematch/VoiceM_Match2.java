package project.voicematch;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ca.uol.aig.fftpack.RealDoubleFFT;

import com.visualizer.VisualizerView;
import com.visualizer.renderer.LineRenderer;

public class VoiceM_Match2 extends Activity implements OnClickListener {

	private static final int RECORDER_BPP = 16;

	RecordAudio recordTask;// ����

	ImageView mStartBtn, mPlayBtn, mUStartBtn, mUPlayBtn, mStopBtn, mUStopBtn,
			okay;
	TextView statusText, bufferText;
	// ImageView imageView;//���ļ� �׸��� ��

	File recordingFile1, recordingFile2;

	boolean isRecording_1 = false;
	boolean isRecording_2 = false;
	boolean isPlaying_1 = false;
	boolean isPlaying_2 = false;

	int playing = 0;

	int frequency = 8000;

	int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;

	boolean mIsStart = false;
	public DataOutputStream dos;
	public DataInputStream dis;

	File path = new File(Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/voicematch/");
	// "/Android/data/com.voicematch/files/"
	File wavpath1 = null;
	File wavpath2 = null;

	private String wavpathstr_1 = "";
	private String wavpathstr_2 = "";

	private static double ffttext_1, ffttext_2;

	public static double getFfttext_1() {
		return ffttext_1;
	}

	public static void setFfttext_1(double ffttext_1) {
		VoiceM_Match2.ffttext_1 = ffttext_1;
	}

	public static double getFfttext_2() {
		return ffttext_2;
	}

	public static void setFfttext_2(double ffttext_2) {
		VoiceM_Match2.ffttext_2 = ffttext_2;
	}

	public String getWavpath1() {
		return wavpathstr_1;
	}

	public String getWavpath2() {
		return wavpathstr_2;
	}

	// int bufferSize = AudioTrack.getMinBufferSize(frequency,
	// channelConfiguration, audioEncoding);
	int bufferSize = AudioRecord.getMinBufferSize(frequency,
			channelConfiguration, audioEncoding);

	int blockSize = 512;

	// private MediaPlayer mPlayer;
	private VisualizerView mVisualizerView;
	private MediaPlayer player1, player2;

	private RealDoubleFFT transformer;

	// path.mkdirs();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_match2);

		statusText = (TextView) this.findViewById(R.id.freqtext);
		mStartBtn = (ImageView) this.findViewById(R.id.start);
		mPlayBtn = (ImageView) this.findViewById(R.id.play);
		mStopBtn = (ImageView) this.findViewById(R.id.stop);
		mUStartBtn = (ImageView) this.findViewById(R.id.ustart);
		mUPlayBtn = (ImageView) this.findViewById(R.id.uplay);
		mUStopBtn = (ImageView) this.findViewById(R.id.ustop);

		okay = (ImageView) this.findViewById(R.id.next);

		mStartBtn.setOnClickListener(this);
		mPlayBtn.setOnClickListener(this);
		mStopBtn.setOnClickListener(this);
		mUStartBtn.setOnClickListener(this);
		mUPlayBtn.setOnClickListener(this);
		mUStopBtn.setOnClickListener(this);
		okay.setOnClickListener(this);

		mPlayBtn.setEnabled(false);
		mUStartBtn.setEnabled(false);
		mUPlayBtn.setEnabled(false);

		path.mkdirs();// ���丮 ����

		mVisualizerView = (VisualizerView) findViewById(R.id.visualizerView);
		transformer = new RealDoubleFFT(blockSize);

	}

	public void onClick(View v) {// ��ư Ŭ������ ��
		if (v == mStartBtn) {// ù��° ��������+���� ��ư
			if (!isRecording_1) {// ���� �������� ��
				new AlertDialog.Builder(VoiceM_Match2.this)
						.setTitle("�� ��Ҹ� ǰ�� �ְ� �����ϱ�")
						.setMessage(R.string.recordingmanual)
						.setPositiveButton("���� �����ϱ�",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										wavpath1 = new File(path + "/"
												+ "recording1.wav");
										wavpathstr_1 = wavpath1
												.getAbsoluteFile().toString();
										mStartBtn
												.setImageResource(R.drawable.record_un);
										mStartBtn.setEnabled(false);
										mPlayBtn.setImageResource(R.drawable.play_un);
										mPlayBtn.setEnabled(false);// ���� �߿� ������
																	// ��ư �� ����
										mUStartBtn
												.setImageResource(R.drawable.record_un);
										mUStartBtn.setEnabled(false);
										mUPlayBtn
												.setImageResource(R.drawable.play_un);
										mUPlayBtn.setEnabled(false);
										mStopBtn.setImageResource(R.drawable.stop_un);
										mStopBtn.setEnabled(false);
										mUStopBtn
												.setImageResource(R.drawable.stop_un);
										mUStopBtn.setEnabled(false);
										okay.setEnabled(false);

										isRecording_1 = true;// while������ ���� ����ǰ�
																// �ٲ���
										recordTask = new RecordAudio();
										recordTask.execute();// ��������

										Handler mHandler = new Handler();
										mHandler.postDelayed(new Runnable() {
											// Do Something
											public void run() {
												mStartBtn
														.setImageResource(R.drawable.record);
												mStartBtn.setEnabled(true);
												okay.setEnabled(true);
												mUStartBtn
														.setImageResource(R.drawable.record);
												mUStartBtn.setEnabled(true);
												isRecording_1 = false;// ���� �����ǰ�
												copyWaveFile(
														recordingFile1
																.getAbsolutePath(),
														wavpath1.getAbsolutePath());
												recordingFile1.delete();
												if (wavpath1.length() != 0) {
													mPlayBtn.setImageResource(R.drawable.play);
													mPlayBtn.setEnabled(true);
												}// �����ȰŸ� ���1��ư ������
												if (isRecording_2
														|| wavpathstr_2
																.length() != 0) {
													mUPlayBtn
															.setImageResource(R.drawable.play);
													mUPlayBtn.setEnabled(true);
												}// �����ȰŸ� ���2��ư ������ (����1�� ����� ��
													// ���� �����Ƿ� ����)
											}
										}, 2000);
									}
								}).show();
			}
		} else if (v == mPlayBtn) {// ù��° ���� ��� ��ư
			// MediaPlayer player1 = new MediaPlayer();
			// Player1 = MediaPlayer.create(this, R.raw.test);
			if (!isPlaying_1) {// ���������
				try {
					playing = 10;
					player1 = new MediaPlayer();
					player1.setDataSource(wavpath1.getAbsolutePath());
					player1.setLooping(true);
					player1.prepare();
					player1.start();
					isPlaying_1 = true;
					mVisualizerView.link(player1);
					// Start with just line renderer
					addLineRenderer();
					mStartBtn.setImageResource(R.drawable.record_un);
					mStartBtn.setEnabled(false);
					mUStartBtn.setImageResource(R.drawable.record_un);
					mUStartBtn.setEnabled(false);
					mPlayBtn.setImageResource(R.drawable.play_un);
					mPlayBtn.setEnabled(false);
					mUPlayBtn.setImageResource(R.drawable.play_un);
					mUPlayBtn.setEnabled(false);
					mStopBtn.setImageResource(R.drawable.stop);
					mStopBtn.setEnabled(true);
					mUStopBtn.setImageResource(R.drawable.stop_un);
					mUStopBtn.setEnabled(false);
				} catch (Exception e) {
					Log.e("play1", "�������");
					Toast.makeText(VoiceM_Match2.this, "����� �����Ͽ����ϴ�.\n���� �ʿ� �������ֽø� �����ϰڽ��ϴ�.",
							Toast.LENGTH_LONG).show();
					playing = 5;
					player1.stop();
					mVisualizerView.clearRenderers();
					player1.release();
					player1 = null;
					isPlaying_1 = false;
				}
			}
		} else if (v == mStopBtn) {
			if (isPlaying_1) {
				playing = 5;
				player1.stop();
				mVisualizerView.release();
				mVisualizerView.clearRenderers();
				player1.release();
				player1 = null;
				isPlaying_1 = false;
				mStartBtn.setImageResource(R.drawable.record);
				mStartBtn.setEnabled(true);
				mUStartBtn.setImageResource(R.drawable.record);
				mUStartBtn.setEnabled(true);
				mPlayBtn.setImageResource(R.drawable.play);
				mPlayBtn.setEnabled(true);
				mStopBtn.setImageResource(R.drawable.stop_un);
				mStopBtn.setEnabled(false);
				if (isRecording_2 || wavpathstr_2.length() != 0) {
					mUPlayBtn.setImageResource(R.drawable.play);
					mUPlayBtn.setEnabled(true);
				}
			}
		} else if (v == mUStartBtn) {// �ι�° ��������,���� ��ư
			if (!isRecording_2) {// ���� �������� ��
				new AlertDialog.Builder(VoiceM_Match2.this)
						.setTitle("���� ��Ҹ� ǰ�� �ְ� �����ϱ�")
						.setMessage(R.string.recordingmanual)
						.setPositiveButton("���� �����ϱ�",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										wavpath2 = new File(path + "/"
												+ "recording2.wav");
										wavpathstr_2 = wavpath2
												.getAbsoluteFile().toString();
										mStartBtn
												.setImageResource(R.drawable.record_un);
										mStartBtn.setEnabled(false);// ���� �� ��
																	// ������ ��ư
																	// �Ⱥ��̰�
										mPlayBtn.setImageResource(R.drawable.play_un);
										mPlayBtn.setEnabled(false);
										mUStartBtn
												.setImageResource(R.drawable.record_un);
										mUStartBtn.setEnabled(false);
										mUPlayBtn
												.setImageResource(R.drawable.play_un);
										mUPlayBtn.setEnabled(false);
										mStopBtn.setImageResource(R.drawable.stop_un);
										mStopBtn.setEnabled(false);
										mUStopBtn
												.setImageResource(R.drawable.stop_un);
										mUStopBtn.setEnabled(false);
										okay.setEnabled(false);

										isRecording_2 = true;
										recordTask = new RecordAudio();// ���� ����
										recordTask.execute();// ��������

										Handler mHandler = new Handler();
										mHandler.postDelayed(new Runnable() {
											// Do Something
											public void run() {
												// mUStartBtn.setText("���� ��Ҹ� ����");
												mStartBtn
														.setImageResource(R.drawable.record);
												mStartBtn.setEnabled(true);// ������
																			// �ٽ�
																			// ������
																			// ��
																			// �����ϱ�
																			// ���
																			// ���̰�
												mUStartBtn
														.setImageResource(R.drawable.record);
												mUStartBtn.setEnabled(true);
												okay.setEnabled(true);
												isRecording_2 = false;// ��������
																		// RecordTask
																		// while������
																		// ������
												copyWaveFile(
														recordingFile2
																.getAbsolutePath(),
														wavpath2.getAbsolutePath());
												recordingFile2.delete();// ���� ����
																		// �� ������
												if (wavpath1.length() != 0) {
													mPlayBtn.setImageResource(R.drawable.play);
													mPlayBtn.setEnabled(true);
												}// ���� �Ǿ�� ���1��ư ������
												if (wavpath2.length() != 0) {
													mUPlayBtn
															.setImageResource(R.drawable.play);
													mUPlayBtn.setEnabled(true);
												}// �����ȰŸ� ���2��ư ������
											}
										}, 2000);
									}
								}).show();
			}
		} else if (v == mUPlayBtn) {// �ι�° ���� ��� ��ư
			if (!isPlaying_2) {// ���������
				try {
					playing = 10;
					player2 = new MediaPlayer();
					player2.setDataSource(wavpath2.getAbsolutePath());
					player2.setLooping(true);
					player2.prepare();
					player2.start();
					isPlaying_2 = true;
					mVisualizerView.link(player2);
					// Start with just line renderer
					addLineRenderer();
					mStartBtn.setImageResource(R.drawable.record_un);
					mStartBtn.setEnabled(false);
					mUStartBtn.setImageResource(R.drawable.record_un);
					mUStartBtn.setEnabled(false);
					mPlayBtn.setImageResource(R.drawable.play_un);
					mPlayBtn.setEnabled(false);
					mUPlayBtn.setImageResource(R.drawable.play_un);
					mUPlayBtn.setEnabled(false);
					mStopBtn.setImageResource(R.drawable.stop_un);
					mStopBtn.setEnabled(false);
					mUStopBtn.setImageResource(R.drawable.stop);
					mUStopBtn.setEnabled(true);
				} catch (Exception e) {
					Log.e("play2", "�������");
					Toast.makeText(VoiceM_Match2.this, "����� �����Ͽ����ϴ�.\n���� �ʿ� �������ֽø� �����ϰڽ��ϴ�.",
							Toast.LENGTH_LONG).show();
					playing = 5;
					mVisualizerView.clearRenderers();
					player2.stop();
					player2.release();
					player2 = null;
					isPlaying_2 = false;
				}
			}
		} else if (v == mUStopBtn) {
			if (isPlaying_2) {
				playing = 5;
				mVisualizerView.release();
				mVisualizerView.clearRenderers();
				player2.stop();
				player2.release();
				player2 = null;
				isPlaying_2 = false;
				mStartBtn.setImageResource(R.drawable.record);
				mStartBtn.setEnabled(true);
				mUStartBtn.setImageResource(R.drawable.record);
				mUStartBtn.setEnabled(true);
				mPlayBtn.setImageResource(R.drawable.play);
				mPlayBtn.setEnabled(true);
				mUPlayBtn.setImageResource(R.drawable.play);
				mUPlayBtn.setEnabled(true);
				mStopBtn.setImageResource(R.drawable.stop_un);
				mStopBtn.setEnabled(false);
				mUStopBtn.setImageResource(R.drawable.stop_un);
				mUStopBtn.setEnabled(false);
			}
		} else if (v == okay) {// Ȯ�ι�ư
			if (playing == 10) {
				Toast.makeText(VoiceM_Match2.this, "����� �������� �ּ���!",
						Toast.LENGTH_SHORT).show();
			} else if (wavpathstr_1.length() == 0) {
				Toast.makeText(VoiceM_Match2.this, "������ ��Ҹ��� �������ּ���!",
						Toast.LENGTH_SHORT).show();
			} else if (wavpathstr_2.length() == 0) {
				Toast.makeText(VoiceM_Match2.this, "������ ��Ҹ��� �������ּ���!",
						Toast.LENGTH_SHORT).show();
			} else {
				VoiceM_Match vm = new VoiceM_Match();
				Toast.makeText(
						VoiceM_Match2.this,
						vm.getname1() + "�԰� " + vm.getname2()
								+ "���� ��Ī���� �˷��帳�ϴ�!", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(VoiceM_Match2.this,
						VoiceM_Loading.class);// ����������
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
				finish();
			}
		}
	}

	public void onBackPressed() {
		if (playing == 10) {
			Toast.makeText(VoiceM_Match2.this, "����� �������� �ּ���!",
					Toast.LENGTH_SHORT).show();
		} else {
			Intent intent = new Intent(VoiceM_Match2.this, VoiceM_Match.class);
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			finish();
		}
	}

	private class RecordAudio extends AsyncTask<Void, double[], Void> {// ����
																		// Integer
		@Override
		protected Void doInBackground(Void... params) {
			try {
				if (isRecording_1 == true) {// ù��° ���� ��ư �������� ����
					recordingFile1 = new File(Environment.getExternalStorageDirectory()
							.getAbsolutePath() + "/voicematch/");
					if(!recordingFile1.isDirectory()){
						recordingFile1.mkdir();
					}
					
					recordingFile1 = new File(Environment.getExternalStorageDirectory()
							.getAbsolutePath() + "/voicematch/", "record1.pcm");
					dos = new DataOutputStream(new BufferedOutputStream(
							new FileOutputStream(recordingFile1)));
				} else if (isRecording_2 == true) {// �ι�° ���� ��ư �������� ����
					recordingFile2 = new File(Environment.getExternalStorageDirectory()
							.getAbsolutePath() + "/voicematch/");
					if(!recordingFile2.isDirectory()){
						recordingFile2.mkdir();
					}
					
					recordingFile2 = new File(Environment.getExternalStorageDirectory()
							.getAbsolutePath() + "/voicematch/", "record2.pcm");
					dos = new DataOutputStream(new BufferedOutputStream(
							new FileOutputStream(recordingFile2)));
				}

				AudioRecord audioRecord = new AudioRecord(
						MediaRecorder.AudioSource.MIC, frequency,
						channelConfiguration, audioEncoding, bufferSize);

				short[] bufferft = new short[blockSize];// short->byte
				byte[] buffer = new byte[bufferSize];
				double[] toTransform = new double[blockSize];

				audioRecord.startRecording();

				// int r = 0;
				while (isRecording_1 || isRecording_2) {// �� ���ϳ��� true�� ���� ����
					int bufferReadResult = audioRecord.read(buffer, 0,
							bufferSize);
					int bufferReadResultft = audioRecord.read(bufferft, 0,
							blockSize);// && i<bufferReadResult2

					// for (int i = 0; i < bufferReadResult; i++) {
					for (int i = 0; i < bufferReadResult && i < bufferSize; i++) {
						// dos.writeShort(buffer[i]);//���ۿ� �Ἥ ����
						dos.write(buffer[i]);
					}
					for (int j = 0; j < bufferReadResultft && j < blockSize; j++) {
						toTransform[j] = (double) bufferft[j] / 32768.0; // signed
					}

					transformer.ft(toTransform);
					publishProgress(toTransform);

					// publishProgress(new Integer(r));
					// r++;

				}
				audioRecord.stop();// while��������
				dos.close();// ���� ����

			} catch (Throwable t) {
				Log.e("AudioRecord", "Recording Failed");
			}

			return null;
		}

		// protected void onProgressUpdate(Integer... progress)
		protected void onProgressUpdate(double[]... toTransform) {

			double ft = 0, ftmax = 0;
			int valuei = 1;
			int valueprev = 0;

			if (isRecording_1) {
				// statusText.setText(progress[0].toString());
				for (int i = 1; i < toTransform[0].length; i++) {
					ft = toTransform[0][i];
					if (i > 4 && i < 117) {
						if (ft > ftmax) {
							ftmax = ft; // �ְ�����
							valueprev = valuei;
							valuei = i;
							if ((valuei - valueprev) < 3) {
								// Log.e("BUFFER",""+bufferSize);//7.8125
								ffttext_1 = valuei * 7.8125;
								statusText.setText("	" + ffttext_1 + "hz");
							}
						}
					}
				}
			} else if (isRecording_2) {
				for (int i = 1; i < toTransform[0].length; i++) {
					ft = toTransform[0][i];
					if (i > 4 && i < 117) {
						if (ft > ftmax) {
							ftmax = ft; // �ְ�����
							valueprev = valuei;
							valuei = i;
							if ((valuei - valueprev) < 3) {
								// Log.e("BUFFER",""+bufferSize);//7.8125
								ffttext_2 = valuei * 7.8125;
								statusText.setText("	" + ffttext_2 + "hz");
							}
						}
					}
				}
			}

		}
	}

	private void copyWaveFile(String inFilename, String outFilename) { // wav���Ϸ�
																		// �ٱ���
		FileInputStream in = null;
		FileOutputStream out = null;
		long totalAudioLen = 0;
		long totalDataLen = totalAudioLen + 36;
		long longSampleRate = frequency;
		int channels = 1;
		long byteRate = RECORDER_BPP * frequency * channels / 8;

		byte[] data = new byte[bufferSize];

		try {
			in = new FileInputStream(inFilename);
			out = new FileOutputStream(outFilename);
			totalAudioLen = in.getChannel().size();
			totalDataLen = totalAudioLen + 36;

			WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
					longSampleRate, channels, byteRate);

			while (in.read(data) != -1) {
				out.write(data);
			}

			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void WriteWaveFileHeader(
			// pcm���Ͽ� ��� �߰�
			FileOutputStream out, long totalAudioLen, long totalDataLen,
			long longSampleRate, int channels, long byteRate)
			throws IOException {

		byte[] header = new byte[44];

		header[0] = 'R'; // RIFF/WAVE header
		header[1] = 'I';
		header[2] = 'F';
		header[3] = 'F';
		header[4] = (byte) (totalDataLen & 0xff);
		header[5] = (byte) ((totalDataLen >> 8) & 0xff);
		header[6] = (byte) ((totalDataLen >> 16) & 0xff);
		header[7] = (byte) ((totalDataLen >> 24) & 0xff);
		header[8] = 'W';
		header[9] = 'A';
		header[10] = 'V';
		header[11] = 'E';
		header[12] = 'f'; // 'fmt ' chunk
		header[13] = 'm';
		header[14] = 't';
		header[15] = ' ';
		header[16] = 16; // 4 bytes: size of 'fmt ' chunk
		header[17] = 0;
		header[18] = 0;
		header[19] = 0;
		header[20] = 1; // format = 1
		header[21] = 0;
		header[22] = (byte) channels;
		header[23] = 0;
		header[24] = (byte) (longSampleRate & 0xff);
		header[25] = (byte) ((longSampleRate >> 8) & 0xff);
		header[26] = (byte) ((longSampleRate >> 16) & 0xff);
		header[27] = (byte) ((longSampleRate >> 24) & 0xff);
		header[28] = (byte) (byteRate & 0xff);
		header[29] = (byte) ((byteRate >> 8) & 0xff);
		header[30] = (byte) ((byteRate >> 16) & 0xff);
		header[31] = (byte) ((byteRate >> 24) & 0xff);
		header[32] = (byte) (2 * 16 / 8); // block align
		header[33] = 0;
		header[34] = RECORDER_BPP; // bits per sample
		header[35] = 0;
		header[36] = 'd';
		header[37] = 'a';
		header[38] = 't';
		header[39] = 'a';
		header[40] = (byte) (totalAudioLen & 0xff);
		header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
		header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
		header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

		out.write(header, 0, 44);
	}

	private void addLineRenderer() {
		Paint linePaint = new Paint();
		linePaint.setStrokeWidth(1f);
		linePaint.setAntiAlias(true);
		linePaint.setColor(Color.argb(88, 0, 128, 255));

		Paint lineFlashPaint = new Paint();
		lineFlashPaint.setStrokeWidth(5f);
		lineFlashPaint.setAntiAlias(true);
		lineFlashPaint.setColor(Color.argb(188, 255, 255, 255));
		LineRenderer lineRenderer = new LineRenderer(linePaint, lineFlashPaint,
				true);
		mVisualizerView.addRenderer(lineRenderer);
	}
}