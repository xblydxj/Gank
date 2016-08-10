package xblydxj.gank.utils;

import android.content.Context;
import android.view.WindowManager;

import xblydxj.gank.AppConfig;

/**
 * Created by 46321 on 2016/7/26/026.
 */
public class Utils {

    private static WindowManager mWm;

    public static int getDisplayWidth() {
        if (mWm == null) {
            mWm = (WindowManager) AppConfig.sContext.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWm.getDefaultDisplay().getWidth();
    }

    public static int getDisplayHeight() {
        if (mWm == null) {
            mWm = (WindowManager) AppConfig.sContext.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWm.getDefaultDisplay().getHeight();
    }

    public static void runOnUIThread(Runnable runnable){
        AppConfig.sHandler.post(runnable);
    }

}
