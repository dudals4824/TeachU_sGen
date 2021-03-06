package sGen.teachu;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import sGen.teachu.DTO.BabyInfoDTO;
import sGen.teachu.database.DBBabyInfoAdapter;
import sGen.teachu.forSettingPage.ListViewDialog;
import sGen.teachu.forSettingPage.photoEditor;
import sGen.teachu.forSettingPage.ListViewDialog.ListViewDialogSelectListener;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;

public class AddBaby extends Activity implements OnClickListener {
	public final static String TAG = "KJK";
	static String SAMPLEIMG = "profile.png";
	static final int REQUEST_ALBUM = 1;
	static final int REQUEST_PICTURE = 2;
	// font
	private Typeface tf;

	// object
	private BabyInfoDTO Baby = new BabyInfoDTO();
	private DBBabyInfoAdapter mAdapter = new DBBabyInfoAdapter(this);
	private Uri currImageURI;
	private String imagePath;

	// view
	private EditText edit_name;
	private EditText edit_password;
	private EditText edit_passwordcheck;
	private ImageView btn_picbtn;
	private RadioGroup rg;
	private Button btn_regist;
	private ListViewDialog mDialog;

	// photo
	private File imgFile;
	private Bitmap mBitmap;

	private int photoAreaWidth;
	private int photoAreaHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addbaby);

		// font setting
		tf = Typeface.createFromAsset(this.getAssets(),
				"font/KOPUBDOTUMMEDIUM.TTF");

		// view initializing
		edit_name = (EditText) findViewById(R.id.name);
		edit_name.setTypeface(tf);
		edit_password = (EditText) findViewById(R.id.password);
		edit_password.setTypeface(tf);
		edit_passwordcheck = (EditText) findViewById(R.id.password_check);
		edit_passwordcheck.setTypeface(tf);
		rg = (RadioGroup) findViewById(R.id.sexradiogroup);

		// get mBitmap
		btn_picbtn = (ImageView) findViewById(R.id.pictureBtn);
		btn_picbtn.setOnClickListener(this);

		BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(
				R.drawable.btn_addbaby_registpic);

		// photoAreaWidth = mPictureBtn.getWidth();
		// photoAreaHeight = mPictureBtn.getHeight();
		photoAreaWidth = bd.getBitmap().getWidth();
		photoAreaHeight = bd.getBitmap().getHeight();
		Log.e("DEBUG", "photo size = " + photoAreaWidth + ", "
				+ photoAreaHeight);

		// register baby
		btn_regist = (Button) findViewById(R.id.addbutton);
		btn_regist.setOnClickListener(this);

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
			if (edit_name.getText().toString().equals("")
					| edit_password.getText().toString().equals("")
					| edit_passwordcheck.getText().toString().equals("")) {
				Toast.makeText(AddBaby.this, "모든 항목을 입력하세요", Toast.LENGTH_SHORT)
						.show();
			} else if (mBitmap == null) {
				Toast.makeText(AddBaby.this, "사진을 선택하세요", Toast.LENGTH_SHORT)
						.show();
			} else {
				if (edit_password.getText().toString()
						.equals(edit_passwordcheck.getText().toString())) {

				} else {
					Toast.makeText(AddBaby.this, "비밀번호를 정확하게 입력하세요",
							Toast.LENGTH_SHORT).show();
					break;
				}
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

				final String babyName = edit_name.getText().toString();
				final String password = edit_password.getText().toString();

				DatePicker babyBirthday = (DatePicker) findViewById(R.id.babyBirthday);

				int year = babyBirthday.getYear();
				int month = babyBirthday.getMonth();
				int day = babyBirthday.getDayOfMonth();
				Log.e("생일날짜!!!3", "Y-M-D = " + year + "년" + month + "월" + day
						+ "일");
				Calendar birthday = new GregorianCalendar();
				birthday.set(year, month + 1, day);

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
				Log.e("intent", "인텐트다음");
				finish();

			}

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
							Intent.createChooser(intent, "Select Picture"),
							REQUEST_ALBUM);
				} else if (position == 1) {
					// 여기서 부터 카메라 사용
					Log.v(TAG, " 두번째 인덱스가 선택되었습니다" + "여기에 맞는 작업을 해준다.");
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File file = new File(Environment
							.getExternalStorageDirectory(), SAMPLEIMG);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
					startActivityForResult(intent, REQUEST_PICTURE);
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
			if (requestCode == REQUEST_ALBUM) {
				// currImageURI is the global variable I'm using to hold the
				// content:// URI of the image
				currImageURI = data.getData();
				// 실제 절대주소를 받아옴
				imagePath = getRealPathFromURI(currImageURI);
				Log.e("KJK", "URI : " + currImageURI.toString());
				Log.e("KJK", "Real Path : " + imagePath);

				// image path 얻어왔으면 imgFile초기화.
				imgFile = new File(imagePath);
				// img file bitmap 변경
				if (imgFile.exists()) {
					mBitmap = BitmapFactory.decodeFile(imgFile
							.getAbsolutePath());
					// getCroppedBitmap(mBitmap);
					Log.e("비트맵 로드", "성공");
				} else
					Log.e("비트맵 디코딩", "실패");
			} else if (requestCode == REQUEST_PICTURE) {
				Log.e("camera", "camera");
				mBitmap = loadPicture();
			}
			// mPictureBtn.setImageBitmap(overlayCover(getCroppedBitmap(resizeBitmapToProfileSize(mBitmap))));
			BitmapDrawable bd = (BitmapDrawable) this.getResources()
					.getDrawable(R.drawable.btn_addbaby_registmask);
			Bitmap coverBitmap = bd.getBitmap();

			// constructor
			// mBitmap에 찍은 사진 넣기
			// cover은 그대로

			photoEditor photoEdit = new photoEditor(mBitmap, coverBitmap,
					photoAreaWidth, photoAreaHeight);
			// resize
			// crop roun
			// overay cover

			// 이거하면 이미지 셋됨
			mBitmap = photoEdit.editPhotoAuto();
			btn_picbtn.setImageBitmap(mBitmap);
		}
	}

	public Bitmap rotate(Bitmap bitmap, int degrees) {
		if (degrees != 0 && bitmap != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees);
			try {
				Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
						bitmap.getWidth(), bitmap.getHeight(), m, true);
				if (bitmap != converted) {
					bitmap = null;
					bitmap = converted;
					converted = null;
				}
			} catch (OutOfMemoryError ex) {
				Toast.makeText(getApplicationContext(), "메모리부족", 0).show();
			}
		}
		return bitmap;
	}

	Bitmap loadPicture() {
		Log.e("camera", "load");
		// TODO Auto-generated method stub
		File file = new File(Environment.getExternalStorageDirectory(),
				SAMPLEIMG);
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inSampleSize = 4;
		return rotate(BitmapFactory.decodeFile(file.getAbsolutePath(), option),
				90);
	}

	// get real path
	private String getRealPathFromURI(Uri contentUri) {
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
}
