package xblydxj.gank.modules.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import xblydxj.gank.R;

/**
 * Created by xblydxj.
 * on 2016/8/12/012
 */
public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id
                .search_fragment);
        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.search_fragment, searchFragment);
            transaction.commit();
        }
        new SearchPresenter(searchFragment);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
