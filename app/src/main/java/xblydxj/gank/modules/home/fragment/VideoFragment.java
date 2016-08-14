package xblydxj.gank.modules.home.fragment;

import com.orhanobut.logger.Logger;

import java.util.List;

import xblydxj.gank.bean.Data;
import xblydxj.gank.modules.home.adapter.BaseRecyclerAdapter;

/**
 * Created by xblydxj
 * on 2016/7/16/016.
 */
public class VideoFragment extends BaseFragment {
    public VideoFragment() {}

    private static class VideoFragmentHolder {
        static VideoFragment instance = new VideoFragment();
    }

    public static VideoFragment getInstance() {
        Logger.d("androidFragment");
        return VideoFragmentHolder.instance;
    }

    @Override
    public BaseRecyclerAdapter getTypeAdapter(List<Data.ResultsBean> list) {
        return new BaseRecyclerAdapter(list);
    }
}
