package sGen.teachu.forSettingPage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;

public class photoEditor {
	private Bitmap photoBitmap;
	private Bitmap coverBitmap;
	private Bitmap coveredPhoto;
	private int photoAreaWidth;
	private int photoAreaHeight;

	public photoEditor(Bitmap photoBitmap, Bitmap coverBitmap,
			int photoAreaWidth, int photoAreaHeight) {
		super();
		this.photoBitmap = photoBitmap;
		this.coverBitmap = coverBitmap;
		this.photoAreaWidth = photoAreaWidth;
		this.photoAreaHeight = photoAreaHeight;
	}

	public Bitmap getPhotoBitmap() {
		return photoBitmap;
	}

	public void setPhotoBitmap(Bitmap photoBitmap) {
		this.photoBitmap = photoBitmap;
	}

	public Bitmap getCoverBitmap() {
		return coverBitmap;
	}

	public void setCoverBitmap(Bitmap coverBitmap) {
		this.coverBitmap = coverBitmap;
	}

	public int getPhotoAreaWidth() {
		return photoAreaWidth;
	}

	public void setPhotoAreaWidth(int photoAreaWidth) {
		this.photoAreaWidth = photoAreaWidth;
	}

	public int getPhotoAreaHeight() {
		return photoAreaHeight;
	}

	public void setPhotoAreaHeight(int photoAreaHeight) {
		this.photoAreaHeight = photoAreaHeight;
	}
	
	public Bitmap editPhotoAuto(){
		resizeBitmapToProfileSize();
		getCroppedCircle();
		overlayCover();
		return coveredPhoto;
	}

	private void getCroppedCircle() {
		Bitmap output = Bitmap.createBitmap(photoAreaWidth, photoAreaHeight,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, photoAreaWidth, photoAreaHeight);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawCircle(photoAreaWidth / 2, photoAreaHeight / 2,
				photoAreaWidth / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(photoBitmap, rect, rect, paint);
		photoBitmap = output;
	}

	// bitmap을 profile width,height size에 맞게 resize
	private void resizeBitmapToProfileSize() {
		Bitmap resized;
		resized = Bitmap.createScaledBitmap(photoBitmap, photoAreaWidth,
				photoAreaHeight, true);
		photoBitmap = resized;
	}

	// cover 씌우는애
	private void overlayCover() {
		coveredPhoto = Bitmap.createBitmap(photoAreaWidth,
				photoAreaHeight, photoBitmap.getConfig());
		Canvas canvas = new Canvas(coveredPhoto);
		canvas.drawBitmap(photoBitmap, new Matrix(), null);
		canvas.drawBitmap(coverBitmap, new Matrix(), null);
	}

}
