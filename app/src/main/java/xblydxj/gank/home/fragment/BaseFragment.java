package xblydxj.gank.home.fragment;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xblydxj.gank.R;
import xblydxj.gank.bean.Data;
import xblydxj.gank.home.adapter.RefreshRecyclerAdapter;
import xblydxj.gank.home.contract.BaseContract;
import xblydxj.gank.manager.uimanager.LoadStatus;
import xblydxj.gank.utils.SnackUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 46321 on 2016/7/16/016.
 */
public class BaseFragment extends Fragment implements BaseContract.View {

    private View mContentView;
    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    @Bind(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    private List<Data.ResultsBean> list = new ArrayList<>();

    @OnClick(R.id.error_reconnect)
    void reconnect() {
        list.clear();
        mPresenter.reconnect();
    }

    public RefreshRecyclerAdapter mAdapter = new RefreshRecyclerAdapter(list);
    public BaseContract.Presenter mPresenter;

    @Override
    public void onResume() {
        super.onResume();
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
//        mLoadStatus = new LoadStatus(getContext()) {};
//        mContentView = View.inflate(AppConfig.sContext, R.layout.fragment_normal, null);
        mContentView = new LoadStatus(getContext());
        ButterKnife.bind(this, mContentView);

        mRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.md_red_400_color_code),
                ContextCompat.getColor(getActivity(), R.color.md_yellow_400_color_code),
                ContextCompat.getColor(getActivity(), R.color.md_green_400_color_code)
        );
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        initListener();
        mRecycler.setAdapter(mAdapter);
        return mContentView;
    }

    private void initListener() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                mPresenter.updateData();
            }
        });
        mAdapter.setOnItemClickListener(new RefreshRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(String url) {
                mPresenter.toWeb(url);
            }
        });
    }


    @Override
    public void setPresenter(BaseContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void showErrorSnack() {
        SnackUtils.showSnackShort(mRecycler, "请求失败....");
    }

    @Override
    public void updateAdapter(Data data) {
        list.addAll(data.getResults());
        mAdapter.notifyDataSetChanged();
    }
}
