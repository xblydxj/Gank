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
import xblydxj.gank.db.dataCatch;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by 46321 on 2016/7/18/018.
 */
public abstract class NormalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /*******
     * 普通类型item与底部item
     ********/
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;


    private List<dataCatch> list = new ArrayList<>();


    public NormalRecyclerAdapter(List<dataCatch> data) {
        list = checkNotNull(data);
    }


    //条目点击回调
    public interface OnItemClickListener {
        void OnItemClick(String url, String desc);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return getTypeLayout(parent);
        } else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_footer, parent, false));
        }
        Logger.d(viewType);
        return null;
    }

    public abstract RecyclerView.ViewHolder getTypeLayout(ViewGroup parent);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM && list.size() != 0) {
            Logger.d("item:" + position);
            final dataCatch result = list.get(position);
            if (result.getAuthor() == null) {
                ((ItemViewHolder) holder).Author.setVisibility(View.INVISIBLE);
            } else {
                ((ItemViewHolder) holder).Author.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).Author.setText(result.getAuthor());
            }
            ((ItemViewHolder) holder).Describe.setText(result.getDesc());
            //publishedAt : 2016-07-15 11:56:07.907Z
            String Date = result.getTime();
            String day = Date.substring(0, 19).replace('T', ' ');
            ((ItemViewHolder) holder).Date.setText(day);
            if (mOnItemClickListener != null) {
                ((ItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.OnItemClick(result.getUrl(), result.getDesc());
                    }
                });
            }
        } else {
            Logger.d("111:" + position);
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.mRecyclerFooter.setText(R.string.loading);
            if (mOnUpPull != null) {
                mOnUpPull.upPullLoad(list.size());
            }
        }
    }

    //上拉加载回调
    public interface OnUpPull {
        void upPullLoad(int size);
    }

    private OnUpPull mOnUpPull;

    public void setOnUpPull(OnUpPull onUpPull) {
        mOnUpPull = onUpPull;
    }


    @Override
    public int getItemCount() {
        //添加一项脚布局
        return list.size() == 0 ? 0 : list.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
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
