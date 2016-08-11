package xblydxj.gank.modules.picture;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private Toolbar mToolBar;


    public BigPictureFragment() {
        // Required empty public constructor
    }

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
        initPhotoView();
        Glide.with(this).load(imageUrl).into(mPhotoView);
        return picture;
    }

    private void initPhotoView() {
        mPhotoView.canZoom();
        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mPresenter.savePicture();
                return false;
            }
        });
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
                        mPresenter.savePicture();
                        SnackUtils.showSnackLong(mToolBar, "showSave", "i see");
                        break;
                    case R.id.action_share:
                        mPresenter.showShare(getActivity());
                        SnackUtils.showSnackLong(mToolBar, "showShare", "i see");
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
}

