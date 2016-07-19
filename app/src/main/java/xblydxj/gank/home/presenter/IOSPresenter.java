package xblydxj.gank.home.presenter;

import com.orhanobut.logger.Logger;

import java.util.List;

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
import xblydxj.gank.home.contract.IOSContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 46321 on 2016/7/18/018.
 */
public class IOSPresenter implements IOSContract.Presenter {

    private final IOSContract.View mIOSFragmentView;
    private String mIOS;

    private CompositeSubscription mSubscription;

    private DataApi mIOSRetrofit = AppConfig.sRetrofit.create(DataApi.class);

    //加载状态
    public static final int STATUS_LOADING = 100;
    //成功状态
    public static final int STATUS_SUCCESS = 101;
    //失败状态
    public static final int STATUS_ERROR = 102;


    public IOSPresenter(IOSContract.View IOSFragmentView, String IOS) {
        mIOSFragmentView = checkNotNull(IOSFragmentView);
        mIOSFragmentView.setPresenter(this);
        mIOS = IOS;
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void toWeb(String url) {
        Logger.d("toWeb"+url);
    }

    @Override
    public void updateData(int IOSData, RefreshRecyclerAdapter IOSAdapter) {
        IOSAdapter.changeStatus(RefreshRecyclerAdapter.LOADING_MORE);
        int page = IOSData/10;
        requestData(page);
    }

    @Override
    public int isSuccess(List<Data.ResultsBean> IOSData) {
        if (IOSData.size() == 0) {
            return STATUS_ERROR;
        }else{
            return STATUS_SUCCESS;
        }
    }

    private Subscription requestData(int page) {
        return mIOSRetrofit.getNormal(mIOS, 10, page)
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
                        mIOSFragmentView.showErrorSnack();
                        Logger.e(e, "IOSPresenter,updateData");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Data data) {
                        mIOSFragmentView.updateAdapter(data);
                    }
                });
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
