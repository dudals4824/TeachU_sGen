package sGen.teachu.database;

import java.text.ParseException;

import sGen.teachu.DTO.CategoryDTO;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBCategoryAdapter {
	private static final String DATABASE_NAME = "teachu.db";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_TABLE = "CATEGORY";

	public static final int TASK_COLUMN = 1;

	private SQLiteDatabase db; // 데이터베이스 작업을 할 수 있게 제공
	private final Context context;
	private DBOpenHelper dbHelper; // SQLite를 활용하는 방법[Open이나 버젼관리]

	public DBCategoryAdapter(Context _context) {
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

	public long addCate(CategoryDTO _cate) {
		// 참고
		// http://blog.daum.net/gunsu0j/252

		// Make row
		ContentValues values = new ContentValues();

		// 각 열에 값 할당
		values.put("CATE_ID", _cate.getCateId());
		values.put("CATE_NAME", _cate.getCateName());
		values.put("PAID", _cate.isPaid());

		// 열삽입
		return db.insert(DATABASE_TABLE, null, values);
	}

	public boolean deleteCate(long _cateId) {
		return db.delete(DATABASE_TABLE, "CATE_ID" + "=" + _cateId, null) > 0;
	}

	public int updateCate(long _cateId, CategoryDTO _cate) {
		// Make row
		ContentValues values = new ContentValues();

		// 각 열에 값 할당
		values.put("CATE_ID", _cate.getCateId());
		values.put("CATE_NAME", _cate.getCateName());
		values.put("PAID", _cate.isPaid());

		return db.update(DATABASE_TABLE, values, "CATE_ID=" + _cateId, null);
	}

	public Cursor getAllCateCursor() {
		return db.query("CATE_INFO", new String[] { "CATE_ID", "CATE_NAME",
				"PAID" }, null, null, null, null, null);
	}

	public Cursor setCursorCategory(long _cateId) throws SQLException {
		Cursor result = db.query(true, DATABASE_TABLE, new String[] {
				"CATE_ID", "CATE_NAME", "PAID" }, "CATE_ID" + "=" + _cateId,
				null, null, null, null, null);

		if ((result.getCount() == 0) || !result.moveToFirst()) {
			throw new SQLException("No Category found for id: " + _cateId);
		}

		return result;
	}

	@SuppressLint("SimpleDateFormat")
	public CategoryDTO getCategory(long _cateId) throws SQLException,
			ParseException {
		String selectSQL = "SELECT * from " + DATABASE_TABLE + "where CATE_ID="
				+ _cateId;
		Cursor cursor = db.rawQuery(selectSQL, null);
		cursor.moveToFirst();

		CategoryDTO cate = new CategoryDTO();
		cate.setCateId(cursor.getInt(0));
		cate.setCateName(cursor.getString(1));

		boolean value = cursor.getInt(2) > 0;
		cate.setPaid(value);

		return cate;
	}

}
