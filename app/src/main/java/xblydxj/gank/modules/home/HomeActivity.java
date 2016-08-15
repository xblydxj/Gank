package xblydxj.gank.modules.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xblydxj.gank.AppConfig;
import xblydxj.gank.R;
import xblydxj.gank.bean.PagerInfo;
import xblydxj.gank.modules.about.AboutActivity;
import xblydxj.gank.modules.home.fragment.AndroidFragment;
import xblydxj.gank.modules.home.fragment.FrontEndFragment;
import xblydxj.gank.modules.home.fragment.IOSFragment;
import xblydxj.gank.modules.home.fragment.MeizhiFragment;
import xblydxj.gank.modules.home.fragment.VideoFragment;
import xblydxj.gank.modules.home.presenter.AndroidPresenter;
import xblydxj.gank.modules.home.presenter.FrontEndPresenter;
import xblydxj.gank.modules.home.presenter.IOSPresenter;
import xblydxj.gank.modules.home.presenter.MeizhiPresenter;
import xblydxj.gank.modules.home.presenter.VideoPresenter;
import xblydxj.gank.modules.search.SearchActivity;

/**
 * Created by xblydxj
 * on 2016/7/16/016.
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
//    @Bind(R.id.home_search_edit)
//    EditText mHomeSearchEdit;
//    @Bind(R.id.home_search)
//    ImageView mHomeSearch;

    private List<PagerInfo> mFragments = new ArrayList<>();
    private AndroidFragment mAndroidFragment;
    private IOSFragment mIosFragment;
    private MeizhiFragment mMeizhiFragment;
    private FrontEndFragment mFrontEndFragment;
    private VideoFragment mVideoFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar_menu, menu);
        return true;
    }

    private void init() {
        initPresenter();
        initFragments();
        initViewPager();
        initListener();
    }


    private void initPresenter() {
        mAndroidFragment = AndroidFragment.getInstance();
        mIosFragment = IOSFragment.getInstance();
        mFrontEndFragment = FrontEndFragment.getInstance();
        mMeizhiFragment = new MeizhiFragment();
        mVideoFragment = VideoFragment.getInstance();
        new AndroidPresenter(mAndroidFragment);
        new IOSPresenter(mIosFragment);
        new FrontEndPresenter(mFrontEndFragment);
        new MeizhiPresenter(mMeizhiFragment);
        new VideoPresenter(mVideoFragment);
    }

    private void initListener() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Snackbar snackbar = Snackbar.make(view, "onClick", Snackbar.LENGTH_INDEFINITE);
                startActivity(new Intent(view.getContext(), AboutActivity.class));
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

    @Override
    protected void onStart() {
        super.onStart();
        initToolbar();
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
        mFragments.add(new PagerInfo(titles[0], mMeizhiFragment));
        mFragments.add(new PagerInfo(titles[1], mAndroidFragment));
        mFragments.add(new PagerInfo(titles[2], mIosFragment));
        mFragments.add(new PagerInfo(titles[3], mFrontEndFragment));
        mFragments.add(new PagerInfo(titles[4], mVideoFragment));
    }

    private void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        assert mToolbar != null;
        mToolbar.inflateMenu(R.menu.home_toolbar_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(AppConfig.sContext, SearchActivity.class));
                return true;
            }
        });
    }
}
