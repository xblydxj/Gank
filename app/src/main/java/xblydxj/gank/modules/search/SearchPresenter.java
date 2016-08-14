package xblydxj.gank.modules.search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import xblydxj.gank.AppConfig;
import xblydxj.gank.api.SearchApi;
import xblydxj.gank.bean.SearchResult;
import xblydxj.gank.modules.web.WebActivity;

/**
 * Created by xblydxj
 * on 2016/7/16/016.
 */
public class SearchPresenter implements SearchContract.Presenter {
    private final SearchContract.View mSearchView;
    private CompositeSubscription mSubscriptions;
    private SearchApi mRetrofit = AppConfig.sRetrofit.create(SearchApi.class);

    private static final String URL = "URL";
    private static final String DESC = "DESC";
    private String mSearch;
    private String mSelectType;
    private String type = "Android";
    private boolean mNoData = false;

    public SearchPresenter(SearchContract.View searchView) {
        mSearchView = searchView;
        mSubscriptions = new CompositeSubscription();
        searchView.setPresenter(this);
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void onSearch(String search, String selectType, ProgressDialog dialog) {
        if (TextUtils.isEmpty(search)) {
            mSearchView.animate();
            mSearchView.showSnack("搜索内容不能为空~");
            return;
        }
        if (TextUtils.isEmpty(selectType)) {
            mSearchView.animate();
            mSearchView.showSnack("请选择搜索类型~");
            return;
        }
        mSearch = search;
        mSelectType = selectType;
        dialog.setMessage("正在加载~");
        dialog.show();
        mSubscriptions.add(getResult(1,dialog));
    }

    @Override
    public void toWeb(String url, String desc) {
        Intent intent = new Intent();
        intent.putExtra(URL, url);
        intent.putExtra(DESC, desc);
        intent.setClass(AppConfig.sContext, WebActivity.class);
        mSearchView.intentToWeb(intent);
    }

    @Override
    public void loadMore(int size) {
        if(mNoData){
            return;
        }
        getResult((size / 10) + 1, null);
    }

    private Subscription getResult(final int page, final ProgressDialog dialog) {
        return mRetrofit.search(mSearch, mSelectType, 10, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResult>() {

                    @Override
                    public void onCompleted() {
                        type = mSelectType;
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        mSearchView.showSnack("加载失败~");
                    }

                    @Override
                    public void onNext(SearchResult searchResult) {
                        List<SearchResult.ResultsBean> results = searchResult.getResults();
                        if (results.size() == 0 && page > 1) {
                            mNoData = true;
                            mSearchView.showSnack("没有更多数据了~");
                        } else {
                            mSearchView.updateView(results, type.equals(mSelectType));
                        }
                    }
                });
    }
}
