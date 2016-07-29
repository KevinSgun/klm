package cn.kuailaimei.store.api.request;

/**
 *
 "address": "科技园科新科学园A座",//详细收获地址
 "cityId": "19,1607,3155",//城市ＩＤ　
 "cityName": "广东,深圳市,南山区",//城市名称
 "icon": "http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg",
 "id": "10001",//店铺ＩＤ　
 "phone": "0755-23939888",//联系电话
 "remark": "我们的店铺好牛鼻",//店铺介绍
 "expired": "23:00",//结束营业时间
 "start": "8:00"//开始营业时间
 * Created by ymh on 2016/7/10 0010.
 */
public class ModifyStoreInfoRequest implements RequestData{

    private String address;
    private String cityId;
    private String cityName;
    private String icon;
    private String id;
    private String phone;
    private String remark;
    private String expired;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
