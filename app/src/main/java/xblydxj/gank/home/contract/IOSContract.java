package xblydxj.gank.home.contract;

import java.util.List;

import xblydxj.gank.base.BasePresenter;
import xblydxj.gank.base.BaseView;
import xblydxj.gank.bean.Data;
import xblydxj.gank.home.adapter.RefreshRecyclerAdapter;

/**
 * Created by 46321 on 2016/7/17/017.
 */
public interface IOSContract {
    interface View extends BaseView<Presenter> {

        void updateAdapter(Data data);
    }

    interface Presenter extends BasePresenter {
        void toWeb(String url);

        List<Data.ResultsBean> getData();

        void updateData(int iosData, RefreshRecyclerAdapter IOSAdapter);
    }
}
