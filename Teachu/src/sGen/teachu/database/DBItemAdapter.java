package sGen.teachu.database;

import sGen.teachu.DTO.BabyInfoDTO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBItemAdapter {
	private static final String DATABASE_NAME = "teachu.db";
	private static final int DATABASE_VERSION = 1;

	public static final int TASK_COLUMN = 1;

	private SQLiteDatabase db; // 데이터베이스 작업을 할 수 있게 제공
	private final Context context;
	private DBOpenHelper dbHelper; // SQLite를 활용하는 방법[Open이나 버젼관리]

	public DBItemAdapter(Context _context) {
		context = _context;
		dbHelper = new DBOpenHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	// DB 열기
	public void open() throws SQLiteException {
		try {
			db = dbHelper.getWritableDatabase(); // 읽고 쓰기 모드로 Open
		} catch (SQLiteException ex) {
			db = dbHelper.getReadableDatabase(); // 읽기 모드로 Open
		}
	}

	// DB 닫기
	public void close() {
		db.close();
	}

	public long addBaby(BabyInfoDTO _baby) {
		ContentValues values = new ContentValues();
		
		//각 열에 값 할당
		values.put("BABY_ID", _baby.getBabyId());
		values.put("NAME", _baby.getName());
		
		//열삽입
		return db.insert("BABY_INFO", null, values);
	}

	public boolean deleteBaby(long _babyIndex) {
		return false;
	}

	public boolean updateBaby(long _babyIndex, BabyInfoDTO _baby) {
		return false;
	}

	public Cursor getAllBabyCursor() {
		return null;
	}

	public Cursor setCursorBabyInfo(long _babyIndex) throws SQLException {
		return null;
	}

	public BabyInfoDTO getBabyInfo(long _babyIndex) throws SQLException {
		return null;
	}

}
