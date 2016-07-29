package cn.kuailaimei.store.api.entity;

/**
 "peopleName": "张三",//合伙人名字
 "sex": "0",// 0 保密 1男 2女
 "totalAmount": 200//利润
 * Created by ymh on 2016/7/8 0008.
 */
public class PartnerItem {

    private String peopleName;
    private String sex;
    private int totalAmount;

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
