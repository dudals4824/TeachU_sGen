package sGen.teachu;

import sGen.teachu.database.DBBabyInfoAdapter;
import sGen.teachu.forSettingPage.Setting;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startActivity(new Intent(this, SplashActivity.class));
		DBBabyInfoAdapter mAdapter = new DBBabyInfoAdapter(this);
		mAdapter.open();

		int babyInt = mAdapter.getBabyCount();

		if (babyInt == 0) { // baby not exist

			Intent AddBabyActivity = new Intent(MainActivity.this,
					AddBaby.class);
			startActivity(AddBabyActivity);
		} else {

			// TODO Auto-generated method stub
			Intent CategoryTree = new Intent(MainActivity.this,
					CategoryTree.class);
			startActivity(CategoryTree);

		}
		mAdapter.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 0, "아기 초기화");
		menu.add(0, 2, 0, "아기 상태보러가기");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:
			// DB initializing : for testing
			DBBabyInfoAdapter mAdapter = new DBBabyInfoAdapter(this);
			mAdapter.open();
			mAdapter.deleteBaby(1);
			int babyInt = mAdapter.getBabyCount();
			mAdapter.close();

			Log.e("MINKA", "delecomplele? mAdapter.getBabyCount() = " + babyInt);

		case 2:
			Intent Setting = new Intent(MainActivity.this, Setting.class);
			startActivity(Setting);
		}
		return super.onOptionsItemSelected(item);
	}
}
