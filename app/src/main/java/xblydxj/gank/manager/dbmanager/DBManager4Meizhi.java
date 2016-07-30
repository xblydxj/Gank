package xblydxj.gank.manager.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.orhanobut.logger.Logger;

import java.util.Iterator;
import java.util.List;

import xblydxj.gank.db.Meizhi.DaoMaster;
import xblydxj.gank.db.Meizhi.DaoSession;
import xblydxj.gank.db.Meizhi.Meizhi;
import xblydxj.gank.db.Meizhi.MeizhiDao;

/**
 * Created by 46321 on 2016/7/26/026.
 */
public class DBManager4Meizhi {
    /***********
     * 数据
     ***********/
    private MeizhiDao dao;

    public DBManager4Meizhi(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "meizhi.db", null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession session = daoMaster.newSession();
        this.dao = session.getMeizhiDao();
    }

    public void addmeizhi(final List<Meizhi> meizhis) {
        Logger.d("add,meizhi:" + meizhis.size());
        for (Meizhi meizhi : meizhis) {
            Logger.d("meizhiUrl:" + meizhi.getUrl());
        }
        dao.insertInTx(new Iterable<Meizhi>() {
            @Override
            public Iterator<Meizhi> iterator() {
                return meizhis.iterator();
            }
        });
    }

    public List<Meizhi> query() {
        return dao.queryBuilder().list();
    }

    public boolean queryExsited(Meizhi meizhi){
        List<Meizhi> meizhis = dao.queryRaw(meizhi.getUrl());
        if (meizhis.isEmpty()) {
            return false;
        }
        return true;
    }
}