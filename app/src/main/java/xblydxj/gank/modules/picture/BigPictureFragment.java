package xblydxj.gank.modules.picture;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import xblydxj.gank.R;
import xblydxj.gank.utils.SnackUtils;
import xblydxj.gank.widget.MaterialInterpolator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xblydxj.
 * on 2016/8/10/010
 */
public class BigPictureFragment extends Fragment implements BigPictureContract.View {

    @Bind(R.id.picture_photo_view)
    PhotoView mPhotoView;

    private static final String IMAGE_URL = "image";

    private String imageUrl;
    private BigPictureContract.Presenter mPresenter;
    private Bitmap mBitmap;
    private ProgressDialog mDialog;
    private Toolbar mToolBar;
    private boolean isToolBarHiding;

    public BigPictureFragment() {}

    public static BigPictureFragment newInstance(String url) {
        BigPictureFragment fragment = new BigPictureFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, url);
        fragment.setArguments(args);

        return fragment;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initTransition() {
        getActivity().getWindow().getSharedElementEnterTransition();
        getActivity().getWindow().getSharedElementExitTransition();
        ActivityOptionsCompat compat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(), mPhotoView, "logo");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            imageUrl = getArguments().getString(IMAGE_URL);
            Logger.d("picture url:" + imageUrl);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View picture = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, picture);
        initToolbar();
        Glide.with(this).load(imageUrl).into(mPhotoView);
        initTransition();
        initPhotoView();
        return picture;
    }

    private void initPhotoView() {
        mPhotoView.setZoomable(true);
        mPhotoView.canZoom();
        mPhotoView.setLongClickable(true);
        mPhotoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                mToolBar.animate()
                        .translationY(isToolBarHiding ? 0 : -mToolBar.getHeight())
                        .setInterpolator(new MaterialInterpolator())
                        .start();
                isToolBarHiding = !isToolBarHiding;
                
//                if (mToolBar.getVisibility() == View.INVISIBLE) {
//                    StatusBarUtils.setColor(getActivity(), getResources().getColor(R.color.colorPrimary));
//                    mToolBar.setVisibility(View.VISIBLE);
//                    mToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                } else {
//                    StatusBarUtils.setColor(getActivity(), getResources().getColor(R.color.md_black_color_code));
//                    mToolBar.setVisibility(View.INVISIBLE);
//                }
            }

            @Override
            public void onOutsidePhotoTap() {
            }
        });

        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                confirm();
                return true;
            }
        });
    }

    private void confirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认保存？");
        builder.setTitle("提示");
        mPhotoView.setDrawingCacheEnabled(true);
        mBitmap = mPhotoView.getDrawingCache();
        Logger.d(mBitmap == null ? "xxx" : "xxxx");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                mPresenter.savePicture(mBitmap);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void initToolbar() {
        setHasOptionsMenu(true);
        mToolBar = (Toolbar) getActivity().findViewById(R.id.picture_tool_bar);
        mToolBar.setTitle("美屡~");
        mToolBar.setTitleTextColor(Color.WHITE);
        mToolBar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        mToolBar.inflateMenu(R.menu.picture_toolbar_menu);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save:
                        mPhotoView.setDrawingCacheEnabled(true);
                        mBitmap = mPhotoView.getDrawingCache();
                        mPresenter.savePicture(mBitmap);
                        break;
                    case R.id.action_share:
                        mPresenter.showShare(getActivity(), mBitmap);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setPresenter(BigPictureContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showSnack(String s) {
        SnackUtils.showSnackLong(mPhotoView, s, "i see");
    }

    @Override
    public void showDialog() {
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("正在保存");
        mDialog.show();
    }

    @Override
    public void dismissDialog() {
        mDialog.dismiss();
    }
}

