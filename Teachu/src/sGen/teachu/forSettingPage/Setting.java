package sGen.teachu.forSettingPage;

import java.text.ParseException;

import sGen.teachu.MainActivity;
import sGen.teachu.R;
import sGen.teachu.DTO.BabyInfoDTO;
import sGen.teachu.database.DBBabyInfoAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends Activity implements OnClickListener {
	LinearLayout layout_graphview, progress, test;
	ScrollView layout_background;
	GridLayout layout_correctionrate;
	TextView textview_name, textview_birth;
	Button btn_setting_delete, btn_setting_prev;
	DBBabyInfoAdapter mBabyAdapter = new DBBabyInfoAdapter(this);
	BabyInfoDTO Baby = new BabyInfoDTO();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		layout_graphview = (LinearLayout) findViewById(R.id.layout_graphview);
		layout_background = (ScrollView) findViewById(R.id.layout_background);
		layout_correctionrate = (GridLayout) findViewById(R.id.layout_correctionrate);
		textview_name = (TextView) findViewById(R.id.textview_name);
		btn_setting_delete = (Button) findViewById(R.id.btn_setting_delete);
		btn_setting_prev = (Button) findViewById(R.id.btn_setting_prev);

		mBabyAdapter.open();
		try {
			Baby = mBabyAdapter.getBabyInfo(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Log.e("babyinfo", Baby.getName());
		textview_name.setText(Baby.getName());

		GraphView graphview = new GraphView(this);
		graphview.makeGraph(this, layout_graphview);

		btn_setting_delete.setOnClickListener(this);
		btn_setting_prev.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btn_setting_delete) {
			Log.e("delete", "delete1");
			final EditText edit_password = new EditText(this);
			edit_password.setInputType(InputType.TYPE_CLASS_NUMBER);
			PasswordTransformationMethod pass = new PasswordTransformationMethod();
			edit_password.setTransformationMethod(pass);
			AlertDialog.Builder alert = new AlertDialog.Builder(Setting.this);
			alert.setTitle("비밀번호 입력");
			alert.setMessage("비밀번호를 입력하세요");
			alert.setView(edit_password);
			alert.setPositiveButton("확인",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							if(Baby.getPassword()==edit_password.getText().toString()){
								Log.e("delete", "비밀번호 맞음");
								mBabyAdapter.deleteBaby(1);
								Intent mainActivity=new Intent(Setting.this, MainActivity.class);
								startActivity(mainActivity);
							}
						}
					});
			alert.show();
		}
		if (v.getId() == R.id.btn_setting_prev) {
			onBackPressed();
		}
	}
}
