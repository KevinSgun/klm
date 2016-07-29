package cn.kuailaimei.store.partner.ui;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.common.ui.AppBarActivity;

/**
 * Created by ymh on 2016/7/8 0008.
 */
public class ProfitSettleActivity extends AppBarActivity implements View.OnClickListener {
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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_profit_settle;
    }

    @Override
    protected void initView() {
        setTitle("利润结算");
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

        takecashbtn.setOnClickListener(this);
        bindingaccountbtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.take_cash_btn:
                //TODO 提现操作
                break;
            case R.id.binding_account_btn:
                //TODO 绑定操作
                break;
        }
    }
}
