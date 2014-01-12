package sGen.teachu.jiwon;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Baby_Information extends Activity {
	TeachuDB mHelper;
	SQLiteDatabase db;
	TextView int_ID, text_NAME, text_PW, int_AGE, text_SEX;
	String id, name, pw, age, sex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baby_info);
		
		LinearLayout linearlayout = (LinearLayout)findViewById(R.id.layout_one_showoneperson_info);
		text_NAME = new TextView(Baby_Information.this);
		text_PW = new TextView(Baby_Information.this);
		int_AGE = new TextView(Baby_Information.this);
		text_SEX = new TextView(Baby_Information.this);
		
		Intent getOnepersonInfomation=getIntent();
		
		name = getOnepersonInfomation.getStringExtra("name");
		
		mHelper=new TeachuDB(this);
		db=mHelper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("select BABY_ID, NAME, PASSWORD, AGE, SEX from BABY_INFO where NAME='"+name+"'", null);
		
		cursor.moveToNext();
		id = cursor.getString(0);
		name = cursor.getString(1);
		pw = cursor.getString(2);
		age = cursor.getString(3);
		sex = cursor.getString(4);
		
		text_NAME.setText(name);
		text_PW.setText(pw);
		int_AGE.setText(age);
		text_SEX.setText(sex);
		
		linearlayout.addView(text_NAME);
		linearlayout.addView(text_PW);
		linearlayout.addView(int_AGE);
		linearlayout.addView(text_SEX);
		
		cursor.close();
	}
	
	public void rOnclick(View v){
		Intent intent4;
		mHelper = new TeachuDB(this);
		db = mHelper.getReadableDatabase();
		// Cursor cursor;
		
		switch(v.getId()){
			case R.id.but_one_reenroll:
				intent4 = new Intent(this, Change_Baby_Info.class);
				intent4.putExtra("name", name);
				startActivity(intent4);
				finish();
				break;
			case R.id.but_one_delete:
				db.execSQL("delete from BABY_INFO where name='"+name+"';");
				Toast.makeText(this, "삭제되었음.", Toast.LENGTH_SHORT).show();
				intent4 = new Intent(this, Teachu_DB_Main.class);
				startActivity(intent4);
				finish();
				break;
			case R.id.but_one_list: 
				intent4 = new Intent(this, Teachu_DB_Main.class);
				startActivity(intent4);
				finish();
				break;
			case R.id.but_baby_grow: 
				intent4 = new Intent(this, Baby_Growth.class);
				intent4.putExtra("id", id);
				startActivity(intent4);
				finish();
				break;
				
		}
		
		// cursor.close();
	}
}
