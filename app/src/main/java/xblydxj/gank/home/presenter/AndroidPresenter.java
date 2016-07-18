package xblydxj.gank.home.presenter;

import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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
    private String mAnddroid;

    private CompositeSubscription mSubscription;

    private DataApi mIosRetrofit;

    public AndroidPresenter(AndroidContract.View AndroidFragmentView, String android) {
        mAndroidFragmentView = checkNotNull(AndroidFragmentView);
        mAndroidFragmentView.setPresenter(this);
        mAnddroid = android;
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void toWeb(String url) {
        Logger.d("toWeb");
    }

    @Override
    public List<Data.ResultsBean> getData() {
        //TODO
        return null;
    }




    @Override
    public void updateData(int androidData, RefreshRecyclerAdapter AndroidAdapter) {
        AndroidAdapter.changeStatus(RefreshRecyclerAdapter.LOADING_MORE);
        mIosRetrofit = AppConfig.sRetrofit.create(DataApi.class);
        int page = androidData/10+1;
        requestData(page);
    }

    private Subscription requestData(int page) {
        Subscription subscription = mIosRetrofit.getNormal(mAnddroid, 10, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Data>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e, "AndroidPresenter,updateData");
                    }

                    @Override
                    public void onNext(Data data) {
                        mAndroidFragmentView.updateAdapter(data);
                    }
                });
        return subscription;
    }

    @Override
    public void subscribe() {
        Subscription subscription = requestData(1);
        mSubscription.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }
}
