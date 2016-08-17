package xblydxj.gank.modules.home.presenter;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import xblydxj.gank.AppConfig;
import xblydxj.gank.api.DataApi;
import xblydxj.gank.bean.Data;
import xblydxj.gank.modules.home.contract.MeizhiContract;
import xblydxj.gank.modules.picture.BigPictureActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xblydxj
 * on 2016/7/26/026.
 */
public class MeizhiPresenter implements MeizhiContract.Presenter {
    private final MeizhiContract.View mMeizhiView;
    private CompositeSubscription mSubscription;
    private DataApi mRetrofit = AppConfig.sRetrofit.create(DataApi.class);
    private List<Data.ResultsBean> mMeizhis = new ArrayList<>();
    private Bitmap mBitmap;
    private static final String IMAGE_URL = "image";

    //加载状态
    public final int STATUS_LOADING = 100;
    //成功状态
    public final int STATUS_SUCCESS = 101;
    //失败状态
    public final int STATUS_ERROR = 102;
    //    private meizhiModel mMeizhiModel = new meizhiModel();
    private boolean mIsEmpty = true;


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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void toPhotoView(FragmentActivity activity, String url,ActivityOptionsCompat compat) {
        Intent intent = new Intent(activity, BigPictureActivity.class);
        intent.putExtra(IMAGE_URL, url);
        activity.startActivity(intent, compat.toBundle());
    }


    @Override
    public void subscribe() {
        initialData(true);
    }

    private void initialData(boolean isNotRefresh) {
        if (isNotRefresh) {
            mMeizhiView.updateStatus(STATUS_LOADING);
        }
        getRetrofitData(1);
    }


    private void getRetrofitData(final int page) {
        Subscription subscription = mRetrofit.getNormal("福利", 10, page)
                .flatMap(new Func1<Data, Observable<Data.ResultsBean>>() {
                    @Override
                    public Observable<Data.ResultsBean> call(Data data) {
                        Logger.d("Meizhi:flatmap");
                        mMeizhis.clear();
                        return Observable.from(data.getResults());
                    }
                })
                .map(new Func1<Data.ResultsBean, Data.ResultsBean>() {
                    @Override
                    public Data.ResultsBean call(Data.ResultsBean resultsBean) {
                        try {
                            mBitmap = Glide.with(AppConfig.sContext).load(resultsBean.getUrl())
                                    .asBitmap().into(-1, -1).get();
                        } catch (Exception e) {
                            Logger.e(e, "getBitmap");
                        }
                        return resultsBean;
                    }
                })
                .flatMap(new Func1<Data.ResultsBean, Observable<Data.ResultsBean>>() {
                    @Override
                    public Observable<Data.ResultsBean> call(Data.ResultsBean resultsBean) {
                        return Observable.zip(Observable.just(resultsBean),
                                Observable.just(mBitmap), new Func2<Data.ResultsBean, Bitmap, Data.ResultsBean>() {
                                    @Override
                                    public Data.ResultsBean call(Data.ResultsBean resultsBean, Bitmap bitmap) {
                                        int width = bitmap.getWidth();
                                        int height = bitmap.getHeight();
                                        resultsBean.setBitmap(bitmap);
                                        resultsBean.setBitmapWidth(width);
                                        resultsBean.setBitmapHeight(height);
                                        return resultsBean;
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Data.ResultsBean>() {
                    @Override
                    public void onCompleted() {
                        if (page == 1) {
                            mMeizhiView.cleanMeizhis();
                        }
                        mMeizhiView.updateAdapter(mMeizhis);
                        mMeizhiView.stopRefreshing();
                        mMeizhiView.updateStatus(STATUS_SUCCESS);
                        Logger.d("meizhi:complete" + mMeizhis.size());
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
                        mIsEmpty = false;
                        resultsBean.setPublishedAt(resultsBean.getPublishedAt()
                                .substring(0, 10)
                                .replace("T", " "));
                        Logger.d("url:" + resultsBean.getUrl());
                        mMeizhis.add(resultsBean);
                    }
                });
        mSubscription.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }
}
