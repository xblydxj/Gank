package xblydxj.gank.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import xblydxj.gank.R;
import xblydxj.gank.config.AppConfig;
import xblydxj.gank.manager.uimanager.LoadStatus;

/**
 * Created by 46321 on 2016/7/16/016.
 */
public abstract class BaseFragment extends Fragment {
    public LoadStatus mLoadStatus;
    private View mContentView;
    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    @Bind(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        mLoadStatus = new LoadStatus(getContext()) {};
        mContentView = View.inflate(AppConfig.sContext, R.layout.fragment_normal,
                null);
        ButterKnife.bind(this, mContentView);

        mRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.md_red_400_color_code),
                ContextCompat.getColor(getActivity(), R.color.md_yellow_400_color_code),
                ContextCompat.getColor(getActivity(), R.color.md_green_400_color_code)
        );

        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mContentView;
    }
}
