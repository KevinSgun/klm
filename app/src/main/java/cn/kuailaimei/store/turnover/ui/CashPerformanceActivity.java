package cn.kuailaimei.store.turnover.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.zitech.framework.widget.SlidingTabs;
import com.zitech.framework.widget.TabsFragmentAdapter;

import java.util.LinkedList;
import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.common.ui.AppBarActivity;

/**
 * Created by ymh on 2016/7/7 0007.
 */
public class CashPerformanceActivity extends AppBarActivity{
    private SlidingTabs slidingtabs;
    private ViewPager pager;
    private List<Fragment> fragments;
    private TabsFragmentAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_cash_performance_list;
    }

    @Override
    protected void initView() {
        setTitle(R.string.cash_performance);
        slidingtabs = (SlidingTabs) findViewById(R.id.sliding_tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        String[] titles = { "7天", "30天", "全部"};

        this.fragments = new LinkedList<>();
        CashPerformanceFragment fragment0 = CashPerformanceFragment.getInstance("7");
        CashPerformanceFragment fragment1 = CashPerformanceFragment.getInstance("30");
        CashPerformanceFragment fragment2 = CashPerformanceFragment.getInstance("");
        this.fragments.add(fragment0);
        this.fragments.add(fragment1);
        this.fragments.add(fragment2);

        this.adapter = new TabsFragmentAdapter(this.getSupportFragmentManager(), titles,
                this.fragments);
        this.pager.setAdapter(this.adapter);
        this.slidingtabs.setViewPager(this.pager);
    }

    @Override
    protected void initData() {

    }

    /**
     *
     * @param act
     */
    public static void launch(Activity act){
        Intent intent = new Intent(act,CashPerformanceActivity.class);
        act.startActivity(intent);
    }
}
