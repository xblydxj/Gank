package xblydxj.gank.modules.web;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import xblydxj.gank.R;
import xblydxj.gank.utils.SnackUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xblydxj on 2016/7/16/016.
 */
public class WebFragment extends Fragment implements WebContract.View {

    @Bind(R.id.web_header_title)
    TextView mWebHeaderTitle;
    @Bind(R.id.web_content)
    WebView mWebContent;

    @Bind(R.id.web_progress)
    ProgressBar mWebProgress;
    private WebContract.Presenter mPresenter;
    private Toolbar mToolBar;

    public static WebFragment newInstance(String url, String desc) {
        Bundle arguments = new Bundle();
        arguments.putString("url", url);
        arguments.putString("desc", desc);
        WebFragment webFragment = new WebFragment();
        webFragment.setArguments(arguments);
        return webFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        mWebContent.onResume();
        initWebView();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebContent.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void setPresenter(WebContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View web = inflater.inflate(R.layout.fragment_web, container, false);
        ButterKnife.bind(this, web);
        initToolBar();
        initFab();
        return web;
    }


    private void initFab() {
        FloatingActionButton webFabForward = (FloatingActionButton) getActivity().findViewById(R.id.web_fab_forward);
        FloatingActionButton webFabBack = (FloatingActionButton) getActivity().findViewById(R.id.web_fab_back);
        webFabForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goForward(mWebContent);
            }
        });
        webFabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goBack(mWebContent);
            }
        });
    }


    private void initWebView() {
        WebSettings settings = mWebContent.getSettings();
        settings.setLoadsImagesAutomatically(true);
        settings.getJavaScriptEnabled();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        mWebContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mWebProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebProgress.setVisibility(View.INVISIBLE);
            }
        });
        mWebContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mPresenter.updateProgress(newProgress);
            }
        });
    }

    private void initToolBar() {
        setHasOptionsMenu(true);
        mToolBar = (Toolbar) getActivity().findViewById(R.id.web_tool_bar);
        mToolBar.setTitle("详情");
        mToolBar.setTitleTextColor(Color.WHITE);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        mToolBar.inflateMenu(R.menu.web_toolbar_menu);
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mPresenter.showShare(getActivity());
                return true;
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mWebContent.destroy();
        ButterKnife.unbind(this);
    }


    @Override
    public void showTitle(String desc) {
        mWebHeaderTitle.setText(desc);
    }

    @Override
    public void showWeb(String url) {
        mWebContent.loadUrl(url);
    }

    @Override
    public void updateProgress(int progress) {
        Logger.d("progress:" + progress);
        if (mWebProgress != null) {
            mWebProgress.setProgress(progress);
        }
    }

    @Override
    public void showSnack(String str) {
        SnackUtils.showSnackShort(mToolBar,str);
    }
}
