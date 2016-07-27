package xblydxj.gank.home.fragment;

import android.content.Intent;
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
import android.widget.Button;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xblydxj.gank.AppConfig;
import xblydxj.gank.R;
import xblydxj.gank.db.normalData.dataCatch;
import xblydxj.gank.home.adapter.NormalRecyclerAdapter;
import xblydxj.gank.home.contract.BaseContract;
import xblydxj.gank.manager.uimanager.LoadStatus;
import xblydxj.gank.utils.SnackUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 46321 on 2016/7/16/016.
 */
public abstract class BaseFragment extends Fragment implements BaseContract.View {

    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    @Bind(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @Bind(R.id.error_reconnect)
    Button reconnect;

    private List<dataCatch> list = new ArrayList<>();

    public NormalRecyclerAdapter mAdapter = getTypeAdapter(list);

    public abstract NormalRecyclerAdapter getTypeAdapter(List<dataCatch> list);

    public BaseContract.Presenter mPresenter;
    public LoadStatus mLoadStatus;
    private View mContentView;

    @Override
    public void onResume() {
        super.onResume();
        if (list.size() == 0) {
            mPresenter.subscribe();
        }
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        mContentView = View.inflate(AppConfig.sContext, R.layout.fragment_normal, null);
        mLoadStatus = new LoadStatus(getContext());
        mLoadStatus.addView(mContentView, 0);
        Logger.d("count:"+mLoadStatus.getChildCount());
        ButterKnife.bind(this, mLoadStatus);
        mRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.md_red_400_color_code),
                ContextCompat.getColor(getActivity(), R.color.md_yellow_400_color_code),
                ContextCompat.getColor(getActivity(), R.color.md_green_400_color_code)
        );
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        initListener();
        mRecycler.setAdapter(mAdapter);
        return mLoadStatus;
    }

    private void initListener() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                mPresenter.updateData();
            }
        });
        mAdapter.setOnItemClickListener(new NormalRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(String url,String desc) {
                mPresenter.toWeb(url,desc);
            }
        });
        mAdapter.setOnUpPull(new NormalRecyclerAdapter.OnUpPull() {
            @Override
            public void upPullLoad(int size) {
                mPresenter.upPullLoad(size);
            }
        });
        reconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                Logger.d("onclick");
                mPresenter.reconnect();
            }
        });
    }


    @Override
    public void setPresenter(BaseContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void updateAdapter(List<dataCatch> data) {
        list.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void stopRefreshing() {
        mRefresh.setRefreshing(false);
    }

    @Override
    public void updateStatus(int status) {
        mLoadStatus.updateView(status);
    }

    @Override
    public void intentToWeb(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showSnack() {
        SnackUtils.showSnackShort(mRefresh,"刷新失败~");
    }
}
