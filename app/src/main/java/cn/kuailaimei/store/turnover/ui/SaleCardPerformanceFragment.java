package cn.kuailaimei.store.turnover.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.AmountItem;
import cn.kuailaimei.store.api.request.DateRequest;
import cn.kuailaimei.store.api.request.PageRequest;
import cn.kuailaimei.store.common.ui.BaseFragment;
import cn.kuailaimei.store.common.widget.MVCSwipeRefreshHelper;
import cn.kuailaimei.store.turnover.adapter.SaleCardAdapter;
import cn.kuailaimei.store.turnover.datasource.SaleCardPerformanceDataSource;

/**
 * Created by ymh on 2016/7/7 0007.
 */
public class SaleCardPerformanceFragment extends BaseFragment{
    private static String DATE = "date";
    private SwipeRefreshLayout swipeRefreshLayout;
    private SaleCardPerformanceDataSource dataSource;
    private MVCSwipeRefreshHelper<List<AmountItem>> mvcHelper;
    private String date;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_sale_card_performance;
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
        mvcHelper = new MVCSwipeRefreshHelper<List<AmountItem>>(swipeRefreshLayout);
        dataSource = new SaleCardPerformanceDataSource(request);
        mvcHelper.setDataSource(dataSource);
        // 设置适配器
        mvcHelper.setAdapter(new SaleCardAdapter(getActivity(),new ArrayList<AmountItem>()));
        // 加载数据
        mvcHelper.refresh();
    }

    public static SaleCardPerformanceFragment getInstance(String date) {
        Bundle bundle = new Bundle();
        bundle.putString(DATE,date);
        SaleCardPerformanceFragment fragment = new SaleCardPerformanceFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
