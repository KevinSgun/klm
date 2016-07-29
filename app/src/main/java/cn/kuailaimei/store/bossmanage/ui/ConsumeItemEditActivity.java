package cn.kuailaimei.store.bossmanage.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.zitech.framework.widget.ActionSheet;
import com.zitech.framework.widget.ActionSheetHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.entity.ConsumeItemDetail;
import cn.kuailaimei.store.api.entity.NormalItem;
import cn.kuailaimei.store.api.entity.RoleItem;
import cn.kuailaimei.store.api.entity.Service;
import cn.kuailaimei.store.api.request.IdRequest;
import cn.kuailaimei.store.api.request.ModifyOrPostServiceRequest;
import cn.kuailaimei.store.api.request.PageRequest;
import cn.kuailaimei.store.api.request.PageRequestData;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.api.response.ConsumeItemDetailResponse;
import cn.kuailaimei.store.api.response.StoreServicesResponse;
import cn.kuailaimei.store.common.event.EventFactory;
import cn.kuailaimei.store.common.ui.AppBarActivity;
import cn.kuailaimei.store.common.widget.ChooseItemView;
import cn.kuailaimei.store.common.widget.CommonDialog;
import cn.kuailaimei.store.common.widget.ToolBarHelper;
import cn.kuailaimei.store.common.widget.timepicker.OptionsPickerView;
import cn.kuailaimei.store.utils.StringUtils;
import cn.kuailaimei.store.utils.ToastMaster;
import cn.kuailaimei.store.utils.Utils;
import rx.functions.Action1;

/**
 * Created by ymh on 2016/7/9 0009.
 */
