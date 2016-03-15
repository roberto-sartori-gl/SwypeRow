package com.robertogl.swyperow; 

import de.robv.android.xposed.XSharedPreferences;

public class Preferences {
    // This current package.
    private static final String PACKAGE_NAME = "com.robertogl.swyperow";

    // Our single instance of an XSharedPreferences.
    private static XSharedPreferences instance = null;
    private static XSharedPreferences getInstance() {
        if (instance == null) {
            instance = new XSharedPreferences(PACKAGE_NAME);
            instance.makeWorldReadable();
        } else {
            instance.reload();
        }
        
        return instance;
    }
    
    public static boolean hasArrowKeys() {
        return getInstance().getBoolean("arrow_keys", true);
    }
    
    public static String height() {
    	return (getInstance().getString("height", "100"));
    }


}
	