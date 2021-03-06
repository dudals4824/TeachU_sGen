package sGen.teachu;

import sGen.teachu.database.DBBabyInfoAdapter;
import sGen.teachu.forSettingPage.Setting;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CategoryTree extends Activity implements OnClickListener {

	public static int CategoryID = 0;

	public static int getCategoryID() {
		return CategoryID;
	}

	public static void setCategoryID(int categoryID) {
		CategoryID = categoryID;
	}

	private boolean mIsBackButtonTouched = false;
	// 다른 액티비티에서 카테고리 트리를 종료시키기 위한 액티비티 변수
	public static Activity categorytree;
	private ImageView pic_cloud1, pic_cloud3, pic_yellowplanet, pic_cloud4,
			pic_cloud5, pic_cloud2, pic_mintplanet, pic_spaceship1,
			pic_spaceship2;
	private Button btn_categorytree_fruit, btn_category_setting,
			btn_categorytree_animal, btn_categorytree_thing;
	private ImageView light;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_tree);

		light = (ImageView) findViewById(R.id.light);
		Drawable alpha1 = light.getDrawable();
		alpha1.setAlpha(50);

		categorytree = CategoryTree.this;

		btn_categorytree_fruit = (Button) findViewById(R.id.btn_categorytree_fruit);
		btn_categorytree_animal = (Button) findViewById(R.id.btn_categorytree_animal);
		btn_categorytree_thing = (Button) findViewById(R.id.btn_categorytree_object);
		btn_category_setting = (Button) findViewById(R.id.btn_setting);

		pic_cloud1 = (ImageView) findViewById(R.id.pic_categorytree_cloud1);
		pic_cloud3 = (ImageView) findViewById(R.id.pic_categorytree_cloud3);
		pic_cloud4 = (ImageView) findViewById(R.id.pic_categorytree_cloud4);
		pic_cloud5 = (ImageView) findViewById(R.id.pic_categorytree_cloud5);
		pic_cloud2 = (ImageView) findViewById(R.id.pic_categorytree_cloud2);
		pic_yellowplanet = (ImageView) findViewById(R.id.pic_categorytree_yellowplantet);
		pic_mintplanet = (ImageView) findViewById(R.id.pic_categorytree_mintplanet);
		pic_spaceship1 = (ImageView) findViewById(R.id.pic_categorytree_spaceship1);
		pic_spaceship2 = (ImageView) findViewById(R.id.pic_categorytree_spaceship2);

		btn_categorytree_fruit.setOnClickListener(this);
		btn_categorytree_animal.setOnClickListener(this);
		btn_categorytree_thing.setOnClickListener(this);

		btn_category_setting.setOnClickListener(this);

		onAnimateBackground();
	}

	public void onAnimateBackground() {
		TranslateAnimation animation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				2.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(10000);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.REVERSE);
		pic_cloud1.setAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, -3.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(10000);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.REVERSE);
		pic_cloud3.setAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 2.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(8000);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.REVERSE);
		pic_cloud4.setAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.3f);
		animation.setDuration(5000);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.REVERSE);
		pic_cloud5.setAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(2000);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.REVERSE);
		pic_cloud2.setAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -0.1f,
				Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
		animation.setDuration(2000);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.REVERSE);
		pic_mintplanet.setAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -0.2f,
				Animation.RELATIVE_TO_SELF, 0.4f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
		animation.setDuration(3000);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.REVERSE);
		pic_yellowplanet.setAnimation(animation);

		animation = new TranslateAnimation(-500.0f, 1400.0f, 1000.0f, -800.0f);
		// animation = new TranslateAnimation(-100.2f, 100.4f, -200.0f, 100.0f);
		animation.setDuration(5000);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.RESTART);
		pic_spaceship1.setAnimation(animation);

		// animation = new TranslateAnimation(-1000.2f, 1000.4f, -2000.0f,
		// 1000.0f);
		animation = new TranslateAnimation(500.0f, -300.0f, 400.0f, -1900.0f);
		animation.setDuration(7000);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.RESTART);
		pic_spaceship2.setAnimation(animation);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		setCategoryID(v.getId());
		if ((v.getId() == R.id.btn_categorytree_fruit)
				|| (v.getId() == R.id.btn_categorytree_animal)
				|| (v.getId() == R.id.btn_categorytree_object)) {
			Intent TeachuplayActivity = new Intent(CategoryTree.this,
					Play.class);
			startActivity(TeachuplayActivity);
		} else if (v.getId() == R.id.btn_setting) {
			Intent TeachuSettingActivity = new Intent(CategoryTree.this,
					Setting.class);
			startActivityForResult(TeachuSettingActivity, 1);
		}

	}

	// 백버튼 두번 누르면 종료시킴 플래그 바꿔서
	@Override
	public void onBackPressed() {
		if (mIsBackButtonTouched == false) {
			Toast.makeText(this, "한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
					.show();
			mIsBackButtonTouched = true;
			Intent svc = new Intent(this, BackgroundSoundService.class);
			stopService(svc);
		} else if (mIsBackButtonTouched == true) {
			finish();
		}
	}

}
