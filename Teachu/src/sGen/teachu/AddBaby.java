package sGen.teachu;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import sGen.teachu.DTO.BabyInfoDTO;
import sGen.teachu.database.DBBabyInfoAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

		// final EditText babyAgeEdit = (EditText) findViewById(R.id.age);
		final RadioGroup rg = (RadioGroup) findViewById(R.id.sexradiogroup);

		Button okayButton = (Button) findViewById(R.id.addbutton);
		okayButton.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("SimpleDateFormat")
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

				DatePicker babyBirthday = (DatePicker) findViewById(R.id.babyBirthday);

				int year = babyBirthday.getYear();
				int month = babyBirthday.getMonth();
				int day = babyBirthday.getDayOfMonth();
				Log.e("생일날짜!!!3", "Y-M-D = " + year + "년" + month + "월" + day
						+ "일");

				Calendar birthday = new GregorianCalendar();
				birthday.set(year, month, day);
				
				Baby.setBabyId(1);
				Baby.setName(babyName);
				Baby.setPassword(password);
				Baby.setBirth(birthday.getTimeInMillis());
				Baby.setSex(sex);

				Log.e("KJK", Baby.toString());

				// DB추가

				mAdapter.open();
				mAdapter.addBaby(Baby);
				mAdapter.close();

				// TODO : add baby face photo

				Log.e("MINKA", "이름 : " + babyName);
				Log.e("MINKA", "비밀번호 : " + password);
				Log.e("MINKA", "생일 : " + birthday);
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
