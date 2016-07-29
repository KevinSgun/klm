package cn.kuailaimei.store.bossmanage.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.Constants;
import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.EmpItem;
import cn.kuailaimei.store.api.request.PageRequest;
import cn.kuailaimei.store.api.request.PageRequestData;
import cn.kuailaimei.store.bossmanage.adapter.EmpManagerListAdapter;
import cn.kuailaimei.store.bossmanage.datasource.EmpDataSource;
import cn.kuailaimei.store.common.event.EventFactory;
import cn.kuailaimei.store.common.ui.AppBarActivity;
import cn.kuailaimei.store.common.widget.MVCSwipeRefreshHelper;
import cn.kuailaimei.store.common.widget.ToolBarHelper;

/**
 * Created by ymh on 2016/7/8 0008.
 */
public class EmployeeManagerActivity extends AppBarActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView empItemList;
    private MVCSwipeRefreshHelper<List<EmpItem>> mvcHelper;
    private EmpDataSource dataSource;
    private EmpManagerListAdapter mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_employ_manager;
    }

    @Subscribe
    public void onMainThreadEmpUpdate(EventFactory.EmpUpdate data){
        if(mvcHelper!=null) mvcHelper.refresh();
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra(Constants.ActivityExtra.MANAGER_NAME);
        setTitle(title);
        setRightText(R.string.add);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        empItemList = (ListView) findViewById(R.id.pagerListView);

        empItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //TODO 进入编辑员工信息页
                int index = position>=mvcHelper.getAdapter().getData().size()?mvcHelper.getAdapter().getData().size()-1:position;
                EditEmpActivity.launch(EmployeeManagerActivity.this,mAdapter.getList().get(index));
            }
        });

    }
//
    @Override
    protected void initData() {
        PageRequest request = new PageRequest(new PageRequestData());
        request.sign();
        mvcHelper = new MVCSwipeRefreshHelper<List<EmpItem>>(swipeRefreshLayout);
        dataSource = new EmpDataSource(request);
        mvcHelper.setDataSource(dataSource);
        // 设置适配器
        mAdapter = new EmpManagerListAdapter(this, new ArrayList<EmpItem>());
        mvcHelper.setAdapter(mAdapter);
        // 加载数据
        mvcHelper.refresh();
    }

    @Override
    protected void onActionBarItemClick(int position) {
        super.onActionBarItemClick(position);
        if(position == ToolBarHelper.ITEM_RIGHT){
            EditEmpActivity.launch(this);
        }
    }

    public static void launch(Activity act, String managerName){
        Intent intent = new Intent(act,EmployeeManagerActivity.class);
        intent.putExtra(Constants.ActivityExtra.MANAGER_NAME, managerName);
        act.startActivity(intent);
    }
}
