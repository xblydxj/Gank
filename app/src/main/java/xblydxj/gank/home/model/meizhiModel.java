package xblydxj.gank.home.model;

import java.util.List;

import xblydxj.gank.AppConfig;
import xblydxj.gank.db.Meizhi.Meizhi;
import xblydxj.gank.manager.dbmanager.DBManager4Meizhi;

/**
 * Created by 46321 on 2016/7/26/026.
 */
public class meizhiModel {
    private DBManager4Meizhi mDBUtils = new DBManager4Meizhi(AppConfig.sContext);

    public List<Meizhi> isCatchExist() {
        return mDBUtils.query();
    }

    public void putMeizhisToDB(List<Meizhi> images) {
        for (Meizhi image : images) {
            mDBUtils
        }
        mDBUtils.addmeizhi(images);
    }
}
