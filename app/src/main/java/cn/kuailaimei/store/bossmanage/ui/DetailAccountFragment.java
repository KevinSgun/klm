package cn.kuailaimei.store.bossmanage.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.TransItem;
import cn.kuailaimei.store.api.request.DateRequest;
import cn.kuailaimei.store.api.request.PageRequest;
import cn.kuailaimei.store.bossmanage.adapter.DetailAccountAdapter;
import cn.kuailaimei.store.bossmanage.datasource.AccountListDataSource;
import cn.kuailaimei.store.common.ui.BaseFragment;
import cn.kuailaimei.store.common.widget.MVCSwipeRefreshHelper;

/**
 * Created by ymh on 2016/7/9 0009.
 */
public class DetailAccountFragment extends BaseFragment{
    private static final String DATE = "date";
    private String date = "";
    private SwipeRefreshLayout swipeRefreshLayout;
    private MVCSwipeRefreshHelper<List<TransItem>> mvcHelper;
    private AccountListDataSource dataSource;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_order_lsit;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        Bundle bundle = getArguments();
        if(bundle != null){
            date = bundle.getString(DATE);
        }
        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipeRefreshLayout);

    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        DateRequest dateRequest = new DateRequest();
        dateRequest.setDate(date);
        PageRequest request = new PageRequest(dateRequest);
        request.sign();
        mvcHelper = new MVCSwipeRefreshHelper<List<TransItem>>(swipeRefreshLayout);
        dataSource = new AccountListDataSource(request);
        mvcHelper.setDataSource(dataSource);
        // 设置适配器
        mvcHelper.setAdapter(new DetailAccountAdapter(getActivity(),new ArrayList<TransItem>()));
        // 加载数据
        mvcHelper.refresh();
    }

    public static DetailAccountFragment getInstance(String date) {
        Bundle bundle = new Bundle();
        bundle.putString(DATE,date);
        DetailAccountFragment fragment = new DetailAccountFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
