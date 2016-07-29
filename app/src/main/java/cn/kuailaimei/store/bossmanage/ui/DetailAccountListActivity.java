package cn.kuailaimei.store.bossmanage.ui;

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
 * Created by ymh on 2016/7/9 0009.
 */
public class DetailAccountListActivity extends AppBarActivity {
    private SlidingTabs slidingtabs;
    private ViewPager pager;
    private List<Fragment> fragments;
    private TabsFragmentAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_detail_account;
    }

    @Override
    protected void initView() {
        setTitle("账目明细");
        slidingtabs = (SlidingTabs) findViewById(R.id.sliding_tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        String[] titles = {"7天", "30天", "全部"};

        this.fragments = new LinkedList<>();
        DetailAccountFragment fragment0 = DetailAccountFragment.getInstance("7");
        DetailAccountFragment fragment1 = DetailAccountFragment.getInstance("30");
        DetailAccountFragment fragment2 = DetailAccountFragment.getInstance("");
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

    public static void launch(Activity act) {
        Intent intent = new Intent(act, DetailAccountListActivity.class);
        act.startActivity(intent);
    }
}
