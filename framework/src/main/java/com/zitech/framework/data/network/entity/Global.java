package com.zitech.framework.data.network.entity;

/**
 * Created by ymh on 2016/6/30 0030.
 */
public class Global {
    /**
     * "appKey": "55c8b4abfcf4bea6",
     * "os": "2",//手机系统类型 1 android 2 IOS  （必须填）
     * "  sign": "91BF6056B0B6DE9597E647C5FE7C29C2"//数据签名（必须填）
     */
    private String os;
    private String sign;
    private String token;
    private String appKey;

    public Global(){
        os = "1";
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
