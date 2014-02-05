package sGen.teachu.forSettingPage;

import java.text.ParseException;

import sGen.teachu.R;
import sGen.teachu.DTO.BabyInfoDTO;
import sGen.teachu.database.DBBabyInfoAdapter;
import android.app.Activity;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Setting extends Activity {
	LinearLayout layout_graphview, progress, test;
	ScrollView layout_background;
	GridLayout layout_correctionrate;
	TextView textview_name,textview_birth;
	DBBabyInfoAdapter mBabyAdapter = new DBBabyInfoAdapter(this); 
	BabyInfoDTO Baby = new BabyInfoDTO(); 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		layout_graphview = (LinearLayout) findViewById(R.id.layout_graphview);
		layout_background = (ScrollView) findViewById(R.id.layout_background);
		layout_correctionrate = (GridLayout) findViewById(R.id.layout_correctionrate);
		textview_name=(TextView)findViewById(R.id.textview_name);
		
		mBabyAdapter.open();
		try {
			Baby=mBabyAdapter.getBabyInfo(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	Log.e("babyinfo", Baby.getName());
		textview_name.setText(Baby.getName());
		
		GraphView graphview = new GraphView(this);
		graphview.makeGraph(this, layout_graphview);
	}
}
