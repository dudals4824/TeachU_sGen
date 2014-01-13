package sGen.teachu.database;

import sGen.teachu.DTO.ItemInfoDTO;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBItemInfoAdapter {
	private static final String DATABASE_NAME = "teachu.db";
	private static final int DATABASE_VERSION = 1;

	public static final int TASK_COLUMN = 1;

	private SQLiteDatabase db; // 데이터베이스 작업을 할 수 있게 제공
	private final Context context;
	private DBOpenHelper dbHelper; // SQLite를 활용하는 방법[Open이나 버젼관리]

	public DBItemInfoAdapter(Context _context) {
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

	public long additem(ItemInfoDTO _item) {
		return 0;
	}

	public boolean deleteitem(long _itemIndex) {
		return false;
	}

	public boolean updateitem(long _itemIndex, ItemInfoDTO _item) {
		return false;
	}

	public Cursor getAllitemCursor() {
		return null;
	}

	public Cursor setCursoritemInfo(long _itemIndex) throws SQLException {
		return null;
	}

	public ItemInfoDTO getitemInfo(long _itemIndex) throws SQLException {
		return null;
	}

}
