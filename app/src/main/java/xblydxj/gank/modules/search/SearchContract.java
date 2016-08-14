package xblydxj.gank.modules.search;

import android.app.ProgressDialog;
import android.content.Intent;

import java.util.List;

import xblydxj.gank.BasePresenter;
import xblydxj.gank.BaseView;
import xblydxj.gank.bean.SearchResult;

/**
 * Created by xblydxj
 * on 2016/7/16/016.
 */
public class SearchContract {
    public interface View extends BaseView<Presenter>{

        void animate();

        void showSnack(String s);

        void updateView(List<SearchResult.ResultsBean> results, boolean b);

        void intentToWeb(Intent intent);
    }

    public interface Presenter extends BasePresenter{


        void onSearch(String search, String selectType, ProgressDialog dialog);

        void toWeb(String url, String desc);

        void loadMore(int size);
    }
}
