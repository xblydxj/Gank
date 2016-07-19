package xblydxj.gank.home.fragment;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xblydxj.gank.R;
import xblydxj.gank.base.BaseFragment;
import xblydxj.gank.bean.Data;
import xblydxj.gank.config.AppConfig;
import xblydxj.gank.home.adapter.RefreshRecyclerAdapter;
import xblydxj.gank.home.contract.IOSContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 46321 on 2016/7/16/016.
 */
public class IOSFragment extends BaseFragment implements IOSContract.View, RefreshRecyclerAdapter.OnItemClickListener {
    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    @Bind(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    private IOSContract.Presenter mPresenter;

    private View mContentView;
    private RefreshRecyclerAdapter mIOSAdapter;
    private List<Data.ResultsBean> IOSData = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private int mLastVisibleItemPosition;

    @Override
    protected View loadFragmentView() {
        mContentView = View.inflate(AppConfig.sContext, R.layout.fragment_normal,
                null);
        ButterKnife.bind(this, mContentView);

        mRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.md_red_400_color_code),
                ContextCompat.getColor(getActivity(), R.color.md_yellow_400_color_code),
                ContextCompat.getColor(getActivity(), R.color.md_green_400_color_code)
        );
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mLoadStatus.loadData();
                mRefresh.setRefreshing(false);
            }
        });
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLinearLayoutManager);



        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisibleItemPosition + 1 == mIOSAdapter
                        .getItemCount()) {
                    mPresenter.updateData(IOSData.size(),mIOSAdapter);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
        return mContentView;
    }

    @Override
    protected List<Data.ResultsBean> loadFragmentData() {
//        IOSData = mPresenter.getData();
        //TODO
        return IOSData;
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
    public void setPresenter(IOSContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void OnItemClick(String url) {
        mPresenter.toWeb(url);
    }








    @Override
    public void updateAdapter(Data data) {
        IOSData.addAll(data.getResults());
        mIOSAdapter = new RefreshRecyclerAdapter(IOSData);
        mIOSAdapter.addMoreItem(data.getResults());
        mIOSAdapter.changeStatus(RefreshRecyclerAdapter.PULLUP_LOAD_MORE);
        mRecycler.setAdapter(mIOSAdapter);
        mIOSAdapter.setOnItemClickListener(this);
    }
}
