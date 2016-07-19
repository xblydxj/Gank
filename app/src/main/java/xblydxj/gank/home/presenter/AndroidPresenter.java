package xblydxj.gank.home.presenter;

import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import xblydxj.gank.api.DataApi;
import xblydxj.gank.bean.Data;
import xblydxj.gank.config.AppConfig;
import xblydxj.gank.home.adapter.RefreshRecyclerAdapter;
import xblydxj.gank.home.contract.AndroidContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 46321 on 2016/7/18/018.
 */
public class AndroidPresenter implements AndroidContract.Presenter {

    private final AndroidContract.View mAndroidFragmentView;
    private String mAndroid;

    private CompositeSubscription mSubscription;

    private DataApi mAndroidRetrofit = AppConfig.sRetrofit.create(DataApi.class);


    public AndroidPresenter(AndroidContract.View AndroidFragmentView, String android) {
        mAndroidFragmentView = checkNotNull(AndroidFragmentView);
        mAndroidFragmentView.setPresenter(this);
        mAndroid = android;
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void toWeb(String url) {
        Logger.d("toWeb"+url);
    }

    @Override
    public void getData() {
        Subscription subscription = requestData(1);
        mSubscription.add(subscription);
    }




    @Override
    public void updateData(int androidData, RefreshRecyclerAdapter AndroidAdapter) {
        AndroidAdapter.changeStatus(RefreshRecyclerAdapter.LOADING_MORE);
        int page = androidData/10;
        requestData(page);
    }

    private Subscription requestData(int page) {
        Logger.d("requestData");
        return mAndroidRetrofit.getNormal(mAndroid, 10, page)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Data, Data>() {
                    @Override
                    public Data call(Data data) {
                        return data;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Data>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAndroidFragmentView.showErrorSnack();
                        Logger.e(e, "AndroidPresenter,updateData");
                    }

                    @Override
                    public void onNext(Data data) {
                        mAndroidFragmentView.updateAdapter(data);
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }
}
