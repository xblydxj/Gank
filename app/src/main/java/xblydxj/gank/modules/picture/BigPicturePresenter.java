package xblydxj.gank.modules.picture;

import android.support.v4.app.FragmentActivity;

import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xblydxj.
 * on 2016/8/10/010
 */
public class BigPicturePresenter implements BigPictureContract.Presenter{

    private BigPictureContract.View mView;
    private CompositeSubscription mSubscription;

    public BigPicturePresenter(String url, BigPictureContract.View baseView) {
        mView = checkNotNull(baseView);
        mView.setPresenter(this);
        mSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscription.clear();
    }

    @Override
    public void savePicture() {

    }

    @Override
    public void showShare(FragmentActivity activity) {

    }
}
