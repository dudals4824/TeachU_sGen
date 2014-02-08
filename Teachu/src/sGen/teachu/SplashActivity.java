package sGen.teachu;

import sGen.teachu.database.DBBabyInfoAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				DBBabyInfoAdapter mAdapter = new DBBabyInfoAdapter(SplashActivity.this);
				mAdapter.open();
				int babyInt = mAdapter.getBabyCount();

				if (babyInt == 0) { // baby not exist

					Intent AddBabyActivity = new Intent(SplashActivity.this,
							AddBaby.class);
					startActivity(AddBabyActivity);
				} else {

					// TODO Auto-generated method stub
					Intent CategoryTree = new Intent(SplashActivity.this,
							CategoryTree.class);
					startActivity(CategoryTree);

				}
				mAdapter.close();
			}
		}, 3000);
	}

}
