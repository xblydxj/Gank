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

        WebFragment webFragment = (WebFragment) getSupportFragmentManager().findFragmentById(R.id.web_frame_fragment);
        if (webFragment == null) {
            webFragment = WebFragment.newInstance(url, desc);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.web_frame_fragment, webFragment);
            transaction.commit();
        }
        new WebPresenter(url, desc, webFragment);
    }
}
