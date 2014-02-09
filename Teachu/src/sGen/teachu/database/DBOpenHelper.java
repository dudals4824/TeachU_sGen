package sGen.teachu.database;

import java.util.ArrayList;

import sGen.teachu.DTO.ItemInfoDTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

	private static final int CATEGORY_FRUIT = 1; // 과일
	private static final int CATEGORY_ANIMAL = 2; // 동물
	private static final int CATEGORY_TRANSPORT = 3; // 탈것
	
	private ArrayList<ItemInfoDTO> itemList = new ArrayList<ItemInfoDTO>();

	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// BABYINFO TABLE DDL
	private static final String BABYINFO_CREATE = "create table BABY_INFO("
			+ "BABY_ID integer primary key autoincrement,"
			+ "NAME text not null," + "PASSWORD text not null,"
			+ "BIRTH string not null," + "SEX integer not null,"
			+ "PHOTO blob not null);";

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

		// add item to DB
		registAllItem();
		for (int i = 0; i < itemList.size(); i++) {
			ContentValues values = new ContentValues();
			values.put("ITEM_ID", itemList.get(i).getItemId());
			values.put("CATE_ID", itemList.get(i).getCateId());
			values.put("ITEM_NAME", itemList.get(i).getItemName());
			values.put("ITEM_FILENAME", itemList.get(i).getItemFileName());
			_db.insert("ITEM", null, values);
		}
	}

	private void registAllItem() {
		// TODO Auto-generated method stub
		
		int idx = 0;

		// 과일
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "사과", "apple")); // 사과
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "바나나", "banana")); // 바나나
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "당근", "carrot")); // 당근
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "포도", "grape")); // 포도
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "레몬", "lemon")); // 레몬
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "복숭아", "peach")); // 복숭아
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "감", "persimmon")); // 배
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "토끼", "rabbit")); // 토끼
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "토마토", "tomato")); // 토마토
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "수박", "watermelon"));// 수박
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "석류", "pomegranate"));// 수박
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "망고", "mango"));// 수박
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "배", "pear"));// 수박
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "메론", "melon"));// 수박
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "귤", "mandarin"));// 수박
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "딸기", "strawberry"));// 수박
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "체리", "cherry"));// 수박
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "참외", "muskmelon"));// 수박
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "파인애플", "pineapple"));// 수박
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_FRUIT, "코코넛", "coconut"));// 수박

		// 동물
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "사자", "lion")); // 사자
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "호랑이", "tiger")); // 호랑이
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "코끼리", "elephant")); // 코끼리
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "강아지", "dog")); // 강아지
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "고양이", "cat")); // 고양이
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "갈매기", "seamew")); // 새
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "곰", "bear")); // 곰
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "토끼", "rabbit")); // 토끼
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "닭", "chicken")); // 닭
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "병아리",
				"babychicken")); // 병아리
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "악어", "crocodile")); // 말
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "고래", "whale")); // 말
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "돼지", "pig")); // 말
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "소", "cow")); // 말
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "독수리", "eagle")); // 말
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "원숭이", "monkey")); // 말
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "너구리", "raccoon")); // 말
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "판다", "panda")); // 말
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "염소", "goat")); // 말
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_ANIMAL, "사슴", "dear")); // 말

		// 사물
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "의자", "chair")); // 의자
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "안경", "glasses")); // 안경
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "책", "book")); // 책
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "연필", "pencil")); // 연필
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "신발", "shoes")); // 신발
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "컴퓨터", "computer")); // 컴퓨터
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "가방", "bag")); // 가방
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "전화기", "phone")); //
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "침대", "bed"));
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "식탁", "dinningtable"));
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "마이크", "mic"));
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "마우스", "mouse"));
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "키보드", "keyboard"));
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "세탁기", "washingmachine"));
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "밥솥", "ricecooker"));
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "냉장고", "refrigerator"));
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "칠판", "board"));
		itemList.add(new ItemInfoDTO(idx++, CATEGORY_TRANSPORT, "변기", "toiletbowl"));

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