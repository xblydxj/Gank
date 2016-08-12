package xblydxj.gank.modules.web;

import android.content.Context;
import android.webkit.WebView;

import xblydxj.gank.BasePresenter;
import xblydxj.gank.BaseView;

/**
 * Created by xblydxj on 2016/7/16/016.
 */
public class WebContract {
    public interface View extends BaseView<Presenter>{
        void showTitle(String desc);

        void showWeb(String url);

        void updateProgress(int progress);

        void showSnack(String str);
    }

    public interface Presenter extends BasePresenter{

        void showShare(Context context);

        void goForward(WebView view);

        void goBack(WebView view);

        void updateProgress(int progress);
    }
}
