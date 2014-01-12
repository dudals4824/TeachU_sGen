package sGen.teachu.jiwon;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Teachu_DB_Main extends Activity implements OnClickListener {
	SQLiteDatabase db;
	LinearLayout linear;
	TeachuDB mHelper;
	TextView text_NAME, text_PW, int_AGE, text_SEX;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teachu_db_main);
		// Log.i("여기가 main", "MAIN");
		
		linear = (LinearLayout)findViewById(R.id.listlinearlayout);
		mHelper = new TeachuDB(this);
		dataStatus();
	}
	
	public void dataStatus() {
		int i = 0;
		db = mHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT NAME, PASSWORD, AGE, SEX FROM BABY_INFO", null);
		if(cursor.getCount() > 0){
			while(cursor.moveToNext()){
				text_NAME = new TextView(Teachu_DB_Main.this);
				text_PW = new TextView(Teachu_DB_Main.this);
				int_AGE = new TextView(Teachu_DB_Main.this);
				text_SEX = new TextView(Teachu_DB_Main.this);
				
				text_NAME.setId(i);
				text_NAME.setTag(cursor.getString(0));
				text_NAME.setOnClickListener(this);
				
				text_NAME.setText(cursor.getString(0));
				text_PW.setText(cursor.getString(1));
				int_AGE.setText(cursor.getString(2));
				text_SEX.setText(cursor.getString(3));
				
				text_NAME.setBackgroundColor(Color.BLUE);
				text_NAME.setTextColor(Color.YELLOW);
				text_NAME.setTextSize(20);
				linear.addView(text_NAME);
				linear.addView(text_PW);
				linear.addView(int_AGE);
				linear.addView(text_SEX);
				i++;
			}
		} else if(cursor.getCount() == 0) {
			TextView text = new TextView(Teachu_DB_Main.this);
			text.setText("데이터가 비어있음");
			linear.addView(text);			
		}
		
		cursor.close();
		mHelper.close();
	}

	
	public void mOnClick(View v){
		if(v.getId()==R.id.but_enroll_main){
			Intent intent2 = new Intent(Teachu_DB_Main.this, TeachuEnroll.class);
			startActivity(intent2);
			finish();
		}
	}

	public void onClick(View v) {
		Intent intent3 = new Intent(Teachu_DB_Main.this, Baby_Information.class);
		intent3.putExtra("name", (String)v.getTag());
		startActivity(intent3);
		finish();
		
	}
}
