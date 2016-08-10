package xblydxj.gank.modules.home.fragment;

import com.orhanobut.logger.Logger;

import java.util.List;

import xblydxj.gank.bean.Data;
import xblydxj.gank.modules.home.adapter.IOSAdapter;
import xblydxj.gank.modules.home.adapter.BaseRecyclerAdapter;

/**
 * Created by xblydxj on 2016/7/16/016.
 */
public class IOSFragment extends BaseFragment{
    public IOSFragment() {}

    private static class IOSFragmentHolder {
        static IOSFragment instance = new IOSFragment();
    }

    public static IOSFragment getInstance() {
        Logger.d("IOSFragment");
        return IOSFragmentHolder.instance;
    }
    @Override
    public BaseRecyclerAdapter getTypeAdapter(List<Data.ResultsBean> list) {
        return new IOSAdapter(list);
    }
}
