package xblydxj.gank.home.contract;

import xblydxj.gank.base.BasePresenter;
import xblydxj.gank.base.BaseView;

/**
 * Created by 46321 on 2016/7/17/017.
 */
public interface AndroidContract {
    interface View extends BaseView<Presenter> {

        void showError();

    }

    interface Presenter extends BasePresenter {
        void toWeb();

        void refreshAndroid();
    }

}
