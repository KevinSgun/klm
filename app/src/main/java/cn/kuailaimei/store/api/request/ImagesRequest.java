package cn.kuailaimei.store.api.request;

import java.util.List;

/**
 * Created by ymh on 2016/7/20 0020.
 */
public class ImagesRequest implements RequestData{
    private List<String> images;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
