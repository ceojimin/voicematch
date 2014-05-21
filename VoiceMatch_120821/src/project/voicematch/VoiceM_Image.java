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
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.audiofx.Visualizer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ca.uol.aig.fftpack.RealDoubleFFT;

import com.visualizer.VisualizerView;
import com.visualizer.renderer.LineRenderer;

public class VoiceM_Image extends Activity {
	MediaRecorder mRecorder = null;
	Activity act = this;
	ImageView imageview, mStartBtn, mPlayBtn, mStopBtn, okay;
	EditText name;
	private static String nameedit;
	// boolean mIsStart = false;
	boolean isPlaying = false;
	boolean isRecording = false;
	// String path = "";
	Visualizer mVisualizer;
	LinearLayout mLinearLayout;
	private VisualizerView mVisualizerView;
	RecordAudio recordTask;
	File recordingFile;

	int frequency = 8000;
	private int pagenum;

	int playing = 0;

	int blockSize = 512;

	private RealDoubleFFT transformer;
	private static double fftvalue;

	TextView fftstatus;

	int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;// 모노로 하면
																		// 소리 깨짐
																		// 이상해
	int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	File path = new File(Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/voicematch/");
	File wavpath = null;

	private String wavpathstr = "";

	int bufferSize = AudioRecord.getMinBufferSize(frequency,
			channelConfiguration, audioEncoding);

	public DataOutputStream dos;
	public DataInputStream dis;
	private MediaPlayer player;

	private static final int RECORDER_BPP = 16;

	public double getStopfft() {
		return fftvalue;
	}

	public void setStopfft(double fftvalue) {
		VoiceM_Image.fftvalue = fftvalue;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_image);

		mStartBtn = (ImageView) findViewById(R.id.start);
		mPlayBtn = (ImageView) findViewById(R.id.play);
		mStopBtn = (ImageView) findViewById(R.id.stop);
		okay = (ImageView) findViewById(R.id.go);

		mPlayBtn.setEnabled(false);

		mVisualizerView = (VisualizerView) findViewById(R.id.visualizerView);

		pagenum = 2;
		VoiceM_Loading.setPagenum(pagenum);

		transformer = new RealDoubleFFT(blockSize);
		fftstatus = (TextView) findViewById(R.id.fftstatus);

		findViewById(R.id.go).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EditText nameEdit = (EditText) findViewById(R.id.nametxt);
				name = (EditText) findViewById(R.id.nametxt);
				nameedit = name.getText().toString();
				nameedit = nameedit.trim();

				if (playing == 5) {
					Toast.makeText(VoiceM_Image.this, "재생을 중지시켜 주세요!",
							Toast.LENGTH_SHORT).show();
				} else if (nameEdit.getText().toString().equals("")) {
					Toast.makeText(VoiceM_Image.this, "본인의 이름을 입력해주세요!",
							Toast.LENGTH_SHORT).show();
				} else if (wavpathstr.length() == 0) {
					Toast.makeText(VoiceM_Image.this, "목소리를 녹음해주세요!!",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(VoiceM_Image.this,
							name.getText().toString() + "님의 목소리 이미지를 알려드립니다!",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(VoiceM_Image.this,
							VoiceM_Loading.class);
					startActivity(intent);
					overridePendingTransition(android.R.anim.fade_in,
							android.R.anim.fade_out);
					finish();
				}
			}
		});

