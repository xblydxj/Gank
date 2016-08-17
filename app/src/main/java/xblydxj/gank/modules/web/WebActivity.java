package xblydxj.gank.modules.web;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.Window;

import butterknife.ButterKnife;
import xblydxj.gank.R;
import xblydxj.gank.widget.MaterialInterpolator;

/**
 * Created by xblydxj
 * on 2016/7/16/016.
 */
public class WebActivity extends AppCompatActivity {

    private static final String URL = "URL";
    private static final String DESC = "DESC";
    private static final String TYPE = "TYPE";
    private WebFragment mWebFragment;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
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
                (WebActivity.this);
    }

    private void initFragment() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(URL);
        String desc = intent.getStringExtra(DESC);
        String type = intent.getStringExtra(TYPE);
        mWebFragment = (WebFragment) getSupportFragmentManager().findFragmentById(R.id.web_frame_fragment);
        if (mWebFragment == null) {
            mWebFragment = WebFragment.newInstance(url, desc, type);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.web_frame_fragment, mWebFragment);
            transaction.commit();
        }
        new WebPresenter(url, desc, type, mWebFragment);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        mWebFragment.onBack();
        finishAfterTransition();
        super.onBackPressed();
    }
}
