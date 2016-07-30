package xblydxj.gank.home.presenter;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import xblydxj.gank.AppConfig;
import xblydxj.gank.api.DataApi;
import xblydxj.gank.bean.Data;
import xblydxj.gank.db.Meizhi.Meizhi;
import xblydxj.gank.home.contract.MeizhiContract;
import xblydxj.gank.home.model.meizhiModel;
import xblydxj.gank.utils.NetWorkUtil;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 46321 on 2016/7/26/026.
 */
public class MeizhiPresenter implements MeizhiContract.Presenter {
    private final MeizhiContract.View mMeizhiView;
    private CompositeSubscription mSubscription;
    private DataApi mRetrofit = AppConfig.sRetrofit.create(DataApi.class);
    private List<Meizhi> mMeizhis;
    //加载状态
    public final int STATUS_LOADING = 100;
    //成功状态
    public final int STATUS_SUCCESS = 101;
    //失败状态
    public final int STATUS_ERROR = 102;
    private meizhiModel mMeizhiModel = new meizhiModel();
    private boolean mIsEmpty;


    public MeizhiPresenter(MeizhiContract.View meizhiView) {
        mMeizhiView = checkNotNull(meizhiView);
        mMeizhiView.setPresenter(this);
        mSubscription = new CompositeSubscription();
    }


    @Override
    public void updateData() {
        getRetrofitData(1);
    }

    @Override
    public void reconnect() {
        mMeizhiView.updateStatus(STATUS_LOADING);
        initialData(true);
    }

    @Override
    public void loadMore(int size) {
        getRetrofitData((size / 10) + 1);
    }


    @Override
    public void subscribe() {
        initialData(true);
    }

    private void initialData(boolean isNotRefresh) {
        if (isNotRefresh) {
            mMeizhiView.updateStatus(STATUS_LOADING);
        }
        getData(1);
    }

    private void getData(int page) {
        List<Meizhi> meizhisCache = mMeizhiModel.isCatchExist();
        if (NetWorkUtil.isNetWorkAvailable(AppConfig.sContext)) {
            if (!meizhisCache.isEmpty()) {
                mIsEmpty = false;
                mMeizhiView.updateAdapter(meizhisCache);
                mMeizhiView.updateStatus(STATUS_SUCCESS);
                mMeizhiView.stopRefreshing();
            } else {
                mIsEmpty = true;
            }
            mSubscription.clear();
            Subscription subscription = getRetrofitData(page);
            mSubscription.add(subscription);
        } else {
            if (!meizhisCache.isEmpty()) {
                mMeizhiView.updateAdapter(meizhisCache);
                mMeizhiView.updateStatus(STATUS_SUCCESS);
                mMeizhiView.stopRefreshing();
            } else {
                mMeizhiView.stopRefreshing();
                mMeizhiView.updateStatus(STATUS_ERROR);
            }
        }
    }


    private Subscription getRetrofitData(int page) {
        return mRetrofit.getNormal("福利", 10, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Data, Observable<Data.ResultsBean>>() {

                    @Override
                    public Observable<Data.ResultsBean> call(Data data) {
                        Logger.d("Meizhi:flatmap");
                        mMeizhis = new ArrayList<>();
                        return Observable.from(data.getResults());
                    }
                })
                .subscribe(new Subscriber<Data.ResultsBean>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("meizhi:complete" + mMeizhis.size());
                        getLastDBData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e, "meizhi，error");
                        if (mIsEmpty) {
                            mMeizhiView.updateStatus(STATUS_ERROR);
                        }
                        mMeizhiView.showSnack();
                        mMeizhiView.stopRefreshing();
                    }

                    @Override
                    public void onNext(Data.ResultsBean resultsBean) {
                        Logger.d("meizhi:next");
                        Meizhi meizhi = new Meizhi();
                        meizhi.setUrl(resultsBean.getUrl());
                        meizhi.setTime(resultsBean.getPublishedAt()
                                .substring(0, 10)
                                .replace("T", " "));
                        mMeizhis.add(meizhi);
                    }
                });
    }

    private void getLastDBData() {
        Observable.just(mMeizhis)
                .map(new Func1<List<Meizhi>, List<Meizhi>>() {
                    @Override
                    public List<Meizhi> call(List<Meizhi> meizhis) {
                        mMeizhiModel.putMeizhisToDB(meizhis);
                        return mMeizhiModel.isCatchExist();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Meizhi>>() {
                    @Override
                    public void call(List<Meizhi> meizhis) {
                        mMeizhiView.updateAdapter(meizhis);
                        mMeizhiView.stopRefreshing();
                        mMeizhiView.updateStatus(STATUS_SUCCESS);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable, "meizhiDB");
                    }
                });
    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }
}
