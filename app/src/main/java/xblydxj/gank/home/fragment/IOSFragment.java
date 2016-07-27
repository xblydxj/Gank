package xblydxj.gank.home.fragment;

import com.orhanobut.logger.Logger;

import java.util.List;

import xblydxj.gank.db.normalData.dataCatch;
import xblydxj.gank.home.adapter.IOSAdapter;
import xblydxj.gank.home.adapter.NormalRecyclerAdapter;

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
    public NormalRecyclerAdapter getTypeAdapter(List<dataCatch> list) {
        return new IOSAdapter(list);
    }
}
