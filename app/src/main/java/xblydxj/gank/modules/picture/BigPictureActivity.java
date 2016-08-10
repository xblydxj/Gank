package xblydxj.gank.modules.picture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

import xblydxj.gank.R;

/**
 * Created by xblydxj on 2016/8/10/010.
 *
 */

public class BigPictureActivity extends AppCompatActivity{
    private static final String IMAGE_URL = "image";
    private BigPictureFragment mPictureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigpicture);
        initFragment();
    }

    private void initFragment() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(IMAGE_URL);
        Logger.d("picture url:"+url);
        mPictureFragment = (BigPictureFragment) getSupportFragmentManager().findFragmentById(R.id
                .picture_fragment);
        if (mPictureFragment == null) {
            mPictureFragment = BigPictureFragment.newInstance(url);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.picture_fragment, mPictureFragment);
            transaction.commit();
        }
        new BigPicturePresenter(url,mPictureFragment);
    }
}

