package cn.kuailaimei.store.login.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zitech.framework.data.network.entity.Basic;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.kuailaimei.store.Constants;
import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.request.LoginRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.api.response.UserInfoResponse;
import cn.kuailaimei.store.common.User;
import cn.kuailaimei.store.common.ui.AppBarActivity;
import cn.kuailaimei.store.home.ui.MainActivity;
import cn.kuailaimei.store.utils.StringUtils;
import cn.kuailaimei.store.utils.ToastMaster;
import cn.kuailaimei.store.utils.Utils;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class LoginActivity extends AppBarActivity implements View.OnClickListener {
    private EditText inputphoneet;
    private EditText inputpsdet;
    private TextView freeregistertv;
    private TextView forgetpsdtv;
    private Button loginbtn;
    private boolean isFirstLaunchMain;
    private int loginType;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        isFirstLaunchMain = getIntent().getBooleanExtra(Constants.ActivityExtra.LOGIN_ABOUT,true);
        loginType = getIntent().getIntExtra(Constants.ActivityExtra.LOGIN_TYPE,0);
        toolbar.setLeftVisible(View.GONE);
        setTitle(R.string.login);
        initialize();
    }

    @Override
    protected void initData() {
        if(!TextUtils.isEmpty(User.get().getMobile())) {
            inputphoneet.setText(User.get().getMobile());
            inputpsdet.requestFocus();
        }
    }

    private void initialize() {
        inputphoneet = (EditText) findViewById(R.id.input_phone_et);
        inputpsdet = (EditText) findViewById(R.id.input_psd_et);
        freeregistertv = (TextView) findViewById(R.id.free_register_tv);
        forgetpsdtv = (TextView) findViewById(R.id.forget_psd_tv);
        loginbtn = (Button) findViewById(R.id.login_btn);
        loginbtn.setOnClickListener(this);
        freeregistertv.setOnClickListener(this);
        forgetpsdtv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        if(view.getId() == R.id.free_register_tv){
            //注册
//            skipActivity(RegisterActivity.class);
//        }else
        if(view.getId() == R.id.forget_psd_tv){
            //忘记密码
            showActivity(ForgetPassWordActivity.class);
        }else if(view.getId() == R.id.login_btn){
            //登录首页
            if(inputStatusIsCorrect()){
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUserName(inputphoneet.getText().toString());
                loginRequest.setPassword(inputpsdet.getText().toString());
                Request request = new Request(loginRequest);
                request.sign();
                ApiFactory.requestLogin(request).subscribe(new ProgressSubscriber<ApiResponse<UserInfoResponse>>(this) {
                    @Override
                    protected void onNextInActive(ApiResponse<UserInfoResponse> userInfoResponseApiResponse) {
                        Basic basic = userInfoResponseApiResponse.getBasic();
                        if(basic.getStatus() == 1){
                            User.get().storeFromUserInfo(userInfoResponseApiResponse.getData());
                            JPushInterface.setAliasAndTags(getApplicationContext(),User.get().getMobile(),null,mAliasCallback);
                            if(isFirstLaunchMain)
                                skipActivity(MainActivity.class);
                            else
                                finish();
                        }
                        ToastMaster.shortToast(basic.getMsg());
                    }
                });
            }
        }
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            if(code == 0){
                Utils.bindJGPushIdToService(alias);
            }
        }

    };

    private boolean inputStatusIsCorrect() {
        if(TextUtils.isEmpty(inputphoneet.getText().toString())){
            ToastMaster.shortToast("手机号不能为空");
            return false;
        }

        if(inputphoneet.getText().toString().length()!=11){
            ToastMaster.shortToast("请输入11位手机号");
            return false;
        }

        if(!StringUtils.isPhoneNum(inputphoneet.getText().toString())){
            ToastMaster.shortToast("手机号格式不正确");
            return false;
        }

        if(TextUtils.isEmpty(inputpsdet.getText().toString())){
            ToastMaster.shortToast("密码不能为空");
            return false;
        }

        return true;
    }

    /**
     *
     * @param act
     * @param firstLaunchMain 是否为第一次登录首页MainActivity，默认为true
     *                         为true表示需要新打开一个MainActivity
     *                         为false表示不需要新打开MainActivity
     * @param loginType  员工登录为0，老板登录为1
     */
    public static void launch(Activity act,boolean firstLaunchMain,int loginType){
        Intent intent = new Intent(act,LoginActivity.class);
        intent.putExtra(Constants.ActivityExtra.LOGIN_ABOUT,firstLaunchMain);
        intent.putExtra(Constants.ActivityExtra.LOGIN_TYPE,loginType);
        act.startActivity(intent);
    }
}
