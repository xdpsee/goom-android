package net.zhuoweizhang.goom;

import java.nio.*;

import android.app.Activity;
import android.media.audiofx.*;
import android.os.*;
import android.widget.*;

import android.graphics.*;

public class MainActivity extends Activity
{

	private Goom goom;
	private ByteBuffer screenBuffer;
	private short[] audioData;
	private byte[] visualizerData = new byte[512];
	private ImageView imageView;
	private Bitmap bmp;
	private Visualizer visualizer;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			renderGoom();
			this.sendEmptyMessageDelayed(0, 25);
		}
	};
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		imageView = (ImageView) findViewById(R.id.main_image_view);
		goom = Goom.goomInit(640, 480);
		screenBuffer = ByteBuffer.allocateDirect(640 * 480 * 4);
		goom.setScreenbuffer(screenBuffer);
		audioData = new short[2 * 512];
		bmp = Bitmap.createBitmap(640, 480, Bitmap.Config.ARGB_8888);
		imageView.setImageBitmap(bmp);
		visualizer = new Visualizer(0);
		visualizer.setCaptureSize(512);
		visualizer.setEnabled(true);
		handler.sendEmptyMessageDelayed(0, 25);
	}

	public void renderGoom() {
		updateAudioData();
		goom.update(audioData, 0, 0.5f, null, "test");
		screenBuffer.rewind();
		bmp.copyPixelsFromBuffer(screenBuffer);
		imageView.invalidate();
		
	}

	private void updateAudioData() {
		visualizer.getWaveForm(visualizerData);
		for (int i = 0; i < 512; i++) {
			audioData[i] = audioData[512 + i] = (short) ((visualizerData[i] - 128) << 8);
		}
	}

	public void onDestroy() {
		super.onDestroy();
		goom.close();
		visualizer.release();
		handler.removeMessages(0);
	}

}