public class ConsumeItemEditActivity extends AppBarActivity implements View.OnClickListener, TextWatcher {
    private EditText consumenameet;
    private EditText consumeitemintroduceet;
    private EditText consumepriceet;
    private TextView ifispackagetv;
    private LinearLayout ifispackagelayout;
    private EditText designercommissionet;
    private EditText assistantcommissionet;
    private static final String CONSUME_ITEM_ID = "consume_item_id";
    private String consumeItemId;
    private ConsumeItemDetail consumeItemDetail = new ConsumeItemDetail();
    private List<String> currentRoleList;
    private ArrayList<RoleItem> roleList;
    private ArrayList<Service> allServiceList;
    private ChooseItemView chooseitemview;
    private boolean isSetRole;
    private LinearLayout itemclassifylayout;
    private int isPackage = 1;
    private TextView consumeclassifytv;
    private int cId;
    private ArrayList<String> choosedRoleList = new ArrayList<>();
    private HashMap<String, String> choosedRoleMap = new HashMap<String, String>();
    private LinearLayout commissionlayout;
    private boolean isChange;
    private Button deletebtn;
    private boolean serviceIsFill;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_consumer_item_edit;
    }

    @Override
    protected void initView() {
        setTitle(R.string.item_manager);
        setRightText(R.string.save);

        consumeItemId = getIntent().getStringExtra(CONSUME_ITEM_ID);

        consumenameet = (EditText) findViewById(R.id.consume_name_et);
        consumeitemintroduceet = (EditText) findViewById(R.id.consume_item_introduce_et);
        consumepriceet = (EditText) findViewById(R.id.consume_price_et);
        ifispackagetv = (TextView) findViewById(R.id.if_is_package_tv);
        ifispackagelayout = (LinearLayout) findViewById(R.id.if_is_package_layout);
        designercommissionet = (EditText) findViewById(R.id.designer_commission_et);
        assistantcommissionet = (EditText) findViewById(R.id.assistant_commission_et);
        itemclassifylayout = (LinearLayout) findViewById(R.id.item_classify_layout);
        chooseitemview = (ChooseItemView) findViewById(R.id.choose_item_view);
        consumeclassifytv = (TextView) findViewById(R.id.consume_classify_tv);
        commissionlayout = (LinearLayout) findViewById(R.id.commission_layout);
        deletebtn = (Button) findViewById(R.id.delete_btn);
        if (consumeItemId != null) {
            deletebtn.setVisibility(View.VISIBLE);
        } else {
            deletebtn.setVisibility(View.GONE);
        }

        ifispackagelayout.setOnClickListener(this);
        itemclassifylayout.setOnClickListener(this);
        deletebtn.setOnClickListener(this);

        consumepriceet.addTextChangedListener(this);
        designercommissionet.addTextChangedListener(this);
        assistantcommissionet.addTextChangedListener(this);
    }

    @Override
    protected void initData() {
        getAllRoleList();
        getItemDetail();
        getConsumeItems();
    }

    private void getConsumeItems() {
        PageRequest pageRequest = new PageRequest(new PageRequestData());
        pageRequest.sign();
        ApiFactory.getStoreServices(pageRequest).subscribe(new ProgressSubscriber<ApiResponse<StoreServicesResponse>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<StoreServicesResponse> resp) {
                allServiceList = resp.getData().getServices();
                fillService();

            }
        });
    }

    private void fillService() {
        if (allServiceList != null && allServiceList.size() > 0 && !serviceIsFill) {
            for (Service service : allServiceList) {
                if (service.getCId() == consumeItemDetail.getCid()) {
                    serviceIsFill = true;
                    consumeclassifytv.setText(service.getName());
                    cId = service.getCId();
                }
            }
        }
    }

    private void getAllRoleList() {
        PageRequest pageRequest = new PageRequest(new PageRequestData());
        pageRequest.sign();
        ApiFactory.getRoleList(pageRequest).subscribe(new Action1<ApiResponse<ArrayList<RoleItem>>>() {
            @Override
            public void call(ApiResponse<ArrayList<RoleItem>> listApiResponse) {
                roleList = listApiResponse.getData();
                if (!isSetRole) {
                    chooseitemview.setTags(wrapNormalItem());
                    chooseitemview.setOnTagClickListener(new ChooseItemView.OnTagClickListener() {
                        @Override
                        public void onTagClick(NormalItem item, boolean isChoose) {
                            if (isChoose) {
                                choosedRoleMap.put(item.getId(), item.getName());
                            } else {
                                choosedRoleMap.remove(item.getId());
                            }

                        }
                    });
                }
            }
        });
    }

    private List<NormalItem> wrapNormalItem() {
        if (roleList == null || (consumeItemId != null && currentRoleList == null))
            return new ArrayList<NormalItem>();
        List<NormalItem> normalItems = new ArrayList<>();
        for (RoleItem roleItem : roleList) {
            NormalItem item = new NormalItem();
            item.setId(roleItem.getRid());
            item.setName(roleItem.getPosition());
            if (currentRoleList != null)
                for (String currentItem : currentRoleList) {
                    if (currentItem.equals(roleItem.getRid())) {
                        item.setSelected(true);
                        choosedRoleMap.put(roleItem.getRid(), roleItem.getPosition());
                    }
                }
            normalItems.add(item);
        }
        isSetRole = true;
        return normalItems;
    }

    private void getItemDetail() {
        if (consumeItemId == null) return;
        IdRequest idRequest = new IdRequest();
        idRequest.setId(consumeItemId);
        Request request = new Request(idRequest);
        request.sign();
        ApiFactory.getConsumeItemDetail(request).subscribe(new Action1<ApiResponse<ConsumeItemDetailResponse>>() {
            @Override
            public void call(ApiResponse<ConsumeItemDetailResponse> response) {
                ConsumeItemDetailResponse itemDetail = response.getData();
                currentRoleList = itemDetail.getRoleList();
                if (itemDetail.getService() != null) consumeItemDetail = itemDetail.getService();
                fillService();
                if (!isSetRole) {
                    chooseitemview.setTags(wrapNormalItem());
                    chooseitemview.setOnTagClickListener(new ChooseItemView.OnTagClickListener() {
                        @Override
                        public void onTagClick(NormalItem item, boolean isChoose) {
                            if (isChoose) {
                                choosedRoleMap.put(item.getId(), item.getName());
                            } else {
                                choosedRoleMap.remove(item.getId());
                            }

                        }
                    });
                }

                consumenameet.setText(consumeItemDetail.getName());
                consumeitemintroduceet.setText(consumeItemDetail.getContent());
                consumepriceet.setText(String.format(getString(R.string.money), consumeItemDetail.getPrice()));
                ifispackagetv.setText(consumeItemDetail.isPackage());
                designercommissionet.setText(String.format(getString(R.string.money), consumeItemDetail.getDesignerPrice()));
                assistantcommissionet.setText(String.format(getString(R.string.money), consumeItemDetail.getAssistantPrice()));
                if (consumeItemDetail.getIsGroup() == 1) {
                    isPackage = 1;
                    commissionlayout.setVisibility(View.VISIBLE);
                } else {
                    isPackage = 0;
                    commissionlayout.setVisibility(View.GONE);
                }

            }
        });
    }

    public static void launch(Activity act, String consumeItemId) {
        Intent intent = new Intent(act, ConsumeItemEditActivity.class);
        intent.putExtra(CONSUME_ITEM_ID, consumeItemId);
        act.startActivity(intent);
    }

    public static void launch(Activity act) {
        launch(act, null);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.if_is_package_layout:
                final ActionSheet sheet = (ActionSheet) ActionSheetHelper.createDialog(this, null);
                String[] items = {"是", "否", "取消"};
                sheet.addButton(items[0], ActionSheet.WHITE_STYLE_BTN);
                sheet.addButton(items[1], ActionSheet.WHITE_STYLE_BTN);
                sheet.setMainTitle("是否为套餐");
                sheet.setOnButtonClickListener(new ActionSheet.OnButtonClickListener() {

                    @Override
                    public void OnClick(View clickedView, int which) {
                        if (which == 0) {
                            isPackage = 1;
                            ifispackagetv.setText("是");
                            commissionlayout.setVisibility(View.VISIBLE);
                        } else {
                            isPackage = 0;
                            ifispackagetv.setText("否");
                            commissionlayout.setVisibility(View.GONE);
                        }
                        sheet.dismiss();
                    }
                });
                sheet.addCancelButton(items[2]);
                sheet.show();
                break;
            case R.id.item_classify_layout:
                OptionsPickerView<Service> servicePicker = new OptionsPickerView<>(this);
                servicePicker.setPicker(allServiceList);
                servicePicker.setCancelable(true);
                servicePicker.setCyclic(false);
                servicePicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        consumeclassifytv.setText(allServiceList.get(options1).getName());
                        cId = allServiceList.get(options1).getCId();
                    }
                });
                servicePicker.show();
                break;
            case R.id.delete_btn:
                if (consumeItemId != null && !ViewUtils.isFastDoubleClick()) {
                    CommonDialog commonDialog = new CommonDialog(this, "确定要删除该项目吗");
                    commonDialog.setPositiveButtonText("取消");
                    commonDialog.setNegativeButtonText("删除");
                    commonDialog.setOnNegativeButtonClickListener(new CommonDialog.OnNegativeButtonClickListener() {
                        @Override
                        public void onClick(Dialog dialog) {
                            deleteConsumeItem();
                        }
                    });
                    commonDialog.show();
                }
                break;
        }
    }

    private void deleteConsumeItem() {
        IdRequest idRequest = new IdRequest();
        idRequest.setId(consumeItemId);
        Request request = new Request(idRequest);
        ApiFactory.deleteService(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
            @Override
            protected void onNextInActive(ApiResponse apiResponse) {
                ToastMaster.shortToast(apiResponse.getBasic().getMsg());
                EventBus.getDefault().post(new EventFactory.ItemUpdate());
                finish();
            }
        });
    }

    @Override
    protected void onActionBarItemClick(int position) {
        super.onActionBarItemClick(position);
        if (position == ToolBarHelper.ITEM_RIGHT) {
            if(!checkPass()) return;
            if (isChange) {
                ModifyOrPostServiceRequest mRequest = new ModifyOrPostServiceRequest();
                mRequest.setName(consumenameet.getText().toString());
                mRequest.setCid(cId);
                mRequest.setContent(consumeitemintroduceet.getText().toString());
                mRequest.setId(consumeItemDetail.getId());
                mRequest.setIsGroup(isPackage);
                mRequest.setPrice(consumepriceet.getText().toString());
                if (isPackage == 1) {
                    mRequest.setDesignerPrice(designercommissionet.getText().toString());
                    mRequest.setAssistantPrice(assistantcommissionet.getText().toString());
                }
                mRequest.setSid(consumeItemDetail.getSid());
                Iterator iterator = choosedRoleMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String key = (String) entry.getKey();
                    choosedRoleList.add(key);
                }
                mRequest.setRoleList(choosedRoleList);
                Request request = new Request(mRequest);
                request.sign();
                ApiFactory.modifyOrPostService(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
                    @Override
                    protected void onNextInActive(ApiResponse apiResponse) {
                        ToastMaster.shortToast(apiResponse.getBasic().getMsg());
                        EventBus.getDefault().post(new EventFactory.ItemUpdate());
                        finish();
                    }
                });
            }else{
                finish();
            }
        }
    }

    private boolean checkPass() {
        if (StringUtils.etvIsEmpty(consumenameet)) {
            ToastMaster.shortToast("项目名称不能为空");
            return false;
        } else {
            isChange = !consumenameet.getText().toString().equals(consumeItemDetail.getName());
        }
        if (TextUtils.isEmpty(consumeclassifytv.getText().toString())) {
            ToastMaster.shortToast("请选择一个项目类型");
            return false;
        } else {
            isChange = cId != consumeItemDetail.getCid();
        }

        if (StringUtils.etvIsEmpty(consumepriceet)) {
            ToastMaster.shortToast("服务价格不能为空");
            return false;
        } else {
            isChange = !consumepriceet.getText().toString().equals(String.valueOf(consumeItemDetail.getPrice()));
        }
        if (choosedRoleMap.size() == 0) {
            ToastMaster.shortToast("至少适配一个职位");
            return false;
        }
        if (isPackage == 1) {
            if (StringUtils.etvIsEmpty(designercommissionet)) {
                ToastMaster.shortToast("技师佣金不能为空");
                return false;
            }
            if (StringUtils.etvIsEmpty(assistantcommissionet)) {
                ToastMaster.shortToast("助理佣金不能为空");
                return false;
            }

            float designerMoney = Float.parseFloat(designercommissionet.getText().toString());
            float assistantMoney = Float.parseFloat(assistantcommissionet.getText().toString());
            float totalMonyey = Float.parseFloat(consumepriceet.getText().toString());
            if (totalMonyey != Utils.floatAddFloat(designerMoney, assistantMoney)) {
                ToastMaster.shortToast("技术与助理佣金之和必须等于服务价格");
                return false;
            }

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


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() < 1) return;
        EditText editText = null;
        if (consumepriceet.isFocused())
            editText = consumepriceet;
        else if (designercommissionet.isFocused())
            editText = designercommissionet;
        else if (assistantcommissionet.isFocused())
            editText = assistantcommissionet;
        if (editText == null) return;
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + 3);
                editText.setText(s);
                editText.setSelection(s.length());
            }
        }
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        }

        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(1);
            }
        }
    }


    @Override
    public void afterTextChanged(Editable s) {

    }
}
