package xblydxj.gank.modules.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import xblydxj.gank.bean.PagerInfo;

/**
 * Created by 46321 on 2016/7/17/017.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private List<PagerInfo> tabFragments = new ArrayList<>();

    public void setTabFragments(List<PagerInfo> fragments) {
        tabFragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position).mFragment;
    }

    @Override
    public int getCount() {
        return tabFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabFragments.get(position).title;
    }
}
