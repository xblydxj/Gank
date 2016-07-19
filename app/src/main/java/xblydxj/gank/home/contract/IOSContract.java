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

        void showErrorSnack();
    }

    interface Presenter extends BasePresenter {
        void toWeb(String url);

        void updateData(int IOSData, RefreshRecyclerAdapter IOSAdapter);

        int isSuccess(List<Data.ResultsBean> IOSData);
    }
}
