package xblydxj.gank.home.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import xblydxj.gank.AppConfig;
import xblydxj.gank.api.DataApi;
import xblydxj.gank.bean.Data;
import xblydxj.gank.home.contract.BaseContract;
import xblydxj.gank.home.model.baseModel;
import xblydxj.gank.utils.NetWorkUtil;
import xblydxj.gank.web.WebActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 46321 on 2016/7/21/021.
 */
public abstract class BaseFragmentPresenter implements BaseContract.Presenter {
    private final BaseContract.View mBaseView;
    private CompositeSubscription mSubscription;
    private DataApi mRetrofit = AppConfig.sRetrofit.create(DataApi.class);
    private String mType;
    //加载状态
    public final int STATUS_LOADING = 100;
    //成功状态
    public final int STATUS_SUCCESS = 101;
    //失败状态
    public final int STATUS_ERROR = 102;

    private static final String URL = "URL";
    private static final String DESC = "DESC";

    private baseModel mBaseModel = new baseModel();
    private boolean mIsEmpty;


    public BaseFragmentPresenter(BaseContract.View baseView, String type) {
        mBaseView = checkNotNull(baseView);
        mBaseView.setPresenter(this);
        mSubscription = new CompositeSubscription();
        mType = type;
    }

    @Override
    public void toWeb(String url, String desc) {
        Logger.d("toWeb" + url);
        Intent intent = new Intent();
        intent.putExtra(URL, url);
        intent.putExtra(DESC, desc);
        intent.setClass(AppConfig.sContext, WebActivity.class);
        mBaseView.intentToWeb(intent);
    }

    @Override
    public void subscribe() {
        initialData(true);
    }

    private void initialData(boolean isNotRefresh) {
        if (isNotRefresh) {
            mBaseView.updateStatus(STATUS_LOADING);
        }
        getData(1);
    }

    private void getData(int page) {
        List<dataCatch> catchDatas = mBaseModel.isCatchExist(mType);
        if (NetWorkUtil.isNetWorkAvailable(AppConfig.sContext)) {
            Logger.d("1.1");
            if (!catchDatas.isEmpty()) {
                mIsEmpty = false;
                Logger.d("1.2");
                mBaseView.updateAdapter(catchDatas);
                mBaseView.updateStatus(STATUS_SUCCESS);
                mBaseView.stopRefreshing();
            } else {
                mIsEmpty = true;
            }
            Logger.d("1.3");
            mSubscription.clear();
            Subscription subscription = getRetrofitData(page);
            mSubscription.add(subscription);
        } else {
            if (!catchDatas.isEmpty()) {
                mBaseView.updateAdapter(catchDatas);
                mBaseView.updateStatus(STATUS_SUCCESS);
                mBaseView.stopRefreshing();
            } else {
                mBaseView.stopRefreshing();
                mBaseView.updateStatus(STATUS_ERROR);
            }
        }
    }

    private Subscription getRetrofitData(final int page) {
        return mRetrofit.getNormal(mType, 10, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Data>() {
                    @Override
                    public void onCompleted() {
                        mBaseView.updateStatus(STATUS_SUCCESS);
                        mBaseView.stopRefreshing();
                        Logger.d("success");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mBaseView.stopRefreshing();
                        if (mIsEmpty) {
                            mBaseView.updateStatus(STATUS_ERROR);
                        }
                        mBaseView.showSnack();
                        Logger.e(e, "error");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Data data) {
                        mBaseView.updateAdapter(list);
                        if (page == 1) {
                            mBaseModel.putListToDB(list, mType);
                        } else {
                            mBaseModel.putListToDBNotDelete(list, mType);
                        }
                    }
                })new Subscriber<Data.ResultsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Data.ResultsBean resultsBean) {

                    }
                });
        new Subscriber<Data>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Data data) {

                    }
                });
    }

    @NonNull
    private List<dataCatch> toDataCatch(Data data) {
        List<dataCatch> list = new ArrayList<>();
        List<Data.ResultsBean> results = data.getResults();
        for (Data.ResultsBean result : results) {
            dataCatch dataCatch = new dataCatch();
            dataCatch.setAuthor(result.getWho());
            dataCatch.setDesc(result.getDesc());
            dataCatch.setTime(result.getPublishedAt());
            dataCatch.setType(mType);
            dataCatch.setUrl(result.getUrl());
            Logger.d("url" + result.getUrl());
            list.add(dataCatch);
        }
        return list;
    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }

    @Override
    public void updateData() {
        getRetrofitData(1);
    }

    @Override
    public void reconnect() {
        mBaseView.updateStatus(STATUS_LOADING);
        initialData(true);
    }

    @Override
    public void upPullLoad(int listSize) {
        getRetrofitData((listSize / 10) + 1);
    }
}
