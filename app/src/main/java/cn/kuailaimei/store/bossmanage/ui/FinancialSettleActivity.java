package cn.kuailaimei.store.bossmanage.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import org.greenrobot.eventbus.Subscribe;

import cn.kuailaimei.store.Constants;
import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.entity.BankInfo;
import cn.kuailaimei.store.api.request.NullDataRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.api.response.AccountInfoResponse;
import cn.kuailaimei.store.common.event.EventFactory;
import cn.kuailaimei.store.common.ui.AppBarActivity;
import cn.kuailaimei.store.common.widget.PsdSafeEnsureDialog;
import cn.kuailaimei.store.common.widget.ToolBarHelper;
import cn.kuailaimei.store.partner.ui.TakeCashActivity;
import cn.kuailaimei.store.utils.StringUtils;

/**
 * Created by ymh on 2016/7/8 0008.
 */
public class FinancialSettleActivity extends AppBarActivity implements View.OnClickListener
{
    private TextView takecashbalancetv;
    private Button takecashbtn;
    private TextView hadtakecashtv;
    private TextView intakecashtv;
    private TextView accountinfotv;
    private TextView accounttypetv;
    private TextView accounttv;
    private TextView accountbanktv;
    private TextView accountnametv;
    private Button bindingaccountbtn;
    private LinearLayout accountbanklayout;
    private float canTakeCash;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_financial_settle;
    }
    @Subscribe
    public void onMainThreadAccountChange(EventFactory.AccountChange data){
        requestAccountInfo();
    }
    @Override
    protected void initView() {
        String title = getIntent().getStringExtra(Constants.ActivityExtra.MANAGER_NAME);
        setTitle(title);
        setRightText("账目明细");

        takecashbalancetv = (TextView) findViewById(R.id.take_cash_balance_tv);
        takecashbtn = (Button) findViewById(R.id.take_cash_btn);
        hadtakecashtv = (TextView) findViewById(R.id.had_take_cash_tv);
        intakecashtv = (TextView) findViewById(R.id.in_take_cash_tv);
        accountinfotv = (TextView) findViewById(R.id.account_info_tv);
        accounttypetv = (TextView) findViewById(R.id.account_type_tv);
        accounttv = (TextView) findViewById(R.id.account_tv);
        accountbanktv = (TextView) findViewById(R.id.account_bank_tv);
        accountnametv = (TextView) findViewById(R.id.account_name_tv);
        bindingaccountbtn = (Button) findViewById(R.id.binding_account_btn);
        accountbanklayout = (LinearLayout) findViewById(R.id.account_bank_layout);

        takecashbtn.setOnClickListener(this);
        bindingaccountbtn.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        requestAccountInfo();
    }

    private void requestAccountInfo() {
        Request request = new Request(new NullDataRequest());
        ApiFactory.getAccountInfo(request).subscribe(new ProgressSubscriber<ApiResponse<AccountInfoResponse>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<AccountInfoResponse> response) {
                AccountInfoResponse accountInfo = response.getData();
                refreshUI(accountInfo);
            }
        });
    }

    private void refreshUI(AccountInfoResponse accountInfo) {
        if (accountInfo != null) {
            if(accountInfo.getAmountToBeSettled()>0)
                takecashbtn.setEnabled(true);
            else
                takecashbtn.setEnabled(false);
            canTakeCash = accountInfo.getAmountToBeSettled();
            takecashbalancetv.setText(String.format(getString(R.string.rmb),accountInfo.getAmountToBeSettled()));
            hadtakecashtv.setText(String.format(getString(R.string.rmb),accountInfo.getSettlementAmount()));
            intakecashtv.setText(String.format(getString(R.string.rmb),accountInfo.getFreezingAmount()));
            BankInfo info = accountInfo.getBankInfo();
            if(info != null) {
                bindingaccountbtn.setVisibility(View.GONE);
                accountinfotv.setVisibility(View.GONE);
                if ("支付宝".equals(info.getBankName())) {
                    accountbanklayout.setVisibility(View.GONE);
                    accounttypetv.setText("支付宝");
                    accounttv.setText(StringUtils.hideEmail(info.getBankCode()));
                    accountnametv.setText(StringUtils.hideEnd(info.getName()));
                } else {
                    accountbanklayout.setVisibility(View.VISIBLE);
                    accounttypetv.setText("银行卡");
                    accountbanktv.setText(info.getBankName());
                    accounttv.setText(StringUtils.hideMiddle(info.getBankCode()));
                    accountnametv.setText(StringUtils.hideEnd(info.getName()));
                }
            }else{
                bindingaccountbtn.setVisibility(View.VISIBLE);
                accountinfotv.setVisibility(View.VISIBLE);
            }

        }else{
            bindingaccountbtn.setVisibility(View.VISIBLE);
            accountinfotv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActionBarItemClick(int position) {
        super.onActionBarItemClick(position);
        if(position == ToolBarHelper.ITEM_RIGHT){
            //TODO 账目明细
            DetailAccountListActivity.launch(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.take_cash_btn:
                //TODO 提现操作
                TakeCashActivity.launch(this,canTakeCash);
                break;
            case R.id.binding_account_btn:
                //TODO 绑定操作
                PsdSafeEnsureDialog psdSafeEnsureDialog = new PsdSafeEnsureDialog(this);
                psdSafeEnsureDialog.show();
                break;
        }
    }

    public static void launch(Activity act, String managerName){
        Intent intent = new Intent(act,FinancialSettleActivity.class);
        intent.putExtra(Constants.ActivityExtra.MANAGER_NAME, managerName);
        act.startActivity(intent);
    }
}
