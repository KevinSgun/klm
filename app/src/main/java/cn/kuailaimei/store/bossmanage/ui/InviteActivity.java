package cn.kuailaimei.store.bossmanage.ui;

import android.app.Activity;
import android.content.Intent;

import cn.kuailaimei.store.Constants;
import cn.kuailaimei.store.R;
import cn.kuailaimei.store.common.ui.AppBarActivity;

/**
 * Created by ymh on 2016/7/8 0008.
 */
public class InviteActivity extends AppBarActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_invite;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra(Constants.ActivityExtra.MANAGER_NAME);
        setTitle(title);

    }

    @Override
    protected void initData() {

    }

    public static void launch(Activity act, String managerName){
        Intent intent = new Intent(act,InviteActivity.class);
        intent.putExtra(Constants.ActivityExtra.MANAGER_NAME, managerName);
        act.startActivity(intent);
    }
}
