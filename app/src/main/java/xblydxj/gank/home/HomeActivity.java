package xblydxj.gank.home;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Bind;
import xblydxj.gank.R;
import xblydxj.gank.bean.PagerInfo;
import xblydxj.gank.config.AppConfig;
import xblydxj.gank.home.fragment.AndroidFragment;
import xblydxj.gank.home.fragment.IOSFragment;
import xblydxj.gank.home.fragment.MeizhiFragment;
import xblydxj.gank.home.fragment.OthersFragment;
import xblydxj.gank.home.fragment.VedioFragment;
import xblydxj.gank.home.presenter.AndroidPresenter;
import xblydxj.gank.home.presenter.IOSPresenter;

/**
 * Created by xblydxj on 2016/7/16/016.
 */
public class HomeActivity extends AppCompatActivity {
    @Bind(R.id.home_view_pager)
    ViewPager mViewPager;
    @Bind(R.id.home_tool_bar)
    Toolbar mToolbar;
    @Bind(R.id.home_tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.home_fab)
    FloatingActionButton mFab;
    private List<PagerInfo> mFragments = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initToolbar();
        initFragments();
        initViewPager();
        initListener();
        initPresenter();
    }

    private void initPresenter() {
        new IOSPresenter(new IOSFragment(), "IOS");
        new AndroidPresenter(new AndroidFragment(), "Android");
    }

    private void initListener() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Snackbar snackbar = Snackbar.make(view, "onClick", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        });
    }

    private void initViewPager() {
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        homePagerAdapter.setTabFragments(mFragments);
        mViewPager.setAdapter(homePagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(
                getColors(R.color.md_blue_grey_200_color_code),
                getColors(R.color.md_white_color_code));
    }

    public int getColors(int resId) {
        return AppConfig.sContext.getResources().getColor(resId);
    }

    private void initFragments() {
        String[] titles = AppConfig.sContext.getResources().getStringArray(R.array.tab_names);
        mFragments.add(new PagerInfo(titles[0], new AndroidFragment()));
        mFragments.add(new PagerInfo(titles[1], new IOSFragment()));
        mFragments.add(new PagerInfo(titles[2], new MeizhiFragment()));
        mFragments.add(new PagerInfo(titles[3], new OthersFragment()));
        mFragments.add(new PagerInfo(titles[4], new VedioFragment()));
    }

    private void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }
}
