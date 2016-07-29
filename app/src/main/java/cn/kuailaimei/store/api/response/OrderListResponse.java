package cn.kuailaimei.store.api.response;

import java.util.List;

import cn.kuailaimei.store.api.entity.OrderItem;
import cn.kuailaimei.store.api.entity.Pagination;

/**
 * Created by ymh on 2016/7/13 0013.
 */
public class OrderListResponse {
    private Pagination pagination;
    private List<OrderItem> items;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<OrderItem> getList() {
        return items;
    }

    public void setList(List<OrderItem> list) {
        this.items = list;
    }
}
