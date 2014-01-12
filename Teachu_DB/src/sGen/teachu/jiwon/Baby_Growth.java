package sGen.teachu.jiwon;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Baby_Growth extends Activity {
	GrowthDB gHelper;
	SQLiteDatabase db;
	TextView int_itemID, int_cateID, int_babyID, int_showCNT, int_correctANS;
	String itemID, cateID, babyID, showCNT, correctANS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baby_growth);
		
		Intent intent5 = this.getIntent();
		babyID = intent5.getStringExtra("id");
		
		gHelper = new GrowthDB(this);
		
		db = gHelper.getWritableDatabase();
		db.execSQL("update BABY_GROWTH set BABY_ID='" + babyID + "';");
		// insert("0", "0", babyID, "0", "0");
		db = gHelper.getReadableDatabase();
		
		LinearLayout linearlayout = (LinearLayout)findViewById(R.id.layout_one_showoneperson_growth);
		int_itemID = new TextView(Baby_Growth.this);
		int_cateID = new TextView(Baby_Growth.this);
		int_babyID = new TextView(Baby_Growth.this);
		int_showCNT = new TextView(Baby_Growth.this);
		int_correctANS = new TextView(Baby_Growth.this);
		// select  from 
		Cursor cursor = db.rawQuery("select ITEM_ID, CATE_ID, SHOW_CNT, CORRECT_ANS from BABY_GROWTH where BABY_ID='" + babyID + "'", null);
		
		if(cursor != null && cursor.getCount() > 0){
			cursor.moveToNext();
			itemID = cursor.getString(0);
			cateID = cursor.getString(1);
			showCNT = cursor.getString(2);
			correctANS = cursor.getString(3);
			
			int_itemID.setText(itemID);
			int_cateID.setText(cateID);
			int_babyID.setText(babyID);
			int_showCNT.setText(showCNT);
			int_correctANS.setText(correctANS);
			
			linearlayout.addView(int_itemID);
			linearlayout.addView(int_cateID);
			linearlayout.addView(int_babyID);
			linearlayout.addView(int_showCNT);
			linearlayout.addView(int_correctANS);
		}
		
		cursor.close();
		
	}
	
	/*public void rOnclick(View v){
		Intent intent;
		mHelper = new TeachuDB(this);
		db = mHelper.getReadableDatabase();
		Cursor cursor;
		
		switch(v.getId()){
			case R.id.but_one_reenroll:
				intent = new Intent(this, Change_Baby_Info.class);
				intent.putExtra("itemID", itemID);
				startActivity(intent);
				finish();
				break;
			case R.id.but_one_delete:
				db.execSQL("delete from BABY_GROWTH where ITEM_ID='" + itemID + "';");
				Toast.makeText(this, "삭제되었음.", Toast.LENGTH_SHORT).show();
				intent = new Intent(this, Teachu_DB_Main.class);
				startActivity(intent);
				finish();
				break;
			case R.id.but_one_list: 
				intent = new Intent(this, Teachu_DB_Main.class);
				startActivity(intent);
				finish();
				break;
		}
	}*/
	
	/*public void insert(String item, String cate, String baby, String cnt, String ans) { 
        ContentValues values = new ContentValues();      
        db = gHelper.getWritableDatabase();

        values.put("ITEM_ID", item); 
        values.put("CATE_ID", cate);
        values.put("BABY_ID", baby);
        values.put("SHOW_CNT", cnt);
        values.put("CORRECT_ANS", ans);

        db.insert("BABY_GROWTH", null, values);
        
        Log.i("SQLite", "itemID : " + item + ", cateID : " + cate + ", babyID : " + baby + ", showCNT : " + cnt + ", correctANS : " + ans); 
	}	*/
}
