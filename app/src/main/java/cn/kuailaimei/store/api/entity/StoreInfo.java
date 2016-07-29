package cn.kuailaimei.store.api.entity;

/**
 * 店铺信息
 *
 *"address": "广东深圳市南山区园西工业区25栋2单元605",//地址
 "cityId": "19,1607,3155",
 "cityName": "广东,深圳市,南山区",
 "allComment": 0,//总评论数
 "badCount": 0,//不满意数
 "corp": "深圳皇朝国际理发中心",//店铺名字全称
 "expired": "21:30",//结束营业时间
 "goodCount": 0,//满意次数
 "icon": "http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg",
 "id": 10000,
 "isStoreUp": 0,
 "latitude": "22.61667",//纬度
 "longitude": "114.06667",//经度
 "name": "皇朝国际",
 "perfectCount": 0,//非常满意评论次数
 "phone": "0755-88888888",//电话
 "remark": "店铺介绍店铺介绍店铺介绍店铺介绍",//电话
 "satisfactory": "100%",//满意度
 "start": "8:00"//开始营业时间
 * Created by ymh on 2016/7/10 0010.
 */
public class StoreInfo {
    private int id;
    private int isStoreUp;
    private int allComment;
    private int badCount;
    private int goodCount;
    private int perfectCount;
    private String address;
    private String cityId;
    private String cityName;
    private String corp;
    private String expired;
    private String icon;
    private String latitude;
    private String longitude;
    private String name;
    private String phone;
    private String remark;
    private String satisfactory;
    private String start;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getAllComment() {
        return allComment;
    }

    public void setAllComment(int allComment) {
        this.allComment = allComment;
    }

    public int getBadCount() {
        return badCount;
    }

    public void setBadCount(int badCount) {
        this.badCount = badCount;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsStoreUp() {
        return isStoreUp;
    }

    public void setIsStoreUp(int isStoreUp) {
        this.isStoreUp = isStoreUp;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPerfectCount() {
        return perfectCount;
    }

    public void setPerfectCount(int perfectCount) {
        this.perfectCount = perfectCount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSatisfactory() {
        return satisfactory;
    }

    public void setSatisfactory(String satisfactory) {
        this.satisfactory = satisfactory;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
