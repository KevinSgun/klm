package cn.kuailaimei.store.api.response;

import java.util.List;

import cn.kuailaimei.store.api.entity.CommentHeader;
import cn.kuailaimei.store.api.entity.CommentItem;
import cn.kuailaimei.store.api.entity.Pagination;

/**
 * Created by ymh on 2016/7/13 0013.
 */
public class CommentListResponse {
    private CommentHeader head;
    private Pagination pagination;
    private List<CommentItem> items;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<CommentItem> getList() {
        return items;
    }

    public void setList(List<CommentItem> list) {
        this.items = list;
    }

    public CommentHeader getHead() {
        return head;
    }

    public void setHead(CommentHeader head) {
        this.head = head;
    }
}
