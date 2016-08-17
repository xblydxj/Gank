package xblydxj.gank;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xblydxj.gank.api.URL;
import xblydxj.gank.utils.CacheInterceptor;

/**
 *                #####################################################
 *                #                                                   #
 *                #                       _oo0oo_                     #
 *                #                      o8888888o                    #
 *                #                      88" . "88                    #
 *                #                      (| -_- |)                    #
 *                #                      0\  =  /0                    #
 *                #                    ___/`---'\___                  #
 *                #                  .' \\|     |# '.                 #
 *                #                 / \\|||  :  |||# \                #
 *                #                / _||||| -:- |||||- \              #
 *                #               |   | \\\  -  #/ |   |              #
 *                #               | \_|  ''\---/''  |_/ |             #
 *                #               \  .-\__  '-'  ___/-. /             #
 *                #             ___'. .'  /--.--\  `. .'___           #
 *                #          ."" '<  `.___\_<|>_/___.' >' "".         #
 *                #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 *                #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 *                #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 *                #                       `=---='                     #
 *                #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 *                #                                                   #
 *                #               佛祖保佑         永无BUG              #
 *                #                                                   #
 *                #####################################################
 *
 *
 * Created by xblydxj
 * on 2016/7/16/016.
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
//        LeakCanary.install(this);
        initRetrofit();
    }

    private void initRetrofit() {
        File cacheFile = new File(this.getCacheDir(), "CacheData");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        Interceptor interceptor = new CacheInterceptor();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(interceptor)
                .build();

        sRetrofit = new Retrofit.Builder()
                .baseUrl(URL.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
//
//    private void setupDatabase() {
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "data.db", null);
//        SQLiteDatabase database = helper.getWritableDatabase();
//        DaoMaster daoMaster = new DaoMaster(database);
//        DaoSession daoSession = daoMaster.newSession();
//        dataCatchDao dao = daoSession.getDataCatchDao();
//    }
}
