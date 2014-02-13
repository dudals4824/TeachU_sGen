package sGen.teachu;

import sGen.teachu.database.DBBabyInfoAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent svc = new Intent(this, BackgroundSoundService.class);
		startService(svc);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				DBBabyInfoAdapter mAdapter = new DBBabyInfoAdapter(
						MainActivity.this);
				mAdapter.open();
				int babyInt = mAdapter.getBabyCount();

				if (babyInt == 0) { // baby not exist

					Intent AddBabyActivity = new Intent(MainActivity.this,
							AddBaby.class);
					
					startActivity(AddBabyActivity);
					finish();

				} else {

					// TODO Auto-generated method stub
					Intent CategoryTree = new Intent(MainActivity.this,
							CategoryTree.class);
					CategoryTree.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(CategoryTree);
					finish();
				}
				mAdapter.close();
				
				

			}
		}, 3000);

	}
}
