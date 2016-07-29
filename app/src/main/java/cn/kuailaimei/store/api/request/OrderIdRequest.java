package cn.kuailaimei.store.api.request;

/**
 * Created by ymh on 2016/7/20 0020.
 */
public class OrderIdRequest implements RequestData{
    private String orderId;//订单id

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
