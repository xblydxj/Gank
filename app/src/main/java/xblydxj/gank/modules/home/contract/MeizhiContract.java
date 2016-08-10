package xblydxj.gank.modules.home.contract;

import android.support.v4.app.FragmentActivity;

import java.util.List;

import xblydxj.gank.BasePresenter;
import xblydxj.gank.BaseView;
import xblydxj.gank.bean.Data;

/**
 * Created by 46321 on 2016/7/25/025.
 *
 */
public class MeizhiContract {
    public interface View extends BaseView<Presenter>{

        void updateAdapter(List<Data.ResultsBean> data);

        void stopRefreshing();

        void updateStatus(int status);

        void showSnack();
    }
    public interface Presenter extends BasePresenter{

        void updateData();

        void reconnect();

        void loadMore(int size);

        void toPhotoView(FragmentActivity activity, String url);
    }
}
