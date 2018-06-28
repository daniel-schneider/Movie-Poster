package com.popular.movies.popularmovies.utilities;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by danielschneider on 6/26/18.
 */

public class Utilities {

    public static boolean isPackageInstalled(String packageName, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
