package cn.kuailaimei.store.api.entity;

/**
 *
 "id": 1,//项目ID
 "assistantPrice": 0,//套餐助理分成
 "cid": 10003,//项目分类ID
 "content": "有一个ie好的服务流程",//服务流程介绍
 "designerPrice": 0,//套餐设计师分成
 "isGroup": 0,//是不是套餐 0 不是 1是
 "mid": 11902,
 "name": "豪华单剪项目",//项目名字
 "price": "50.00",//服务价格
 "sid": 10001//所属店铺ID
 * Created by ymh on 2016/7/10 0010.
 */
public class ConsumeItemDetail {

    private int id;
    private float assistantPrice;
    private int cid;
    private String content;
    private float designerPrice;
    private int isGroup;
    private int mid;
    private String name;
    private float price;
    private int sid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAssistantPrice() {
        return assistantPrice;
    }

    public void setAssistantPrice(float assistantPrice) {
        this.assistantPrice = assistantPrice;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getDesignerPrice() {
        return designerPrice;
    }

    public void setDesignerPrice(float designerPrice) {
        this.designerPrice = designerPrice;
    }

    public int getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(int isGroup) {
        this.isGroup = isGroup;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String isPackage(){
        return isGroup==1?"是":"否";
    }
}
