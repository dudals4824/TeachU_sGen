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

	// BABYINFO TABLE DDL
	private static final String BABYINFO_CREATE = "create table BABY_INFO("
			+ "BABY_ID integer primary key autoincrement,"
			+ "NAME text not null," + "PASSWORD text not null,"
			+ "BIRTH string not null," + "SEX	integer not null);";

	// BABYGROWTH TABLE DDL
	private static final String BABYGROWTH_CREATE = "create table BABY_GROWTH("
			+ "ITEM_ID integer primary key autoincrement,"
			+ "CATE_ID integer not null," + "BABY_ID integer not null,"
			+ "SHOW_CNT integer," + "CORRECT_ANS integer);";

	// ITEMINFO TABLE DDL
	private static final String ITEM_CREATE = "create table ITEM("
			+ "ITEM_ID integer primary key autoincrement,"
			+ "CATE_ID integer not null," + "ITEM_NAME text not null,"
			+ "ITEM_FILENAME text not null);";

	// CATEGORY TABLE DDL
	private static final String CATEGORY_CREATE = "create table CATEGORY("
			+ "CATE_ID integer primary key autoincrement,"
			+ "CATE_NAME text not null," + "PAID boolean not null);";

	// DB 파일이 생성될 때 호출됨
	public void onCreate(SQLiteDatabase _db) {
		_db.execSQL(BABYINFO_CREATE);
		_db.execSQL(BABYGROWTH_CREATE);
		_db.execSQL(ITEM_CREATE);
		_db.execSQL(CATEGORY_CREATE);

	}

	// 버젼관리 할 때 호출됨[버젼이 일치하지 않을 때]
	public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
		Log.w("TaskDBAdapter", "Upgrading from version " + _oldVersion + " to "
				+ _newVersion + ", which will destroy all old data");

		// 테이블 삭제 및 생성
		_db.execSQL("DROP TABLE IF EXISTS BABY_INFO");
		_db.execSQL("DROP TABLE IF EXISTS BABY_GROWTH");
		_db.execSQL("DROP TABLE IF EXISTS ITEM");
		_db.execSQL("DROP TABLE IF EXISTS CATEGORY");
		onCreate(_db);
	}
}