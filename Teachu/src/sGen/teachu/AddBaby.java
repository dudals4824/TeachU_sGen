package sGen.teachu;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import sGen.teachu.ListViewDialog.ListViewDialogSelectListener;
import sGen.teachu.DTO.BabyInfoDTO;
import sGen.teachu.database.DBBabyInfoAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddBaby extends Activity implements OnClickListener {
	final static String TAG = "KJK";
	
	// object
	private BabyInfoDTO Baby = new BabyInfoDTO();
	private DBBabyInfoAdapter mAdapter = new DBBabyInfoAdapter(this);

	// view
	private EditText babyNameEdit = null;
	private EditText babypassword = null;
	private Button mPictureBtn = null;
	private RadioGroup rg = null;
	private Button okayButton = null;
	private Button mBtnListDialog = null;
	private ListViewDialog mDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addbaby);

		// view initializing
		babyNameEdit = (EditText) findViewById(R.id.name);
		babypassword = (EditText) findViewById(R.id.password);
		mPictureBtn = (Button) findViewById(R.id.pictureBtn);
		mPictureBtn.setOnClickListener(this);
		rg = (RadioGroup) findViewById(R.id.sexradiogroup);
		okayButton = (Button) findViewById(R.id.addbutton);
		okayButton.setOnClickListener(this);

		// TODO: after finishing add baby, unable backtraking button

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case (R.id.pictureBtn):

			Log.d("onClick", "click button picture dialog.......");
			showListDialog();
			break;

		// addbutton
		case (R.id.addbutton): {
			int sex = 0;
			String babySex = null;
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
			Log.e("생일날짜!!!3", "Y-M-D = " + year + "년" + month + "월" + day + "일");

			Calendar birthday = new GregorianCalendar();
			birthday.set(year, month, day);
			
			
			Bitmap myBitmap = null;
			File imgFile = new  File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/img1.jpg");
			if(imgFile.exists()){
			    myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			    Log.e("비트맵 로드", "성공");
			}else
				Log.e("비트맵 로드", "실패");
			
			Baby.setBabyId(1);
			Baby.setName(babyName);
			Baby.setPassword(password);
			Baby.setBirth(birthday.getTimeInMillis());
			Baby.setSex(sex);
			Baby.setPhoto(myBitmap);

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
			Intent CategoryTree = new Intent(AddBaby.this, CategoryTree.class);
			startActivity(CategoryTree);

		}
			break;
		}
	}

	private void showListDialog() {
		// TODO Auto-generated method stub

		/**
		 * 
		 * ListDialog를 보여주는 함수..
		 */

		String[] item = getResources().getStringArray(
				R.array.photo_change_list_item);

		// array를 ArrayList로 변경을 하는 방법

		List<String> listItem = Arrays.asList(item);

		ArrayList<String> itemArrayList = new ArrayList<String>(listItem);

		mDialog = new ListViewDialog(this,
				getString(R.string.photo_change_title), itemArrayList);

		mDialog.onOnSetItemClickListener(new ListViewDialogSelectListener() {

			@Override
			public void onSetOnItemClickListener(int position) {
				// TODO Auto-generated method stub
				if (position == 0) {
					Log.v(TAG, " 첫번째 인덱스가 선택되었습니다" +
					"여기에 맞는 작업을 해준다.");
					
				}
				else if (position == 1) {
					Log.v(TAG, " 두번째 인덱스가 선택되었습니다" +
					"여기에 맞는 작업을 해준다.");
				}
				else if (position == 2) {
					Log.v(TAG, " 세번째 인덱스가 선택되었습니다" +
					"여기에 맞는 작업을 해준다.");
				}
				else if (position == 3) {
					Log.v(TAG, " 네번째 인덱스가 선택되었습니다" +
					"여기에 맞는 작업을 해준다.");
				}
				mDialog.dismiss();
			}
		});
		mDialog.show();
	}
}
