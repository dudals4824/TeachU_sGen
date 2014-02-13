package sGen.teachu.forPlay;

import sGen.teachu.AddBaby;
import sGen.teachu.CategoryTree;
import sGen.teachu.MainActivity;
import sGen.teachu.Play;
import sGen.teachu.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayResult extends Activity implements OnClickListener {

	private TextView correctCnt;
	private Button retrialBtn;
	private Button goToMainBtn;
	private TextView showCorrectCnt;
	private int totalCorrectCnt;
	private ImageView star1, star2, star3, star4, star5;
	private ImageView halfstar1, halfstar2, halfstar3, halfstar4, halfstar5;

	private boolean mIsBackButtonTouched = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_result);
		
		//int totalCorrectCnt = Integer.parseInt(findViewById(R.id.correctCnt).toString());
		totalCorrectCnt = Play.correctCnt_;
		Log.e("resultPage", "totalCorrectCnt = " + totalCorrectCnt);
		
		showCorrectCnt = (TextView)findViewById(R.id.result_correctCnt);
		showCorrectCnt.setText("" + totalCorrectCnt);
		
		star1 = (ImageView) findViewById(R.id.star4);
		star1.setVisibility(View.INVISIBLE);
		halfstar1 = (ImageView) findViewById(R.id.halfstar4);
		halfstar1.setVisibility(View.INVISIBLE);
		star2 = (ImageView)findViewById(R.id.star5);
		star2.setVisibility(View.INVISIBLE);
		halfstar2 = (ImageView) findViewById(R.id.halfstar5);
		halfstar2.setVisibility(View.INVISIBLE);
		star3 = (ImageView)findViewById(R.id.star3);
		star3.setVisibility(View.INVISIBLE);
		halfstar3 = (ImageView) findViewById(R.id.halfstar3);
		halfstar3.setVisibility(View.INVISIBLE);
		star4 = (ImageView)findViewById(R.id.star2);
		star4.setVisibility(View.INVISIBLE);
		halfstar4 = (ImageView) findViewById(R.id.halfstar2);
		halfstar4.setVisibility(View.INVISIBLE);
		star5 = (ImageView)findViewById(R.id.star1);
		star5.setVisibility(View.INVISIBLE);
		halfstar5 = (ImageView) findViewById(R.id.halfstar1);
		halfstar5.setVisibility(View.INVISIBLE);
		
		Animation anima = AnimationUtils.loadAnimation(this, R.anim.alpha);
		switch(totalCorrectCnt){
		
		case 10:
			star1.startAnimation(anima);
			star1.setVisibility(View.VISIBLE);
			star2.startAnimation(anima);
			star2.setVisibility(View.VISIBLE);
			star3.startAnimation(anima);
			star3.setVisibility(View.VISIBLE);
			star4.startAnimation(anima);
			star4.setVisibility(View.VISIBLE);
			star5.startAnimation(anima);
			star5.setVisibility(View.VISIBLE);
			break;

		case 8:
			star1.startAnimation(anima);
			star1.setVisibility(View.VISIBLE);
			star2.startAnimation(anima);
			star2.setVisibility(View.VISIBLE);
			star3.startAnimation(anima);
			star3.setVisibility(View.VISIBLE);
			star4.startAnimation(anima);
			star4.setVisibility(View.VISIBLE);
			break;
		case 9:
			star1.startAnimation(anima);
			star1.setVisibility(View.VISIBLE);
			star2.startAnimation(anima);
			star2.setVisibility(View.VISIBLE);
			star3.startAnimation(anima);
			star3.setVisibility(View.VISIBLE);
			star4.startAnimation(anima);
			star4.setVisibility(View.VISIBLE);
			halfstar5.startAnimation(anima);
			halfstar5.setVisibility(View.VISIBLE);
			break;
			
		case 7:
			star1.startAnimation(anima);
			star1.setVisibility(View.VISIBLE);
			star2.startAnimation(anima);
			star2.setVisibility(View.VISIBLE);
			star3.startAnimation(anima);
			star3.setVisibility(View.VISIBLE);
			halfstar4.startAnimation(anima);
			halfstar4.setVisibility(View.VISIBLE);
			break;
			
		case 6:
			star1.startAnimation(anima);
			star1.setVisibility(View.VISIBLE);
			star2.startAnimation(anima);
			star2.setVisibility(View.VISIBLE);
			star3.startAnimation(anima);
			star3.setVisibility(View.VISIBLE);
			break;
		case 5:
			star1.startAnimation(anima);
			star1.setVisibility(View.VISIBLE);
			star2.startAnimation(anima);
			star2.setVisibility(View.VISIBLE);
			halfstar3.startAnimation(anima);
			halfstar3.setVisibility(View.VISIBLE);
			break;
		case 4:
			star1.startAnimation(anima);
			star1.setVisibility(View.VISIBLE);
			star2.startAnimation(anima);
			star2.setVisibility(View.VISIBLE);
			break;
		case 3:
			star1.startAnimation(anima);
			star1.setVisibility(View.VISIBLE);
			halfstar2.startAnimation(anima);
			halfstar2.setVisibility(View.VISIBLE);
			break;
		case 2:
			star1.startAnimation(anima);
			star1.setVisibility(View.VISIBLE);
			break;
		case 1:
	        halfstar1.startAnimation(anima);
	        halfstar1.setVisibility(View.VISIBLE);
			break;
		case 0:
			break;
			
		}
		
		retrialBtn = (Button)findViewById(R.id.retrialbutton);
		goToMainBtn = (Button)findViewById(R.id.goToMain);
		retrialBtn.setOnClickListener(this);
		goToMainBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.retrialbutton:
			Intent rePlay = new Intent(PlayResult.this, Play.class);
			startActivity(rePlay);
			break;
		case R.id.goToMain:
			Intent goToMain = new Intent(PlayResult.this, CategoryTree.class);
			finish();
			startActivity(goToMain);
			break;
		}
		// TODO Auto-generated method stub

	}
	// 백버튼 두번 누르면 종료시킴 플래그 바꿔서
	@Override
	public void onBackPressed() {
		if (mIsBackButtonTouched == false) {
			Toast.makeText(this, "한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
					.show();
			mIsBackButtonTouched = true;
		} else if (mIsBackButtonTouched == true) {
			finish();
		}
	}

}
