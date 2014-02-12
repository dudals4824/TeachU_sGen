package sGen.teachu.forSettingPage;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class ProgressView2 extends View {
	private Point point;
	private int deviceheight, devicewidth;
	private Display display;
	private Paint mPaint = new Paint();
	private int degree = 0;
	private double percent;
	private int categoryId = 0;
	private String categoryStr = "분류";
	private DecimalFormat form = new DecimalFormat("0.##");
	private Typeface tf;

	public static final int CATEGORY_FRUIT = 0; // 과일
	public static final int CATEGORY_ANIMAL = 1; // 동물
	public static final int CATEGORY_THING = 2; // 물건

	public ProgressView2(Context context, Double percent, int categoryId) {
		super(context);
		display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		point = new Point();
		display.getSize(point);
		devicewidth = point.x;
		deviceheight = point.y;
		this.percent = percent;
		this.categoryId = categoryId;
		tf = Typeface.createFromAsset(context.getAssets(), "font/KOPUBDOTUMMEDIUM.TTF");
		setWillNotDraw(false);
		// TODO Auto-generated constructor stub
	}

	public ProgressView2(Context context, AttributeSet attrs, int categoryId,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		point = new Point();
		display.getSize(point);
		devicewidth = point.x;
		deviceheight = point.y;
		this.categoryId = categoryId;
		setWillNotDraw(false);
	}

	public ProgressView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		point = new Point();
		display.getSize(point);
		devicewidth = point.x;
		deviceheight = point.y;
		setWillNotDraw(false);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		boolean isDrawn = false;
		super.onDraw(canvas);
		mPaint.setTypeface(tf);
		mPaint.setAntiAlias(true);
		float radius = (float) (devicewidth * 0.25);
		RectF mArea = new RectF(0, 0, radius, radius);
		mPaint.setColor(Color.WHITE);

		canvas.drawCircle(radius / 2, radius / 2, radius / 2, mPaint);
		mPaint.setColor(Color.rgb(255, 80, 80));
		canvas.drawArc(mArea, -90, degree, true, mPaint);
		// 퍼센트에 정답률 넣으면 됨.
		if (degree < 360 * percent)
			delay();
		else {
			isDrawn = true;

		}
		mPaint.setColor(Color.rgb(06, 06, 15));
		
		canvas.drawCircle(radius / 2, radius / 2, radius / 2 - 25, mPaint);
		if (isDrawn) {
			mPaint.setColor(Color.WHITE);
			mPaint.setTextSize(60);
			canvas.drawText(form.format(percent * (double)100)+ "%", radius / 2 - 57, radius / 2 + 30,
					mPaint);
			mPaint.setTextSize(40);
			
			switch (categoryId) {
			case CATEGORY_ANIMAL:
				categoryStr = "동물";
				break;
			case CATEGORY_FRUIT:
				categoryStr = "과일";
				break;
			case CATEGORY_THING:
				categoryStr = "사물";
				break;
			default:
				break;
			}
			canvas.drawText(categoryStr + "", radius / 2 - 36, radius / 2 - 30,
					mPaint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int w = resolveSize((int) (devicewidth * 0.25), widthMeasureSpec);
		int h = resolveSize((int) (devicewidth * 0.25), heightMeasureSpec);
		setMeasuredDimension(w, h);
	}

	// 0에서 부터 원하는 각이 나올때 까지 onDraw 메소드 호출함
	void delay() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				degree++;
				invalidate();
			}
		}, 1);
	}
}
