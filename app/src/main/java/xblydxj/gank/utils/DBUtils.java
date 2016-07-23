package xblydxj.gank.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.orhanobut.logger.Logger;

import java.util.Iterator;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import xblydxj.gank.db.DaoMaster;
import xblydxj.gank.db.DaoSession;
import xblydxj.gank.db.dataCatch;
import xblydxj.gank.db.dataCatchDao;

/**
 * Created by 46321 on 2016/7/23/023.
 */
public class DBUtils {
    private dataCatchDao dao;
    public DBUtils(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "datacatch.db", null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession session = daoMaster.newSession();
        this.dao = session.getDataCatchDao();
    }

    public void addData(List<dataCatch> datas) {
        Logger.d("add:"+datas.size());
        for (dataCatch data : datas) {
            dao.insert(data);
        }
    }

    public List<dataCatch> queryData(String type) {
        Logger.d("query:"+type);
        QueryBuilder<dataCatch> queryBuilder = dao.queryBuilder();
        QueryBuilder<dataCatch> builder = queryBuilder.where(dataCatchDao.Properties.Type.eq(type));
        Query<dataCatch> build = builder.build();
        return build.list();
    }

    public void deleteType(final String type) {
        Logger.d("delete:"+type);
        dao.deleteInTx(new Iterable<dataCatch>() {
            @Override
            public Iterator<dataCatch> iterator() {
                return queryData(type).iterator();
            }
        });
    }
}
