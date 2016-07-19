package xblydxj.gank.manager.uimanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.Bind;
import xblydxj.gank.R;
import xblydxj.gank.config.AppConfig;

/**
 * Created by 46321 on 2016/7/18/018.
 */
public abstract class LoadStatus extends FrameLayout{
    @Bind(R.id.error_reconnect)
    Button mErrorReconnect;
    private View mLoadingView;
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
        if (mErrorView == null) {
            mErrorView = View.inflate(AppConfig.sContext, R.layout.status_error, null);
            mErrorReconnect = (Button) mErrorView.findViewById(R.id.error_reconnect);
            mErrorReconnect.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentStatus = STATUS_LOADING;
                    updateView();
                }
            });
        }
        addView(mErrorView);
        updateView();
    }


    public void updateView() {
        if (currentStatus == STATUS_ERROR) {
            mErrorView.setVisibility(VISIBLE);
            mLoadingView.setVisibility(GONE);
        } else if (currentStatus == STATUS_LOADING) {
            mErrorView.setVisibility(GONE);
            mLoadingView.setVisibility(VISIBLE);
        } else if (currentStatus == STATUS_SUCCESS) {
            mErrorView.setVisibility(GONE);
            mLoadingView.setVisibility(GONE);
        }
    }
}



















