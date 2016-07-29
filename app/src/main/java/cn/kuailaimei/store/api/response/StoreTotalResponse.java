package cn.kuailaimei.store.api.response;

/**
 "amount": 50,//总业绩
 "cashAmount": 50,//现金业绩
 "xkAmount": 0,//消卡业绩
 "orderCount": 1//总订单数
 * Created by ymh on 2016/7/13 0013.
 */
public class StoreTotalResponse {

    private float amount;
    private float cashAmount;
    private float xkAmount;
    private int orderCount;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(float cashAmount) {
        this.cashAmount = cashAmount;
    }

    public float getXkAmount() {
        return xkAmount;
    }

    public void setXkAmount(float xkAmount) {
        this.xkAmount = xkAmount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }
}
