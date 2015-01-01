package info.androidhive.imageslider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
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

    public class StringPair {
        public String lineOne = "";
        public String lineTwo = "";

        StringPair(String lineOne, String lineTwo) {
            this.lineOne = lineOne;
            this.lineTwo = lineTwo;
        }
    }

    /** List of descriptions added to photos */
    private Map<String , StringPair> _infoLines = new HashMap<String , StringPair>();

    private TextInfoManager() {
        // Configure json mapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        File appDataFile = new File("assets/applist.json");

        if(appDataFile.exists()) {
            try {
                // Read static data from json
                _infoLines = mapper.readValue(appDataFile, new TypeReference<Map<String, StringPair>>() { } );

                System.out.println("Applications list loaded");

            } catch (IOException e) {
                System.out.println("Applications list read failed");
                e.printStackTrace();
            }

        }
        else {
            System.out.println("Application info file doesn't exist!");
        }
    }
}
