package cn.kuailaimei.store.api.entity;

/**
 "addDate": "2016-06-19 16:18:53",
 "amount": 80,
 "designerName": "１号设计师 ",
 "designerId": "111 ",
 "msg": "待付款",
 "orderId": "20160619180453",
 "sIcon": "http://img4.imgtn.bdimg.com/it/u=3854292911,2543367747&fm=11&gp=0.jpg",
 "sId": 10000,
 "sName": "深圳皇朝国际理发中心",
 "serviceName": "单剪",
 "status": 1
 * Created by ymh on 2016/7/9 0009.
 */
public class OrderItem {
    public static final int ALL = 0;//全部
    public static final int WAIT_PAY = 1;//待付款
    public static final int WAIT_CONFIRM = 2;//待确认
    public static final int WAIT_COMMENT = 3;//待评价
    public static final int COMPLETE = 4;//已完成

    private int sId;
    private float amount;
    private int status;
    private String addDate;
    private String designerName;
    private String designerId;
    private String msg;
    private String orderId;
    private String sIcon;
    private String sName;
    private String serviceName;

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
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

    public String getDesignerId() {
        return designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSIcon() {
        return sIcon;
    }

    public void setSIcon(String sIcon) {
        this.sIcon = sIcon;
    }

    public int getSId() {
        return sId;
    }

    public void setSId(int sId) {
        this.sId = sId;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
