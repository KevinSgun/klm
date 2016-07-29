package cn.kuailaimei.store.mine.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ViewUtils;

import org.greenrobot.eventbus.Subscribe;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.entity.BankInfo;
import cn.kuailaimei.store.api.request.NullDataRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.api.response.BankInfoResponse;
import cn.kuailaimei.store.common.event.EventFactory;
import cn.kuailaimei.store.common.ui.AppBarActivity;
import cn.kuailaimei.store.common.widget.PsdSafeEnsureDialog;
import cn.kuailaimei.store.utils.StringUtils;

/**
 * Created by ymh on 2016/7/6 0006.
 */
public class MyAccountActivity extends AppBarActivity implements View.OnClickListener {
    private TextView zfbaccounttv;
    private TextView zfbaccountnametv;
    private LinearLayout zfblayout;
    private TextView bankaccounttv;
    private TextView banknametv;
    private TextView bankaccountnametv;
    private LinearLayout banklayout;
    private Button rebidingbtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_account;
    }

    @Subscribe
    public void onMainThreadBindingChange(EventFactory.BindingChange data){
        requestBankInfo();
    }

    @Override
    protected void initView() {
        setTitle(R.string.my_account);
        zfbaccounttv = (TextView) findViewById(R.id.zfb_account_tv);
        zfbaccountnametv = (TextView) findViewById(R.id.zfb_account_name_tv);
        zfblayout = (LinearLayout) findViewById(R.id.zfb_layout);
        bankaccounttv = (TextView) findViewById(R.id.bank_account_tv);
        banknametv = (TextView) findViewById(R.id.bank_name_tv);
        bankaccountnametv = (TextView) findViewById(R.id.bank_account_name_tv);
        banklayout = (LinearLayout) findViewById(R.id.bank_layout);
        rebidingbtn = (Button) findViewById(R.id.re_biding_btn);

        rebidingbtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        requestBankInfo();
    }

    private void requestBankInfo() {
        Request request = new Request(new NullDataRequest());
        ApiFactory.getBankInfo(request).subscribe(new ProgressSubscriber<ApiResponse<BankInfoResponse>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<BankInfoResponse> response) {
                BankInfoResponse bankInfo = response.getData();
                refreshUI(bankInfo);
            }
        });
    }

    private void refreshUI(BankInfoResponse bankInfo) {
        if (bankInfo != null) {
            rebidingbtn.setText("重新绑定账户");
            BankInfo info = bankInfo.getBankInfo();
            if ("支付宝".equals(info.getBankName())) {
                zfblayout.setVisibility(View.VISIBLE);
                banklayout.setVisibility(View.GONE);

                zfbaccounttv.setText(StringUtils.hideEmail(info.getBankCode()));
                zfbaccountnametv.setText(StringUtils.hideEnd(info.getName()));
            } else {
                zfblayout.setVisibility(View.GONE);
                banklayout.setVisibility(View.VISIBLE);

                bankaccounttv.setText(StringUtils.hideMiddle(info.getBankCode()));
                banknametv.setText(info.getBankName());
                bankaccountnametv.setText(StringUtils.hideEnd(info.getName()));

            }
        }else{
            zfblayout.setVisibility(View.GONE);
            banklayout.setVisibility(View.GONE);
            rebidingbtn.setText("绑定账户");
        }

    }

    public static void launch(Activity act) {
        Intent intent = new Intent(act, MyAccountActivity.class);
        act.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(ViewUtils.isFastDoubleClick()) return;
        PsdSafeEnsureDialog safeEnsureDialog = new PsdSafeEnsureDialog(this);
        safeEnsureDialog.show();
    }
}
