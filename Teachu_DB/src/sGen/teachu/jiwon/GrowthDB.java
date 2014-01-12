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

public class GrowthDB extends SQLiteOpenHelper {
	
	public GrowthDB(Context context){
		super(context, "Growth db", null, 1);
		Log.i("textdbCons", "생성되었음");
	}
	
	public void onCreate(SQLiteDatabase db){
		Log.i("textdbCons", "생성되었음22222");
		db.execSQL("create table BABY_GROWTH("+
	"ITEM_ID integer primary key,"+
	"CATE_ID integer not null," +
	"BABY_ID integer not null," +
	"SHOW_CNT integer not null," +
	"CORRECT_ANS integer not null);");
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXITS BABY_GROWTH");
		onCreate(db);
	}
}
	