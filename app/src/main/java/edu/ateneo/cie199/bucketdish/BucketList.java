package edu.ateneo.cie199.bucketdish;

import java.util.ArrayList;

/**
 * Created by Kyle Bartido on 14/11/2017.
 */

public class BucketList {
    public String name;
    public String creatorID;
    public ArrayList List = new ArrayList();


    public BucketList(String name, String creatorID, ArrayList list) {
        this.name = name;
        this.creatorID = creatorID;
        List = list;
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

    public ArrayList getList() {
        return List;
    }

    public void setList(ArrayList list) {
        List = list;
    }
}
