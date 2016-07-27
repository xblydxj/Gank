package xblydxj.gank.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import xblydxj.gank.R;

/**
 * Created by 46321 on 2016/7/16/016.
 */
public class WebActivity extends AppCompatActivity {

    private static final String URL = "URL";
    private static final String DESC = "DESC";
    private WebFragment mWebFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(URL);
        String desc = intent.getStringExtra(DESC);

        mWebFragment = (WebFragment) getSupportFragmentManager().findFragmentById(R.id.web_frame_fragment);
        if (mWebFragment == null) {
            mWebFragment = WebFragment.newInstance(url, desc);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.web_frame_fragment, mWebFragment);
            transaction.commit();
        }
        new WebPresenter(url, desc, mWebFragment);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        mWebFragment = null;
        finish();
    }
}
