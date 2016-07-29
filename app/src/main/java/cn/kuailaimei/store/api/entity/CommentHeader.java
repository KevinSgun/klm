package cn.kuailaimei.store.api.entity;

/**
 *
 "allComment": 0,//全部评论
 "badCount": 0,//不满意评论
 "goodCount": 0,//满意评论次数
 "perfectCount": 0,//非常满意评论次数
 "satisfactory": "100%"//满意度百分比
 * Created by ymh on 2016/7/15 0015.
 */
public class CommentHeader {

    /**
     * allComment : 0
     * badCount : 0
     * goodCount : 0
     * perfectCount : 0
     * satisfactory : 100%
     */

    private int allComment;
    private int badCount;
    private int goodCount;
    private int perfectCount;
    private String satisfactory;

    public int getAllComment() {
        return allComment;
    }

    public void setAllComment(int allComment) {
        this.allComment = allComment;
    }

    public int getBadCount() {
        return badCount;
    }

    public void setBadCount(int badCount) {
        this.badCount = badCount;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getPerfectCount() {
        return perfectCount;
    }

    public void setPerfectCount(int perfectCount) {
        this.perfectCount = perfectCount;
    }

    public String getSatisfactory() {
        return satisfactory;
    }

    public void setSatisfactory(String satisfactory) {
        this.satisfactory = satisfactory;
    }
}
