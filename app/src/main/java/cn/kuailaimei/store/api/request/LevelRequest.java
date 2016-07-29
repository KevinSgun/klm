package cn.kuailaimei.store.api.request;

/**
 * "lever": "1",//合伙人等级
 * Created by ymh on 2016/7/13 0013.
 */
public class LevelRequest extends PageRequestData{
    private String lever;

    public String getLever() {
        return lever;
    }

    public void setLever(String lever) {
        this.lever = lever;
    }
}
