package xblydxj.gank.modules.picture;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;

import xblydxj.gank.BasePresenter;
import xblydxj.gank.BaseView;

/**
 * Created by xblydxj.
 * on 2016/8/10/010
 */
public class BigPictureContract {
    interface View extends BaseView<Presenter>{

        void showSnack(String s);

        void showDialog();

        void dismissDialog();
    }
    interface Presenter extends BasePresenter{

        void savePicture(Bitmap bitmap);

        void showShare(FragmentActivity activity);
    }
}
