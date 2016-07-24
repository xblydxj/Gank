package xblydxj.gank.web;

import android.webkit.WebView;

import xblydxj.gank.BasePresenter;
import xblydxj.gank.BaseView;

/**
 * Created by 46321 on 2016/7/16/016.
 */
public class WebContract {
    public interface View extends BaseView<Presenter>{
        void showTitle(String desc);

        void showWeb(String url);

        void updateProgress(int progress);

    }

    public interface Presenter extends BasePresenter{

        void share();

        void goForward(WebView view);

        void goBack(WebView view);
    }
}
