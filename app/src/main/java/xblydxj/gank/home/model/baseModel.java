package xblydxj.gank.home.model;

import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import xblydxj.gank.AppConfig;
import xblydxj.gank.db.normalData.dataCatch;
import xblydxj.gank.manager.dbmanager.DBManager4Base;

/**
 * Created by 46321 on 2016/7/23/023.
 */
public class baseModel {
    private DBManager4Base mDBUtils = new DBManager4Base(AppConfig.sContext);

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
