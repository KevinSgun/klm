package cn.kuailaimei.store.partner.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.PartnerItem;
import cn.kuailaimei.store.api.request.LevelRequest;
import cn.kuailaimei.store.api.request.PageRequest;
import cn.kuailaimei.store.common.ui.AppBarActivity;
import cn.kuailaimei.store.common.widget.MVCSwipeRefreshHelper;
import cn.kuailaimei.store.partner.adapter.PartnerListAdapter;
import cn.kuailaimei.store.partner.datasource.PartnerListDataSource;

/**
 * Created by ymh on 2016/7/8 0008.
 */
public class PartnerListActivity extends AppBarActivity{
    private static final String LEVEL = "level";
    private String level;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MVCSwipeRefreshHelper<List<PartnerItem>> mvcHelper;
    private PartnerListDataSource dataSource;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_partner_list;
    }

    @Override
    protected void initView() {
        level = getIntent().getStringExtra(LEVEL);
        setTitle(getLevelTitle());

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

    }

    private String getLevelTitle() {
        return "1".equals(level)?"一级会员":("2".equals(level)?"二级会员":"三级会员");
    }

    @Override
    protected void initData() {
        LevelRequest lRequest = new LevelRequest();
        lRequest.setLever(level);
        PageRequest request = new PageRequest(lRequest);
        request.sign();
        mvcHelper = new MVCSwipeRefreshHelper<List<PartnerItem>>(swipeRefreshLayout);
        dataSource = new PartnerListDataSource(request);
        mvcHelper.setDataSource(dataSource);
        // 设置适配器
        mvcHelper.setAdapter(new PartnerListAdapter(this,new ArrayList<PartnerItem>()));
        // 加载数据
        mvcHelper.refresh();
    }

    public static void launch(Activity act, String level){
        Intent intent = new Intent(act,PartnerListActivity.class);
        intent.putExtra(LEVEL,level);
        act.startActivity(intent);
    }
}
