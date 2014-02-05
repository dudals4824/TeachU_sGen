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
	int percent = 0;

	public ProgressView2(Context context) {
		super(context);
		display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		point = new Point();
		display.getSize(point);
		devicewidth = point.x;
		deviceheight = point.y;
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
		super.onDraw(canvas);

		int mPercent = 120;
		RectF mArea = new RectF((float) (devicewidth * 0.01),
				(float) (devicewidth * 0.01), (float) (devicewidth * 0.25),
				(float) (devicewidth * 0.25));
		final float startAngle = -90;
		final float drawTo = startAngle + (mPercent * 360);
		// Rotate the canvas around the center of the pie by 90 degrees
		// counter clockwise so the pie stars at 12 o'clock.
		// canvas.rotate(-90f, mArea.centerX(), mArea.centerY());
		mPaint.setColor(Color.WHITE);
		canvas.drawCircle((float) (devicewidth * 0.01),
				(float) (devicewidth * 0.25),
				(float)(devicewidth * 0.25), mPaint);
		mPaint.setColor(Color.rgb(255, 80, 80));
		canvas.drawArc(mArea, -90, percent, true, mPaint);
		if (percent < 90)
			delay();
		else {
			mPaint.setColor(Color.BLACK);
			mPaint.setTextSize(40);
			canvas.drawText(percent + "", (float) ((devicewidth * 0.1)),
					(float) (devicewidth * 0.142), mPaint);
		}
		// Draw inner oval and text on top of the pie (or add any other
		// decorations such as a stroke) here..
		// Don't forget to rotate the canvas back if you plan to add text!
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int w = resolveSize(290, widthMeasureSpec);
		int h = resolveSize(290, heightMeasureSpec);
		setMeasuredDimension(w, h);
	}

	void delay() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				percent++;
				invalidate();
			}
		}, 1);
	}
}
