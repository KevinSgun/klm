package cn.kuailaimei.store.api.response;

import java.util.List;

import cn.kuailaimei.store.api.entity.AmountItem;
import cn.kuailaimei.store.api.entity.Pagination;

/**
 * Created by ymh on 2016/7/13 0013.
 */
public class AmountListResponse {
    private Pagination pagination;
    private List<AmountItem> list;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<AmountItem> getList() {
        return list;
    }

    public void setList(List<AmountItem> list) {
        this.list = list;
    }
}
