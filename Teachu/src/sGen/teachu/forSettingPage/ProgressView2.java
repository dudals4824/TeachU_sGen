package sGen.teachu.forSettingPage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class ProgressView2 extends View {
	Point point;
	int deviceheight, devicewidth;
	Display display;
	Paint mPaint = new Paint();
	int degree = 0;

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

		mPaint.setAntiAlias(true);
		RectF mArea = new RectF(0,0,170,170);
		// Rotate the canvas around the center of the pie by 90 degrees
		// counter clockwise so the pie stars at 12 o'clock.
		// canvas.rotate(-90f, mArea.centerX(), mArea.centerY());
		mPaint.setColor(Color.WHITE);
		canvas.drawCircle(85,85,85, mPaint);
		mPaint.setColor(Color.rgb(255, 80, 80));
		canvas.drawArc(mArea, -90, degree, true, mPaint);
		double percent=0.8;
		double a=((percent/50)*Math.PI)-((1/2)*Math.PI);
		if (degree < 360*percent)
			delay();
		else {
			mPaint.setColor(Color.BLUE);
			mPaint.setTextSize(20);
			canvas.drawText(a + "", (float) ((devicewidth * 0.1)),
					(float) (devicewidth * 0.142), mPaint);
		}
		// Draw inner oval and text on top of the pie (or add any other
		// decorations such as a stroke) here..
		// Don't forget to rotate the canvas back if you plan to add text!
		
		
		mPaint.setColor(Color.rgb(06,06,15));
		canvas.drawCircle(85,85,70, mPaint);
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
				degree++;
				invalidate();
			}
		}, 1);
	}
}
