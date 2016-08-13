package xblydxj.gank.modules.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xblydxj.gank.AppConfig;
import xblydxj.gank.R;
import xblydxj.gank.bean.Data;
import xblydxj.gank.manager.uimanager.LoadStatus;
import xblydxj.gank.modules.home.contract.MeizhiContract;
import xblydxj.gank.utils.SnackUtils;
import xblydxj.gank.widget.MeizhiImage;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xblydxj
 * on 2016/7/16/016.
 */
public class MeizhiFragment extends Fragment implements MeizhiContract.View {

    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    @Bind(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @Bind(R.id.error_reconnect)
    Button reconnect;

    private List<Data.ResultsBean> meizhis = new ArrayList<>();
    public MeizhiAdapter mAdapter = new MeizhiAdapter(AppConfig.sContext, meizhis);

    public MeizhiContract.Presenter mPresenter;
    public LoadStatus mLoadStatus;

    @Override
    public void onResume() {
        super.onResume();
        if (meizhis.size() == 0) {
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
        View contentView = View.inflate(AppConfig.sContext, R.layout.fragment_normal, null);
        mLoadStatus = new LoadStatus(getContext());
        mLoadStatus.addView(contentView, 0);
        ButterKnife.bind(this, mLoadStatus);
        mRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.md_red_400_color_code),
                ContextCompat.getColor(getActivity(), R.color.md_yellow_400_color_code),
                ContextCompat.getColor(getActivity(), R.color.md_green_400_color_code)
        );
        mRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        mRecycler.setItemAnimator(new NoAlphaRecyclerViewAnimator());
//        mRecycler.setItemAnimator(null);
        initListener();
        mRecycler.setAdapter(mAdapter);
        return mLoadStatus;
    }

    private void initListener() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                meizhis.clear();
                mPresenter.updateData();
            }
        });
        reconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meizhis.clear();
                mPresenter.reconnect();
            }
        });
    }


    @Override
    public void setPresenter(MeizhiContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void updateAdapter(List<Data.ResultsBean> data) {
        meizhis.addAll(data);
        if (meizhis.size() == 10) {
            mAdapter.notifyDataSetChanged();
        } else {
            int index = meizhis.size() - data.size();
            for (int i = 0; i <= data.size(); i++) {
                mAdapter.notifyItemChanged(index + i);
                Logger.d("notify:" + (index + i));
            }
        }
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
    public void showSnack() {
        SnackUtils.showSnackShort(mRefresh, "刷新失败~");
    }


    class MeizhiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Data.ResultsBean> mMeizhis;
        private Context mContext;

        public MeizhiAdapter(Context context, List<Data.ResultsBean> list) {
            this.mContext = context;
            this.mMeizhis = list;
            Logger.d("list:" + list.size());
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new meizhiViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item_meizhi, parent, false));
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            Data.ResultsBean resultsBean = meizhis.get(position);
            int width = resultsBean.getBitmapWidth();
            int height = resultsBean.getBitmapHeight();
            ((meizhiViewHolder) holder).mImageView.setOriginal(width, height);
            Glide.with(mContext).load(meizhis.get(position).getUrl())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(((meizhiViewHolder) holder).mImageView);
            ((meizhiViewHolder) holder).time.setText(meizhis.get(position).getPublishedAt());
            Logger.d("position:" + position + "  " + mMeizhis.size());
            if (position == (mMeizhis.size() - 1)) {
                mPresenter.loadMore(position + 1);
            }
        }


        @Override
        public int getItemCount() {
            return mMeizhis.size();
        }

        public class meizhiViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.meizhi)
            MeizhiImage mImageView;
            @Bind(R.id.time)
            TextView time;

            @OnClick(R.id.meizhi)
            public void onClick(View view) {
                mPresenter.toPhotoView(getActivity(), meizhis.get(getAdapterPosition()).getUrl());
            }

            public meizhiViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
