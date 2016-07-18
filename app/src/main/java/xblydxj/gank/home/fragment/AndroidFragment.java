package xblydxj.gank.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xblydxj.gank.R;
import xblydxj.gank.bean.Android;
import xblydxj.gank.config.AppConfig;
import xblydxj.gank.home.contract.AndroidContract;
import xblydxj.gank.utils.SnackUtils;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by xblydxj on 2016/7/16/016.
 */
public class AndroidFragment extends Fragment implements AndroidContract.View {

    @Bind(R.id.recycler)
    RecyclerView mAndroidRecycler;
    @Bind(R.id.refresh)
    SwipeRefreshLayout mAndroidRefresh;
    @Bind(R.id.content_layout)
    LinearLayout mAndroidContentLayout;
    @Bind(R.id.error_reconnect)
    Button mAndroidErrorReconnect;
    @Bind(R.id.error_layout)
    LinearLayout mAndroidErrorLayout;

    private AndroidContract.Presenter mPresenter;

    private AndroidAdapter mAndroidAdapter;

    public AndroidFragment() {}

    @Override
    public void showError() {
        SnackUtils.showSnackLong(mAndroidRecycler, "网络未连接~", "知道了");
        mAndroidErrorReconnect.setVisibility(View.VISIBLE);
        mAndroidContentLayout.setVisibility(View.GONE);
    }



    @OnClick(R.id.error_reconnect)
    public void onClick() {
        AppConfig.sHandler.post(new Runnable() {
            @Override
            public void run() {
                mAndroidErrorReconnect.setVisibility(View.GONE);
                mAndroidContentLayout.setVisibility(View.VISIBLE);
                mAndroidRefresh.setRefreshing(true);
            }
        });
    }

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new AndroidAdapter()
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_android, container, false);
        ButterKnife.bind(this, view);
        mAndroidRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAndroidRecycler.setAdapter(mAndroidAdapter);

        mAndroidRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        mAndroidRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshAndroid();
            }
        });
        setRetainInstance(true);
        return view;
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
        ButterKnife.unbind(this);
    }


    public class AndroidAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Android.ResultsBean> AndroidList = new ArrayList<>();
        private int preDay = 0;

        public AndroidAdapter(List<Android.ResultsBean> data) {
            AndroidList = checkNotNull(data);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AndroidViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.android_recycler_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Android.ResultsBean result = AndroidList.get(position);
            ((AndroidViewHolder) holder).androidAuthor.setText(result.getWho());
            ((AndroidViewHolder) holder).androidDescribe.setText(result.getDesc());
            //publishedAt : 2016-07-15T11:56:07.907Z
            String Date = result.getPublishedAt();
            int day = Integer.parseInt(Date.substring(8, 10));

            if (position == 0 || preDay != day) {
                ((AndroidViewHolder) holder).androidCardData.setVisibility(View.VISIBLE);
                String date = Date.substring(0, 10);
                ((AndroidViewHolder) holder).androidDate.setText(date);
            }
            preDay = day;
        }

        @Override
        public int getItemCount() {
            return AndroidList.size();
        }

        public class AndroidViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.date)
            TextView androidDate;
            @Bind(R.id.date_card)
            CardView androidCardData;
            @Bind(R.id.author)
            TextView androidAuthor;
            @Bind(R.id.describe)
            TextView androidDescribe;

            public AndroidViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
