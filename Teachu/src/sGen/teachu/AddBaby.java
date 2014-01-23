package sGen.teachu;

import java.sql.Date;

import sGen.teachu.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.GpsStatus.Listener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import sGen.teachu.database.*;
import sGen.teachu.DTO.*;

public class AddBaby extends Activity {

	BabyInfoDTO Baby = new BabyInfoDTO();
	DBBabyInfoAdapter mAdapter = new DBBabyInfoAdapter(this);
	String babySex = "";
	int sex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.addbaby);

		final EditText babyNameEdit = (EditText) findViewById(R.id.name);
		final EditText babypassword = (EditText) findViewById(R.id.password);
		final DatePicker babyBrithday = (DatePicker) findViewById(R.id.babyBirthday);
		// final EditText babyAgeEdit = (EditText) findViewById(R.id.age);
		final RadioGroup rg = (RadioGroup) findViewById(R.id.sexradiogroup);

		long now = System.currentTimeMillis();
		Date date = new Date(now);
		
		
		OnDateSetListener listener = new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				Toast.makeText(getApplicationContext(),
						year + "년" + monthOfYear + "월" + dayOfMonth + "일",
						Toast.LENGTH_SHORT).show();
			}
		};
		
		DatePickerDialog dialog = new DatePickerDialog(this, listener, 2013,
				10, 22);
		dialog.show();

		
		Button okayButton = (Button) findViewById(R.id.addbutton);
		okayButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// press enroll button
				RadioButton rb = (RadioButton) findViewById(rg
						.getCheckedRadioButtonId());
				babySex = rb.getText().toString();
				if (babySex.equals("남자"))
					sex = 0;
				else if (babySex.equals("여자"))
					sex = 1;

				final String babyName = babyNameEdit.getText().toString();
				final String password = babypassword.getText().toString();
				final int birthdayM = babyBrithday.getMonth();
				final int birthdayY = babyBrithday.getYear();
				final int birthdayD = babyBrithday.getDayOfMonth();
				// Date birthday = new Date()
				// final String babyAge = babyAgeEdit.getText().toString();

				Baby.setBabyId(1);
				Baby.setName(babyName);
				Baby.setPassword(password);
				// Baby.setBirth(babyAge);
				Baby.setSex(sex);
				Context _context = null;
				// DB추가

				mAdapter.open();
				mAdapter.addBaby(Baby);
				mAdapter.close();

				// TODO : add baby face photo

				Log.e("MINKA", "이름 : " + babyName);
				Log.e("MINKA", "비밀번호 : " + password);
				// Log.e("MINKA", "나이: " + babyAge);
				Log.e("MINKA", "성별 : " + sex);
				// activity return
				Intent MainActivity = new Intent(AddBaby.this,
						MainActivity.class);
				startActivity(MainActivity);

			}

		});

		// TODO: after finishing add baby, unable backtraking button

	}

}
