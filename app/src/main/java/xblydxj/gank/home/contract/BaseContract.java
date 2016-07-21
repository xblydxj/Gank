package xblydxj.gank.home.contract;

import xblydxj.gank.base.BasePresenter;
import xblydxj.gank.base.BaseView;
import xblydxj.gank.bean.Data;

/**
 * Created by 46321 on 2016/7/21/021.
 */
public class BaseContract {
    public interface View extends BaseView<Presenter> {
        void showErrorSnack();

        void updateAdapter(Data data);
    }

    public interface Presenter extends BasePresenter {

        void updateData();

        void reconnect();

        void upPullLoad(int listSize);

        void toWeb(String url);
    }
}
