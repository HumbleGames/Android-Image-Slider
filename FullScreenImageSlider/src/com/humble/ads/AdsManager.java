// Copyright 2014 Vladimir Alyamkin. All Rights Reserved.

package com.humble.ads;

import android.app.Activity;

/**
 * Created by ufna on 04.01.2015.
 */
public class AdsManager {
    public static String PARAM_A2A = "A2A_ID";
    public static String PARAM_START_DELAY = "A2A_START_DELAY";
    public static String PARAM_DELAY_BETWEEN = "A2A_DELAY_BETWEEN";
    public static String PARAM_FLURRY = "FLURRY_ID";

    public void initAds(Activity activity) {

        // Prepare digital parameters
        /*int startDelay = 86400;
        if(isInteger(PARAM_START_DELAY)) {
            startDelay = Integer.parseInt(PARAM_START_DELAY);
        }

        int delayBetween = 180;
        if(isInteger(PARAM_DELAY_BETWEEN)) {
            delayBetween = Integer.parseInt(PARAM_DELAY_BETWEEN);
        }

        // Init library
        AdsConfig.init();
        AdsConfig.closeOnClickOutside(true);

        // @TODO Domains list should be able to be set externally
        AdsConfig.domains("http://zogene.com", "http://aganiro.com", "http://edonamo.com");

        // External parameters
        AdsConfig.appId(PARAM_A2A);

        AdsConfig.startDelay(startDelay);
        AdsConfig.delayBetweenBanners(delayBetween);

        System.out.println("Start delay: " + startDelay);
        System.out.println("Delay between banners: " + delayBetween);

        // Ad networks keys
        AdsConfig.flurry(PARAM_FLURRY);

        // Connect with activity
        AdsExtras.bootstrap(activity);*/
    }

    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }
}
