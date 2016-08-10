package xblydxj.gank.modules.home.contract;

import android.content.Intent;

import java.util.List;

import xblydxj.gank.BasePresenter;
import xblydxj.gank.BaseView;
import xblydxj.gank.bean.Data;

/**
 * Created by 46321 on 2016/7/21/021.
 */
public class BaseContract {
    public interface View extends BaseView<Presenter> {

        void updateAdapter(List<Data.ResultsBean> data);

        void stopRefreshing();

        void updateStatus(int status);

        void intentToWeb(Intent intent);

        void showSnack();
    }

    public interface Presenter extends BasePresenter {

        void updateData();

        void reconnect();

        void upPullLoad(int listSize);

        void toWeb(String url,String desc);
    }
}
