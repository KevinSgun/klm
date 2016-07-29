package cn.kuailaimei.store.api.response;

/**
 "onePeoples": 4,//一级人数
 "twoPeoples": 2,//二级人数
 "threePeoples": 0//三级人数
 * Created by ymh on 2016/7/13 0013.
 */
public class PartnerTotalResponse {

    /**
     * amountToBeSettled :0
     * onePeoples : 4
     * twoPeoples : 2
     * threePeoples : 0
     */

    private int onePeoples;
    private int twoPeoples;
    private int threePeoples;
    private float amountToBeSettled;

    public int getOnePeoples() {
        return onePeoples;
    }

    public void setOnePeoples(int onePeoples) {
        this.onePeoples = onePeoples;
    }

    public int getTwoPeoples() {
        return twoPeoples;
    }

    public void setTwoPeoples(int twoPeoples) {
        this.twoPeoples = twoPeoples;
    }

    public int getThreePeoples() {
        return threePeoples;
    }

    public void setThreePeoples(int threePeoples) {
        this.threePeoples = threePeoples;
    }

    public float getAmountToBeSettled() {
        return amountToBeSettled;
    }

    public void setAmountToBeSettled(float amountToBeSettled) {
        this.amountToBeSettled = amountToBeSettled;
    }
}
