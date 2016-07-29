package cn.kuailaimei.store.common.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import cn.kuailaimei.store.Constants;
import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.EditData;
import cn.kuailaimei.store.common.event.EventFactory;
import cn.kuailaimei.store.utils.StringUtils;
import cn.kuailaimei.store.utils.ToastMaster;

/**
 * Created by ymh on 2016/7/17 0017.
 */
public class EditActivity extends AppBarActivity implements TextWatcher, View.OnClickListener {
    private long lastClickTime;
    private EditText contentet;
    private Button commitbtn;
    private EditData editData;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void initView() {
        editData = getIntent().getParcelableExtra(Constants.ActivityExtra.EDIT_DATA);
        contentet = (EditText) findViewById(R.id.content_et);
        commitbtn = (Button) findViewById(R.id.commit_btn);

        contentet.addTextChangedListener(this);
        commitbtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        int title = editData.getTitleResId();
        if(title!=0) setTitle(title);
        String content = editData.getContentStr();
        int hintContent = editData.getHintResId();
        if(hintContent!=0) {
            contentet.setHint(hintContent);
        }
        if(!TextUtils.isEmpty(content)){
            contentet.setText(content);
            contentet.setSelection(content.length());
            commitbtn.setEnabled(true);
        }
        contentet.setFilters(new InputFilter[]{new InputFilter.LengthFilter(editData.getLimit()+1)});
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (StringUtils.etvIsEmpty(contentet)) {
            commitbtn.setEnabled(false);
        } else {
//            if(isFastDoubleClick()) return;
            if(contentet.getText().toString().length()>editData.getLimit()){
                if(!isFastDoubleClick())ToastMaster.shortToast(editData.getLimitTips());
                commitbtn.setEnabled(false);
            }else{
                commitbtn.setEnabled(true);
            }
        }
    }

    /**
     * 防止快速点击
     *
     * @return
     */
    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (500 < timeD) {
            lastClickTime = time;
            return false;
        }
        lastClickTime = time;
        return true;
    }

    @Override
    public void onClick(View v) {
        String content = contentet.getText().toString();
        EventFactory.EditContent data = new EventFactory.EditContent();
        data.editContent = content;
        EventBus.getDefault().post(data);
        finish();
    }

    public static void launch(Activity context, EditData data) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(Constants.ActivityExtra.EDIT_DATA,data);
        context.startActivity(intent);
    }
}
