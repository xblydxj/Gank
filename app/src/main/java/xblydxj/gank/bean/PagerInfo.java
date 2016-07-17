package xblydxj.gank.bean;

import android.support.v4.app.Fragment;

/**
 * Created by 46321 on 2016/7/17/017.
 */
public class PagerInfo {
    public String title;
    public Fragment mFragment;

    public PagerInfo(String title, Fragment fragment) {
        this.title = title;
        mFragment = fragment;
    }
}
