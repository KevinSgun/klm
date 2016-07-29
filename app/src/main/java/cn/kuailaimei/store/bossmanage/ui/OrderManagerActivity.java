package cn.kuailaimei.store.bossmanage.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.zitech.framework.widget.SlidingTabs;
import com.zitech.framework.widget.TabsFragmentAdapter;

import java.util.LinkedList;
import java.util.List;

import cn.kuailaimei.store.Constants;
import cn.kuailaimei.store.R;
import cn.kuailaimei.store.common.ui.AppBarActivity;

/**
 * Created by ymh on 2016/7/8 0008.
 */
public class OrderManagerActivity extends AppBarActivity {
    private SlidingTabs slidingtabs;
    private ViewPager pager;
    private List<Fragment> fragments;
    private TabsFragmentAdapter adapter;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_order_manager;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra(Constants.ActivityExtra.MANAGER_NAME);
        setTitle(title);
        slidingtabs = (SlidingTabs) findViewById(R.id.sliding_tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        //0全部 1待付款 2待评价 3 已完成
        String[] titles = { "全部", "待付款", "待确认","待评价","已完成"};

        this.fragments = new LinkedList<>();
        OrderListFragment fragment0 = OrderListFragment.getInstance("0");
        OrderListFragment fragment1 = OrderListFragment.getInstance("1");
        OrderListFragment fragment2 = OrderListFragment.getInstance("2");
        OrderListFragment fragment3 = OrderListFragment.getInstance("3");
        OrderListFragment fragment4 = OrderListFragment.getInstance("4");
        this.fragments.add(fragment0);
        this.fragments.add(fragment1);
        this.fragments.add(fragment2);
        this.fragments.add(fragment3);
        this.fragments.add(fragment4);

        this.adapter = new TabsFragmentAdapter(this.getSupportFragmentManager(), titles,
                this.fragments);
        this.pager.setAdapter(this.adapter);
        this.slidingtabs.setViewPager(this.pager);
    }

    @Override
    protected void initData() {

    }

    public static void launch(Activity act, String managerName){
        Intent intent = new Intent(act,OrderManagerActivity.class);
        intent.putExtra(Constants.ActivityExtra.MANAGER_NAME, managerName);
        act.startActivity(intent);
    }
}
