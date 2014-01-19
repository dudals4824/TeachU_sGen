package sGen.teachu.database;

import sGen.teachu.DTO.ItemInfoDTO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBItemInfoAdapter {
	private static final String DATABASE_NAME = "teachu.db";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_TABLE = "ITEM_INFO";

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
		ContentValues values = new ContentValues();

		values.put("ITEM_ID", _item.getItemId());
		values.put("CATE_ID", _item.getCateId());
		values.put("ITEM_NAME", _item.getItemName());
		values.put("ITEM_FILENAME", _item.getItemFileName());

		return db.insert(DATABASE_TABLE, null, values);
	}

	public boolean deleteitem(long _itemId) {
		return db.delete(DATABASE_TABLE, "ITEM_ID" + "=" + _itemId, null) > 0;
	}

	public int updateitem(long _itemId, ItemInfoDTO _item) {
		ContentValues values = new ContentValues();

		values.put("ITEM_ID", _item.getItemId());
		values.put("CATE_ID", _item.getCateId());
		values.put("ITEM_NAME", _item.getItemName());
		values.put("ITEM_FILENAME", _item.getItemFileName());
		return db.update(DATABASE_TABLE, values, "ITEM_ID=" + _itemId, null);
	}

	public Cursor getAllitemCursor() {
		return db.query("ITEM_INFO", new String[] { "ITEM_ID",
				"CATE_ID", "ITEM_NAME", "ITEM_FILENAME" }, null, null, null, null, null);
	}

	public Cursor setCursoritemInfo(long _itemId) throws SQLException {
		Cursor result = db.query(true, "ITEM_INFO", new String[] { "ITEM_ID",
				"CATE_ID", "ITEM_NAME", "ITEM_FILENAME"},
				"ITEM_ID" + "=" + _itemId, null, null, null, null, null);

		if ((result.getCount() == 0) || !result.moveToFirst()) {
			throw new SQLException("No Todo items found for row: " + _itemId);
		}

		return result;
	}

	public ItemInfoDTO getitemInfo(long _itemId) throws SQLException {
		String selectSQL = "SELECT * from " + DATABASE_TABLE + "where ITEM_ID="
				+ _itemId;
		Cursor cursor = db.rawQuery(selectSQL, null);
		ItemInfoDTO item = new ItemInfoDTO();
		item.setItemId(cursor.getInt(0));
		item.setCateId(cursor.getInt(1));
		item.setItemName(cursor.getString(2));
		item.setItemFileName(cursor.getString(3));

		return item;
	}

}
