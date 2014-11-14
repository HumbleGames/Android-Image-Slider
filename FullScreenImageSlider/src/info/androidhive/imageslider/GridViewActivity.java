package info.androidhive.imageslider;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import info.androidhive.imageslider.adapter.GridViewImageAdapter;
import info.androidhive.imageslider.helper.AppConstant;
import info.androidhive.imageslider.helper.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.GridView;

public class GridViewActivity extends Activity {

	private Utils utils;
	private ArrayList<String> imagePaths = new ArrayList<String>();
	private GridViewImageAdapter adapter;
	private GridView gridView;
	private int columnWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_view);

		gridView = (GridView) findViewById(R.id.grid_view);

		//---- More button action
		Button btnMore = (Button) findViewById(R.id.btnMoreMain);

		// More button click event (opens Google Play account)
		btnMore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String publisherName = getString(R.string.app_publisher);
				try {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:" + publisherName)));
				} catch (android.content.ActivityNotFoundException anfe) {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://market.android.com/search?q=pub:" + publisherName)));
				}
			}
		});
		//---- END

		utils = new Utils(this);

		// Initilizing Grid View
		InitilizeGridLayout();

		// loading all image paths from SD card
		// imagePaths = utils.getFilePaths();

		//----

		String[] assetsList = new String[0];

		AssetManager aMan = getAssets();
		try {
			assetsList = aMan.list("");
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<String> imagePaths = new ArrayList<String>();

		// loop through all files
		for (int j = 0; j < assetsList.length; j++) {
			// get file path
			String filePath = assetsList[j];

			// check for supported file extension
			if (utils.IsSupportedFile(filePath)) {
				// Add image path to array list
				imagePaths.add(filePath);

				System.out.println("Image added:" + filePath);
			}
		}

		//----

		// Gridview adapter
		adapter = new GridViewImageAdapter(GridViewActivity.this, imagePaths,
				columnWidth);

		// setting grid view adapter
		gridView.setAdapter(adapter);
	}

	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				AppConstant.GRID_PADDING, r.getDisplayMetrics());

		columnWidth = (int) ((utils.getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);

		gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
		gridView.setColumnWidth(columnWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);
	}

}
