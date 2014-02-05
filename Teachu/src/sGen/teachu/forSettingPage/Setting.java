package sGen.teachu.forSettingPage;

import java.text.ParseException;

import sGen.teachu.MainActivity;
import sGen.teachu.R;
import sGen.teachu.DTO.BabyInfoDTO;
import sGen.teachu.database.DBBabyInfoAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends Activity implements OnClickListener {
	LinearLayout layout_graphview, progress, test;
	ScrollView layout_background;
	GridLayout layout_correctionrate;
	TextView textview_name,textview_birth;
	Button btn_setting_delete,btn_setting_prev;
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
		btn_setting_delete=(Button)findViewById(R.id.btn_setting_delete);
		
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
		
		btn_setting_delete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.btn_setting_delete){
			mBabyAdapter.deleteBaby(1);
			Toast.makeText(this, "삭제되었습니다", Toast.LENGTH_SHORT).show();
			Intent mainActivity=new Intent(Setting.this, MainActivity.class);
			startActivity(mainActivity);
		}
	}
}
