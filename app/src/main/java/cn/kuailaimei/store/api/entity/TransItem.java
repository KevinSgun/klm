package cn.kuailaimei.store.api.entity;

/**
 *  "afterAmount": 0,//操作后账户余额
 "amont": 50,//操作金额
 "beforeAmount": 50,//操作前账户余额
 "createTime": "2016-07-13 02:21:55",//操作发生时间
 "id": 2,
 "remark": "申请提现冻结",//备注说明
 "sId": 10013,
 "tType": 2//1 进账 2处账
 * Created by ymh on 2016/7/15 0015.
 */
public class TransItem {

    /**
     * afterAmount : 0
     * amont : 50
     * beforeAmount : 0
     * createTime : 2016-07-13 02:20:00
     * id : 1
     * remark : 申请提现冻结
     * sId : 10013
     * tType : 2
     */

    private int afterAmount;
    private int amont;
    private int beforeAmount;
    private String createTime;
    private int id;
    private String remark;
    private int sId;
    private int tType;

    public int getAfterAmount() {
        return afterAmount;
    }

    public void setAfterAmount(int afterAmount) {
        this.afterAmount = afterAmount;
    }

    public int getAmont() {
        return amont;
    }

    public void setAmont(int amont) {
        this.amont = amont;
    }

    public int getBeforeAmount() {
        return beforeAmount;
    }

    public void setBeforeAmount(int beforeAmount) {
        this.beforeAmount = beforeAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSId() {
        return sId;
    }

    public void setSId(int sId) {
        this.sId = sId;
    }

    public int getTType() {
        return tType;
    }

    public void setTType(int tType) {
        this.tType = tType;
    }
}
