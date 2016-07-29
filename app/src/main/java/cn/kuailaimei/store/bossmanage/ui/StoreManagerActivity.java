package cn.kuailaimei.store.bossmanage.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.response.FileUploadResponse;
import com.zitech.framework.data.network.subscribe.ProgressSubscriber;
import com.zitech.framework.utils.ViewUtils;
import com.zitech.framework.widget.RemoteImageView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.kuailaimei.store.Constants;
import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.entity.StoreInfo;
import cn.kuailaimei.store.api.request.ModifyStoreInfoRequest;
import cn.kuailaimei.store.api.request.NullDataRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.api.request.UpdateStorePositionRequest;
import cn.kuailaimei.store.api.response.FilePathResponse;
import cn.kuailaimei.store.api.response.StoreInfoResponse;
import cn.kuailaimei.store.common.ui.PhotoPickingActivity;
import cn.kuailaimei.store.common.widget.CommonDialog;
import cn.kuailaimei.store.common.widget.ToolBarHelper;
import cn.kuailaimei.store.common.widget.timepicker.TimePickerView;
import cn.kuailaimei.store.map.LocationService;
import cn.kuailaimei.store.utils.StringUtils;
import cn.kuailaimei.store.utils.ToastMaster;
import cn.kuailaimei.store.utils.Utils;
import rx.functions.Action1;

/**
 * 店铺管理
 * Created by ymh on 2016/7/8 0008.
 */
