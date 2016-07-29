package cn.kuailaimei.store.login.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import cn.kuailaimei.store.Constants;
import cn.kuailaimei.store.R;
import cn.kuailaimei.store.common.ui.BaseActivity;
import cn.kuailaimei.store.home.ui.MainActivity;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class ChooseLoginTypeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout tenantslayout;
    private LinearLayout tenantsemployee;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_choose_login_type);
    }

    @Override
    protected void initView() {
        tenantslayout = (LinearLayout) findViewById(R.id.tenants_layout);
        tenantsemployee = (LinearLayout) findViewById(R.id.tenants_employee);

        tenantslayout.setOnClickListener(this);
        tenantsemployee.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tenants_layout){
            //TODO 商户登录
            LoginActivity.launch(this,true, Constants.TYPE_BOSS);
            finish();

        }else if(view.getId() == R.id.tenants_employee){
            //TODO 员工登录
            LoginActivity.launch(this,true, Constants.TYPE_EMPLOYEE);
            finish();
        }

    }

    public static void launch(Context context){
        Intent intent = new Intent(context,ChooseLoginTypeActivity.class);
        context.startActivity(intent);
    }
}
