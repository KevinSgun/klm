package cn.kuailaimei.store.api.response;

import java.util.List;

import cn.kuailaimei.store.api.entity.EmpItem;
import cn.kuailaimei.store.api.entity.Pagination;

/**
 * Created by ymh on 2016/7/10 0010.
 */
public class EmpInfoResponse {

    private Pagination pagination;
    /**
     * alias : 张三
     * employeeId : 1号
     * mobile : 15914087332
     * position : 发型师
     * rid : 1
     * portrait : http://baidu.com/1.jpg
     * uid : 11902
     */

    private List<EmpItem> items;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<EmpItem> getItems() {
        return items;
    }

    public void setItems(List<EmpItem> items) {
        this.items = items;
    }
}
