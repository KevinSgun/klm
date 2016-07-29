package cn.kuailaimei.store.common.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zitech.framework.data.network.IContext;
import com.zitech.framework.utils.VersionUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.kuailaimei.store.common.event.EventFactory;

/**
 * Created by lu on 2016/6/12.
 */
public abstract class BaseActivity extends AppCompatActivity implements IContext {


    protected void handleIntent() {

    }

    protected abstract void setContentView();

    protected abstract void initView();

    protected abstract void initData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView();
        handleIntent();
        initView();
        initData();
    }

    @Subscribe
    public void onMainThreadReceived(EventFactory.CloseAll data){
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean isActive() {
        if (VersionUtils.hasIceScreamSandwich()) {
            return !isDestroyed();
        }
        return !isFinishing();
    }

    @Override
    public Context getContext() {
        return this;
    }

    /**
     * @param cls 目标activity
     *            跳转并finish当前activity
     * @throws ActivityNotFoundException
     */
    public void skipActivity(Class<?> cls) {
        showActivity(cls);
        finish();
    }

    /***
     * @param it
     */
    public void skipActivity(Intent it) {
        super.startActivity(it);
        finish();
    }

    /***
     * @param it
     */
    public void showActivity(Intent it) {
        super.startActivity(it);
    }

    /**
     * @param cls
     * @param extras
     */
    public void skipActivity(Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(this, cls);
        startActivity(intent);
        finish();
    }

    public void showActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        super.startActivity(intent);
    }

    public void showActivity(Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.putExtras(extras);
        super.startActivity(intent);
    }


}
