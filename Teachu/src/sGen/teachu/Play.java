package sGen.teachu;

import sGen.teachu.R;
import android.R.integer;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.text.ParseException;
import java.util.ArrayList;

import javax.security.auth.Destroyable;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import sGen.teachu.database.*;
import sGen.teachu.forPlay.Compareword;
import sGen.teachu.forPlay.PlayMic;
import sGen.teachu.forPlay.PlayResult;
import sGen.teachu.forSettingPage.Setting;
import sGen.teachu.DTO.*;

public class Play extends Activity implements OnClickListener {

	private final int GOOGLE_STT = 1000, MY_UI = 1001; // requestCode. 구글음성인식,
														// 내가 만든 Activity

	private static final int CATEGORY_FRUIT = 0; // 과일
	private static final int CATEGORY_ANIMAL = 1; // 동물
	private static final int CATEGORY_THING = 2; // 물건

	private ArrayList<String> mResult; // 음성인식 결과 저장할 list
	private String mSelectedString; // 결과 list 중 사용자가 선택한 텍스트
	private TextView mResultTextView; // 최종 결과 출력하는 텍스트 뷰
	private TextView mItemNumber, mCorrectCnt;

	private ArrayList<ItemInfoDTO> itemList = new ArrayList<ItemInfoDTO>(); // item

	private ImageView itemImage, mCorrect, mWrong;
	private int itemNumber = 0;

	public static int correctCnt_;// 총 맞은 갯수
	private DBBabyGrowthAdapter mBabyAdapter;

	private ImageView teacherOn, teacherOff, goToMain;
	private boolean teacherMode = false; // 선생님모드

	private boolean mIsBackButtonTouched = false;

	// 목소리 남자/여자
	public static int voice = 0;// 0이 남자 1이 여자
	private static MediaPlayer mp;// 소리 재생을위한!

