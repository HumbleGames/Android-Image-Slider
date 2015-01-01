package info.androidhive.imageslider.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import info.androidhive.imageslider.R;
import info.androidhive.imageslider.TextInfoManager;
import info.androidhive.imageslider.helper.TouchImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FullScreenImageAdapter extends PagerAdapter {

	private Activity _activity;
	private ArrayList<String> _imagePaths;
	private LayoutInflater inflater;

	// constructor
	public FullScreenImageAdapter(Activity activity,
			ArrayList<String> imagePaths) {
		this._activity = activity;
		this._imagePaths = imagePaths;
	}

    /**
     * Get birmap from assets folder
     * @param strName
     * @return
     */
    private Bitmap getBitmapFromAsset(String strName, BitmapFactory.Options opts)
    {
        AssetManager assetManager = _activity.getAssets();
        InputStream istr = null;

        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }

	@Override
	public int getCount() {
		return this._imagePaths.size();
	}

	@Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
	
	@Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;
        Button btnClose;
        Button btnMore;
        TextView mainText;
        TextView subText;
 
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);
 
        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
        btnMore = (Button) viewLayout.findViewById(R.id.btnMore);
        
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        //----
        //Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);
        Bitmap bitmap = getBitmapFromAsset(_imagePaths.get(position), options);

        // Set description
        mainText = (TextView) viewLayout.findViewById(R.id.textInfo_main);
        subText = (TextView) viewLayout.findViewById(R.id.textInfo_sub);

        TextInfoManager.StringPair TextPair = TextInfoManager.getInstance().get_infoLines().get(_imagePaths.get(position));
        if(TextPair != null) {
            mainText.setText((CharSequence) TextPair.lineOne);
            subText.setText((CharSequence) TextPair.lineTwo);

        } else {
            System.out.println("No info loaded for: " + _imagePaths.get(position) + " :: " + TextInfoManager.getInstance().get_infoLines().size());

            mainText.setText("");
            subText.setText("");
        }

        //----


        imgDisplay.setImageBitmap(bitmap);
        
        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				_activity.finish();
			}
		});

        // More button click event (opens Google Play account)
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String publisherName = _activity.getString(R.string.app_publisher);
                try {
                    _activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:" + publisherName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    _activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://market.android.com/search?q=pub:" + publisherName)));
                }
            }
        });

        ((ViewPager) container).addView(viewLayout);
 
        return viewLayout;
	}
	
	@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
 
    }

}
