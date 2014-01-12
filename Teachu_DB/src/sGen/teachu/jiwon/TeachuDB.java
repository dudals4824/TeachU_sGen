package sGen.teachu.jiwon;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class TeachuDB extends SQLiteOpenHelper {
	
	public TeachuDB(Context context){
		super(context, "Teachu db", null, 1);
		Log.i("textdbCons", "생성되었음");
	}
	
	public void onCreate(SQLiteDatabase db){
		db.execSQL("create table BABY_INFO("+
	"BABY_ID integer primary key autoincrement,"+
	"NAME	text not null,"+
	"PASSWORD text not null,"+
	"AGE	integer not null,"+
	"SEX	text not null);");
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXITS BABY_INFO");
		onCreate(db);
	}
}
	