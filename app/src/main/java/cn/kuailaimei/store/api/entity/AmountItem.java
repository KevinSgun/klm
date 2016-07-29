package cn.kuailaimei.store.api.entity;

/**
 "orderId": "212122222",//订单号
 "serviceName": "总监单剪",//服务内容
 "amount": 50,//金额
 "designerName": "2号李四 ",//技师名字
 "addTime": "2016-07-12 01:57:25"//服务时间
 * Created by ymh on 2016/7/13 0013.
 */
public class AmountItem {

    /**
     * orderId : 212122222
     * serviceName : 总监单剪
     * amount : 50
     * designerName : 2号李四
     * addTime : 2016-07-12 01:57:25
     */
    private float amount;
    private String orderId;
    private String serviceName;
    private String designerName;
    private String addTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
