package sGen.teachu.forPlay;

import sGen.teachu.R;
import sGen.teachu.R.layout;
import sGen.teachu.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PlayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}

}
