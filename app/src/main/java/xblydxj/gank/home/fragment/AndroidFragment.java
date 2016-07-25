package xblydxj.gank.home.fragment;

import com.orhanobut.logger.Logger;

import java.util.List;

import xblydxj.gank.db.dataCatch;
import xblydxj.gank.home.adapter.AndroidAdapter;
import xblydxj.gank.home.adapter.NormalRecyclerAdapter;

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
    public NormalRecyclerAdapter getTypeAdapter(List<dataCatch> list) {
        return new AndroidAdapter(list);
    }
}
