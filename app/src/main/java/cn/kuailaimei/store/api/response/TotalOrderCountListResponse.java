package cn.kuailaimei.store.api.response;

import java.util.List;

import cn.kuailaimei.store.api.entity.Pagination;
import cn.kuailaimei.store.api.entity.TotalOrderCountItem;

/**
 * Created by ymh on 2016/7/13 0013.
 */
public class TotalOrderCountListResponse {
    private Pagination pagination;
    private List<TotalOrderCountItem> list;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<TotalOrderCountItem> getList() {
        return list;
    }

    public void setList(List<TotalOrderCountItem> list) {
        this.list = list;
    }
}
