package cn.kuailaimei.store.api.response;

import java.util.ArrayList;

import cn.kuailaimei.store.api.entity.Pagination;
import cn.kuailaimei.store.api.entity.Service;

/**
 * Created by ymh on 2016/7/10 0010.
 */
public class StoreServicesResponse {
    private Pagination pagination;
    private ArrayList<Service> services;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }
}
