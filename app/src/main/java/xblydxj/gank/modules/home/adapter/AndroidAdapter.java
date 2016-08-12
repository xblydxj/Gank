package xblydxj.gank.modules.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import xblydxj.gank.R;
import xblydxj.gank.bean.Data;


/**
 * Created by xblydxj on 2016/7/25/025.
 */
public class AndroidAdapter extends BaseRecyclerAdapter {
    public AndroidAdapter(List<Data.ResultsBean> data) {
        super(data);
    }

    @Override
    public RecyclerView.ViewHolder getTypeLayout(ViewGroup parent) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_android, parent, false));
    }
}
