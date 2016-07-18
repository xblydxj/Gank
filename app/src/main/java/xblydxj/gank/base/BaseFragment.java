package xblydxj.gank.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 46321 on 2016/7/16/016.
 */
public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        if (mLoadingPager == null) {


            mLoadingPager = new LoadingPager(getContext()) {

                @Override
                protected Object loadSubData() {
                    return loadFragmentData();
                }


                @Override
                protected View createSuccessView() {
                    return loadFragmentView();
                }
            };
        }
        return mLoadingPager;
    }
    protected abstract View loadFragmentView();

    protected abstract Object loadFragmentData();
}
