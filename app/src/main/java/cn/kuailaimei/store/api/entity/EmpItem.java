package cn.kuailaimei.store.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ymh on 2016/7/9 0009.
 */
public class EmpItem implements Parcelable{
    private int rid;
    private int uid;
    private String alias;
    private String employeeId;
    private String mobile;
    private String position;
    private String portrait;

    public EmpItem(){}
    protected EmpItem(Parcel in) {
        rid = in.readInt();
        uid = in.readInt();
        alias = in.readString();
        employeeId = in.readString();
        mobile = in.readString();
        position = in.readString();
        portrait = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rid);
        dest.writeInt(uid);
        dest.writeString(alias);
        dest.writeString(employeeId);
        dest.writeString(mobile);
        dest.writeString(position);
        dest.writeString(portrait);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EmpItem> CREATOR = new Creator<EmpItem>() {
        @Override
        public EmpItem createFromParcel(Parcel in) {
            return new EmpItem(in);
        }

        @Override
        public EmpItem[] newArray(int size) {
            return new EmpItem[size];
        }
    };

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
