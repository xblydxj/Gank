package xblydxj.gank.modules.search;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;

import butterknife.ButterKnife;
import xblydxj.gank.R;
import xblydxj.gank.widget.MaterialInterpolator;

/**
 * Created by xblydxj.
 * on 2016/8/12/012
 */
public class SearchActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initFragment();
        initTransition();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initTransition() {
        Explode explode = new Explode();
        explode.setDuration(600);
        explode.setInterpolator(new MaterialInterpolator());

        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation
                (SearchActivity.this);
    }

    private void initFragment() {
        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id
                .search_fragment);
        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.search_fragment, searchFragment);
            transaction.commit();
        }
        new SearchPresenter(searchFragment);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        finishAfterTransition();
        super.onBackPressed();
    }
}
