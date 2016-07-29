package cn.kuailaimei.store.api.response;

import cn.kuailaimei.store.api.entity.BankInfo;

/**
 "amountToBeSettled": 50,//可提现金额
 "freezingAmount": 0,//冻结金额 结算中
 "settlementAmount": 0,//已结算金额
 "totalAmount": 50,//总金额
 "bankInfo": {//提现账户信息
 "bankCode": "liuyidang@qq.com",//银行账号
 "bankName": "支付宝",//银行名称
 "id": 3,
 "name": "刘依当",//持卡人姓名
 "userId": 11918
 * Created by ymh on 2016/7/15 0015.
 */
public class AccountInfoResponse {

    private float amountToBeSettled;
    private float freezingAmount;
    private float settlementAmount;
    private float totalAmount;

    private BankInfo bankInfo;

    public float getAmountToBeSettled() {
        return amountToBeSettled;
    }

    public void setAmountToBeSettled(float amountToBeSettled) {
        this.amountToBeSettled = amountToBeSettled;
    }

    public float getFreezingAmount() {
        return freezingAmount;
    }

    public void setFreezingAmount(float freezingAmount) {
        this.freezingAmount = freezingAmount;
    }

    public float getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(float settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BankInfo getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(BankInfo bankInfo) {
        this.bankInfo = bankInfo;
    }

}
