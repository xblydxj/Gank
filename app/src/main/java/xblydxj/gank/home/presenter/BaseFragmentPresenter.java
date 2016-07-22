package xblydxj.gank.home.presenter;

import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import xblydxj.gank.api.DataApi;
import xblydxj.gank.bean.Data;
import xblydxj.gank.config.AppConfig;
import xblydxj.gank.home.contract.BaseContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 46321 on 2016/7/21/021.
 */
public abstract class BaseFragmentPresenter implements BaseContract.Presenter {
    private final BaseContract.View mBaseView;
    private CompositeSubscription mSubscription;
    private DataApi mRetrofit;
    private String mType;
    //加载状态
    public final int STATUS_LOADING = 100;
    //成功状态
    public final int STATUS_SUCCESS = 101;
    //失败状态
    public final int STATUS_ERROR = 102;
    public BaseFragmentPresenter(BaseContract.View baseView,String type) {
        mBaseView = checkNotNull(baseView);
        mBaseView.setPresenter(this);
        mSubscription = new CompositeSubscription();
        mType = type;
    }

    @Override
    public void toWeb(String url) {
        Logger.d("toWeb" + url);
    }

    @Override
    public void subscribe() {
        mRetrofit = AppConfig.sRetrofit.create(DataApi.class);
        initialData(true);
    }

    private void initialData(boolean isNotRefresh) {
        if(isNotRefresh) {
            mBaseView.updateStatus(STATUS_LOADING);
        }
        getData(1);
    }

    private void getData(int page) {
        mSubscription.clear();
        Subscription subscription = mRetrofit.getNormal(mType, 20, page)
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
                        //TODO 去本地拿数据，如果本地没有更新状态为error
                        Logger.e(e, "error");
                        mBaseView.updateStatus(STATUS_ERROR);
                        mBaseView.stopRefreshing();
                        e.printStackTrace();
                        mBaseView.showErrorSnack();
                    }

                    @Override
                    public void onNext(Data data) {
                        mBaseView.updateAdapter(data);
                    }
                });
        mSubscription.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }

    @Override
    public void updateData() {
        initialData(false);

    }

    @Override
    public void reconnect() {
        initialData(true);
    }

    @Override
    public void upPullLoad(int listSize) {
        getData((listSize/10)+1);
    }
}
