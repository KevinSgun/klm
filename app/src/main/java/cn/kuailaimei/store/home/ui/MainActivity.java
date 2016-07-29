package cn.kuailaimei.store.home.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import cn.jpush.android.api.JPushInterface;
import cn.kuailaimei.store.Constants;
import cn.kuailaimei.store.R;
import cn.kuailaimei.store.common.User;
import cn.kuailaimei.store.common.ui.BaseActivity;
import cn.kuailaimei.store.common.ui.BaseFragment;
import cn.kuailaimei.store.utils.ToastMaster;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private long exitTime;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.main_radio_group);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        jPushInit();
        int position = getIntent().getIntExtra(Constants.ActivityExtra.CHECK_POSITION, 0);
        if (position == 0) {
            showFragment(HomePageFragment.class);
        } else if (position == 2) {
            // showFragment(RecentMessagesFragment.class);
//            radioGroup.check(R.id.main_menu_chat);
        } else {
//            showFragment(MyHomeFragment.class);
        }
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void jPushInit(){
        JPushInterface.init(getApplicationContext());
    }

    private static final int CHECK_UPDATE = 0x10;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case CHECK_UPDATE:
                    checkUpdate();
                    break;
            }
            return false;
        }
    });

    public Fragment showFragment(Class<? extends Fragment> fragmentClass) {
        return BaseFragment.replace(getSupportFragmentManager(), R.id.content_frame, fragmentClass);
    }

    public static void launch(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) { // 第一次System.currentTimeMillis()无论何时调用，差值肯定大于2000
                ToastMaster.shortToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void checkUpdate() {
    }

    @Override
    public void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.main_menu_home:
                showFragment(HomePageFragment.class);
                break;
            case R.id.main_menu_store_manager:
                if(User.get().isBoss())
                    showFragment(ManagerForBossFragment.class);
                else
                    showFragment(ManagerForEmployeeFragment.class);
                break;
            case R.id.main_menu_mine:
                showFragment(MineFragment.class);
                break;
            default:
                break;
        }
    }
}
