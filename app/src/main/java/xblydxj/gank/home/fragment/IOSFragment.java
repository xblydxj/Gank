package xblydxj.gank.home.fragment;

import com.orhanobut.logger.Logger;

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
}
