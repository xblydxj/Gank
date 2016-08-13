package xblydxj.gank.modules.web;

import android.content.Context;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import xblydxj.gank.utils.SnackUtils;

/**
 * Created by xblydxj
 * on 2016/7/16/016.
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
    public void showShare(Context context) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(desc);
        oks.setUrl(url);
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                mWebView.showSnack("分享成功~");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                mWebView.showSnack("发生了一些问题，分享失败了~");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                mWebView.showSnack("分享失败~");
            }
        });
        oks.setComment("gank.io倾情分享");
        oks.show(context);
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
