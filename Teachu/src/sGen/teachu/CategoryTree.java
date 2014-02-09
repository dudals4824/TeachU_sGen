package sGen.teachu;

import sGen.teachu.database.DBBabyInfoAdapter;
import sGen.teachu.forSettingPage.Setting;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

public class CategoryTree extends Activity implements OnClickListener {

	static int CategoryID_ = 0;
	private boolean mIsBackButtonTouched = false;
	// 다른 액티비티에서 카테고리 트리를 종료시키기 위한 액티비티 변수
	public static Activity categorytree;

	private Button btn_categorytree_fruit, btn_category_setting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_tree);

		categorytree = CategoryTree.this;

		btn_categorytree_fruit = (Button) findViewById(R.id.btn_categorytree_fruit);
		btn_category_setting = (Button) findViewById(R.id.btn_setting);

		btn_category_setting.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btn_categorytree_fruit) {
			Intent TeachuplayActivity = new Intent(CategoryTree.this,
					Play.class);
			startActivity(TeachuplayActivity);
		} else if (v.getId() == R.id.btn_setting) {
			Intent TeachuSettingActivity = new Intent(CategoryTree.this,
					Setting.class);
			startActivityForResult(TeachuSettingActivity, 1);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 0, "아기 초기화");
		menu.add(0, 2, 0, "아기 상태 보러가기");
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
			Intent Setting = new Intent(CategoryTree.this, Setting.class);
			startActivity(Setting);
		}
		return super.onOptionsItemSelected(item);
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
