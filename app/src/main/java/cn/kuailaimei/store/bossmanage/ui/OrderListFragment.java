package cn.kuailaimei.store.bossmanage.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.OrderItem;
import cn.kuailaimei.store.api.request.PageRequest;
import cn.kuailaimei.store.api.request.StatusRequest;
import cn.kuailaimei.store.bossmanage.adapter.OrderManagerAdapter;
import cn.kuailaimei.store.bossmanage.datasource.OrderListDataSource;
import cn.kuailaimei.store.common.User;
import cn.kuailaimei.store.common.event.EventFactory;
import cn.kuailaimei.store.common.ui.BaseFragment;
import cn.kuailaimei.store.common.widget.MVCSwipeRefreshHelper;

/**
 * Created by ymh on 2016/7/9 0009.
 */
public class OrderListFragment extends BaseFragment {
    private static final String STATUS = "status";
    private String status = "0";
    private SwipeRefreshLayout swipeRefreshLayout;
    private MVCSwipeRefreshHelper<List<OrderItem>> mvcHelper;
    private OrderListDataSource dataSource;

    @Override
    protected void onPreInflate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onPreInflate(inflater, container, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void MainThreadOrderDataChange(EventFactory.OrderDataChange data) {
//        if (String.valueOf(OrderItem.ALL).equals(status) && !data.allNoUpdate
//                || String.valueOf(OrderItem.WAIT_PAY).equals(status) && !data.waitPayNoUpdate
//                || String.valueOf(OrderItem.WAIT_COMMENT).equals(status) && !data.waitConfirmNoUpdate
//                || String.valueOf(OrderItem.COMPLETE).equals(status) && !data.completeNoUpdate) {
        mvcHelper.refresh();
//        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_order_lsit;
    }

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            status = bundle.getString(STATUS);
        }
        swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipeRefreshLayout);

    }

    @Override
    public void onPrepareData() {
        super.onPrepareData();
        StatusRequest statusRequest = new StatusRequest();
        statusRequest.setStatus(status);
        PageRequest request = new PageRequest(statusRequest);
        request.sign();
        mvcHelper = new MVCSwipeRefreshHelper<List<OrderItem>>(swipeRefreshLayout);
        dataSource = new OrderListDataSource(request);
        mvcHelper.setDataSource(dataSource);
        // 设置适配器
        mvcHelper.setAdapter(new OrderManagerAdapter(getActivity(), new ArrayList<OrderItem>(), User.get().isBoss()));
        // 加载数据
        mvcHelper.refresh();
    }

    public static OrderListFragment getInstance(String status) {
        Bundle bundle = new Bundle();
        bundle.putString(STATUS, status);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
