package cn.kuailaimei.store.api.entity;

/**
 *
 *  "bankCode": "liuyidang@qq.com",//银行卡帐号
 "bankName": "支付宝",//银行名称
 "id": 3,
 "name": "刘依当",//持卡人姓名
 "userId": 11910
 * Created by ymh on 2016/7/10 0010.
 */
public class BankInfo {
    private int id;
    private int userId;
    private String bankCode;
    private String bankName;
    private String name;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
