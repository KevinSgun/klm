package cn.kuailaimei.store.mine.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.request.Feedback;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.common.ui.AppBarActivity;
import cn.kuailaimei.store.utils.ToastMaster;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class FeedBackActivity extends AppBarActivity {
    private EditText contactmethodet;
    private EditText suggestet;
    private Button commitbtn;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        setTitle(R.string.feed_back);
        contactmethodet = (EditText) findViewById(R.id.contact_method_et);
        suggestet = (EditText) findViewById(R.id.suggest_et);
        commitbtn = (Button) findViewById(R.id.commit_btn);
        commitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commitFeedBack();
            }

        });
    }

    private void commitFeedBack() {
        if(TextUtils.isEmpty(suggestet.getText().toString())){
            ToastMaster.shortToast("输入您的问题或者建议（必填）");
            return;
        }

        Feedback feedback = new Feedback();
        feedback.setMobile(contactmethodet.getText().toString());
        feedback.setContent(suggestet.getText().toString());
        Request request = new Request(feedback);
        ApiFactory.feedback(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
            @Override
            protected void onNextInActive(ApiResponse apiResponse) {
                ToastMaster.shortToast(apiResponse.getBasic().getMsg());
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    public static void launch(Activity act){
        Intent intent = new Intent(act,FeedBackActivity.class);
        act.startActivity(intent);
    }

}
