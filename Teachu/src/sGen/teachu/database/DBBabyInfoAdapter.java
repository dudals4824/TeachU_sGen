package sGen.teachu.database;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;

import sGen.teachu.DTO.BabyInfoDTO;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class DBBabyInfoAdapter {
	private static final String DATABASE_NAME = "teachu.db";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_TABLE = "BABY_INFO";

	public static final int TASK_COLUMN = 1;

	private SQLiteDatabase db; // 데이터베이스 작업을 할 수 있게 제공
	private final Context context;
	private DBOpenHelper dbHelper; // SQLite를 활용하는 방법[Open이나 버젼관리]

	public DBBabyInfoAdapter(Context _context) {
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
		// 참고
		// http://blog.daum.net/gunsu0j/252

		// Make row
		ContentValues values = new ContentValues();

		// convert baby photo bitmap to byte array

		// 출력 스트링 생성, 압축 및 바이트로 변환
		ByteArrayOutputStream photoStream = new ByteArrayOutputStream();
		_baby.getPhoto().compress(CompressFormat.PNG, 100, photoStream);
		byte[] photoByte = photoStream.toByteArray();

		// 각 열에 값 할당
		values.put("BABY_ID", _baby.getBabyId());
		values.put("NAME", _baby.getName());
		values.put("PASSWORD", _baby.getPassword());
		values.put("BIRTH", _baby.getBirth());
		values.put("SEX", _baby.getSex());
		values.put("PHOTO", photoByte);

		// 열삽입
		return db.insert(DATABASE_TABLE, null, values);
	}

	public boolean deleteBaby(long _babyId) {
		return db.delete(DATABASE_TABLE, "BABY_ID" + "=" + _babyId, null) > 0;
	}

	public int updateBaby(long _babyId, BabyInfoDTO _baby) {
		// Make row
		ContentValues values = new ContentValues();

		// 출력 스트링 생성, 압축 및 바이트로 변환
		ByteArrayOutputStream photoStream = new ByteArrayOutputStream();
		_baby.getPhoto().compress(CompressFormat.PNG, 100, photoStream);
		byte[] photoByte = photoStream.toByteArray();

		// 각 열에 값 할당
		values.put("BABY_ID", _baby.getBabyId());
		values.put("NAME", _baby.getName());
		values.put("PASSWORD", _baby.getPassword());
		values.put("BIRTH", _baby.getBirth());
		values.put("SEX", _baby.getSex());
		values.put("PHOTO", photoByte);

		return db.update(DATABASE_TABLE, values, "BABY_ID=" + _babyId, null);
	}

	public Cursor getAllBabyCursor() {
		return db.query("BABY_INFO", new String[] { "BABY_ID", "NAME",
				"PASSWORD", "SEX", "BIRTH" }, null, null, null, null, null);
	}

	public Cursor setCursorBabyInfo(long _babyId) throws SQLException {
		Cursor result = db.query(true, "BABY_INFO", new String[] { "BABY_ID",
				"NAME", "PASSWORD", "SEX", "BIRTH" },
				"BABY_ID" + "=" + _babyId, null, null, null, null, null);

		if ((result.getCount() == 0) || !result.moveToFirst()) {
			throw new SQLException("No Todo items found for row: " + _babyId);
		}

		return result;
	}

	@SuppressLint("SimpleDateFormat")
	public BabyInfoDTO getBabyInfo(long _babyId) throws SQLException,
			ParseException {
		String selectSQL = "SELECT * from " + DATABASE_TABLE
				+ " where BABY_ID = " + _babyId;
		Cursor cursor = db.rawQuery(selectSQL, null);
		cursor.moveToFirst();

		byte[] photoByte = cursor.getBlob(5);
		Bitmap photoBitmap = BitmapFactory.decodeByteArray(photoByte, 0,
				photoByte.length);

		BabyInfoDTO Baby = new BabyInfoDTO();
		Baby.setBabyId(cursor.getInt(0)); // babyId
		Baby.setName(cursor.getString(1));
		Baby.setPassword(cursor.getString(2));
		Baby.setSex(cursor.getInt(3));
		Baby.setBirth(cursor.getLong(4));
		Baby.setPhoto(photoBitmap);

		cursor.close();
		return Baby;
	}

	public int getBabyCount() {
		Cursor cursor = db.rawQuery("SELECT * from " + DATABASE_TABLE, null);
		return cursor.getCount();
	}

}
