package cn.kuailaimei.store.api.entity;

/**
 * Created by ymh on 2016/7/9 0009.
 */
public class ConsumeItem {

    /**
     * id : 10002  项目id
     * name : 洗剪吹  获取店铺自定义的项目名字从属于平台定义的项目
     *"price": "50.00"//项目价格
     */

    private int id;
    private float price;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
