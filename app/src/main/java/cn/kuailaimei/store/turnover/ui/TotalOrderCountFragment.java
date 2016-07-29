package cn.kuailaimei.store.turnover.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.AmountItem;
import cn.kuailaimei.store.api.entity.TotalOrderCountItem;
import cn.kuailaimei.store.api.request.DateRequest;
import cn.kuailaimei.store.api.request.PageRequest;
import cn.kuailaimei.store.common.User;
import cn.kuailaimei.store.common.ui.BaseFragment;
import cn.kuailaimei.store.common.widget.MVCSwipeRefreshHelper;
import cn.kuailaimei.store.turnover.adapter.EmpTotalOrderCountAdapter;
import cn.kuailaimei.store.turnover.adapter.TotalOrderCountAdapter;
import cn.kuailaimei.store.turnover.datasource.EmpTotalOrderCountDataSource;
import cn.kuailaimei.store.turnover.datasource.TotalOrderCountDataSource;

/**
 * Created by ymh on 2016/7/7 0007.
 */
public class TotalOrderCountFragment extends BaseFragment{
    private static final String DATE = "date";
    private String date = "7";
    private SwipeRefreshLayout swipeRefreshLayout;
    private TotalOrderCountDataSource bossDataSource;
    private MVCSwipeRefreshHelper<List<TotalOrderCountItem>> bossMvcHelper;
    private MVCSwipeRefreshHelper<List<AmountItem>> empMvcHelper;
    private EmpTotalOrderCountDataSource empDataSource;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_total_order_count;
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
        if(User.get().isBoss())
            requestBossOrderCount(request);
        else
            requestEmpOrderCount(request);
    }

    private void requestBossOrderCount(PageRequest request) {
        bossMvcHelper = new MVCSwipeRefreshHelper<List<TotalOrderCountItem>>(swipeRefreshLayout);
        bossDataSource = new TotalOrderCountDataSource(request);
        bossMvcHelper.setDataSource(bossDataSource);
        // 设置适配器
        bossMvcHelper.setAdapter(new TotalOrderCountAdapter(getActivity(),new ArrayList<TotalOrderCountItem>()));
        // 加载数据
        bossMvcHelper.refresh();
    }

    private void requestEmpOrderCount(PageRequest request) {
        empMvcHelper = new MVCSwipeRefreshHelper<List<AmountItem>>(swipeRefreshLayout);
        empDataSource = new EmpTotalOrderCountDataSource(request);
        empMvcHelper.setDataSource(empDataSource);
        // 设置适配器
        empMvcHelper.setAdapter(new EmpTotalOrderCountAdapter(getActivity(),new ArrayList<AmountItem>()));
        // 加载数据
        empMvcHelper.refresh();
    }

    public static TotalOrderCountFragment getInstance(String date) {
        Bundle bundle = new Bundle();
        bundle.putString(DATE,date);
        TotalOrderCountFragment fragment = new TotalOrderCountFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
