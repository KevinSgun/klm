package cn.kuailaimei.store.api.request;

/**
 * Created by ymh on 2016/7/20 0020.
 */
public class PushRequest implements RequestData{
    private String pushId;//上传极光推送registerId的时候用
    private String message;//推送消息的时候用

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
