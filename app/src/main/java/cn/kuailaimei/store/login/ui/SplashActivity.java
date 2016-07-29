package cn.kuailaimei.store.login.ui;

import cn.kuailaimei.store.common.User;
import cn.kuailaimei.store.common.ui.BaseActivity;
import cn.kuailaimei.store.home.ui.MainActivity;

/**
 * Created by ymh on 2016/7/15 0015.
 */
public class SplashActivity extends BaseActivity{
    @Override
    protected void setContentView() {
//        R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        if(!User.get().isNotLogin()){
            MainActivity.launch(this);
        }else{
            ChooseLoginTypeActivity.launch(this);
        }
        finish();
    }
}
