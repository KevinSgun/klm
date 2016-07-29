package cn.kuailaimei.store.partner.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.request.NullDataRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.api.response.PartnerTotalResponse;
import cn.kuailaimei.store.common.ui.AppBarActivity;

/**
 * Created by ymh on 2016/7/8 0008.
 */
public class PartnerMainActivity extends AppBarActivity implements View.OnClickListener {
    private TextView firstvipcounttv;
    private LinearLayout firstviplayout;
    private TextView secondvipcounttv;
    private LinearLayout secondviplayout;
    private TextView thirdvipcounttv;
    private LinearLayout thirdviplayout;

    private Button settlebtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_partner_main;
    }

    @Override
    protected void initView() {
        setTitle("我的合伙人");
        initialize();
    }

    private void initialize() {
        firstvipcounttv = (TextView) findViewById(R.id.first_vip_count_tv);
        firstviplayout = (LinearLayout) findViewById(R.id.first_vip_layout);
        secondvipcounttv = (TextView) findViewById(R.id.second_vip_count_tv);
        secondviplayout = (LinearLayout) findViewById(R.id.second_vip_layout);
        thirdvipcounttv = (TextView) findViewById(R.id.third_vip_count_tv);
        thirdviplayout = (LinearLayout) findViewById(R.id.third_vip_layout);

        settlebtn = (Button) findViewById(R.id.settle_btn);

        firstviplayout.setOnClickListener(this);
        secondviplayout.setOnClickListener(this);
        thirdviplayout.setOnClickListener(this);
        settlebtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getPartnerTotal();
    }

    private void getPartnerTotal() {
        ApiFactory.getPartnerTotal(new Request(new NullDataRequest())).subscribe(new ProgressSubscriber<ApiResponse<PartnerTotalResponse>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<PartnerTotalResponse> response) {
                refreshUI(response.getData());
            }
        });
    }

    private void refreshUI(PartnerTotalResponse data) {
        firstvipcounttv.setText(String.valueOf(data.getOnePeoples()));
        secondvipcounttv.setText(String.valueOf(data.getTwoPeoples()));
        thirdvipcounttv.setText(String.valueOf(data.getThreePeoples()));
        if(data.getAmountToBeSettled() >0)settlebtn.setText("结算￥"+data.getAmountToBeSettled());
    }

    public static void launch(Activity act) {
        Intent intent = new Intent(act, PartnerMainActivity.class);
        act.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_vip_layout:
                PartnerListActivity.launch(this, "1");
                break;
            case R.id.second_vip_layout:
                PartnerListActivity.launch(this, "2");
                break;
            case R.id.third_vip_layout:
                PartnerListActivity.launch(this, "3");
                break;
            case R.id.settle_btn:
                //结算
                break;
        }
    }

}
