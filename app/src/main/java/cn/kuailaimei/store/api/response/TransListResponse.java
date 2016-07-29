package cn.kuailaimei.store.api.response;

import java.util.List;

import cn.kuailaimei.store.api.entity.Pagination;
import cn.kuailaimei.store.api.entity.TransItem;

/**
 * Created by ymh on 2016/7/15 0015.
 */
public class TransListResponse {
    private Pagination pagination;
    private List<TransItem> list;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<TransItem> getList() {
        return list;
    }

    public void setList(List<TransItem> list) {
        this.list = list;
    }
}