	// categoryID 를 불러올때는 CategoryTree.getCategoryID();로....-> categoryTree에만

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_main);
		
		Intent svc = new Intent(this, BackgroundSoundService.class);
		stopService(svc);
		
		mBabyAdapter = new DBBabyGrowthAdapter(this);
		mBabyAdapter.open();
		try {
			initItem(itemList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mBabyAdapter.close();

		correctCnt_ = 0;// 초기화
		// correct image
		mCorrect = (ImageView) findViewById(R.id.correct);
		mCorrect.setVisibility(View.INVISIBLE);
		// wrong imge
		mWrong = (ImageView) findViewById(R.id.wrong);
		mWrong.setVisibility(View.INVISIBLE);

		// item image
		itemImage = (ImageView) findViewById(R.id.wordCard);
		Log.e("여기가문제", "itemList.get(0).getItemFileNAme() = "
				+ itemList.get(0).getItemFileName());
		itemImage.setImageResource(getResources()
				.getIdentifier(itemList.get(0).getItemFileName(), "drawable",
						getPackageName()));
		itemImage.setOnClickListener(this); // 내가 만든activity 이용.

		mResultTextView = (TextView) findViewById(R.id.result); // 결과 출력 뷰
		mItemNumber = (TextView) findViewById(R.id.itemNumber);

		// teacherMode image
		teacherOn = (ImageView) findViewById(R.id.teacherOn);
		teacherOn.setVisibility(View.INVISIBLE);
		teacherOn.setOnClickListener(this);
		teacherOff = (ImageView) findViewById(R.id.teacherOff);
		teacherOff.setOnClickListener(this);
		// goToMain Image
		goToMain = (ImageView) findViewById(R.id.goMainAtPlay);
		goToMain.setOnClickListener(this);

	}

	// itemList 초기화
	private void initItem(ArrayList<ItemInfoDTO> itemList) throws SQLException,
			ParseException {
		DBItemInfoAdapter mItemAdaper = new DBItemInfoAdapter(this);
		mItemAdaper.open();
		Log.e("initList", "지금선택한 카테고리 아이디 = " + CategoryTree.getCategoryID());
		Log.e("initList",
				"지금선택한 카테고리 아이템갯수 itemList.size() = " + itemList.size());
		// category 확인
		switch (CategoryTree.getCategoryID()) {
		case R.id.btn_categorytree_fruit:
			// *************카테고리 아이디 별로 아이템가져와서 itemList에 깊은복사..******
			itemList.addAll(mItemAdaper.getItemInfoByCategoryId(CATEGORY_FRUIT));// 깊은복사
			break;
		case R.id.btn_categorytree_animal:
			itemList.addAll(mItemAdaper
					.getItemInfoByCategoryId(CATEGORY_ANIMAL));// 깊은복사
			break;
		case R.id.btn_categorytree_object:
			itemList.addAll(mItemAdaper.getItemInfoByCategoryId(CATEGORY_THING));// 깊은복사
			break;

		}
		mItemAdaper.close();
		// 순서 정렬..
		mBabyAdapter.open();
		for (int i = 0; i < itemList.size() - 1; i++) {
			for (int k = i; k < itemList.size(); k++) {

				float front = mBabyAdapter.getBabyGrowth(itemList.get(i)
						.getItemId());
				float back = mBabyAdapter.getBabyGrowth(itemList.get(k)
						.getItemId());
				if (front >= back) {
					ItemInfoDTO trash = itemList.get(i);
					itemList.set(i, itemList.get(k));
					itemList.set(k, trash);
				}
			}

		}

		for (int i = 0; i < itemList.size(); i++) {
			Log.e("paly", "itemList.get(i).getItemId()"
					+ itemList.get(i).getItemId());
			Log.e("play",
					"mBabyAdapter.getBabyGrowth(itemList.get(i).getItemId()) = "
							+ mBabyAdapter.getBabyGrowth(itemList.get(i)
									.getItemId()));
		}
		mBabyAdapter.close();
	}

	@Override
	public void onClick(View v) {
		int view = v.getId();

		if (view == R.id.wordCard) {
			startActivityForResult(new Intent(this, PlayMic.class), MY_UI); // 내가
			// 실행
		} else if (v.getId() == R.id.teacherOn) {
			teacherMode = false;
			teacherOff.setVisibility(View.VISIBLE);
			teacherOn.setVisibility(View.INVISIBLE);

		} else if (v.getId() == R.id.teacherOff) {
			teacherMode = true;
			teacherOff.setVisibility(View.INVISIBLE);
			teacherOn.setVisibility(View.VISIBLE);

			playSound();

		} else if (v.getId() == R.id.goMainAtPlay) {
			Intent CategoryTreeActivity = new Intent(Play.this,
					CategoryTree.class);
			startActivity(CategoryTreeActivity);
			this.finish();
		}
		Log.e("minka", "teacherMode = " + teacherMode);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK
				&& (requestCode == GOOGLE_STT || requestCode == MY_UI)) { // 결과가
																			// 있으면
			showSelectDialog(requestCode, data); // 결과를 다이얼로그로 출력.
		} else { // 결과가 없으면 에러 메시지 출력
			String msg = null;

			// 내가 만든 activity에서 넘어오는 오류 코드를 분류
			switch (resultCode) {
			case SpeechRecognizer.ERROR_AUDIO:
				msg = "오디오 입력 중 오류가 발생했습니다.";
				break;
			case SpeechRecognizer.ERROR_CLIENT:
				msg = "단말에서 오류가 발생했습니다.";
				break;
			case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
				msg = "권한이 없습니다.";
				break;
			case SpeechRecognizer.ERROR_NETWORK:
			case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
				msg = "네트워크 오류가 발생했습니다.";
				break;
			case SpeechRecognizer.ERROR_NO_MATCH:
				msg = "일치하는 항목이 없습니다.";
				break;
			case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
				msg = "음성인식 서비스가 과부하 되었습니다.";
				break;
			case SpeechRecognizer.ERROR_SERVER:
				msg = "서버에서 오류가 발생했습니다.";
				break;
			case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
				msg = "입력이 없습니다.";
				break;
			}

			if (msg != null) // 오류 메시지가 null이 아니면 메시지 출력
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
						.show();
			playSound();
		}
		
	}

	//소리 출력하는 함수
	public void playSound() {
		if (teacherMode) {

			String sound = itemList.get(itemNumber).getItemFileName() + "_";
			Log.e("소리파일 이름", "" + sound);
			if (voice == 1)
				sound += "female";
			else if (voice == 0)
				sound += "male";
			mp = MediaPlayer.create(this, getResources()
					.getIdentifier(sound, "raw", getPackageName()));
			mp.start();

		}
	}

	// 결과 list 출력하는 다이얼로그 생성
	private void showSelectDialog(int requestCode, Intent data) {
		String key = "";
		if (requestCode == GOOGLE_STT) // 구글음성인식이면
			key = RecognizerIntent.EXTRA_RESULTS; // 키값 설정
		else if (requestCode == MY_UI) // 내가 만든 activity 이면
			key = SpeechRecognizer.RESULTS_RECOGNITION; // 키값 설정

		mResult = data.getStringArrayListExtra(key); // 인식된 데이터 list 받아옴.
		String[] result = new String[mResult.size()]; // 배열생성. 다이얼로그에서 출력하기
														// 위해
		mResult.toArray(result); // list 배열로 변환

		Log.e("minka", "mResult.size() = " + mResult.size());
		for (int i = 0; i < mResult.size(); i++)
			Log.e("minka", "mResult.get(i) = " + mResult.get(i));

		// 결과세트중에 맞는게 하나라도 있으면 정답처리
		boolean correctFlag = false;

		Log.e("minka", "itemList.size() = " + itemList.size());
		ItemInfoDTO item = itemList.get(itemNumber);

		Log.e("minka", "item.getItemName() 정답 = " + item.getItemName());
		// 5개 음성인식 결과와 비교
		for (int i = 0; i < mResult.size(); i++) {
			Compareword mCompare = new Compareword(item.getItemName(),
					mResult.get(i));
			double correctionrate = mCompare
					.getCorrectionrate(mCompare.analysis_word_array);
			Log.e("몇점" , "점수 = " + correctionrate);
			if (item.getItemName().equals(mResult.get(i))) { // 비교
				Log.e("play", "결과물들중에 답이랑 똑같은게 잇어서 맞은경우");
				correctFlag = true;
				correctCnt_++;
				break;
			}
			// compare 함수사용한경우
			else if (correctionrate >= 70) {
				Log.e("play", "compare 함수를 써서 70 이상인경우");
				correctFlag = true;
				correctCnt_++;
				break;
			}
			// 틀린경우
			else
				correctFlag = false;
		}
		// 제출된 item에 대해 출제 횟수 증가 및 정답여부에 따른 정답횟수 증가
		Log.e("play", "item.getItemId() = " + item.getItemId());
		
		mBabyAdapter.open();
		Log.e("play", "item.getItemId(),getCateId() =  " + item.getItemId()
				+ "," + item.getCateId() + correctFlag);

		Log.e("play", "mBabyAdapter.getAllBabyGrowthCursor().getCount() = "
				+ mBabyAdapter.getAllBabyGrowthCursor().getCount());
		if (mBabyAdapter.changeGrowthForItem(item.getItemId(),
				item.getCateId(), 1, correctFlag) == 1) {
			Log.e("minka", "1이나옴 .. 참! 오예");

			try {

				Log.e("증가 후 play Item",
						"mBabyAdapter.getBabyGrowth(item.getItemId()).getShowCnt() = "
								+ mBabyAdapter.getBabyGrowthByItemId(
										item.getItemId()).getShowCnt());
				Log.e("증가 후 play Item",
						"mBabyAdapter.getBabyGrowth(item.getItemId()).getCorrectAns() = "
								+ mBabyAdapter.getBabyGrowthByItemId(
										item.getItemId()).getCorrectAns());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		mBabyAdapter.close();
		itemImage.setOnClickListener(null);
		findViewById(R.id.result).setOnClickListener(null);
		// 5개 중에 정답이 있는 경우
		if (correctFlag == true) {
			mCorrect.setVisibility(View.VISIBLE);
			mCorrect.setOnClickListener(null);

			mResultTextView.setText("정답 :" + item.getItemName());
			findViewById(R.id.wordCard).setOnClickListener(null);
			new CountDownTimer(2000, 2000) {
				public void onTick(long millisUntilFinished) {
					System.out.println("ontick");
				}

				public void onFinish() {

					mCorrect.setVisibility(View.INVISIBLE);
					mResultTextView.setText("");
					itemNumber++;
					// TODO Auto-generated method stub

					if (itemNumber >= 10) {

						
						finish();
						
						Intent playResult = new Intent(Play.this,
								PlayResult.class);
						startActivity(playResult);
					} else {
						mItemNumber.setText(Integer.toString(itemNumber + 1)
								+ " / 10");
						itemImage.setImageResource(getResources()
								.getIdentifier(
										itemList.get(itemNumber)
												.getItemFileName(), "drawable",
										getPackageName()));
						playSound();
					}
					
				}
				
			}.start();

		} else {
			mWrong.setVisibility(View.VISIBLE);
			mWrong.setOnClickListener(null);

			mResultTextView.setText("정답 :" + item.getItemName());
			findViewById(R.id.wordCard).setOnClickListener(null);

			new CountDownTimer(2000, 2000) {

				public void onTick(long millisUntilFinished) {
					System.out.println("ontick");
				}

				public void onFinish() {

					mWrong.setVisibility(View.INVISIBLE);
					mResultTextView.setText("");
					itemNumber++;
					// TODO Auto-generated method stub
					if (itemNumber >= 10) {
						Intent playResult = new Intent(Play.this,
								PlayResult.class);
						finish();
						startActivity(playResult);
					} else {
						mItemNumber.setText(Integer.toString(itemNumber + 1)
								+ " / 10");
						ItemInfoDTO item = itemList.get(itemNumber);
						itemImage.setImageResource(getResources()
								.getIdentifier(item.getItemFileName(),
										"drawable", getPackageName()));
						playSound();
					}

				}
			}.start();

		}
		itemImage.setOnClickListener(this);
		findViewById(R.id.wordCard).setOnClickListener(this);
		playSound();
	}

	// 백버튼 두번 누르면 종료시킴 플래그 바꿔서
	@Override
	public void onBackPressed() {
		if (mIsBackButtonTouched == false) {
			Toast.makeText(this, "한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
					.show();
			Intent svc = new Intent(this, BackgroundSoundService.class);
			startService(svc);
			mIsBackButtonTouched = true;
		} else if (mIsBackButtonTouched == true) {
			finish();
		}
	}
}
