package cn.kuailaimei.store.api.response;

import java.util.List;

import cn.kuailaimei.store.api.entity.Pagination;
import cn.kuailaimei.store.api.entity.PartnerItem;

/**
 * Created by ymh on 2016/7/13 0013.
 */
public class PartnerListResponse {
    private Pagination pagination;
    private List<PartnerItem> list;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<PartnerItem> getList() {
        return list;
    }

    public void setList(List<PartnerItem> list) {
        this.list = list;
    }
}
