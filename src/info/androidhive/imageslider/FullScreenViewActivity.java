package info.androidhive.imageslider;

import android.content.res.AssetManager;
import info.androidhive.imageslider.adapter.FullScreenImageAdapter;
import info.androidhive.imageslider.helper.Utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FullScreenViewActivity extends Activity{

	private Utils utils;
	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_view);

		viewPager = (ViewPager) findViewById(R.id.pager);

		utils = new Utils(getApplicationContext());

		Intent i = getIntent();
		int position = i.getIntExtra("position", 0);

		//----

		String[] assetsList = new String[0];

		AssetManager aMan = getAssets();
		try {
			assetsList = aMan.list("");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\nFILE LIST:" + assetsList);

		ArrayList<String> filePaths = new ArrayList<String>();

		// loop through all files
		for (int j = 0; j < assetsList.length; j++) {
			// get file path
			String filePath = assetsList[j];

			// check for supported file extension
			if (utils.IsSupportedFile(filePath)) {
				// Add image path to array list
				filePaths.add(filePath);
			}
		}

		//----

		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
				filePaths/*utils.getFilePaths()*/);

		viewPager.setAdapter(adapter);

		// displaying selected image first
		viewPager.setCurrentItem(position);
	}
}
