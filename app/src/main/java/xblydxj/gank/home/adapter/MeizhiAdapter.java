package xblydxj.gank.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xblydxj.gank.R;
import xblydxj.gank.db.Meizhi.Meizhi;
import xblydxj.gank.utils.Utils;

/**
 * Created by 46321 on 2016/7/25/025.
 */
public class MeizhiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int IMAGE_WIDTH = Utils.getDisplayWidth() / 2;

    private List<Meizhi> mMeizhis;
    private Context mContext;

    public MeizhiAdapter(Context context, List<Meizhi> list) {
        this.mContext = context;
        this.mMeizhis = list;
        Logger.d("list:" + list.size());
    }

    public interface OnMeizhiClickListener {
        void meizhiClickListener(View view, int position);
    }

    private OnMeizhiClickListener mListener;

    public void setOnMeizhiClickListener(OnMeizhiClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new meizhiViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item_meizhi, parent, false));
        } else if (viewType == TYPE_FOOTER) {
            return new LoadMoreViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_footer, parent, false));
        }
        Logger.d("viewtype:" + viewType);
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            Glide.with(mContext).load(mMeizhis.get(position).getUrl())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .fitCenter()
                    .crossFade()
                    .into(((meizhiViewHolder) holder).mImageView);

            ((meizhiViewHolder) holder).time.setText(mMeizhis.get(position).getTime());
            if (mListener != null) {
                ((meizhiViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO
                        Logger.d("meizhiClick" + position);
                    }
                });
            }
        } else {
            Logger.d("111:" + position);
            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;
            loadMoreViewHolder.loadMore.setText(R.string.loading);
            if (mOnLoadMore != null) {
                mOnLoadMore.loadMore(mMeizhis.size());
            }
        }
    }

    //上拉加载回调
    public interface OnLoadMore {
        void loadMore(int size);
    }

    private OnLoadMore mOnLoadMore;

    public void setOnLoadMore(OnLoadMore onLoadMore) {
        mOnLoadMore = onLoadMore;
    }


    @Override
    public int getItemCount() {
        //添加一项脚布局
        return mMeizhis.size() == 0 ? 0 : mMeizhis.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public class meizhiViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.meizhi)
        ImageView mImageView;
        @Bind(R.id.time)
        TextView time;

        public meizhiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.recycler_footer)
        TextView loadMore;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
