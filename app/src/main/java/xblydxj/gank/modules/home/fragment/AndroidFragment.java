package xblydxj.gank.modules.home.fragment;

import com.orhanobut.logger.Logger;

import java.util.List;

import xblydxj.gank.bean.Data;
import xblydxj.gank.modules.home.adapter.AndroidAdapter;
import xblydxj.gank.modules.home.adapter.BaseRecyclerAdapter;

/**
 * Created by xblydxj on 2016/7/16/016.
 */
public class AndroidFragment extends BaseFragment {
    public AndroidFragment() {}

    private static class AndroidFragmentHolder {
        static AndroidFragment instance = new AndroidFragment();
    }

    public static AndroidFragment getInstance() {
        Logger.d("androidFragment");
        return AndroidFragmentHolder.instance;
    }
    @Override
    public BaseRecyclerAdapter getTypeAdapter(List<Data.ResultsBean> list) {
        return new AndroidAdapter(list);
    }
}
