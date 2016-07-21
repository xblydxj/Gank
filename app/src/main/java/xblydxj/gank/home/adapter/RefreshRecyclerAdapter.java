package xblydxj.gank.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xblydxj.gank.R;
import xblydxj.gank.bean.Data;
import xblydxj.gank.home.fragment.BaseFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 46321 on 2016/7/18/018.
 */
public class RefreshRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /*******
     * 普通类型item与底部item
     ********/
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //上拉加载更多状态-默认为0
    private int load_more_status = 0;


    private List<Data.ResultsBean> list = new ArrayList<>();



    public RefreshRecyclerAdapter(List<Data.ResultsBean> data) {
        list = checkNotNull(data);
    }


    public interface OnItemClickListener {
        void OnItemClick(String url);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item, parent, false));
        } else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_footer, parent, false));
        }

        Logger.d(viewType);
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < list.size()) {
            final Data.ResultsBean result = list.get(position);
            ((ItemViewHolder) holder).Author.setText(result.getWho());
            ((ItemViewHolder) holder).Describe.setText(result.getDesc());
            //publishedAt : 2016-07-15T11:56:07.907Z
            String Date = result.getPublishedAt();
            String day = Date.substring(0, 19).replace('T', ' ');
            ((ItemViewHolder) holder).Date.setText(day);
            if (mOnItemClickListener != null) {
                ((ItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.OnItemClick(result.getUrl());
                    }
                });
            }

        } else{
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.mRecyclerFooter.setText(R.string.loading);
            new BaseFragment().mPresenter.upPullLoad(list.size());
            //TODO
        }

    }

    @Override
    public int getItemCount() {
        //添加一项脚布局
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }


    public void changeStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.date)
        TextView Date;
        @Bind(R.id.author)
        TextView Author;
        @Bind(R.id.describe)
        TextView Describe;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.recycler_footer)
        TextView mRecyclerFooter;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
