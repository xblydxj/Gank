package xblydxj.gank.modules.home.contract;

import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;

import java.util.List;

import xblydxj.gank.BasePresenter;
import xblydxj.gank.BaseView;
import xblydxj.gank.bean.Data;

/**
 * Created by xblydxj
 * on 2016/7/21/021.
 */
public class BaseContract {
    public interface View extends BaseView<Presenter> {

        void updateAdapter(List<Data.ResultsBean> data);

        void stopRefreshing();

        void updateStatus(int status);

        void showSnack();
    }

    public interface Presenter extends BasePresenter {

        void updateData();

        void reconnect();

        void upPullLoad(int listSize);

        void toWeb(FragmentActivity activity, String url, String desc, ActivityOptionsCompat compat);
    }
}
