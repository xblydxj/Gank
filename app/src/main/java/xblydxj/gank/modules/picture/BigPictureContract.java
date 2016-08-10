package xblydxj.gank.modules.picture;

import android.support.v4.app.FragmentActivity;

import xblydxj.gank.BasePresenter;
import xblydxj.gank.BaseView;

/**
 * Created by xblydxj.
 * on 2016/8/10/010
 */
public class BigPictureContract {
    interface View extends BaseView<Presenter>{

    }
    interface Presenter extends BasePresenter{

        void savePicture();

        void showShare(FragmentActivity activity);
    }
}
