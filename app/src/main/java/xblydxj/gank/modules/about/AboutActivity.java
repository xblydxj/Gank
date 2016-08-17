package xblydxj.gank.modules.about;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import butterknife.Bind;
import butterknife.ButterKnife;
import xblydxj.gank.AppConfig;
import xblydxj.gank.R;
import xblydxj.gank.widget.MaterialInterpolator;

/**
 * Created by xblydxj
 * on 2016/7/16/016.
 */
public class AboutActivity extends AppCompatActivity {
    @Bind(R.id.about_toolbar)
    Toolbar mAboutToolbar;
    @Bind(R.id.about_photo)
    ImageView mAboutPhoto;
    @Bind(R.id.rxjava)
    TextView mRxjava;
    @Bind(R.id.rxandroid)
    TextView mRxandroid;
    @Bind(R.id.retrofit)
    TextView mRetrofit;
    @Bind(R.id.glide)
    TextView mGlide;
    @Bind(R.id.gson)
    TextView mGson;
    @Bind(R.id.butterknife)
    TextView mButterknife;
    @Bind(R.id.logger)
    TextView mLogger;
    @Bind(R.id.photoview)
    TextView mPhotoview;
    @Bind(R.id.leakcanary)
    TextView mLeakcanary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initToolBar();
        initLink();
        initPhoto();
        initTransition();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initTransition() {
        Slide slide = new Slide();
        slide.setDuration(600);
        slide.setInterpolator(new MaterialInterpolator());
        getWindow().setExitTransition(slide);
        getWindow().setEnterTransition(slide);
    }

    private void initPhoto() {
//        Glide.with(this).load(R.drawable.photo).transform(
        Glide.with(this).load(R.drawable.photo).asBitmap().centerCrop().into(new BitmapImageViewTarget(mAboutPhoto) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(AppConfig.sContext.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                mAboutPhoto.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


    private void initLink() {
        MovementMethod instance = LinkMovementMethod.getInstance();
        mRxjava.setMovementMethod(instance);
        mRxandroid.setMovementMethod(instance);
        mRetrofit.setMovementMethod(instance);
        mGlide.setMovementMethod(instance);
        mGson.setMovementMethod(instance);
        mButterknife.setMovementMethod(instance);
        mLogger.setMovementMethod(instance);
        mLeakcanary.setMovementMethod(instance);
        mPhotoview.setMovementMethod(instance);
    }

    private void initToolBar() {
        mAboutToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mAboutToolbar.setTitle("关于");
        mAboutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        finishAfterTransition();
        super.onBackPressed();
    }
}
