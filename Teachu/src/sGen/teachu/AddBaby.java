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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddBaby extends Activity implements OnClickListener {
	final static String TAG = "KJK";

	// object
	private BabyInfoDTO Baby = new BabyInfoDTO();
	private DBBabyInfoAdapter mAdapter = new DBBabyInfoAdapter(this);
	private Uri currImageURI;
	private String imagePath;

	// view
	private EditText babyNameEdit;
	private EditText babypassword;
	private ImageView mPictureBtn;
	private RadioGroup rg;
	private Button okayButton;
	private ListViewDialog mDialog;

	// photo
	private File imgFile;
	private Bitmap mBitmap;
	
	private int photoAreaWidth;
	private int photoAreaHeight;

	// validation check
	private boolean isEmpty = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addbaby);

		// view initializing
		babyNameEdit = (EditText) findViewById(R.id.name);
		babypassword = (EditText) findViewById(R.id.password);
		rg = (RadioGroup) findViewById(R.id.sexradiogroup);

		// get mBitmap
		mPictureBtn = (ImageView) findViewById(R.id.pictureBtn);
		mPictureBtn.setOnClickListener(this);
		
		photoAreaWidth = mPictureBtn.getWidth();
		photoAreaHeight = mPictureBtn.getHeight();
		
		// register baby
		okayButton = (Button) findViewById(R.id.addbutton);
		okayButton.setOnClickListener(this);

		// TODO: after finishing add baby, unable backtraking button

	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case (R.id.pictureBtn): {
			Log.e("onClick", "click button picture dialog.......");
			showListDialog();
			break;
		}
		// addbutton
		case (R.id.addbutton):
			if (isEmpty) {
				Log.e("KJK", "빈칸있음");
			} else {
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
				Log.e("생일날짜!!!3", "Y-M-D = " + year + "년" + month + "월" + day
						+ "일");
				Calendar birthday = new GregorianCalendar();
				birthday.set(year, month, day);

				Baby.setBabyId(1);
				Baby.setName(babyName);
				Baby.setPassword(password);
				Baby.setBirth(birthday.getTimeInMillis());
				Baby.setSex(sex);
				Baby.setPhoto(mBitmap);

				Log.e("KJK", Baby.toString());

				// DB추가
				mAdapter.open();
				mAdapter.addBaby(Baby);
				mAdapter.close();

				// activity return
				Intent CategoryTree = new Intent(AddBaby.this,
						CategoryTree.class);
				startActivity(CategoryTree);

			}
			finish();
			break;
		}
	}

	private void showListDialog() {
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
					Log.v(TAG, " 첫번째 인덱스가 선택되었습니다" + "여기에 맞는 작업을 해준다.");
					// open gallery browser
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(
							Intent.createChooser(intent, "Select Picture"), 1);
				} else if (position == 1) {
					Log.v(TAG, " 두번째 인덱스가 선택되었습니다" + "여기에 맞는 작업을 해준다.");
				}
				mDialog.dismiss();
			}
		});
		mDialog.show();
	}

	// To handle when an image is selected from the browser, add the following
	// to your Activity
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {

			if (requestCode == 1) {

				// currImageURI is the global variable I'm using to hold the
				// content:// URI of the image
				currImageURI = data.getData();
				imagePath = getRealPathFromURI(currImageURI);
				Log.e("KJK", "URI : " + currImageURI.toString());
				Log.e("KJK", "Real Path : " + imagePath);

				// image path 얻어왔으면 imgFile초기화.
				imgFile = new File(imagePath);
				// img file bitmap 변경
				if (imgFile.exists()) {
					mBitmap = BitmapFactory.decodeFile(imgFile
							.getAbsolutePath());
					//getCroppedBitmap(mBitmap);
					Log.e("비트맵 로드", "성공");
				} else
					Log.e("비트맵 디코딩", "실패");
				mPictureBtn.setImageBitmap(getCroppedBitmap(mBitmap));
			}
			
		}
	}

	// get real path
	public String getRealPathFromURI(Uri contentUri) {
		String path = null;
		String[] proj = { MediaStore.MediaColumns.DATA };
		Cursor cursor = getContentResolver().query(contentUri, proj, null,
				null, null);
		if (cursor.moveToFirst()) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
			path = cursor.getString(column_index);
		}
		cursor.close();
		return path;
	}

	public Bitmap getCroppedBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, photoAreaWidth , photoAreaHeight);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		canvas.drawCircle(bitmap.getWidth() / 4, bitmap.getHeight() / 4,
				mPictureBtn.getHeight() /2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		// Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
		// return _bmp;
		return output;
	}

}
