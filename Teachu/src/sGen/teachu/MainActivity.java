package sGen.teachu;

import sGen.teachu.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import sGen.teachu.database.*;
import sGen.teachu.forSettingPage.Setting;
import sGen.teachu.DTO.*;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Context _context=getApplicationContext();
        DBBabyInfoAdapter mAdapter = new DBBabyInfoAdapter(this);
        mAdapter.open();
       
        int babyInt = mAdapter.getBabyCount();
        
        
        Log.e("MINKA", "cursor.getCount() = " + babyInt);
     
        if(babyInt == 0){	//baby not exist
        	AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        	alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
        	    @Override
        	    public void onClick(DialogInterface dialog, int which) {
        	    	//make baby add activity 
        	    	
        	    	Log.e("MINKA", "아기추가 activity");
        	    	Intent AddBabyActivity = 
    						new Intent(MainActivity.this, AddBaby.class);
    				startActivity(AddBabyActivity);
        	    }
        	});
        	alert.setMessage("아이를 등록하세요");
        	alert.show();
        }
        else{
        	//case :  add baby
        	Button goCateTree = (Button)findViewById(R.id.goCateTree);
        	goCateTree.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent CategoryTree = new Intent(MainActivity.this, CategoryTree.class);
		        	startActivity(CategoryTree);
				}
			});
        	
        }
        mAdapter.close();
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 0, "아기 초기화");
		menu.add(0, 2, 0, "아기 상태보러가기");
		return super.onCreateOptionsMenu(menu);
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case 1 :
			//DB initializing : for testing
			DBBabyInfoAdapter mAdapter = new DBBabyInfoAdapter(this);
			mAdapter.open();
			mAdapter.deleteBaby(1);
			int babyInt = mAdapter.getBabyCount();
	        mAdapter.close();
	        
	        Log.e("MINKA", "delecomplele? mAdapter.getBabyCount() = " + babyInt);
			
		case 2 :
			Intent Setting = new Intent(MainActivity.this, Setting.class);
			startActivity(Setting);
		}
		return super.onOptionsItemSelected(item);
	}
}
