package sGen.teachu.forSettingPage;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sGen.teachu.AddBaby;
import sGen.teachu.CategoryTree;
import sGen.teachu.R;
import sGen.teachu.DTO.BabyInfoDTO;
import sGen.teachu.database.DBBabyGrowthAdapter;
import sGen.teachu.database.DBBabyInfoAdapter;
import sGen.teachu.forSettingPage.ListViewDialog.ListViewDialogSelectListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends Activity implements OnClickListener {
	private static final int CATEGORY_FRUIT = 0; // 과일
	private static final int CATEGORY_ANIMAL = 1; // 동물
	private static final int CATEGORY_TRANSPORT = 2; // 탈것
	
	private LinearLayout layout_graphview, progress, test;
	private ScrollView layout_background;
	private GridLayout layout_correctionrate;
	private TextView textview_name, textview_birth;
	private Button btn_setting_delete, btn_setting_prev;
	private Bitmap mBitmap;
	private ImageView pic_baby;
	static String SAMPLEIMG = "profile.png";
	static final int REQUEST_ALBUM = 1;
	static final int REQUEST_PICTURE = 2;
	private DBBabyInfoAdapter mBabyAdapter = new DBBabyInfoAdapter(this);
	private DBBabyGrowthAdapter mGrowthAdapter = new DBBabyGrowthAdapter(this);
	private BabyInfoDTO Baby = new BabyInfoDTO();

	private ListViewDialog mDialog;
	private File imgFile;
	private int photoAreaWidth;
	private int photoAreaHeight;
	private Uri currImageURI;
	private String imagePath;

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
		pic_baby = (ImageView) findViewById(R.id.pic_baby);
		
		mGrowthAdapter.open();
		double graph1 = (double)mGrowthAdapter.getCategoryGrowth(CATEGORY_FRUIT, 1);
		double graph2 = (double)mGrowthAdapter.getCategoryGrowth(CATEGORY_ANIMAL, 1);
		double graph3 = (double)mGrowthAdapter.getCategoryGrowth(CATEGORY_TRANSPORT, 1);
		
		// 생성자에 퍼센트로 정답률 넣으면 됨
		layout_correctionrate.addView(new ProgressView2(this, graph1));
		layout_correctionrate.addView(new ProgressView2(this, graph2));
		layout_correctionrate.addView(new ProgressView2(this, graph3));

		BitmapDrawable bd = (BitmapDrawable) this.getResources().getDrawable(
				R.drawable.btn_addbaby_registpic);
		photoAreaWidth = bd.getBitmap().getWidth();
		photoAreaHeight = bd.getBitmap().getHeight();

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
		textview_name.setText(Baby.getName());
		mBitmap = Baby.getPhoto();
		pic_baby.setImageBitmap(mBitmap);

		GraphView graphview = new GraphView(this);
		graphview.makeGraph(this, layout_graphview);

		btn_setting_delete.setOnClickListener(this);
		btn_setting_prev.setOnClickListener(this);
		pic_baby.setOnClickListener(this);
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
							String inputpwd = edit_password.getText()
									.toString();
							if (Baby.getPassword().equals(inputpwd)) {
								mBabyAdapter.deleteBaby(1);

								Intent addBabyActivity = new Intent(
										Setting.this, AddBaby.class);
								startActivity(addBabyActivity);
								finish();
								CategoryTree categorytree = (CategoryTree) CategoryTree.categorytree;
								categorytree.finish();
							} else {
								Toast.makeText(Setting.this, "비밀번호가 틀렸습니다",
										Toast.LENGTH_SHORT).show();
							}
						}
					});
			alert.setNegativeButton("취소",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
			alert.show();
		}
		if (v.getId() == R.id.btn_setting_prev) {
			onBackPressed();
		}
		if (v.getId() == R.id.pic_baby) {
			showListDialog();
			// ((AddBaby) AddBaby.mAddBaby).showListDialog();
		}
	}

	public void showListDialog() {
		/**
		 * 
		 * ListDialog를 보여주는 함수..
		 */
		Log.e("YJ", "yj");
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
					// Log.v(TAG, " 첫번째 인덱스가 선택되었습니다" + "여기에 맞는 작업을 해준다.");
					// open gallery browser
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(
							Intent.createChooser(intent, "Select Picture"),
							REQUEST_ALBUM);
				} else if (position == 1) {
					// 여기서 부터 카메라 사용
					// Log.v(TAG, " 두번째 인덱스가 선택되었습니다" + "여기에 맞는 작업을 해준다.");
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
			pic_baby.setImageBitmap(mBitmap);
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
