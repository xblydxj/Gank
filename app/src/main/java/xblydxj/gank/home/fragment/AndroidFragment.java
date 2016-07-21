package xblydxj.gank.home.fragment;

/**
 * Created by xblydxj on 2016/7/16/016.
 */
public class AndroidFragment extends BaseFragment{
    public AndroidFragment() {}

    private static class AndroidFragmentHolder {
        static AndroidFragment instance = new AndroidFragment();
    }

    public static AndroidFragment getInstance() {
        return AndroidFragmentHolder.instance;
    }
}
