package cn.kuailaimei.store.api.request;

/**
 *****添加或编辑员工信息时都需要传的字段start*****
 "alias": "张小3",//员工姓名
 "employeeId": "3号",//工号
 "position": "总监设计师",//员工职位
 "rid": "1"//职位编号
 *****添加或编辑员工信息时都需要传的字段end*****
 *
 *****仅添加员工信息时都需要传的字段start*****
 "mobile": "13692115043",//员工电话
 "password": "123456",//登录密码
 *****仅添加员工信息时都需要传的字段end*****
 *
 * *****仅编辑员工信息时都需要传的字段start*****
 "uid": 11902//员工帐号ＩＤ　
 *****仅编辑员工信息时都需要传的字段end*****
 *
 * Created by ymh on 2016/7/10 0010.
 */
public class AddOrEditEmpInfoRequest implements RequestData{

    private String alias;
    private String employeeId;
    private String mobile;
    private String password;
    private String position;
    private String rid;
    private String uid;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
