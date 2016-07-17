package xblydxj.gank.config;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by 46321 on 2016/7/16/016.
 */
public class AppConfig extends Application {

    public static Context sContext;
    public static Handler sHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        sHandler = new Handler();
        Logger.init();
        LeakCanary.install(this);
    }
}
