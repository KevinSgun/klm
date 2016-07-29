package cn.kuailaimei.store.api.request;

/**
 *  "money": "50"//提现50
 * Created by ymh on 2016/7/15 0015.
 */
public class MoneyRequest implements RequestData{
    private String money;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
