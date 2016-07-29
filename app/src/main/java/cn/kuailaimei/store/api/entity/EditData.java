package cn.kuailaimei.store.api.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ymh on 2016/7/17 0017.
 */
public class EditData implements Parcelable{
    /**
     * 标题的资源id
     */
    int titleResId;
    /**
     * hint提示的资源id
     */
    int hintResId;
    /**
     * 最大字数限制
     */
    int limit;
    /**
     * 超出最大字数限制的提示语
     */
    String limitTips;
    /**
     * 内容
     */
    String contentStr;
    public EditData(){}
    protected EditData(Parcel in) {
        titleResId = in.readInt();
        hintResId = in.readInt();
        limit = in.readInt();
        limitTips = in.readString();
        contentStr = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(titleResId);
        dest.writeInt(hintResId);
        dest.writeInt(limit);
        dest.writeString(limitTips);
        dest.writeString(contentStr);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EditData> CREATOR = new Creator<EditData>() {
        @Override
        public EditData createFromParcel(Parcel in) {
            return new EditData(in);
        }

        @Override
        public EditData[] newArray(int size) {
            return new EditData[size];
        }
    };

    public int getTitleResId() {
        return titleResId;
    }

    public void setTitleResId(int titleResId) {
        this.titleResId = titleResId;
    }

    public int getHintResId() {
        return hintResId;
    }

    public void setHintResId(int hintResId) {
        this.hintResId = hintResId;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getLimitTips() {
        return limitTips;
    }

    public void setLimitTips(String limitTips) {
        this.limitTips = limitTips;
    }

    public String getContentStr() {
        return contentStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }
}
