package xblydxj.gank;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xblydxj.gank.api.URL;
import xblydxj.gank.db.normalData.DaoMaster;
import xblydxj.gank.db.normalData.DaoSession;
import xblydxj.gank.db.normalData.dataCatchDao;

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
        setupDatabase();
    }

    private void initRetrofit() {
        sRetrofit = new Retrofit.Builder()
                .baseUrl(URL.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private void setupDatabase(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "data.db", null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        dataCatchDao dao = daoSession.getDataCatchDao();
    }
}
