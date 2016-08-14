package xblydxj.gank.modules.search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xblydxj.gank.R;
import xblydxj.gank.bean.Data;
import xblydxj.gank.bean.SearchResult;
import xblydxj.gank.modules.home.adapter.BaseRecyclerAdapter;
import xblydxj.gank.utils.SnackUtils;
import xblydxj.gank.widget.NoAlphaRecyclerViewAnimator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xblydxj
 * on 2016/7/16/016.
 */
public class SearchFragment extends Fragment implements SearchContract.View, AdapterView.OnItemSelectedListener {
    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    @Bind(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    private SearchContract.Presenter mPresenter;
    private Toolbar mToolBar;
    private String mSelectType;
    private String[] mStringArray;
    private Animation mAnimation;
    private CardView mSearch_card;
    private TextInputEditText mSearch_edit;
    private List<Data.ResultsBean> list = new ArrayList<>();
    public BaseRecyclerAdapter mAdapter = new BaseRecyclerAdapter(list);


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View search = inflater.inflate(R.layout.fragment_normal, container, false);
        ButterKnife.bind(this, search);
        init();
        return search;
    }

    private void init() {
        initToolBar();
        initRecycler();
        initSpinner();
        initSearch();
        initListener();
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(String url, String desc) {
                mPresenter.toWeb(url, desc);
            }
        });
        mAdapter.setOnUpPull(new BaseRecyclerAdapter.OnUpPull() {
            @Override
            public void upPullLoad(int size) {
                mPresenter.loadMore(size);
            }
        });
    }

    private void initRecycler() {
        mRefresh.setEnabled(false);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setItemAnimator(new NoAlphaRecyclerViewAnimator());
        mRecycler.setAdapter(mAdapter);
    }

    private void initSpinner() {
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.search_spinner);
        mStringArray = getResources().getStringArray(R.array.search_type);
        SpinnerAdapter mAdapter = new SpinnerAdapter(getActivity(), mStringArray);
        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mSelectType = mStringArray[i];
//        list.clear();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void initSearch() {
        mSearch_card = (CardView) getActivity().findViewById(R.id.search_card);
        mSearch_edit = (TextInputEditText) getActivity().findViewById(R.id.search_et);
        ImageView search_bt = (ImageView) getActivity().findViewById(R.id.search);
        Logger.d("spinner,init");
        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = mSearch_edit.getText().toString().trim();
                ProgressDialog dialog = new ProgressDialog(getContext());
                mPresenter.onSearch(search, mSelectType,dialog);
            }
        });
    }

    private void initToolBar() {
        mToolBar = (Toolbar) getActivity().findViewById(R.id.search_tool_bar);
        mToolBar.setTitle("搜索");
        mToolBar.setTitleTextColor(Color.WHITE);
        mToolBar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void animate() {
        if (mAnimation == null) {
            mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
            mAnimation.setInterpolator(getContext(), R.anim.shake_times);
        }
        mSearch_card.startAnimation(mAnimation);
    }

    @Override
    public void showSnack(String s) {
        SnackUtils.showSnackShort(mToolBar, "搜索内容不能为空~");
    }

    @Override
    public void updateView(List<SearchResult.ResultsBean> results, boolean isOld) {
        if (!isOld) {
            list.clear();
        }
        list.addAll(results);
        if (list.size() == 10) {
            mAdapter.notifyDataSetChanged();
        } else {
            int index = list.size() - results.size();
            for (int i = 0; i <= results.size(); i++) {
                mAdapter.notifyItemChanged(index + i);
            }
        }
    }

    @Override
    public void intentToWeb(Intent intent) {
        startActivity(intent);
    }
}
