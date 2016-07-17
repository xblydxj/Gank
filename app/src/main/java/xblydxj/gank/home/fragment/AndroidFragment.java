package xblydxj.gank.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xblydxj.gank.R;
import xblydxj.gank.home.contract.AndroidContract;

import static xblydxj.gank.utils.CheckUtil.checkNotNull;

/**
 * Created by xblydxj on 2016/7/16/016.
 */
public class AndroidFragment extends Fragment implements AndroidContract.View {

    @InjectView(R.id.android_recycler)
    RecyclerView mAndroidRecycler;


    private AndroidContract.Presenter mPresenter;

    public AndroidFragment() {}

    private static class SingletonHolder {
        static AndroidFragment instance = new AndroidFragment();
    }

    public static AndroidFragment newInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public void setPresenter(AndroidContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_android, container, false);
        ButterKnife.inject(this,view);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
