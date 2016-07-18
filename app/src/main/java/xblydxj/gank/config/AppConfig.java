package xblydxj.gank.config;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xblydxj.gank.api.URL;

/**
 * Created by 46321 on 2016/7/16/016.
 */
public class AppConfig extends Application {

    public static Context sContext;
    public static Handler sHandler;
    public static Retrofit sRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        sHandler = new Handler();
        Logger.init();
        LeakCanary.install(this);
        initRetrofit();
    }

    private void initRetrofit() {
        sRetrofit = new Retrofit.Builder()
                .baseUrl(URL.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
