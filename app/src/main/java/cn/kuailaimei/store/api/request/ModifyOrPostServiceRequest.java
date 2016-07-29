package cn.kuailaimei.store.api.request;

import java.util.List;

/**
 * 修改或发布项目
 * "id": 1,//项目ID
 "assistantPrice": 0,//套餐助理分成
 "cid": 10003,//项目分类ID
 "content": "有一个ie好的服务流程",//服务流程介绍
 "designerPrice": 0,//套餐设计师分成
 "isGroup": 0,//是不是套餐 0 不是 1是
 "name": "豪华单剪项目",//项目名字
 "price": "50.00",//服务价格
 "sid": 10001//所属店铺ID
 * Created by ymh on 2016/7/10 0010.
 */
public class ModifyOrPostServiceRequest implements RequestData{
    private List<String> roleList;
    private int id;
    private String assistantPrice;
    private int cid;
    private String designerPrice;
    private int isGroup;
    private int sid;
    private String name;
    private String price;
    private String content;

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssistantPrice() {
        return assistantPrice;
    }

    public void setAssistantPrice(String assistantPrice) {
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

    public String getDesignerPrice() {
        return designerPrice;
    }

    public void setDesignerPrice(String designerPrice) {
        this.designerPrice = designerPrice;
    }

    public int getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(int isGroup) {
        this.isGroup = isGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }
}
