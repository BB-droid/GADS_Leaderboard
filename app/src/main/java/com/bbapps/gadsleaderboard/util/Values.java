package com.bbapps.gadsleaderboard.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Values {
    public static String HOURS_KEY = "LeadersHours";
    public static String SKILLIQ_KEY = "SkillIQ";
    public static String LEADERS_HOURS =  "LeadersHours";
    public static String LEADERS_SKILLIQ = "LeadersSkillIQ";

    public static boolean checkConnectivity(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
