package xblydxj.gank.home.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xblydxj.gank.R;
import xblydxj.gank.bean.Data;

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
    public static final int  PULLUP_LOAD_MORE=0;
    //正在加载中
    public static final int  LOADING_MORE=1;
    //上拉加载更多状态-默认为0
    private int load_more_status=0;


    private List<Data.ResultsBean> list = new ArrayList<>();
    private int preDay = 0;


    public RefreshRecyclerAdapter(List<Data.ResultsBean> data) {
        list = checkNotNull(data);
    }


    public interface OnItemClickListener{
        void OnItemClick(String url);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item, parent, false));
        } else {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_footer, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            final Data.ResultsBean result = list.get(position);
            ((ItemViewHolder)holder).Author.setText(result.getWho());
            ((ItemViewHolder)holder).Describe.setText(result.getDesc());
            //publishedAt : 2016-07-15T11:56:07.907Z
            String Date = result.getPublishedAt();
            int day = Integer.parseInt(Date.substring(8, 10));
            if (position == 0 || preDay != day) {
                ((ItemViewHolder)holder).CardData.setVisibility(View.VISIBLE);
                String date = Date.substring(0, 10);
                ((ItemViewHolder)holder).Date.setText(date);
            }
            if (mOnItemClickListener != null) {
                ((ItemViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.OnItemClick(result.getUrl());
                    }
                });
            }
            preDay = day;
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    footerViewHolder.mRecyclerFooter.setText("上拉加载....");
                    break;
                case LOADING_MORE:
                    footerViewHolder.mRecyclerFooter.setText("Loading....");
                    break;
            }
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


    public void addItem(List<Data.ResultsBean> newData) {
        newData.addAll(list);
        list.clear();
        list.addAll(newData);
        notifyDataSetChanged();
    }

    public void addMoreItem(List<Data.ResultsBean> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void changeStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.date)
        TextView Date;
        @Bind(R.id.date_card)
        CardView CardData;
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