		findViewById(R.id.start).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isRecording == false) {
					new AlertDialog.Builder(VoiceM_Image.this)
							.setTitle("내 목소리 품격 있게 녹음하기")
							.setMessage(R.string.recordingmanual)
							.setPositiveButton("녹음 시작하기",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											wavpath = new File(path + "/"
													+ "recordimage.wav");
											wavpathstr = wavpath
													.getAbsoluteFile()
													.toString();
											isRecording = true;// while문에서 녹음
																// 실행되게 바꿔줌
											recordTask = new RecordAudio();
											recordTask.execute();// 녹음시작
											mStartBtn
													.setImageResource(R.drawable.record_un);
											mStartBtn.setEnabled(false);
											mPlayBtn.setImageResource(R.drawable.play_un);
											mPlayBtn.setEnabled(false);
											mStopBtn.setImageResource(R.drawable.stop_un);
											mStopBtn.setEnabled(false);
											okay.setEnabled(false);

											Handler mHandler = new Handler();
											mHandler.postDelayed(
													new Runnable() {
														// Do Something
														public void run() {
															isRecording = false;
															mStartBtn
																	.setImageResource(R.drawable.record);
															mPlayBtn.setImageResource(R.drawable.play_un);
															mPlayBtn.setEnabled(false);
															mStartBtn
																	.setEnabled(true);
															okay.setEnabled(true);
															copyWaveFile(
																	recordingFile
																			.getAbsolutePath(),
																	wavpath.getAbsolutePath());
															recordingFile
																	.delete();// 파일
																				// 복사
																				// 후
																				// 지워줌
															if (wavpath
																	.length() != 0) {
																mPlayBtn.setImageResource(R.drawable.play);
																mPlayBtn.setEnabled(true);
																okay.setEnabled(true);
															}// 녹음 되어야 버튼 보여줌
														}
													}, 2000);
										}
									}).show();
				}
			}
		});

		findViewById(R.id.play).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!isPlaying) {// 재생했을때
					try {
						playing = 5;
						player = new MediaPlayer();
						player.setDataSource(wavpath.getAbsolutePath());
						player.setLooping(true);
						player.prepare();
						player.start();
						isPlaying = true;
						mVisualizerView.link(player);
						// Start with just line renderer
						addLineRenderer();
						mStartBtn.setImageResource(R.drawable.record_un);
						mStartBtn.setEnabled(false);
						mPlayBtn.setImageResource(R.drawable.play_un);
						mPlayBtn.setEnabled(false);
						mStopBtn.setImageResource(R.drawable.stop);
						mStopBtn.setEnabled(true);
					} catch (Exception e) {
						Log.e("play1", "재생실패");
						Toast.makeText(VoiceM_Image.this, "재생에 실패하였습니다.\n저희 쪽에 문의해주시면 감사하겠습니다.",
								Toast.LENGTH_LONG).show();
						playing = 10;
						player.stop();
						mVisualizerView.clearRenderers();
						player.release();
						player = null;
						isPlaying = false;
					}
				}
			}
		});

		findViewById(R.id.stop).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isPlaying) {
					playing = 10;
					mPlayBtn.setImageResource(R.drawable.play);
					player.stop();
					mVisualizerView.release();
					mVisualizerView.clearRenderers();
					player.release();
					player = null;
					isPlaying = false;
					mStartBtn.setImageResource(R.drawable.record);
					mStartBtn.setEnabled(true);
					mPlayBtn.setImageResource(R.drawable.play);
					mPlayBtn.setEnabled(true);
					mStopBtn.setImageResource(R.drawable.stop_un);
					mStopBtn.setEnabled(false);
				}
			}
		});
	}

	public void onBackPressed() {
		if (playing == 5) {
			Toast.makeText(VoiceM_Image.this, "재생을 중지시켜 주세요!",
					Toast.LENGTH_SHORT).show();
		} else {
			super.onBackPressed();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
		}
	}

	public void onDestroy() {
		super.onDestroy();
		if (mRecorder != null) {
			mRecorder.release();
			mVisualizer.release();
			mRecorder = null;
		}
	}

	public String getName() {
		return nameedit;
	}

	private void copyWaveFile(String inFilename, String outFilename) { // wav파일로
		// 바까줌
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
			// pcm파일에 헤더 추가
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

	protected class RecordAudio extends AsyncTask<Void, double[], Void> {// 원래
		// Integer
		@Override
		protected Void doInBackground(Void... params) {
			try {
				recordingFile = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/voicematch/");
				if(!recordingFile.isDirectory()){
					recordingFile.mkdir();
				}
				
				recordingFile = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/voicematch/", "recordimage.pcm");
				
				dos = new DataOutputStream(new BufferedOutputStream(
						new FileOutputStream(recordingFile)));

				AudioRecord audioRecord = new AudioRecord(
						MediaRecorder.AudioSource.MIC, frequency,
						channelConfiguration, audioEncoding, bufferSize);

				byte[] buffer = new byte[bufferSize];// short->byte
				short[] bufferft = new short[blockSize];
				double[] toTransform = new double[blockSize];

				audioRecord.startRecording();

				int r = 0;
				while (isRecording) {// 둘 중하나가 true일 동안 실행
					int bufferReadResult = audioRecord.read(buffer, 0,
							bufferSize);
					int bufferReadResultft = audioRecord.read(bufferft, 0,
							blockSize);

					// for (int i = 0; i < bufferReadResult; i++) {
					for (int i = 0; i < bufferReadResult && i < bufferSize; i++) {
						// dos.writeShort(buffer[i]);//버퍼에 써서 녹음
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
				audioRecord.stop();// while빠져나옴
				dos.close();// 파일 닫음

			} catch (Throwable t) {
				Log.e("AudioRecord", "Recording Failed");
			}

			return null;
		}

		protected void onProgressUpdate(double[]... toTransform) {

			double ft = 0, ftmax = 0;
			int valuei = 1;
			int valueprev = 0;
			int[] freq = new int[toTransform.length];

			// statusText.setText(progress[0].toString());
			for (int i = 1; i < toTransform[0].length; i++) {
				ft = toTransform[0][i];
				if (i > 4 && i < 117) {
					if (ft > ftmax) {
						ftmax = ft; // 최고값저장
						valueprev = valuei;
						valuei = i;
						if ((valuei - valueprev) < 3) {
							// Log.e("BUFFER",""+bufferSize);//7.8125
							fftvalue = valuei * 7.8125;
							setStopfft(fftvalue);
							fftstatus.setText("" + fftvalue + "hz");
							// for(int j=0; j < freq.length; j++)
							// freq[j]=fftvalue;
						}
					}
				}
			}
		}
	}
}