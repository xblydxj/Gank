package xblydxj.gank.modules.web;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.webkit.WebView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import xblydxj.gank.AppConfig;
import xblydxj.gank.R;
import xblydxj.gank.utils.ShareUtil;
import xblydxj.gank.utils.SnackUtils;

/**
 * Created by xblydxj
 * on 2016/7/16/016.
 */
public class WebPresenter implements WebContract.Presenter {
    private final WebContract.View mWebView;
    private String url;
    private String desc;
    private String type;
    private Drawable mDrawable;
    private CompositeSubscription mSubscriptions;
    private int mPreProgress = 0;

    public WebPresenter(String url, String desc, String type,
                        WebContract.View webView) {
        this.url = url;
        this.desc = desc;
        this.type = type;
        mWebView = webView;
        mSubscriptions = new CompositeSubscription();
        webView.setPresenter(this);
    }


    @Override
    public void showShare(Context context) {
        ShareUtil.shareWeb(context, desc, url);

//
//        ShareSDK.initSDK(context);
//        OnekeyShare oks = new OnekeyShare();
//        oks.disableSSOWhenAuthorize();
//        oks.setTitle(desc);
//        oks.setTitleUrl(url);
//        oks.setText(desc);
//        oks.setUrl(url);
//        oks.setCallback(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                mWebView.showSnack("分享成功~");
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                mWebView.showSnack("发生了一些问题，分享失败了~");
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                mWebView.showSnack("分享失败~");
//            }
//        });
//        oks.setComment("gank.io倾情分享");
//        oks.show(context);
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
        content.add(type);
        Logger.d("type:" + type);
        Subscription subscription = Observable.just(content)
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        switch (strings.get(2)) {
                            case "Android":
                                mDrawable = ContextCompat.getDrawable(AppConfig.sContext, R.drawable.item_ic_android);
                                break;
                            case "iOS":
                                mDrawable = ContextCompat.getDrawable(AppConfig.sContext, R.mipmap.ic_apple);
                                break;
                            case "前端":
                                mDrawable = ContextCompat.getDrawable(AppConfig.sContext, R.mipmap.ic_front_end);
                                break;
                            case "休息视频":
                                mDrawable = ContextCompat.getDrawable(AppConfig.sContext, R.drawable.ic_player);
                                break;
                            case "福利":
                                mDrawable = ContextCompat.getDrawable(AppConfig.sContext, R.drawable.ic_girl);
                                break;
                            case "拓展资源":
                                mDrawable = ContextCompat.getDrawable(AppConfig.sContext, R.drawable.ic_add_sources);
                                break;
                            default:
                                mDrawable = ContextCompat.getDrawable(AppConfig.sContext, R.drawable.ic_normal);
                                break;
                        }
                        mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable
                                .getMinimumHeight());
                        mWebView.showWeb(strings.get(0));
                        mWebView.showTitle(strings.get(1), mDrawable);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
