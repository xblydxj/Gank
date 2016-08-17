package xblydxj.gank.modules.home.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
import xblydxj.gank.AppConfig;
import xblydxj.gank.R;
import xblydxj.gank.bean.SearchResult;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xblydxj
 * on 2016/7/18/018.
 */
public class BaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Drawable mDrawable_android;
    private Drawable mDrawable_ios;
    private Drawable mDrawable_前端;
    private Drawable mDrawable_normal;
    private Drawable mDrawable_video;
    private Drawable mDrawable_meizhi;
    private Drawable mDrawable_sources;

    private List<SearchResult.ResultsBean> list = new ArrayList<>();


    public BaseRecyclerAdapter(List<SearchResult.ResultsBean> data) {
        list = checkNotNull(data);
    }

    //条目点击回调
    public interface OnItemClickListener {
        void OnItemClick(View view, TextView describe, String url, String desc, Drawable drawable);
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM && list.size() != 0) {
            final SearchResult.ResultsBean resultsBean = list.get(position);
            if (mDrawable_android == null) {
                mDrawable_android = ContextCompat
                        .getDrawable(AppConfig.sContext, R.drawable.item_ic_android);
                mDrawable_android.setBounds(0, 0, mDrawable_android.getMinimumWidth(), mDrawable_android
                        .getMinimumHeight());
            }
            if (mDrawable_ios == null) {
                mDrawable_ios = ContextCompat
                        .getDrawable(AppConfig.sContext, R.mipmap.ic_apple);
                mDrawable_ios.setBounds(0, 0, mDrawable_ios.getMinimumWidth(), mDrawable_ios.getMinimumHeight());
            }
            if (mDrawable_前端 == null) {
                mDrawable_前端 = ContextCompat
                        .getDrawable(AppConfig.sContext, R.mipmap.ic_front_end);
                mDrawable_前端.setBounds(0, 0, mDrawable_前端.getMinimumWidth(), mDrawable_前端.getMinimumHeight());
            }
            if (mDrawable_normal == null) {
                mDrawable_normal = ContextCompat
                        .getDrawable(AppConfig.sContext, R.drawable.ic_normal);
                mDrawable_normal.setBounds(0, 0, mDrawable_normal.getMinimumWidth(), mDrawable_normal
                        .getMinimumHeight());
            }
            if (mDrawable_video == null) {
                mDrawable_video = ContextCompat
                        .getDrawable(AppConfig.sContext, R.drawable.ic_player);
                mDrawable_video.setBounds(0, 0, mDrawable_video.getMinimumWidth(), mDrawable_video
                        .getMinimumHeight());
            }
            if (mDrawable_meizhi == null) {
                mDrawable_meizhi = ContextCompat
                        .getDrawable(AppConfig.sContext, R.drawable.ic_girl);
                mDrawable_meizhi.setBounds(0, 0, mDrawable_meizhi.getMinimumWidth(), mDrawable_meizhi
                        .getMinimumHeight());
            }
            if (mDrawable_sources == null) {
                mDrawable_sources = ContextCompat
                        .getDrawable(AppConfig.sContext, R.drawable.ic_add_sources);
                mDrawable_sources.setBounds(0, 0, mDrawable_sources.getMinimumWidth(), mDrawable_sources
                        .getMinimumHeight());
            }


            switch (resultsBean.getType()) {
                case "Android":
                    ((ItemViewHolder) holder).Describe.setCompoundDrawables(mDrawable_android, null, null, null);
                    break;
                case "iOS":
                    ((ItemViewHolder) holder).Describe.setCompoundDrawables(mDrawable_ios, null, null, null);
                    break;
                case "前端":
                    ((ItemViewHolder) holder).Describe.setCompoundDrawables(mDrawable_前端, null, null, null);
                    break;
                case "休息视频":
                    ((ItemViewHolder) holder).Describe.setCompoundDrawables(mDrawable_video, null, null, null);
                    break;
                case "福利":
                    ((ItemViewHolder) holder).Describe.setCompoundDrawables(mDrawable_meizhi, null, null, null);
                    break;
                case "拓展资源":
                    ((ItemViewHolder) holder).Describe.setCompoundDrawables(mDrawable_sources, null, null, null);
                    break;
                default:
                    ((ItemViewHolder) holder).Describe.setCompoundDrawables(mDrawable_normal, null, null, null);
                    break;
            }

            if (resultsBean.getWho() == null) {
                ((ItemViewHolder) holder).Author.setVisibility(View.INVISIBLE);
            } else {
                ((ItemViewHolder) holder).Author.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).Author.setText(resultsBean.getWho());
            }
            ((ItemViewHolder) holder).Describe.setText(resultsBean.getDesc());
            //publishedAt : 2016-07-15 11:56:07.907Z
            String Date = resultsBean.getPublishedAt();
            String day = Date.substring(0, 19).replace('T', ' ');
            ((ItemViewHolder) holder).Date.setText(day);
            if (mOnItemClickListener != null) {
                ((ItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Drawable drawable = ((ItemViewHolder) holder).Describe.getCompoundDrawables()[0];
                        TextView describe = ((ItemViewHolder) holder).Describe;
                        mOnItemClickListener.OnItemClick(view, describe,resultsBean.getUrl(),
                                resultsBean.getDesc(), drawable);
                    }
                });
            }
        } else if (list.size() >= 10) {
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

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.recycler_footer)
        public TextView mRecyclerFooter;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
