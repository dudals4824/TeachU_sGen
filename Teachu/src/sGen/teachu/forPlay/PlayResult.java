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
import android.widget.Button;
import android.widget.TextView;

public class PlayResult extends Activity implements OnClickListener {

	private TextView correctCnt;
	private Button retrialBtn;
	private Button goToMainBtn;
	private TextView showCorrectCnt;
	private int totalCorrectCnt;


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
		for (int i = 0; i < totalCorrectCnt / 2; i++) {

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
			startActivity(goToMain);
			break;
		}
		// TODO Auto-generated method stub

	}

}
