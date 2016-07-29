package cn.kuailaimei.store.turnover.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.request.NullDataRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.api.response.StoreTotalResponse;
import cn.kuailaimei.store.common.User;
import cn.kuailaimei.store.common.ui.AppBarActivity;

/**
 * Created by ymh on 2016/7/7 0007.
 */
public class TurnoverMainActivity extends AppBarActivity implements View.OnClickListener {
    private TextView cashtv;
    private LinearLayout cashlayout;
    private TextView salecardtv;
    private LinearLayout salecardlayout;
    private TextView totalordercounttv;
    private LinearLayout totalordercountlayout;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_turnover_main;
    }

    @Override
    protected void initView() {
        initialize();
    }

    private void initialize() {
        setTitle("总成交额");
        cashtv = (TextView) findViewById(R.id.cash_tv);
        cashlayout = (LinearLayout) findViewById(R.id.cash_layout);
        salecardtv = (TextView) findViewById(R.id.sale_card_tv);
        salecardlayout = (LinearLayout) findViewById(R.id.sale_card_layout);
        totalordercounttv = (TextView) findViewById(R.id.total_order_count_tv);
        totalordercountlayout = (LinearLayout) findViewById(R.id.total_order_count_layout);

        cashlayout.setOnClickListener(this);
        salecardlayout.setOnClickListener(this);
        totalordercountlayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if(User.get().isBoss())
            getBossTotal();
        else
            getEmpTotal();
    }

    /**
     * 获取老板店铺业绩统计
     */
    private void getBossTotal() {
        ApiFactory.getStoreTotal(new Request(new NullDataRequest())).subscribe(new ProgressSubscriber<ApiResponse<StoreTotalResponse>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<StoreTotalResponse> response) {
                refreshUI(response.getData());
            }
        });
    }

    /**
     * 获取员工个人业绩统计
     */
    private void getEmpTotal() {
        ApiFactory.getEmpTotal(new Request(new NullDataRequest())).subscribe(new ProgressSubscriber<ApiResponse<StoreTotalResponse>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<StoreTotalResponse> response) {
                refreshUI(response.getData());
            }
        });
    }

    private void refreshUI(StoreTotalResponse data) {
        if(data!=null){
            cashtv.setText(String.format(getString(R.string.rmb),data.getCashAmount()));
            salecardtv.setText(String.format(getString(R.string.rmb),data.getXkAmount()));
            totalordercounttv.setText(String.valueOf(data.getOrderCount()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cash_layout:
                //现金业绩
                CashPerformanceActivity.launch(this);
                break;
            case R.id.sale_card_layout:
                //销卡业绩
                SaleCardPerformanceActivity.launch(this);
                break;
            case R.id.total_order_count_layout:
                //订单总数
                TotalOrderCountActivity.launch(this);
                break;
        }
    }

    public static void launch(Activity act){
        Intent intent = new Intent(act,TurnoverMainActivity.class);
        act.startActivity(intent);
    }
}
