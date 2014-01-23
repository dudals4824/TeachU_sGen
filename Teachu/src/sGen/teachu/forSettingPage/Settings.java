package sGen.teachu.forSettingPage;

import java.text.ParseException;

import sGen.teachu.*;
import sGen.teachu.DTO.BabyInfoDTO;
import sGen.teachu.database.DBBabyInfoAdapter;
import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.TextView;

public class Settings extends Activity{

	DBBabyInfoAdapter mBabyAdapter = new DBBabyInfoAdapter(this); 
	BabyInfoDTO Baby = new BabyInfoDTO(); 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_page);
		
		mBabyAdapter.open();
		try {
			Baby.setName(mBabyAdapter.getBabyInfo(0).getName());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextView BabyName = (TextView)findViewById(R.id.babyName);
		
		BabyName.setText("" + Baby.getName());
	}
	
}
