package sGen.teachu.jiwon;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TeachuEnroll extends Activity {
	EditText text_NAME;
	EditText text_PW;
	EditText int_AGE;
	RadioGroup gr_SEX;
	TeachuDB tHelper;
	GrowthDB gHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enroll_baby);
		
		text_NAME = (EditText)findViewById(R.id.baby_name);
		text_PW = (EditText)findViewById(R.id.baby_pw);
		int_AGE = (EditText)findViewById(R.id.baby_age);
		gr_SEX = (RadioGroup)findViewById(R.id.sexradiogroup);
		tHelper = new TeachuDB(this);
		gHelper = new GrowthDB(this);
	}
	//mOnclick메서드
	public void mOnclick(View v){
		SQLiteDatabase tdb;
		SQLiteDatabase gdb;
		tdb = tHelper.getWritableDatabase();
		gdb = gHelper.getWritableDatabase();
		
		String name = text_NAME.getText().toString();
		String pw = text_PW.getText().toString();
		String age = int_AGE.getText().toString();
		String sex = "";
		
		if(gr_SEX.getCheckedRadioButtonId() == R.id.radio_girls) sex = "여자";
		else sex = "남자";
	
		if(v.getId()==R.id.but_enroll){
			tdb.execSQL("INSERT INTO BABY_INFO VALUES(null,'" + name + "','" + pw + "'," + age + ",'" + sex + "');");
			gdb.execSQL("INSERT INTO BABY_GROWTH VALUES(null, 0, 0, 0, 0);");
			Toast.makeText(TeachuEnroll.this, "아기 정보 등록!!", Toast.LENGTH_SHORT).show();
		} else if(v.getId() == R.id.but_list) {
			Intent intent=new Intent(TeachuEnroll.this, Teachu_DB_Main.class);
			startActivity(intent);
			finish();
		}
	}
}
