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
	int percentage;
	Display display;
	Canvas Outercanvas;
	int deviceheight, devicewidth;
	Point point;
	Paint paint = new Paint();
	int x = 90;

	public ProgressView2(Context context) {
		super(context);
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

	public ProgressView2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		point = new Point();
		display.getSize(point);
		devicewidth = point.x;
		deviceheight = point.y;
		setWillNotDraw(false);

	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int w = resolveSize(290, widthMeasureSpec);
		int h = resolveSize(290, heightMeasureSpec);
		setMeasuredDimension(w, h);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		super.onDraw(canvas);
		// x가 90에서 45
		paint.setColor(Color.BLUE);
		canvas.drawCircle((float) (devicewidth * 0.13),
				(float) (devicewidth * 0.13), (float) (devicewidth * 0.13),
				paint);
		Outercanvas = canvas;
		Log.d("progress", x + "");
		// 180-2x
		paint.setColor(Color.YELLOW);
		canvas.drawArc(new RectF((float) (devicewidth * 0.01),
				(float) (devicewidth * 0.01), (float) (devicewidth * 0.25),
				(float) (devicewidth * 0.25)), x, 180 - (2 * x), false, paint);
		// x는 항상 90부터 시작해서 원하는 각도까지 반복
		if (x !=setPercentage(45))
			delay();
		else {
			paint.setColor(Color.BLACK);
			paint.setTextSize(40);
			canvas.drawText(x + "", (float) ((devicewidth * 0.1)),
					(float) (devicewidth * 0.142), paint);
		}

	}

	void delay() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				x--;
				invalidate();
			}
		}, 1);
	}

	int setPercentage(int p) {
		double degree;
		degree=90-((9/5)*p);
		return (int)degree;
		
	}

}
