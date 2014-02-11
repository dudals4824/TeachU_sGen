package sGen.teachu.forSettingPage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class ProgressView2 extends View {
	Point point;
	int deviceheight, devicewidth;
	Display display;
	Paint mPaint = new Paint();
	int degree = 0;
	double percent;

	public ProgressView2(Context context, Double percent) {
		super(context);
		display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		point = new Point();
		display.getSize(point);
		devicewidth = point.x;
		deviceheight = point.y;
		this.percent=percent;
		setWillNotDraw(false);
		// TODO Auto-generated constructor stub
	}

	public ProgressView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		point = new Point();
		display.getSize(point);
		devicewidth = point.x;
		deviceheight = point.y;
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
		boolean isDrawn=false;
		super.onDraw(canvas);

		mPaint.setAntiAlias(true);
		float radius = (float) (devicewidth * 0.25);
		RectF mArea = new RectF(0, 0, radius, radius);
		mPaint.setColor(Color.WHITE);

		canvas.drawCircle(radius / 2, radius / 2, radius / 2, mPaint);
		mPaint.setColor(Color.rgb(255, 80, 80));
		canvas.drawArc(mArea, -90, degree, true, mPaint);
		//퍼센트에 정답률 넣으면 됨. 
		if (degree < 360 * percent)
			delay();
		else {
			isDrawn=true;
			
		}
		mPaint.setColor(Color.rgb(06, 06, 15));
		canvas.drawCircle(radius / 2, radius / 2, radius / 2 - 25, mPaint);
		if(isDrawn){
			mPaint.setColor(Color.WHITE);
			mPaint.setTextSize(60);
			canvas.drawText(percent * 100 + "",radius / 2-60,radius / 2, mPaint);
			mPaint.setTextSize(20);
			canvas.drawText("분류" + "",radius / 2-20,radius / 2-60, mPaint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int w = resolveSize((int) (devicewidth * 0.25), widthMeasureSpec);
		int h = resolveSize((int) (devicewidth * 0.25), heightMeasureSpec);
		setMeasuredDimension(w, h);
	}
//0에서 부터 원하는 각이 나올때 까지 onDraw 메소드 호출함
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
