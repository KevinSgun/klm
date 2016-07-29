package cn.kuailaimei.store.api.request;

/**
 * "date": "7",//要查询的天数 7 就是7天以内
 * Created by ymh on 2016/7/13 0013.
 */
public class DateRequest extends PageRequestData{
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
