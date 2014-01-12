package sGen.teachu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// 테이블 생성
	private static final String BABYINFO_CREATE = "create table BABY_INFO("
			+ "BABY_ID integer primary key autoincrement,"
			+ "NAME	text not null," + "PASSWORD text not null,"
			+ "AGE	integer not null," + "SEX	text not null);";

	// DB 파일이 생성될 때 호출됨
	public void onCreate(SQLiteDatabase _db) {
		_db.execSQL(BABYINFO_CREATE);
	}

	// 버젼관리 할 때 호출됨[버젼이 일치하지 않을 때]
	public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
		Log.w("TaskDBAdapter", "Upgrading from version " + _oldVersion + " to "
				+ _newVersion + ", which will destroy all old data");

		// 테이블 삭제 및 생성
		_db.execSQL("DROP TABLE IF EXISTS BABY_INFO");
		onCreate(_db);
	}
}