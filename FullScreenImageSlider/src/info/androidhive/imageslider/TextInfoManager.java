package info.androidhive.imageslider;

import android.app.Activity;
import android.content.res.AssetManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.androidhive.imageslider.helper.StringPair;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ufna on 01.01.2015.
 */
public class TextInfoManager {
    private static TextInfoManager ourInstance = new TextInfoManager();

    public static TextInfoManager getInstance() {
        return ourInstance;
    }

    public Map<String, StringPair> get_infoLines() {
        return _infoLines;
    }

    /** List of descriptions added to photos */
    private Map<String , StringPair> _infoLines = new HashMap<String , StringPair>();

    private TextInfoManager() {}

    public void loadAppData(Activity activity) {
        // Configure json mapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        // Load json from assets
        AssetManager assetManager = activity.getAssets();
        InputStream istr = null;

        try {
            istr = assetManager.open("applist.json");
            _infoLines = mapper.readValue(istr, new TypeReference<Map<String, StringPair>>() { } );

        } catch (IOException e) {
            System.out.println("Applications list read failed");
            e.printStackTrace();
        }
    }
}
