package cn.kuailaimei.store.api.response;

import java.util.List;

import cn.kuailaimei.store.api.entity.ConsumeItemDetail;

/**
 * Created by ymh on 2016/7/10 0010.
 */
public class ConsumeItemDetailResponse {
    private List<String> roleList;
    private ConsumeItemDetail service;

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public ConsumeItemDetail getService() {
        return service;
    }

    public void setService(ConsumeItemDetail service) {
        this.service = service;
    }
}
