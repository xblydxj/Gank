package xblydxj.gank.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xblydxj.gank.R;
import xblydxj.gank.base.BaseFragment;
import xblydxj.gank.bean.Data;
import xblydxj.gank.config.AppConfig;
import xblydxj.gank.home.adapter.RefreshRecyclerAdapter;
import xblydxj.gank.home.contract.AndroidContract;
import xblydxj.gank.manager.uimanager.LoadStatus;
import xblydxj.gank.utils.SnackUtils;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by xblydxj on 2016/7/16/016.
 */
public class AndroidFragment extends BaseFragment implements AndroidContract.View, RefreshRecyclerAdapter
        .OnItemClickListener {
    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    @Bind(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    private AndroidContract.Presenter mPresenter;
    private RefreshRecyclerAdapter mAndroidAdapter;
    private List<Data.ResultsBean> AndroidData = new ArrayList<>();

    //加载状态
    public static final int STATUS_LOADING = 100;
    //成功状态
    public static final int STATUS_SUCCESS = 101;
    //失败状态
    public static final int STATUS_ERROR = 102;


    public AndroidFragment() {}

    private static class AndroidFragmentHolder {
        static AndroidFragment instance = new AndroidFragment();
    }

    public static AndroidFragment getInstance() {
        return AndroidFragmentHolder.instance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoadStatus = new LoadStatus(getContext()) {};
        View mContentView =  View.inflate(AppConfig.sContext, R.layout.fragment_normal,
                null);
        ButterKnife.bind(this, mContentView);

        mRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.md_red_400_color_code),
                ContextCompat.getColor(getActivity(), R.color.md_yellow_400_color_code),
                ContextCompat.getColor(getActivity(), R.color.md_green_400_color_code)
        );

        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.updateData(AndroidData.size(),mAndroidAdapter);
                mRefresh.setRefreshing(false);
            }
        });
        int success = mPresenter.isSuccess(AndroidData);

        if (success == STATUS_SUCCESS) {
            mLoadStatus.currentStatus = STATUS_SUCCESS;
            mLoadStatus.updateView();
            return mContentView;
        }else{
            mLoadStatus.currentStatus = STATUS_ERROR;
            mLoadStatus.updateView();
            return mLoadStatus;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // FIXME: 2016/7/19/019
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void setPresenter(AndroidContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void OnItemClick(String url) {
        mPresenter.toWeb(url);
    }


    @Override
    public void updateAdapter(Data data) {
        AndroidData.addAll(data.getResults());
        mAndroidAdapter = new RefreshRecyclerAdapter(AndroidData);
        mRecycler.setAdapter(mAndroidAdapter);
        mAndroidAdapter.setOnItemClickListener(this);
    }

    @Override
    public void showErrorSnack() {
        SnackUtils.showSnackShort(mRecycler, "请求失败....");
    }
}
