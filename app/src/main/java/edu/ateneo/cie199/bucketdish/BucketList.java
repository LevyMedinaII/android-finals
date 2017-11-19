package edu.ateneo.cie199.bucketdish;

/**
 * Created by Kyle Bartido on 14/11/2017.
 */

public class BucketList {
    public String name;
    public String creatorID;
    public String creatorName;
    public String restaurantList;

    public BucketList() {
    }

    public BucketList(String name, String creatorID, String creatorName, String restaurantList) {
        this.name = name;
        this.creatorID = creatorID;
        this.creatorName = creatorName;
        this.restaurantList = restaurantList;
    }

    public String getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(String restaurantList) {
        this.restaurantList = restaurantList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }


}
