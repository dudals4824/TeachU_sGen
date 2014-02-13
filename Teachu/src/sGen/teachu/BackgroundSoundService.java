package sGen.teachu;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class BackgroundSoundService extends Service {
	private static final String TAG = null;
	MediaPlayer player;

	public IBinder onBind(Intent arg0) {

		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.e("MUSIC", "뮤직 플레이 시작");
		player = MediaPlayer.create(this, R.raw.bg);
		player.setLooping(true); // Set looping
		player.setVolume(100, 100);
		player.start();

	}

	public int onStartCommand(Intent intent, int flags, int startId) {

		return 1;
	}

	public void onStart(Intent intent, int startId) {
		// TO DO
	}

	public IBinder onUnBind(Intent arg0) {
		// TO DO Auto-generated method
		return null;
	}

	public void onStop() {

	}

	public void onPause() {

	}

	@Override
	public void onDestroy() {
		player.stop();
		player.release();
	}

	@Override
	public void onLowMemory() {

	}
}