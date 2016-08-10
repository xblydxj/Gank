package xblydxj.gank.modules.home.fragment;

import com.orhanobut.logger.Logger;

import java.util.List;

import xblydxj.gank.bean.Data;
import xblydxj.gank.modules.home.adapter.FrontEndAdapter;
import xblydxj.gank.modules.home.adapter.BaseRecyclerAdapter;

/**
 * Created by 46321 on 2016/7/16/016.
 */
public class FrontEndFragment extends BaseFragment {
    public FrontEndFragment() {}

    private static class FrontEndFragmentHolder {
        static FrontEndFragment instance = new FrontEndFragment();
    }

    public static FrontEndFragment getInstance() {
        Logger.d("FrontEndFragment");
        return FrontEndFragmentHolder.instance;
    }
    @Override
    public BaseRecyclerAdapter getTypeAdapter(List<Data.ResultsBean> list) {
        return new FrontEndAdapter(list);
    }
}
