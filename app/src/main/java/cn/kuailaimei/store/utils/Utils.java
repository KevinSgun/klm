package cn.kuailaimei.store.utils;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.text.TextUtils;

import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.utils.LogUtils;

import java.math.BigDecimal;

import cn.kuailaimei.store.Constants;
import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.request.PushRequest;
import cn.kuailaimei.store.api.request.Request;
import cn.kuailaimei.store.common.SP;
import cn.kuailaimei.store.common.User;
import rx.functions.Action1;

/**
 * Created by ymh on 2016/7/10 0010.
 */
public class Utils {
    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static boolean isGpsOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }
    /**
     * 跳转到GPS设置
     * @param context
     */
    public static void openGPS(Context context) {
        Intent intent = new Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
//        Intent GPSIntent = new Intent();
//        GPSIntent.setClassName("com.android.settings",
//                "com.android.settings.widget.SettingsAppWidgetProvider");
//        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
//        GPSIntent.setData(Uri.parse("custom:3"));
//        try {
//            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
//        } catch (PendingIntent.CanceledException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 浮点数加法，计算金额时使用 a+b
     *
     * @param a
     * @param b
     * @return 返回String类型
     */
    public static String floatAddString(float a, float b) {
        BigDecimal result = new BigDecimal(Float.toString(a)).add(new BigDecimal(Float.toString(b)));
        return result.toString();
    }

    /**
     * 浮点数加法，计算金额时使用 a+b
     *
     * @param a
     * @param b
     * @return 返回float型
     */
    public static float floatAddFloat(float a, float b) {
        BigDecimal result = new BigDecimal(Float.toString(a)).add(new BigDecimal(Float.toString(b)));
        return result.floatValue();
    }

    /**
     * 浮点数减法，计算金额时使用 a-b
     *
     * @param a
     * @param b
     * @return 返回String类型
     */
    public static String floatSubString(float a, float b) {
        BigDecimal result = new BigDecimal(Float.toString(a)).subtract(new BigDecimal(Float.toString(b)));
        return result.toString();
    }

    /**
     * 浮点数减法，计算金额时使用 a-b
     *
     * @param a
     * @param b
     * @return 返回float型
     */
    public static float floatSubFloat(float a, float b) {
        BigDecimal result = new BigDecimal(Float.toString(a)).subtract(new BigDecimal(Float.toString(b)));
        return result.floatValue();
    }

    /**
     * 浮点数除法，计算金额使用 a/b
     *
     * @param a
     * @param b
     * @return 返回String类型
     */
    public static String floatDivideString(float a, float b) {
        BigDecimal result = new BigDecimal(Float.toString(a)).divide(new BigDecimal(Float.toString(b)));
        return result.toString();
    }

    /**
     * 浮点数除法，计算金额使用 a/b
     *
     * @param a
     * @param b
     * @return 返回float类型
     */
    public static float floatDivideFloat(float a, float b) {
        BigDecimal result = new BigDecimal(Float.toString(a)).divide(new BigDecimal(Float.toString(b)));
        return result.floatValue();
    }

    /**
     * 浮点数乘法，计算金额时使用 a*b
     *
     * @param a
     * @param b
     * @return 返回String类型
     */
    public static String floatMultiplyString(float a, float b) {
        BigDecimal result = new BigDecimal(Float.toString(a)).multiply(new BigDecimal(Float.toString(b)));
        return result.toString();
    }

    /**
     * 浮点数乘法，计算金额时使用 a*b
     *
     * @param a
     * @param b
     * @return 返回float类型
     */
    public static float floatMultiplyFloat(float a, float b) {
        BigDecimal result = new BigDecimal(Float.toString(a)).multiply(new BigDecimal(Float.toString(b)));
        return result.floatValue();
    }
    public static void bindJGPushIdToService(final String registerId) {
        if(!TextUtils.isEmpty(User.get().getPushId())) return;
        PushRequest pushRequest = new PushRequest();
        pushRequest.setPushId(registerId);
        Request request = new Request(pushRequest);
        request.sign();
        ApiFactory.uploadJPushId(request).subscribe(new Action1<ApiResponse>() {
            @Override
            public void call(ApiResponse apiResponse) {
                SP.getDefaultSP().putBoolean(Constants.IS_BINDING_JPUSH_ID, true);
                LogUtils.i("成功绑定JPushId = " + registerId + "到服务器");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }


}
