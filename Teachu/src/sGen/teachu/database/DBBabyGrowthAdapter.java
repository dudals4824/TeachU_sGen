package sGen.teachu.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sGen.teachu.DTO.BabyGrowthDTO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DBBabyGrowthAdapter {
	private static final String DATABASE_NAME = "teachu.db";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_TABLE = "BABY_GROWTH";

	private static final int COLUMN_ITEMID = 0;
	private static final int COLUMN_CATEID = 1;
	private static final int COLUMN_BABYID = 2;
	private static final int COLUMN_SHOWCNT = 3;
	private static final int COLUMN_CORRECT = 4;

	public static final int TASK_COLUMN = 1;

	private SQLiteDatabase db; // 데이터베이스 작업을 할 수 있게 제공
	private final Context context;
	private DBOpenHelper dbHelper; // SQLite를 활용하는 방법[Open이나 버젼관리]

	public DBBabyGrowthAdapter(Context _context) {
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

	public long addBabyGrowth(BabyGrowthDTO _babyGrowth) {
		// 참고
		// http://blog.daum.net/gunsu0j/252

		// Make row
		ContentValues values = new ContentValues();

		// 각 열에 값 할당
		values.put("ITEM_ID", _babyGrowth.getItemId());
		values.put("CATE_ID", _babyGrowth.getCateId());
		values.put("BABY_ID", _babyGrowth.getBabyId());
		values.put("SHOW_CNT", _babyGrowth.getShowCnt());
		values.put("CORRECT_ANS", _babyGrowth.getCorrectAns());

		// 열삽입
		return db.insert(DATABASE_TABLE, null, values);
	}

	public boolean deleteBabyGrowth_byItem(long _itemId) {
		return db.delete(DATABASE_TABLE, "ITEM_ID" + "=" + _itemId, null) > 0;
	}

	public boolean deleteBabyGrowth_byBaby(long _babyId) {
		return db.delete(DATABASE_TABLE, "BABY_ID" + "=" + _babyId, null) > 0;
	}

	public int updateGrowthForItem(long _itemId, BabyGrowthDTO _babyGrowth) {
		// Make row
		ContentValues values = new ContentValues();

		// 각 열에 값 할당
		values.put("ITEM_ID", _babyGrowth.getItemId());
		values.put("CATE_ID", _babyGrowth.getCateId());
		values.put("BABY_ID", _babyGrowth.getBabyId());
		values.put("SHOW_CNT", _babyGrowth.getShowCnt());
		values.put("CORRECT_ANS", _babyGrowth.getCorrectAns());

		return db.update(DATABASE_TABLE, values, "ITEM_ID=" + _itemId, null);
	}

	public Cursor getAllBabyGrowthCursor() {
		return db.query("BABY_GROWTH", new String[] { "ITEM_ID", "CATE_ID",
				"BABY_ID", "SHOW_CNT", "CORRECT_ANS" }, null, null, null, null,
				null);
	}

	public Cursor setCursorBabyGrowth(long _itemId) throws SQLException {
		Cursor result = db.query(true, "BABY_GROWTH", new String[] { "ITEM_ID",
				"CATE_ID", "BABY_ID", "SHOW_CNT", "CORRECT_ANS" }, "ITEM_ID"
				+ "=" + _itemId, null, null, null, null, null);

		if ((result.getCount() == 0) || !result.moveToFirst()) {
			throw new SQLException("No growth found for item: " + _itemId);
		}

		result.close();
		return result;
	}

	// return Growth of selected category
	// used in baby information page
	public float getCategoryGrowth(int _cateId, int _babyId) {
		String selectSQL = "SELECT * from " + DATABASE_TABLE
				+ " where CATE_ID = " + _cateId + " and BABY_ID = " + _babyId;
		Cursor cursor = db.rawQuery(selectSQL, null);
		cursor.moveToFirst();
		float showCnt = 0, correctCnt = 0;
		float correctRate = 0, correctRateSum = 0;
		int totalItemCnt = 0;

		while (!cursor.isLast()) {
			showCnt = cursor.getInt(COLUMN_SHOWCNT);
			correctCnt = cursor.getInt(COLUMN_SHOWCNT);
			if (showCnt != 0.0)
				correctRate = (float) (correctCnt / showCnt);
			correctRateSum += correctRate;
			totalItemCnt++;
			cursor.moveToNext();
			Log.e("GROWTH", "itemID = " + cursor.getInt(COLUMN_ITEMID)
					+ "  showCnt =" + showCnt + "  correctCnt = " + correctCnt);
		}
		cursor.close();
		if (correctRateSum == 0)
			return 0;
		else if (totalItemCnt == 0)
			return 0;
		else
			return correctRateSum / totalItemCnt;
	}

	//
	public int changeGrowthForItem(int _itemId, int _cateId, int _babyId,
			boolean isCorrect) {
		String selectSQL = "SELECT * from " + DATABASE_TABLE
				+ " where CATE_ID = " + _cateId + " and ITEM_ID = " + _itemId;
		Cursor cursor = db.rawQuery(selectSQL, null);
		cursor.moveToFirst();
		Log.e("DB", "changeGrowthFroItem 함수에 들어옴");
		Log.e("DB", "" + _itemId + _cateId + _babyId + isCorrect);
		Log.e("DB", "" + cursor.getCount());
		int currentShowCnt = cursor.getInt(COLUMN_SHOWCNT);
		int currentCorrectCnt = cursor.getInt(COLUMN_CORRECT);

		Log.e("KJK", "출제 횟수 :" + currentShowCnt + "정답 횟수 : "
				+ currentCorrectCnt);

		currentShowCnt++; // 문제 출제횟수 증가
		if (isCorrect)
			currentCorrectCnt++; // 정답일 경우 정답 횟수 증가

		ContentValues values = new ContentValues();
		// 각 열에 값 할당
		values.put("ITEM_ID", _itemId);
		values.put("CATE_ID", _cateId);
		values.put("BABY_ID", _babyId);
		values.put("SHOW_CNT", currentShowCnt); // updating
		values.put("CORRECT_ANS", currentCorrectCnt);
		cursor.close();

		return db.update(DATABASE_TABLE, values, "ITEM_ID=" + _itemId, null); // update
																				// to
																				// Database
	}

	public float getBabyGrowth(long _itemId) throws SQLException,
			ParseException {
		String selectSQL = "SELECT * from " + DATABASE_TABLE
				+ " where ITEM_ID = " + _itemId;
		Cursor cursor = db.rawQuery(selectSQL, null);
		cursor.moveToFirst();
		BabyGrowthDTO babyGrowth = new BabyGrowthDTO();
		babyGrowth.setItemId(cursor.getInt(COLUMN_ITEMID));
		babyGrowth.setCateId(cursor.getInt(COLUMN_CATEID));
		babyGrowth.setBabyId(cursor.getInt(COLUMN_BABYID));
		babyGrowth.setShowCnt(cursor.getInt(COLUMN_SHOWCNT));
		babyGrowth.setCorrectAns(cursor.getInt(COLUMN_CORRECT));

		cursor.close();
		if (babyGrowth.getShowCnt() == 0)
			return 0;
		else if (babyGrowth.getCorrectAns() == 0)
			return 0;
		else
			return babyGrowth.getShowCnt() / babyGrowth.getCorrectAns();

		// return babyGrowth;
	}

	public BabyGrowthDTO getBabyGrowthByItemId(long _itemId)
			throws SQLException, ParseException {
		String selectSQL = "SELECT * from " + DATABASE_TABLE
				+ " where ITEM_ID = " + _itemId;
		Cursor cursor = db.rawQuery(selectSQL, null);
		cursor.moveToFirst();

		BabyGrowthDTO babyGrowth = new BabyGrowthDTO();
		babyGrowth.setItemId(cursor.getInt(COLUMN_ITEMID));
		babyGrowth.setCateId(cursor.getInt(COLUMN_CATEID));
		babyGrowth.setBabyId(cursor.getInt(COLUMN_BABYID));
		babyGrowth.setShowCnt(cursor.getInt(COLUMN_SHOWCNT));
		babyGrowth.setCorrectAns(cursor.getInt(COLUMN_CORRECT));

		cursor.close();
		// return babyGrowth.getShowCnt() / babyGrowth.getCorrectAns();

		return babyGrowth;
	}

	public long setDailyGrowth() {
		// Make row
		ContentValues values = new ContentValues();
		double totalGrowth = 0;

		// 오늘 날짜 얻기
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("MM.dd",
				Locale.KOREA);
		Date currentTime = new Date();
		String mTime = mSimpleDateFormat.format(currentTime);

		// Baby 전체 growth 값 얻기
		String selectSQL = "SELECT * from " + DATABASE_TABLE;
		Cursor cursor = db.rawQuery(selectSQL, null);
		cursor.moveToFirst();
		float showCnt = 0, correctCnt = 0;
		float correctRate = 0, correctRateSum = 0;
		int totalItemCnt = 0;

		while (!cursor.isLast()) {
			showCnt = cursor.getInt(COLUMN_SHOWCNT);
			correctCnt = cursor.getInt(COLUMN_SHOWCNT);
			if (showCnt != 0.0)
				correctRate = (float) (correctCnt / showCnt);
			correctRateSum += correctRate;
			totalItemCnt++;
			cursor.moveToNext();
			Log.e("GROWTH", "itemID = " + cursor.getInt(COLUMN_ITEMID)
					+ "  showCnt =" + showCnt + "  correctCnt = " + correctCnt);
		}
		cursor.close();
		if (correctRateSum == 0)
			totalGrowth = 0;
		else if (totalItemCnt == 0)
			totalGrowth = 0;
		else
			totalGrowth = correctRateSum / totalItemCnt;

		Log.e("GROWTH 등록", mTime + "일 " + totalGrowth + "성장");
		// 각 열에 값 할당
		values.put("DATE", mTime);
		values.put("GROWTH", totalGrowth);

		// 열삽입
		long result = db.insert("DAILY_GROWTH", null, values);
		if(result<0)
			result = db.update("DAILY_GROWTH", values, "DATE=" + mTime, null);
		return result;

	}

	public Cursor getAllDailyGrowth() {
		return db.query("DAILY_GROWTH", new String[] { "DATE", "GROWTH" },
				null, null, null, null, null);
	}
}
