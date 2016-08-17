package xblydxj.gank.modules.home;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.orhanobut.logger.Logger;

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
import xblydxj.gank.utils.SnackUtils;
import xblydxj.gank.widget.MaterialInterpolator;

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
    private ActivityOptionsCompat mCompat;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
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
        initTransition();
        initListener();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initTransition() {
        Explode explode = new Explode();
        explode.setMode(Explode.MODE_IN);
        explode.setDuration(600);
        explode.setInterpolator(new MaterialInterpolator());
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);


//        Slide slide = new Slide();
//        slide.setDuration(600);
//        slide.setSlideEdge(Gravity.END);
//        slide.setInterpolator(new MaterialInterpolator());
//        getWindow().setExitTransition(slide);
//        getWindow().setEnterTransition(slide);

//        Fade fade = new Fade();
//        fade.setDuration(1000);
//        fade.setInterpolator(new MaterialInterpolator());
//        getWindow().setExitTransition(fade);
//        getWindow().setEnterTransition(fade);

        mCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this);
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
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                mCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this);
                startActivity(new Intent(HomeActivity.this, AboutActivity.class), mCompat.toBundle());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initToolbar();
    }

    private long time = 0;

    @Override
    public void onBackPressed() {
        Logger.d("backPressed");
        if (System.currentTimeMillis() - time < 3000) {
            super.onBackPressed();
        } else {
            SnackUtils.showSnackShort(mFab, "再点一次退出应用~");
            time = System.currentTimeMillis();
        }
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
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                Explode explode = new Explode();
//                explode.setDuration(600);
//                explode.setInterpolator(new MaterialInterpolator());
//                getWindow().setExitTransition(explode);
//                getWindow().setEnterTransition(explode);
                mCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this);
                startActivity(new Intent(AppConfig.sContext, SearchActivity.class), mCompat.toBundle());
                return true;
            }
        });
    }
}
