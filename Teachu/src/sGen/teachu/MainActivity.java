package sGen.teachu;

import sGen.teachu.database.DBBabyInfoAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button btn_start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				DBBabyInfoAdapter mAdapter = new DBBabyInfoAdapter(
						MainActivity.this);
				mAdapter.open();
				int babyInt = mAdapter.getBabyCount();

				if (babyInt == 0) { // baby not exist
					btn_start = (Button) findViewById(R.id.btn_main_start);
					btn_start.setVisibility(1);
					btn_start.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent AddBabyActivity = new Intent(
									MainActivity.this, AddBaby.class);
							startActivity(AddBabyActivity);
							finish();
						}
					});

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
