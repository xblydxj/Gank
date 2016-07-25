package xblydxj.gank.home.model;

import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import xblydxj.gank.AppConfig;
import xblydxj.gank.db.dataCatch;
import xblydxj.gank.manager.dbmanager.DBManager;

/**
 * Created by 46321 on 2016/7/23/023.
 */
public class baseModel {
    private DBManager mDBUtils = new DBManager(AppConfig.sContext);


    /* if(网络可用){
            if(本地存在对应缓存){
                在界面中先设置本地数据；
            }
            发出网络请求。
            网络请求数据存储到本地。
            等网络请求结束用新的数据刷新界面。
        }else{
            if(本地存在对应缓存){
                在界面中设置本地数据；
            }else{
                界面中提示没有数据，或者网络不可用。
            }
        }*/
    public List<dataCatch> isCatchExist(String type) {
        return mDBUtils.queryData(type);
    }

    public void putListToDB(List<dataCatch> datas, final String type) {
        Observable.just(datas)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<List<dataCatch>>() {
                    @Override
                    public void call(List<dataCatch> dataCatchs) {
                        Logger.d("delete:" + type);
                        mDBUtils.deleteType(type);
                        mDBUtils.addData(dataCatchs);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.d(throwable.getMessage());
                    }
                });
    }

    public void putListToDBNotDelete(List<dataCatch> datas, final String type) {
        mDBUtils.addData(datas);
    }



}
