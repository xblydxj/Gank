package xblydxj.gank.web;

import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import xblydxj.gank.utils.SnackUtils;

/**
 * Created by 46321 on 2016/7/16/016.
 */
public class WebPresenter implements WebContract.Presenter {
    private final WebContract.View mWebView;
    private String url;
    private String desc;
    private CompositeSubscription mSubscriptions;
    private int mPreProgress = 0;

    public WebPresenter(String url, String desc,
                        WebContract.View webView) {
        this.url = url;
        this.desc = desc;
        mWebView = webView;
        mSubscriptions = new CompositeSubscription();
        webView.setPresenter(this);
    }

    @Override
    public void share() {
        //TODO share
    }

    @Override
    public void goForward(WebView view) {
        if (view.canGoForward()) {
            view.goForward();
        } else {
            SnackUtils.showSnackShort(view, "已经是最后一页了~！");
        }
    }

    @Override
    public void goBack(WebView view) {
        if (view.canGoBack()) {
            view.goBack();
        } else {
            SnackUtils.showSnackShort(view, "这就是第一页~！");
        }
    }

    @Override
    public void updateProgress(int progress) {
        if (mPreProgress != progress) {
            mWebView.updateProgress(progress);
        }
        mPreProgress = progress;
    }

    @Override
    public void subscribe() {
        mSubscriptions.clear();
        List<String> content = new ArrayList<>();
        content.add(url);
        content.add(desc);
        Subscription subscription = Observable.just(content)
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        mWebView.showWeb(strings.get(0));
                        mWebView.showTitle(strings.get(1));
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
