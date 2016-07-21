package xblydxj.gank.home.fragment;

import com.orhanobut.logger.Logger;

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
}
