package cn.kuailaimei.store.api.response;

import java.util.List;

import cn.kuailaimei.store.api.entity.ConsumeItem;
import cn.kuailaimei.store.api.entity.Pagination;

/**
 * Created by ymh on 2016/7/10 0010.
 */
public class ConsumeItemResponse {
    private Pagination pagination;
    private List<ConsumeItem> services;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<ConsumeItem> getServices() {
        return services;
    }

    public void setServices(List<ConsumeItem> services) {
        this.services = services;
    }
}
