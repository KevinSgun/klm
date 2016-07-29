package cn.kuailaimei.store.pushmsg.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ViewUtils;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.request.PushRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.common.ui.AppBarActivity;
import cn.kuailaimei.store.utils.StringUtils;
import cn.kuailaimei.store.utils.ToastMaster;

/**
 * Created by ymh on 2016/7/17 0017.
 */
public class PushMsgActivity extends AppBarActivity implements View.OnClickListener {
    private EditText msget;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_push_msg;
    }

    @Override
    protected void initView() {
        setTitle("推送消息");

        msget = (EditText) findViewById(R.id.msg_et);
        Button commitBtn = (Button) findViewById(R.id.commit_btn);

        commitBtn.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        //推送消息
        if(checkPass()){
            PushRequest pushRequest = new PushRequest();
            pushRequest.setMessage(msget.getText().toString());
            Request request = new Request(pushRequest);
            request.sign();
            ApiFactory.pushMsg(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
                @Override
                protected void onNextInActive(ApiResponse apiResponse) {
                    ToastMaster.shortToast(apiResponse.getBasic().getMsg());
                    finish();
                }
            });
        }
    }

    private boolean checkPass() {
        if(StringUtils.etvIsEmpty(msget)){
            ToastMaster.shortToast("推送消息内容不能为空");
            return false;
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (ViewUtils.isTouchedOutView(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public static void launch(Activity act){
        Intent intent = new Intent(act,PushMsgActivity.class);
        act.startActivity(intent);
    }
}
