package sGen.teachu;

import sGen.teachu.R;
import android.R.integer;
import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
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
	private static final int CATEGORY_TRANSPORT = 2; // 탈것

	private ArrayList<String> mResult; // 음성인식 결과 저장할 list
	private String mSelectedString; // 결과 list 중 사용자가 선택한 텍스트
	private TextView mResultTextView; // 최종 결과 출력하는 텍스트 뷰
	private TextView mItemNumber, mCorrectCnt;

	private ArrayList<ItemInfoDTO> itemList = new ArrayList<ItemInfoDTO>(); // item

	private ImageView itemImage, mCorrect, mWrong;
	private int itemNumber = 0;

	public static int correctCnt_ = 0;

	// 문제 랜덤으로 나오게 하기
	// 시간지나면 다음 문제 나오게 하기
	// categoryID 를 불러올때는 CategoryTree.getCategoryID();로....-> categoryTree에만
	// categoryID만들어놓음
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_main);

		initItem(itemList);

		// correct image
		mCorrect = (ImageView) findViewById(R.id.correct);
		mCorrect.setVisibility(View.INVISIBLE);
		// wrong imge
		mWrong = (ImageView) findViewById(R.id.wrong);
		mWrong.setVisibility(View.INVISIBLE);

		// item image
		itemImage = (ImageView) findViewById(R.id.wordCard);
		itemImage.setOnClickListener(this); // 내가 만든activity 이용.
		itemImage.setImageResource(getResources()
				.getIdentifier(itemList.get(0).getItemFileName(), "drawable",
						getPackageName()));

		mResultTextView = (TextView) findViewById(R.id.result); // 결과 출력 뷰
		mItemNumber = (TextView) findViewById(R.id.itemNumber);
		mCorrectCnt = (TextView) findViewById(R.id.correctCnt);

		Log.e("minka", "아이템사이지지지지지지ㅣitemList.size() = " + itemList.size());
		Log.e("minka", "this.getCategoryID() = " + CategoryTree.getCategoryID());

	}

	// itemList 초기화
	private void initItem(ArrayList<ItemInfoDTO> itemList) {
		DBItemInfoAdapter mItemAdaper = new DBItemInfoAdapter(this);
		mItemAdaper.open();
		// category 확인
		switch (CategoryTree.getCategoryID()) {
		case R.id.btn_categorytree_fruit:
			// *************카테고리 아이디 별로 아이템가져와서 itemList에 깊은복사..******
			itemList.addAll(mItemAdaper
					.getItemInfoByCategoryId(CATEGORY_ANIMAL));// 깊은복사
			Log.e("minka",
					"지금선택한 카테고리 아이템갯수 itemList.size() = " + itemList.size());
		}
	}

	@Override
	public void onClick(View v) {
		int view = v.getId();

		if (view == R.id.wordCard) {
			startActivityForResult(new Intent(this, PlayMic.class), MY_UI); // 내가
			// 실행
		}
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
			 Compareword mCompare = new Compareword(item.getItemName(), mResult.get(i));
			 double correctionrate = mCompare
						.getCorrectionrate(mCompare.analysis_word_array);
			if (item.getItemName().equals(mResult.get(i))) { // 비교
				Log.e("play", "결과물들중에 답이랑 똑같은게 잇어서 맞은경우");
				correctFlag = true;
				correctCnt_++;
				break;
			}
			//compare 함수사용한경우
			else if (correctionrate >= 70){
				Log.e("play", "compare 함수를 써서 70 이상인경우");
				correctFlag = true;
				correctCnt_++;
				break;
			}
			//틀린경우
			else
				correctFlag = false;
		}
		// 5개 중에 정답이 있는 경우
		if (correctFlag == true) {
			mCorrect.setVisibility(View.VISIBLE);
			mResultTextView.setText("정답 :" + item.getItemName());

			new CountDownTimer(2000, 2000) {

				public void onTick(long millisUntilFinished) {
					System.out.println("ontick");
				}

				public void onFinish() {

					mCorrect.setVisibility(View.INVISIBLE);
					mResultTextView.setText("");
					itemNumber++;
					Log.e("play", "correctCnt_ = " + correctCnt_);
					//itemList.get(itemNumber); // 새문제 로딩
					
					mItemNumber.setText(Integer.toString(itemNumber + 1)
							+ " / 10");
					mCorrectCnt.setText(Integer.toString(correctCnt_));
					
					Log.e("play", "mCorrectCnt.getText() = " + mCorrectCnt.getText());
					
					//ItemInfoDTO item = itemList.get(itemNumber);
					// itemImage.setImageResource(item.getFileName());요기서 item이
					// final이여야 만해서
					// 여기다 일부로 하나 넣어줫는데 특별한 규칙으로 카드 다시 뿌려줄땐 제대로 수정해야함
					itemImage.setImageResource(getResources().getIdentifier(
							itemList.get(itemNumber).getItemFileName(), "drawable",
							getPackageName()));
					if (itemNumber >= 10){
						//itemNumber = 0; // 9번까지 하면 다시 1번부터 ItemInfoDTO item =
						//Log.e("endQuiz", "mCorrectCnt.getText().toString() = " + mCorrectCnt.getText().toString());
						//correctCnt_ = Integer.parseInt(mCorrectCnt.getText().toString());
						Intent playResult = new Intent(Play.this, PlayResult.class);
						startActivity(playResult);
						
					}
				}
			}.start();

		} else {
			mWrong.setVisibility(View.VISIBLE);
			mResultTextView.setText("정답 :" + item.getItemName());
			new CountDownTimer(2000, 2000) {

				public void onTick(long millisUntilFinished) {
					System.out.println("ontick");
				}

				public void onFinish() {

					mWrong.setVisibility(View.INVISIBLE);
					mResultTextView.setText("");
					itemNumber++;
					mItemNumber.setText(Integer.toString(itemNumber + 1)
							+ " / 10");
					mCorrectCnt.setText(Integer.toString(correctCnt_));
					ItemInfoDTO item = itemList.get(itemNumber);
					// itemImage.setImageResource(item.getFileName());요기서 item이
					// final이여야 만해서
					// 여기다 일부로 하나 넣어줫는데 특별한 규칙으로 카드 다시 뿌려줄땐 제대로 수정해야함
					itemImage.setImageResource(getResources().getIdentifier(
							item.getItemFileName(), "drawable",
							getPackageName()));
					if (itemNumber >= 10){
						//itemNumber = 0; // 9번까지 하면 다시 1번부터 ItemInfoDTO item =
						//Log.e("endQuiz", "mCorrectCnt.getText().toString() = " + mCorrectCnt.getText().toString());
						//correctCnt_ = Integer.parseInt(mCorrectCnt.getText().toString());
						Intent playResult = new Intent(Play.this, PlayResult.class);
						startActivity(playResult);
						
					}
				}
			}.start();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 0, "아기 초기화");
		menu.add(0, 2, 0, "아기 상태보러가기");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:
			// DB initializing : for testing
			DBBabyInfoAdapter mAdapter = new DBBabyInfoAdapter(this);
			mAdapter.open();
			mAdapter.deleteBaby(1);
			int babyInt = mAdapter.getBabyCount();
			mAdapter.close();

			Log.e("MINKA", "delecomplele? mAdapter.getBabyCount() = " + babyInt);

		case 2:
			Intent Setting = new Intent(Play.this, Setting.class);
			startActivity(Setting);
		}
		return super.onOptionsItemSelected(item);
	}
}
