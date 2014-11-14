package info.androidhive.imageslider.adapter;

import android.content.res.AssetManager;
import android.graphics.Rect;
import info.androidhive.imageslider.FullScreenViewActivity;

import java.io.*;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewImageAdapter extends BaseAdapter {

	private Activity _activity;
	private ArrayList<String> _filePaths = new ArrayList<String>();
	private int imageWidth;

	public GridViewImageAdapter(Activity activity, ArrayList<String> filePaths,
			int imageWidth) {
		this._activity = activity;
		this._filePaths = filePaths;
		this.imageWidth = imageWidth;
	}

	@Override
	public int getCount() {
		return this._filePaths.size();
	}

	@Override
	public Object getItem(int position) {
		return this._filePaths.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(_activity);
		} else {
			imageView = (ImageView) convertView;
		}

		// get screen dimensions
		Bitmap image = null;
		try {
			image = decodeFile(_filePaths.get(position), imageWidth, imageWidth);
		} catch (IOException e) {
			e.printStackTrace();
		}

		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));
		imageView.setImageBitmap(image);

		// image view click listener
		imageView.setOnClickListener(new OnImageClickListener(position));

		return imageView;
	}

	class OnImageClickListener implements OnClickListener {

		int _postion;

		// constructor
		public OnImageClickListener(int position) {
			this._postion = position;
		}

		@Override
		public void onClick(View v) {
			// on selecting grid view image
			// launch full screen activity
			Intent i = new Intent(_activity, FullScreenViewActivity.class);
			i.putExtra("position", _postion);
			_activity.startActivity(i);
		}

	}

	/*
	 * Resizing image size
	 */
	private Bitmap decodeFile(String filePath, int WIDTH, int HEIGHT) throws IOException {
		AssetManager assetManager = _activity.getAssets();
		InputStream istr = null;

		try {
			istr = assetManager.open(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(istr, null, o);

		final int REQUIRED_WIDTH = WIDTH;
		final int REQUIRED_HEIGHT = HIGHT;
		int scale = 1;
		while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
				&& o.outHeight / scale / 2 >= REQUIRED_HIGHT)
			scale *= 2;

		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;

		istr.reset();

		return BitmapFactory.decodeStream(istr, null, o2);
	}

}
