package cn.kuailaimei.store.bossmanage.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.widget.SlidingTabs;
import com.zitech.framework.widget.TabsFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.kuailaimei.store.Constants;
import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.CommentHeader;
import cn.kuailaimei.store.api.entity.CommentItem;
import cn.kuailaimei.store.api.request.PageRequest;
import cn.kuailaimei.store.api.request.PageRequestData;
import cn.kuailaimei.store.api.response.CommentListResponse;
import cn.kuailaimei.store.bossmanage.adapter.CommentListAdapter;
import cn.kuailaimei.store.bossmanage.datasource.CommentListDataSource;
import cn.kuailaimei.store.common.datasource.ListFetcher;
import cn.kuailaimei.store.common.ui.AppBarActivity;
import cn.kuailaimei.store.common.widget.MVCSwipeRefreshHelper;

/**
 * Created by ymh on 2016/7/8 0008.
 */
public class LookCommentActivity extends AppBarActivity{
    private SlidingTabs slidingtabs;
    private ViewPager pager;
    private List<Fragment> fragments;
    private TabsFragmentAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView commentlistview;
    private TextView satisfactiondegreetv;
    private TextView allreviews;
    private TextView highpositivereviews;
    private TextView positivereviews;
    private TextView negativereviews;
    private LinearLayout reviewchooser;
    private MVCSwipeRefreshHelper<List<CommentItem>> mvcHelper;
    private CommentListDataSource dataSource;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_look_comment;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra(Constants.ActivityExtra.MANAGER_NAME);
        setTitle(title);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        commentlistview = (ListView) findViewById(R.id.comment_list_view);
        View header = LayoutInflater.from(this).inflate(R.layout.header_comment,null);
        iniHeader(header);
        commentlistview.addHeaderView(header);
    }

    private void iniHeader(View header) {
        satisfactiondegreetv = (TextView) header.findViewById(R.id.satisfaction_degree_tv);
        allreviews = (TextView) header.findViewById(R.id.all_reviews);
        highpositivereviews = (TextView) header.findViewById(R.id.high_positive_reviews);
        positivereviews = (TextView) header.findViewById(R.id.positive_reviews);
        negativereviews = (TextView) header.findViewById(R.id.negative_reviews);
    }

    @Override
    protected void initData() {
        PageRequest request = new PageRequest(new PageRequestData());
        request.sign();
        mvcHelper = new MVCSwipeRefreshHelper<List<CommentItem>>(swipeRefreshLayout);
        ListFetcher<CommentItem> fetcher = new ListFetcher<CommentItem>() {
            @Override
            public List<CommentItem> fetch(ApiResponse<?> response) {
                CommentListResponse commentListResponse = (CommentListResponse)response.getData();
                List<CommentItem> itemsBeenList = commentListResponse.getList();
                if(mvcHelper.getAdapter().getData().size() ==0||mvcHelper.getAdapter().getData().get(0).isEmpty()) {
                    if (itemsBeenList == null) {
                        itemsBeenList = new ArrayList<>();
                        CommentItem itemsBean = new CommentItem();
                        itemsBean.setEmpty(true);
                        itemsBeenList.add(itemsBean);
                    } else if (itemsBeenList.size() == 0) {
                        CommentItem itemsBean = new CommentItem();
                        itemsBean.setEmpty(true);
                        itemsBeenList.add(itemsBean);
                    }
                }
                refreshTop(commentListResponse.getHead());
                return itemsBeenList;
            }
        };
        dataSource = new CommentListDataSource(request,fetcher);
        mvcHelper.setDataSource(dataSource);

        // 设置适配器
        mvcHelper.setAdapter(new CommentListAdapter(this,new ArrayList<CommentItem>()));
        // 加载数据
        mvcHelper.refresh();
    }

    private void refreshTop(CommentHeader head) {
        if(head != null){
            satisfactiondegreetv.setText(head.getSatisfactory());
            allreviews.setText("全部(" + head.getAllComment() + ")");
            highpositivereviews.setText("很满意(" + head.getPerfectCount() + ")");
            positivereviews.setText("满意(" + head.getGoodCount() + ")");
            negativereviews.setText("不满意(" + head.getBadCount() + ")");
        }
    }

    public static void launch(Activity act, String managerName){
        Intent intent = new Intent(act,LookCommentActivity.class);
        intent.putExtra(Constants.ActivityExtra.MANAGER_NAME, managerName);
        act.startActivity(intent);
    }

}
