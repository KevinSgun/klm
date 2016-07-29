package cn.kuailaimei.store.api.entity;

/**
 "content": "3333",//评论内容
 "date": "2016-06-19 17:52:18",//评论时间
 "id": 1,
 "uid": 11903,//评论人编号
 "uname": "大大"//评论人昵称
 "portrait": "http://5eed50352ac75cb7ae.jpg"//评论人图像
 * Created by ymh on 2016/7/9 0009.
 */
public class CommentItem {
    private int id;
    private int uid;
    private String content;
    private String date;
    private String uname;
    private String portrait;
    private boolean isEmpty;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
