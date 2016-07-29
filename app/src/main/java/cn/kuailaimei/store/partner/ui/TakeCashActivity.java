package cn.kuailaimei.store.partner.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.request.MoneyRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.common.event.EventFactory;
import cn.kuailaimei.store.common.ui.AppBarActivity;
import cn.kuailaimei.store.utils.StringUtils;
import cn.kuailaimei.store.utils.ToastMaster;

/**
 * Created by ymh on 2016/7/8 0008.
 */
public class TakeCashActivity extends AppBarActivity implements View.OnClickListener {
    private TextView profittv;
    private EditText takecashet;
    private static final String CAN_TAKE_CASH = "can_take_cash";
    private float canTakeCash;
    private Button commitbtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_take_cash;
    }

    @Override
    protected void initView() {
        setTitle("提现");
        canTakeCash = getIntent().getFloatExtra(CAN_TAKE_CASH, 0);
        profittv = (TextView) findViewById(R.id.profit_tv);
        takecashet = (EditText) findViewById(R.id.take_cash_et);
        commitbtn = (Button) findViewById(R.id.commit_btn);
        takecashet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 1) return;
                EditText editText = null;
                if (takecashet.isFocused())
                    editText = takecashet;
                if (editText == null) return;
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        commitbtn.setOnClickListener(this);
        if (canTakeCash > 0)
            commitbtn.setEnabled(true);
        else
            commitbtn.setEnabled(false);
    }

    @Override
    protected void initData() {

    }

    public static void launch(Activity act, float canTakeCash) {
        Intent intent = new Intent(act, TakeCashActivity.class);
        intent.putExtra(CAN_TAKE_CASH, canTakeCash);
        act.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (ViewUtils.isFastDoubleClick()) return;
        if (checkPass())
            takeCash();
    }

    private boolean checkPass() {
        if(StringUtils.etvIsEmpty(takecashet)){
            ToastMaster.shortToast("提现金额不能为空");
            return false;
        }
        String currentCash = takecashet.getText().toString();
        float currentCahF = Float.parseFloat(currentCash);
        if(currentCahF>canTakeCash){
            ToastMaster.shortToast("提现金额不能大于可提现金额");
            return false;
        }
        return true;
    }

    private void takeCash() {
        MoneyRequest moneyRequest = new MoneyRequest();
        moneyRequest.setMoney(takecashet.getText().toString());
        Request request = new Request(moneyRequest);
        request.sign();
        ApiFactory.takeCash(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
            @Override
            protected void onNextInActive(ApiResponse apiResponse) {
                ToastMaster.shortToast(apiResponse.getBasic().getMsg());
                EventBus.getDefault().post(new EventFactory.AccountChange());
                finish();
            }
        });
    }
}
