package cn.kuailaimei.store.api.entity;

/**
 "cous": 1,//客单数量
 "date": "2016-07-05~2016-07-12",//时间
 "designerName": "张三"//技师名字
 * Created by ymh on 2016/7/7 0007.
 */
public class TotalOrderCountItem {

    /**
     * cous : 1
     * date : 2016-07-05~2016-07-12
     * designerName : 张三
     */

    private int cous;
    private String date;
    private String designerName;

    public int getCous() {
        return cous;
    }

    public void setCous(int cous) {
        this.cous = cous;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }
}
