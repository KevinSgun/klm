package cn.kuailaimei.store.common.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.ValidDialog;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.request.PassWordRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.common.ui.BaseActivity;
import cn.kuailaimei.store.login.ui.ForgetPassWordActivity;
import cn.kuailaimei.store.mine.ui.BindingAccountActivity;
import cn.kuailaimei.store.utils.StringUtils;
import cn.kuailaimei.store.utils.ToastMaster;

/**
 * Created by ymh on 2016/7/6 0006.
 */
public class PsdSafeEnsureDialog extends ValidDialog implements View.OnClickListener {
    private Context mContext;
    private EditText inputpsdet;
    private TextView findpsdtv;
    private Button cancelbtn;
    private Button confirmbtn;

    public PsdSafeEnsureDialog(Context context) {
        super(context, R.style.CommonDialog);
        mContext = context;
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_psd_safe_ensure);

        inputpsdet = (EditText) findViewById(R.id.input_psd_et);
        findpsdtv = (TextView) findViewById(R.id.find_psd_tv);
        cancelbtn = (Button) findViewById(R.id.cancel_btn);
        confirmbtn = (Button) findViewById(R.id.confirm_btn);

        findpsdtv.setOnClickListener(this);
        cancelbtn.setOnClickListener(this);
        confirmbtn.setOnClickListener(this);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = ViewUtils.getDimenPx(R.dimen.w660);
        params.height = ViewUtils.getDimenPx(R.dimen.w430);
        getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.find_psd_tv:
                //TODO 找回密码
                ForgetPassWordActivity.launch((Activity) mContext);
                break;
            case R.id.cancel_btn:
                dismiss();
                break;
            case R.id.confirm_btn:
                //TODO 密码正确则进入绑定页面
                if(checkPass()) {
                    PassWordRequest pRequest = new PassWordRequest();
                    pRequest.setPassword(inputpsdet.getText().toString());
                    Request request = new Request(pRequest);
                    request.sign();
                    ApiFactory.checkPsd(request).subscribe(new ProgressSubscriber<ApiResponse>((BaseActivity)mContext) {
                        @Override
                        protected void onNextInActive(ApiResponse apiResponse) {
                            dismiss();
                            BindingAccountActivity.launch((Activity) mContext);
                        }
                    });
                }
                break;
        }
    }

    private boolean checkPass() {
        if(StringUtils.etvIsEmpty(inputpsdet)){
            ToastMaster.shortToast("请输入密码");
            return false;
        }
        if(!StringUtils.passWordCheck(inputpsdet.getText().toString())){
            ToastMaster.shortToast("请输入6-15数字或字母密码");
            return false;
        }
        return true;
    }
}
