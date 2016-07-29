package cn.kuailaimei.store.pushmsg.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.LogUtils;

import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.request.NullDataRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.common.ui.AppBarActivity;
import cn.kuailaimei.store.common.widget.CommonDialog;
import cn.kuailaimei.store.common.widget.ContentViewHolder;
import cn.kuailaimei.store.common.widget.ToolBarHelper;
import cn.kuailaimei.store.pushmsg.adapter.MessageAdapter;
import cn.kuailaimei.store.utils.StringUtils;
import cn.kuailaimei.store.utils.ToastMaster;

/**
 * Created by ymh on 2016/7/23 0023.
 */
public class MessageActivity extends AppBarActivity{
    public static boolean isForeground;
    private MessageReceiver mMessageReceiver;
    private ListView msglistview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MessageAdapter mAdapter;
    private ContentViewHolder viewAnimator;
    public static final String MESSAGE_RECEIVED_ACTION = "cn.kuailaimei.store.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private static final int DISMISS_LOADING = 23;
    private Handler mHander = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == DISMISS_LOADING){
                if(swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
            }
            return false;
        }
    });

    @Override
    protected int getContentViewId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView() {
        setTitle(R.string.message);
        initialize();
    }

    @Override
    protected void initData() {
        registerMessageReceiver();
        getMsgS();
    }

    private void getMsgS() {
        viewAnimator.showContent();
        ApiFactory.getMessages(new Request(new NullDataRequest())).subscribe(new ProgressSubscriber<ApiResponse<List<cn.kuailaimei.store.api.entity.Message>>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<List<cn.kuailaimei.store.api.entity.Message>> response) {
                if(response.getData()!=null&&response.getData().size()>0){
                    setRightText(R.string.clear);
                    mAdapter.setList(response.getData());
                }else{
                    setRightTextVisiable(View.GONE);
                    viewAnimator.showNoData();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onActionBarItemClick(int position) {
        super.onActionBarItemClick(position);
        if(position == ToolBarHelper.ITEM_RIGHT&&mAdapter.getCount()>0){
            CommonDialog commonDialog = new CommonDialog(this,"确定要清空所有的消息吗");
            commonDialog.setNegativeButtonText("清空");
            commonDialog.setPositiveButtonText("取消");
            commonDialog.setOnNegativeButtonClickListener(new CommonDialog.OnNegativeButtonClickListener() {
                @Override
                public void onClick(Dialog dialog) {
                    clearAllMsg();
                }
            });
            commonDialog.show();
        }
    }

    private void clearAllMsg() {
        ApiFactory.clearMessages(new Request(new NullDataRequest())).subscribe(new ProgressSubscriber<ApiResponse>(this) {
            @Override
            protected void onNextInActive(ApiResponse apiResponse) {
                ToastMaster.shortToast(apiResponse.getBasic().getMsg());
                setRightTextVisiable(View.GONE);
                mAdapter.clear();
            }
        });
    }

    private void initialize() {
        msglistview = (ListView) findViewById(R.id.msg_list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        viewAnimator = (ContentViewHolder) findViewById(R.id.viewAnimator);
        mAdapter = new MessageAdapter(this);
        msglistview.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHander.sendEmptyMessageDelayed(DISMISS_LOADING,1500);
                getMsgS();
            }
        });
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!StringUtils.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                LogUtils.i("-----JPush------"+showMsg.toString());
                ToastMaster.longToast(showMsg.toString());
            }
        }
    }

    public static void launch(Context context){
        Intent intent = new Intent(context,MessageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        isForeground = false;
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
