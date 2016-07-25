package xblydxj.gank.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xblydxj.gank.R;
import xblydxj.gank.bean.Meizhi;

/**
 * Created by 46321 on 2016/7/25/025.
 */
public class MeizhiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Meizhi> mMeizhis;
    private Context mContext;

    public MeizhiAdapter(Context context, List<Meizhi> list) {
        this.mContext = context;
        this.mMeizhis = list;
    }

    public interface OnMeizhiClickListener {
        void meizhiClickListener(View view, int position);

        void meizhiLongClickListener(View view, int position);
    }

    private OnMeizhiClickListener mListener;

    public void setOnMeizhiClickListener(OnMeizhiClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new meizhiViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_meizhi, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(mContext).load(mMeizhis.get(position).imageUrl).centerCrop()
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(((meizhiViewHolder) holder).mImageView);
        ((meizhiViewHolder)holder).time.setText(mMeizhis.get(position).time);
    }

    @Override
    public int getItemCount() {
        return 0;
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
}
