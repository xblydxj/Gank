package xblydxj.gank.manager.uimanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;

import xblydxj.gank.R;
import xblydxj.gank.config.AppConfig;

/**
 * Created by 46321 on 2016/7/18/018.
 */
public class LoadStatus extends FrameLayout {

    //    @Bind(R.id.error)
    private View mErrorView;
    //    @Bind(R.id.loading)
    private View mLoadingView;
    /***************************
     * 显示状态
     **************************/
    //加载状态
    public final int STATUS_LOADING = 100;
    //成功状态
    public final int STATUS_SUCCESS = 101;
    //失败状态
    public final int STATUS_ERROR = 102;
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
        init();
    }

    private void init() {
        mErrorView = View.inflate(AppConfig.sContext, R.layout.status_loading, this);
        mLoadingView = View.inflate(AppConfig.sContext, R.layout.status_error, this);
        addView(mLoadingView);
        addView(mErrorView);
        Logger.d("111  " + this.getChildCount());
//        ButterKnife.bind(this);
//        mErrorView = (LinearLayout) this.findViewById(R.id.error);
//        mLoadingView = (FrameLayout) this.findViewById(R.id.loading);
    }

    public void updateView() {
        switch (currentStatus) {
            case STATUS_ERROR:
                Logger.d("error");
                mErrorView.setVisibility(VISIBLE);
                mLoadingView.setVisibility(GONE);
                break;
            case STATUS_LOADING:
                Logger.d("loading");
                mErrorView.setVisibility(GONE);
                mLoadingView.setVisibility(VISIBLE);
                break;
            case STATUS_SUCCESS:
                Logger.d("success");
                mErrorView.setVisibility(GONE);
                mLoadingView.setVisibility(GONE);
                break;
        }
    }
}



















