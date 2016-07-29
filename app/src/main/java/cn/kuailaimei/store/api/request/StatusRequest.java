package cn.kuailaimei.store.api.request;

/**
 * "status": "1",//订单状态 0全部 1待付款 2待评价 3 已完成
 * Created by ymh on 2016/7/15 0015.
 */
public class StatusRequest extends PageRequestData{

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
