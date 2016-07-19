package xblydxj.gank.manager.uimanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.List;
import java.util.concurrent.Callable;

import butterknife.Bind;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xblydxj.gank.R;
import xblydxj.gank.config.AppConfig;

/**
 * Created by 46321 on 2016/7/18/018.
 */
public abstract class LoadStatus extends FrameLayout {
    @Bind(R.id.error_reconnect)
    Button mErrorReconnect;
    private View mLoadingView;
    private View mSuccessView;
    private View mErrorView;

    /***************************
     * 显示状态
     **************************/
    //加载状态
    public static final int STATUS_LOADING = 100;
    //成功状态
    public static final int STATUS_SUCCESS = 101;
    //失败状态
    public static final int STATUS_ERROR = 102;
    //当前状态
    public int currentStatus = STATUS_LOADING;


    public LoadStatus(Context context) {
        this(context, null);
    }

    public LoadStatus(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadStatus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLoadingPager();
    }

    protected void initLoadingPager() {
        if (mLoadingView == null) {
            mLoadingView = View.inflate(AppConfig.sContext, R.layout.status_loading, null);
        }
        addView(mLoadingView);
        if (mSuccessView == null) {
            mSuccessView = createSuccessView();
        }
        addView(mSuccessView);
        if (mErrorView == null) {
            mErrorView = View.inflate(AppConfig.sContext, R.layout.status_error, null);
            mErrorReconnect = (Button) mErrorView.findViewById(R.id.error_reconnect);
            mErrorReconnect.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    currentStatus = STATUS_LOADING;
                    updateView();
                    loadData();
                }
            });
        }

        addView(mErrorView);
        updateView();
        loadData();
    }

    public void loadData() {
        Observable.fromCallable(new Callable() {
            @Override
            public Object call() throws Exception {
                return loadSubData();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer() {
                    @Override
                    public void onCompleted() {
                        updateView();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        currentStatus = checkData(o);
                    }
                });
    }

    private int checkData(Object o) {
        if (o == null) {
            return STATUS_ERROR;
        } else {
            if (o instanceof List) {
                if (((List) o).size() <= 0) {
                    return STATUS_ERROR;
                }
            }
        }
        return STATUS_SUCCESS;
    }

    public void updateView() {
        mLoadingView.setVisibility(View.GONE);
        mSuccessView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        switch (currentStatus) {
            case STATUS_LOADING:
                mLoadingView.setVisibility(View.VISIBLE);
                break;
            case STATUS_SUCCESS:
                mSuccessView.setVisibility(View.VISIBLE);
                break;
            case STATUS_ERROR:
                mErrorView.setVisibility(View.VISIBLE);
                break;
        }
    }

    protected abstract Object loadSubData();

    protected abstract View createSuccessView();
}



















