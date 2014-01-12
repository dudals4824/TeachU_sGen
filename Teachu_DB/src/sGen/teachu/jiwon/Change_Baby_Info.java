package sGen.teachu.jiwon;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Change_Baby_Info extends Activity {
	EditText text_NAME;
	EditText text_PW;
	EditText int_AGE;
	RadioGroup gr_SEX;
	
	TeachuDB mHelper;
	SQLiteDatabase db;
	String name, pw, age, sex, originName;
	Cursor cursor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baby_newenroll);
		
		text_NAME = (EditText)findViewById(R.id.change_name);
		text_PW = (EditText)findViewById(R.id.change_pw);
		int_AGE = (EditText)findViewById(R.id.change_age);
		gr_SEX = (RadioGroup)findViewById(R.id.change_sexradiogroup);
		
		Intent REgetInent = getIntent();
		name = REgetInent.getStringExtra("name");
		originName = name;
		
		mHelper = new TeachuDB(this);
		db = mHelper.getWritableDatabase();
		
		cursor = db.rawQuery("select NAME, PASSWORD, AGE, SEX from BABY_INFO where NAME='"+name+"'", null);
		
		cursor.moveToNext();
		name = cursor.getString(0);
		pw = cursor.getString(1);
		age = cursor.getString(2);
		sex = cursor.getString(3);
		
		text_NAME.setText(name);
		text_PW.setText(pw);
		int_AGE.setText(age);
		
		if(sex.equals("여자")){
			gr_SEX.check(R.id.ch_radio_girls);
		}else if(sex.equals("남자")){
			gr_SEX.check(R.id.ch_radio_boys);
		}
		
		cursor.close();
		
	}
	
	
	public void mOnclick(View v){
		Intent intent;
		name = ((EditText)findViewById(R.id.change_name)).getText().toString();
		pw = ((EditText)findViewById(R.id.change_pw)).getText().toString();
		age = ((EditText)findViewById(R.id.change_age)).getText().toString();
		if(gr_SEX.getCheckedRadioButtonId() == R.id.ch_radio_boys) sex = "남자";
		else if(gr_SEX.getCheckedRadioButtonId() == R.id.ch_radio_girls) sex = "여자";
		
		switch(v.getId()){
		case R.id.but_change_info:
			db.execSQL("update BABY_INFO set NAME='"+name+"',PASSWORD='"+pw+"',AGE='"+age+"' where name='"+originName+"';");
			Toast.makeText(Change_Baby_Info.this, "갱신되었음", Toast.LENGTH_SHORT).show();
			intent=new Intent(Change_Baby_Info.this, Teachu_DB_Main.class);
			startActivity(intent);
			finish();
			break;
		case R.id.but_change_cancel:
			intent=new Intent(Change_Baby_Info.this, Teachu_DB_Main.class);
			startActivity(intent);
			finish();
			break;
		}
	}
}
