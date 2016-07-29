package cn.kuailaimei.store.api.entity;

/**
 * Created by ymh on 2016/7/13 0013.
 */
public class NormalItem {
    private String id;
    private String name;
    private boolean isSelected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
