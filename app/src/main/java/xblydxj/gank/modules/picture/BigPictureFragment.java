package xblydxj.gank.modules.picture;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
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


    public BigPictureFragment() {}

    public static BigPictureFragment newInstance(String url) {
        BigPictureFragment fragment = new BigPictureFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, url);
        fragment.setArguments(args);
        return fragment;
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
        initPhotoView();
        mBitmap = mPhotoView.getDrawingCache();
        return picture;
    }

    private void initPhotoView() {
        mPhotoView.setZoomable(true);
        mPhotoView.canZoom();
        new PhotoViewAttacher(mPhotoView, true);
        mPhotoView.setLongClickable(true);
        mPhotoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                confirm();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                confirm();
                return false;
            }
        });
    }

    private void confirm() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("确认保存？");
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                SnackUtils.showSnackShort(mPhotoView, "cancel~");
            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                mPresenter.savePicture(mBitmap);
                return true;
            }
        });
    }

    private void initToolbar() {
        setHasOptionsMenu(true);
        Toolbar toolBar = (Toolbar) getActivity().findViewById(R.id.picture_tool_bar);
        toolBar.setTitle("美屡~");
        toolBar.setTitleTextColor(Color.WHITE);
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        toolBar.inflateMenu(R.menu.picture_toolbar_menu);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save:
                        mPresenter.savePicture(mBitmap);
                        break;
                    case R.id.action_share:
                        mPresenter.showShare(getActivity());
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

