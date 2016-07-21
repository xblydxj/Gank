package xblydxj.gank.home.fragment;

/**
 * Created by xblydxj on 2016/7/16/016.
 */
public class IOSFragment extends BaseFragment{
    public IOSFragment() {}

    private static class IOSFragmentHolder {
        static IOSFragment instance = new IOSFragment();
    }

    public static IOSFragment getInstance() {
        return IOSFragmentHolder.instance;
    }
}
