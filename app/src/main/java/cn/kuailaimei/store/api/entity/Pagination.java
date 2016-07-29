package cn.kuailaimei.store.api.entity;

/**
 * * index : 1  当前页数
 * pages : 1  总页数
 * rows : 3   总条数
 * size : 10  每次请求一页的条数
 * Created by ymh on 2016/7/10 0010.
 */
public class Pagination {
    private int index;
    private int pages;
    private int rows;
    private int size;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
