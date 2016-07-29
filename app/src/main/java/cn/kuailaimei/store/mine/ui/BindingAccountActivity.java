package cn.kuailaimei.store.mine.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.request.BindingCardRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.common.event.EventFactory;
import cn.kuailaimei.store.common.ui.AppBarActivity;
import cn.kuailaimei.store.common.widget.MutilRadioGroup;
import cn.kuailaimei.store.utils.StringUtils;
import cn.kuailaimei.store.utils.ToastMaster;

/**
 * Created by ymh on 2016/7/6 0006.
 */
public class BindingAccountActivity extends AppBarActivity implements MutilRadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private RadioButton bankrb;
    private RadioButton zfbrb;
    private MutilRadioGroup bankrg;
    private EditText bankaccountet;
    private EditText banket;
    private EditText bankpointet;
    private EditText bankaccountnameet;
    private LinearLayout banklayout;
    private EditText zfbaccountet;
    private EditText zfbaccountnameet;
    private LinearLayout zfblayout;
    private Button rebidingbtn;
    private static final int ZFB = 0;
    private static final int BANK = 1;
    private int accountType;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bing_account;
    }

    @Override
    protected void initView() {
        setTitle(R.string.binding_account);
        initialize();
    }

    @Override
    protected void initData() {

    }

    private void initialize() {

        bankrb = (RadioButton) findViewById(R.id.bank_rb);
        zfbrb = (RadioButton) findViewById(R.id.zfb_rb);
        bankrg = (MutilRadioGroup) findViewById(R.id.bank_rg);

        banklayout = (LinearLayout) findViewById(R.id.bank_layout);//绑定银行账号的布局
        bankaccountet = (EditText) findViewById(R.id.bank_account_et);//银行卡号
        banket = (EditText) findViewById(R.id.bank_et);//银行名字
        bankpointet = (EditText) findViewById(R.id.bank_point_et);//支行名字
        bankaccountnameet = (EditText) findViewById(R.id.bank_account_name_et);//银行开户真实姓名

        zfblayout = (LinearLayout) findViewById(R.id.zfb_layout);//绑定支付宝账号的布局
        zfbaccountet = (EditText) findViewById(R.id.zfb_account_et);//支付宝账号
        zfbaccountnameet = (EditText) findViewById(R.id.zfb_account_name_et);//支付宝


        rebidingbtn = (Button) findViewById(R.id.re_biding_btn);

        bankrg.setOnCheckedChangeListener(this);
        rebidingbtn.setOnClickListener(this);


        banklayout.setVisibility(View.GONE);
        zfblayout.setVisibility(View.VISIBLE);

    }


    @Override
    public void onCheckedChanged(MutilRadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.bank_rb:
                accountType = BANK;
                zfblayout.setVisibility(View.GONE);
                banklayout.setVisibility(View.VISIBLE);
                break;
            case R.id.zfb_rb:
                accountType = ZFB;
                banklayout.setVisibility(View.GONE);
                zfblayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    public static void launch(Activity act) {
        Intent intent = new Intent(act, BindingAccountActivity.class);
        act.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.re_biding_btn){
            if(checkPass()){
                BindingCardRequest bRequest = new BindingCardRequest();
                bRequest.setBankName(accountType == ZFB?"支付宝":bankpointet.getText().toString());
                bRequest.setBankCode(accountType == ZFB?zfbaccountet.getText().toString():bankaccountet.getText().toString());
                bRequest.setName(accountType == ZFB?zfbaccountnameet.getText().toString():bankaccountnameet.getText().toString());

                Request request = new Request(bRequest);
                request.sign();
                ApiFactory.bindingCard(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
                    @Override
                    protected void onNextInActive(ApiResponse apiResponse) {
                        ToastMaster.shortToast(apiResponse.getBasic().getMsg());
                        EventBus.getDefault().post(new EventFactory.BindingChange());
                        finish();
                    }
                });

            }
        }
    }

    private boolean checkPass() {
        if(accountType == ZFB){
            if(StringUtils.etvIsEmpty(zfbaccountet)){
                ToastMaster.shortToast("支付宝账号不能为空");
                return false;
            }
            if(StringUtils.etvIsEmpty(zfbaccountnameet)){
                ToastMaster.shortToast("账号名不能为空");
                return false;
            }
        }else{
            if(StringUtils.etvIsEmpty(bankaccountet)){
                ToastMaster.shortToast("银行卡号不能为空");
                return false;
            }
            if(StringUtils.etvIsEmpty(bankpointet)){
                ToastMaster.shortToast("开户支行不能为空");
                return false;
            }
            if(StringUtils.etvIsEmpty(bankaccountnameet)){
                ToastMaster.shortToast("开户真实姓名不能为空");
                return false;
            }
        }

        return true;
    }
}