public class StoreManagerActivity extends PhotoPickingActivity implements View.OnClickListener {
    private RemoteImageView storeiconiv;
    private ImageView editstoreiconiv;
    private EditText storenameet;
    private TextView businesstimestarttv;
    private TextView businesstimeendtv;
    private EditText phonenumet;
    private TextView addresset;
    private EditText addressdetailet;
    private EditText storeintroduceet;
    private LocationService locationService;
    private LinearLayout storeaddresslayout;
    private LocationManager mLocationManager;
    private double longitude;
    private double latitude;
    private String cityId;
    private String id;
    private String iconUrl;
    private StoreInfo store;
    private boolean isChange;
    private static final int GPS_OPEN = 11;
    private static final int GPS_CLOSE = 12;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case GPS_OPEN:
                    ToastMaster.shortToast("成功开启GPS定位！");
                    break;
                case GPS_CLOSE:
                    ToastMaster.shortToast("未开启GPS定位");
                    break;
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure
                                .getUriFor(Settings.System.LOCATION_PROVIDERS_ALLOWED),
                        false, mGpsMonitor);

        locationService = new LocationService(this);
        locationService.registerListener(locationListener);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_store_manager;
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra(Constants.ActivityExtra.MANAGER_NAME);
        setTitle(title);
        setRightText(R.string.save);

        storeiconiv = (RemoteImageView) findViewById(R.id.store_icon_iv);
        storenameet = (EditText) findViewById(R.id.store_name_et);
        businesstimestarttv = (TextView) findViewById(R.id.business_time_start_tv);
        businesstimeendtv = (TextView) findViewById(R.id.business_time_end_tv);
        phonenumet = (EditText) findViewById(R.id.phone_num_et);
        addresset = (TextView) findViewById(R.id.address_tv);
        addressdetailet = (EditText) findViewById(R.id.address_detail_et);
        storeintroduceet = (EditText) findViewById(R.id.store_introduce_et);
        editstoreiconiv = (ImageView) findViewById(R.id.edit_store_icon_iv);
        storeaddresslayout = (LinearLayout) findViewById(R.id.store_address_layout);
        storeaddresslayout.setOnClickListener(this);
        editstoreiconiv.setOnClickListener(this);
        businesstimestarttv.setOnClickListener(this);
        businesstimeendtv.setOnClickListener(this);
        storenameet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!storenameet.getText().toString().equals(store != null ? store.getName() : null)) {
                    isChange = true;
                }
            }
        });

        addressdetailet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!addressdetailet.getText().toString().equals(store != null ? store.getAddress() : null)) {
                    isChange = true;
                }
            }
        });

        storeintroduceet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!storeintroduceet.getText().toString().equals(store != null ? store.getRemark() : null)) {
                    isChange = true;
                }
            }
        });
    }

    @Override
    protected void onPhotoCut(String picturePath, final String cutPicturePath) {
        super.onPhotoCut(picturePath, cutPicturePath);
        File file = new File(cutPicturePath);
        ApiFactory.upload("2",file).subscribe(new ProgressSubscriber<FileUploadResponse<FilePathResponse>>(this) {
            @Override
            protected void onNextInActive(FileUploadResponse<FilePathResponse> apiResponse) {
                FilePathResponse response = apiResponse.getData();
                if(response.getImgSrc()!=null&&response.getImgSrc().size()>0){
                    isChange = true;
                    iconUrl = response.getImgSrc().get(0);
                    storeiconiv.setImageUri(cutPicturePath);
                }
            }
        });

    }

    @Override
    protected void initData() {
        Request request = new Request(new NullDataRequest());
        ApiFactory.getStoreInfo(request).subscribe(new ProgressSubscriber<ApiResponse<StoreInfoResponse>>(this) {
            @Override
            protected void onNextInActive(ApiResponse<StoreInfoResponse> storeInfoResponseApiResponse) {
                StoreInfoResponse storeInfo = storeInfoResponseApiResponse.getData();
                if (storeInfo != null && storeInfo.getStoreInfo() != null) {
                    store = storeInfo.getStoreInfo();
                    updateUI();
                }

            }
        });
    }

    private void updateUI() {
        if (TextUtils.isEmpty(store.getLongitude()) || TextUtils.isEmpty(store.getLatitude()))
            if (Utils.isGpsOPen(StoreManagerActivity.this)) {
                locationService.start();
            } else {
                ToastMaster.shortToast("请开启GPS以提高定位精确度");
                Utils.openGPS(StoreManagerActivity.this);
            }
        id = String.valueOf(store.getId());
        cityId = store.getCityId();
        iconUrl = store.getIcon();

        storeiconiv.setImageUri(store.getIcon());
        storenameet.setText(store.getName());
        businesstimestarttv.setText(store.getStart());
        businesstimeendtv.setText(store.getExpired());
        phonenumet.setText(store.getPhone());
        addresset.setText(store.getCityName());
        addressdetailet.setText(store.getAddress());
        storeintroduceet.setText(store.getRemark());
    }


    public static void launch(Activity act, String managerName) {
        Intent intent = new Intent(act, StoreManagerActivity.class);
        intent.putExtra(Constants.ActivityExtra.MANAGER_NAME, managerName);
        act.startActivity(intent);
    }

    private final ContentObserver mGpsMonitor = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            boolean enabled = mLocationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (enabled) {
                mHandler.sendEmptyMessage(GPS_OPEN);
            }else {
                mHandler.sendEmptyMessage(GPS_CLOSE);
            }
            locationService.start();
        }
    };
    private BDLocationListener locationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            longitude = bdLocation.getLongitude();
            latitude = bdLocation.getLatitude();
            cityId = bdLocation.getCityCode();
            String province = bdLocation.getProvince();
            String city = bdLocation.getCity();
            String area = bdLocation.getDistrict();
            if (!TextUtils.isEmpty(province) && !TextUtils.isEmpty(city))
                addresset.setText(province + " " + city + " " + area);
            if(longitude != 49E-324) locationService.stop();
        }
    };

    @Override
    protected void onDestroy() {
        locationService.unregisterListener(locationListener);
        locationService.stop();
        getContentResolver().unregisterContentObserver(mGpsMonitor);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_store_icon_iv:
                requestTakePhoto("店铺图标", EFFECT_TYPE_CUT,  175f/375f);
                break;
            case R.id.store_address_layout:
                SetProvinceAdressActivity.luanchForResult(this);
                break;
            case R.id.business_time_start_tv:
                TimePickerView timePickerViewStart = new TimePickerView(this, TimePickerView.Type.HOURS_MINS);
                timePickerViewStart.setTime(getDateTime("2016-07-12 09:00"));

                timePickerViewStart.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        businesstimestarttv.setText(getStringDateHourMins(date));
                    }
                });
                timePickerViewStart.setCancelable(true);
                timePickerViewStart.show();
                break;
            case R.id.business_time_end_tv:
                TimePickerView timePickerViewEnd = new TimePickerView(this, TimePickerView.Type.HOURS_MINS);

                timePickerViewEnd.setTime(getDateTime("2016-07-12 23:59"));
                timePickerViewEnd.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        businesstimeendtv.setText(getStringDateHourMins(date));
                    }
                });
                timePickerViewEnd.setCancelable(true);
                timePickerViewEnd.show();
                break;
        }
    }

    private String getStringDateHourMins(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.CHINA);
        return formatter.format(date);
    }

    /**
     * 将时间格式为yyyy-MM-dd HH:mm转换为date
     */
    private Date getDateTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        long millionSeconds = 0;
        try {
            millionSeconds = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            millionSeconds = System.currentTimeMillis();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millionSeconds);
        return calendar.getTime();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.ActivityExtra.SELECT_PROVINCE_NAME && data != null) {
            String pname = data.getStringExtra("pname");
            String cname = data.getStringExtra("cname");
            String aname = data.getStringExtra("aname");
            String area = pname + " " + cname + " " + aname;
            addresset.setText(area);
        }
    }

    @Override
    public void onBackPressed() {
        if (isChange) {
            CommonDialog commonDialog = new CommonDialog(this, "确定放弃此次编辑吗");
            commonDialog.setOnPositiveButtonClickListener(new CommonDialog.OnPositiveButtonClickListener() {
                @Override
                public void onClick(Dialog dialog) {
                    finish();
                }
            });
            commonDialog.show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActionBarItemClick(int position) {
        super.onActionBarItemClick(position);
        if (position == ToolBarHelper.ITEM_RIGHT) {
            updateLocation();
            if (isPassCheck()) {
                modifyStoreInfo();
            }
        }
    }

    private void updateLocation() {
        if(latitude ==0 || longitude == 0)return;
        UpdateStorePositionRequest pRequest = new UpdateStorePositionRequest();
        pRequest.setLatitude(String.valueOf(latitude));
        pRequest.setLongitude(String.valueOf(longitude));
        Request request = new Request(pRequest);
        request.sign();
        ApiFactory.updateStorePosition(request).subscribe(new Action1<ApiResponse>() {
            @Override
            public void call(ApiResponse apiResponse) {

            }
        });
    }


    /**
     * 更新店铺信息
     */
    private void modifyStoreInfo() {
        ModifyStoreInfoRequest modifyStoreInfoRequest = new ModifyStoreInfoRequest();
        modifyStoreInfoRequest.setAddress(addressdetailet.getText().toString());
        modifyStoreInfoRequest.setCityId(cityId);
        modifyStoreInfoRequest.setCityName(addresset.getText().toString());
        modifyStoreInfoRequest.setExpired(businesstimeendtv.getText().toString());
        modifyStoreInfoRequest.setStart(businesstimestarttv.getText().toString());
        modifyStoreInfoRequest.setIcon(iconUrl);
        modifyStoreInfoRequest.setPhone(phonenumet.getText().toString());
        modifyStoreInfoRequest.setRemark(storeintroduceet.getText().toString());
        modifyStoreInfoRequest.setId(id);
        Request request = new Request(modifyStoreInfoRequest);
        ApiFactory.modifyStoreInfo(request).subscribe(new ProgressSubscriber<ApiResponse>(this) {
            @Override
            protected void onNextInActive(ApiResponse apiResponse) {
                ToastMaster.shortToast(apiResponse.getBasic().getMsg());
                finish();
            }
        });
    }

    private boolean isPassCheck() {
        storeiconiv = (RemoteImageView) findViewById(R.id.store_icon_iv);
        storenameet = (EditText) findViewById(R.id.store_name_et);
        businesstimestarttv = (TextView) findViewById(R.id.business_time_start_tv);
        businesstimeendtv = (TextView) findViewById(R.id.business_time_end_tv);
        phonenumet = (EditText) findViewById(R.id.phone_num_et);
        addresset = (TextView) findViewById(R.id.address_tv);
        addressdetailet = (EditText) findViewById(R.id.address_detail_et);
        storeintroduceet = (EditText) findViewById(R.id.store_introduce_et);
        editstoreiconiv = (ImageView) findViewById(R.id.edit_store_icon_iv);

        if (StringUtils.etvIsEmpty(storenameet)) {
            ToastMaster.shortToast("店铺名字不能为空");
            return false;
        }

        if (TextUtils.isEmpty(addresset.getText().toString())) {
            ToastMaster.shortToast("店铺地址不能为空");
            return false;
        }

        if (StringUtils.etvIsEmpty(addressdetailet)) {
            ToastMaster.shortToast("详细地址不能为空");
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
}
