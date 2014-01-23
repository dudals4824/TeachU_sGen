package sGen.teachu;

import sGen.teachu.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import sGen.teachu.database.*;
import sGen.teachu.forSettingPage.Settings;
import sGen.teachu.DTO.*;


public class CategoryTree extends Activity {

	static int CategoryID_=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_tree);
		
		Button fruit = (Button)findViewById(R.id.Fruit);
		fruit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				CategoryID_ = v.getId();
				
				Intent TeachuPlayActivity = new Intent(CategoryTree.this, Play.class);
				startActivity(TeachuPlayActivity);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 0, "아기 초기화");
		menu.add(0, 2, 0,  "아기 상태 보러가기");
		return super.onCreateOptionsMenu(menu);
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case 1 :
			//DB initializing : for testing
			DBBabyInfoAdapter mAdapter = new DBBabyInfoAdapter(this);
			mAdapter.open();
			mAdapter.deleteBaby(1);
			int babyInt = mAdapter.getBabyCount();
	        mAdapter.close();
	        
	        Log.e("MINKA", "delecomplele? mAdapter.getBabyCount() = " + babyInt);
			
		case 2 :
			Intent Settings = new Intent(CategoryTree.this, Settings.class);
			startActivity(Settings);
		}
		return super.onOptionsItemSelected(item);
	}
}
