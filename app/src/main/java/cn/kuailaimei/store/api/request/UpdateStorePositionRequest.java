package cn.kuailaimei.store.api.request;

/**
 * Created by ymh on 2016/7/13 0013.
 */
public class UpdateStorePositionRequest implements RequestData{

    /**
     * latitude : 22.58
     * longitude : 113.90
     */

    private String latitude;
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
