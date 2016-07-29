package cn.kuailaimei.store.bossmanage.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.entity.EmpItem;
import cn.kuailaimei.store.api.entity.RoleItem;
import cn.kuailaimei.store.api.request.AddOrEditEmpInfoRequest;
import cn.kuailaimei.store.api.request.PageRequest;
import cn.kuailaimei.store.api.request.PageRequestData;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.common.event.EventFactory;
import cn.kuailaimei.store.common.ui.AppBarActivity;
import cn.kuailaimei.store.common.widget.CommonDialog;
import cn.kuailaimei.store.common.widget.ToolBarHelper;
import cn.kuailaimei.store.common.widget.timepicker.OptionsPickerView;
import cn.kuailaimei.store.utils.StringUtils;
import cn.kuailaimei.store.utils.ToastMaster;

/**
 * Created by ymh on 2016/7/9 0009.
 */
public class EditEmpActivity extends AppBarActivity implements View.OnClickListener {
    private EditText storenameet;
    private LinearLayout choosepositionlayout;
    private EditText jobnumet;
    private EditText empaccountet;
    private EditText accountpsdet;
    private Button deletebtn;
    private static final String IS_EDITE = "is_edit";
    private static final String EMPITEM = "empitem";
    private boolean isEdite;
    private EmpItem empItem;
    private boolean isChange;
    private LinearLayout empaccountlayout;
    private ArrayList<RoleItem> roleList;
    private TextView positiontv;
    private String rId;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit_emp;
    }

    @Override
    protected void initView() {
        setTitle(R.string.emp_manager);
        setRightText(R.string.save);

        isEdite = getIntent().getBooleanExtra(IS_EDITE, false);
        empItem = getIntent().getParcelableExtra(EMPITEM);

        storenameet = (EditText) findViewById(R.id.store_name_et);
        choosepositionlayout = (LinearLayout) findViewById(R.id.choose_position_layout);
        jobnumet = (EditText) findViewById(R.id.job_num_et);
        empaccountet = (EditText) findViewById(R.id.emp_account_et);
        accountpsdet = (EditText) findViewById(R.id.account_psd_et);
        deletebtn = (Button) findViewById(R.id.delete_btn);
        empaccountlayout = (LinearLayout) findViewById(R.id.emp_account_layout);
        positiontv = (TextView) findViewById(R.id.position_tv);

        choosepositionlayout.setOnClickListener(this);
        if (isEdite) {
            empaccountlayout.setVisibility(View.GONE);
            deletebtn.setVisibility(View.VISIBLE);
            deletebtn.setOnClickListener(this);
        } else {
            deletebtn.setVisibility(View.GONE);
            empaccountlayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void initData() {
        PageRequest request = new PageRequest(new PageRequestData());
        ApiFactory.getRoleList(request).subscribe(new ProgressSubscriber<ApiResponse<ArrayList<RoleItem>>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<ArrayList<RoleItem>> response) {
                roleList = response.getData();
            }
        });
        if (empItem != null) {
            positiontv.setText(empItem.getPosition());
            storenameet.setText(empItem.getAlias());
            jobnumet.setText(empItem.getEmployeeId());
            empaccountet.setText(empItem.getMobile());
            accountpsdet.setText("***");
            accountpsdet.setEnabled(false);
            rId = String.valueOf(empItem.getRid());
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_position_layout:
                //TODO 选择职位
                OptionsPickerView<RoleItem> pickerView = new OptionsPickerView<>(this);
                pickerView.setPicker(roleList);
                pickerView.setCyclic(false);
                pickerView.setCancelable(true);
                pickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        positiontv.setText(roleList.get(options1).getPosition());
                        rId = roleList.get(options1).getRid();
                    }
                });
                pickerView.show();
                break;
            case R.id.delete_btn:
                //TODO 删除职位
                CommonDialog commonDialog = new CommonDialog(this, "删除将清除该员工所有数据");
                //这里由于是删除操作，为减少误触几率，故将删除放左边
                commonDialog.setOnNegativeButtonClickListener(new CommonDialog.OnNegativeButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        deleteEmp();
                    }
                });
                commonDialog.setNegativeButtonText(getResources().getString(R.string.delete));
                commonDialog.setPositiveButtonText(getResources().getString(R.string.cancel));
                commonDialog.show();
                break;
        }

    }

    @Override
    protected void onActionBarItemClick(int position) {
        super.onActionBarItemClick(position);
        if (position == ToolBarHelper.ITEM_RIGHT) {
            if(!checkPass()) return;
            if (isChange) {
                AddOrEditEmpInfoRequest addRequest = new AddOrEditEmpInfoRequest();
                addRequest.setAlias(storenameet.getText().toString());
                addRequest.setPosition(positiontv.getText().toString());
                addRequest.setRid(rId);
                addRequest.setEmployeeId(jobnumet.getText().toString());
                if(isEdite) {
                    addRequest.setUid(String.valueOf(empItem.getUid()));
                    Request request = new Request(addRequest);
                    request.sign();
                    ApiFactory.editEmpInfo(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
                        @Override
                        protected void onNextInActive(ApiResponse apiResponse) {
                            ToastMaster.shortToast(apiResponse.getBasic().getMsg());
                            EventBus.getDefault().post(new EventFactory.EmpUpdate());
                            finish();
                        }
                    });
                }else{
                    addRequest.setMobile(empaccountet.getText().toString());
                    addRequest.setPassword(accountpsdet.getText().toString());
                    Request request = new Request(addRequest);
                    request.sign();
                    ApiFactory.addEmpInfo(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
                        @Override
                        protected void onNextInActive(ApiResponse apiResponse) {
                            ToastMaster.shortToast(apiResponse.getBasic().getMsg());
                            EventBus.getDefault().post(new EventFactory.EmpUpdate());
                            finish();
                        }
                    });
                }

            }else{
                finish();
            }
        }
    }

    private boolean checkPass() {

        if (StringUtils.etvIsEmpty(storenameet)) {
            ToastMaster.shortToast("员工姓名不能为空");
            return false;
        }else{
            isChange = !storenameet.getText().toString().equals(empItem!=null?empItem.getAlias():null);
        }
        if (TextUtils.isEmpty(positiontv.getText().toString())) {
            ToastMaster.shortToast("请选择员工的职位");
            return false;
        }else{
            isChange = !positiontv.getText().toString().equals(empItem!=null?empItem.getPosition():null);
        }
        if (StringUtils.etvIsEmpty(jobnumet)) {
            ToastMaster.shortToast("员工工号不能为空");
            return false;
        }else{
            isChange = !jobnumet.getText().toString().equals(empItem!=null?empItem.getEmployeeId():null);
        }
        if(isEdite){
            if (StringUtils.etvIsEmpty(empaccountet)) {
                ToastMaster.shortToast("请设置员工账号");
                return false;
            }

            if (StringUtils.isPhoneNum(empaccountet.getText().toString())) {
                ToastMaster.shortToast("员工账号须填写手机号");
                return false;
            }

            if (StringUtils.etvIsEmpty(accountpsdet)) {
                ToastMaster.shortToast("请设置员工密码");
                return false;
            }

            if (StringUtils.passWordCheck(accountpsdet.getText().toString())) {
                ToastMaster.shortToast("密码须由由6-15位数字或字母组成");
                return false;
            }
            isChange = true;
        }
        return true;
    }

    private void deleteEmp() {
        AddOrEditEmpInfoRequest addOrEditEmpInfoRequest = new AddOrEditEmpInfoRequest();
        addOrEditEmpInfoRequest.setUid(String.valueOf(empItem.getUid()));
        Request request = new Request(addOrEditEmpInfoRequest);
        ApiFactory.deleteEmpInfo(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
            @Override
            protected void onNextInActive(ApiResponse apiResponse) {
                ToastMaster.shortToast(apiResponse.getBasic().getMsg());
                EventBus.getDefault().post(new EventFactory.EmpUpdate());
                finish();
            }
        });
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


    /**
     * 编辑已有的员工信息
     *
     * @param act
     * @param item
     */
    public static void launch(Activity act, EmpItem item) {
        Intent intent = new Intent(act, EditEmpActivity.class);
        intent.putExtra(IS_EDITE, true);
        intent.putExtra(EMPITEM, item);
        act.startActivity(intent);
    }

    /**
     * 默认是添加新的员工信息
     *
     * @param act
     */
    public static void launch(Activity act) {
        Intent intent = new Intent(act, EditEmpActivity.class);
        intent.putExtra(IS_EDITE, false);
        act.startActivity(intent);
    }
}
