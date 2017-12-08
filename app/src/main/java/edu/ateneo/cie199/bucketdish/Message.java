package edu.ateneo.cie199.bucketdish;

/**
 * Created by ljda0 on 09/12/2017.
 */

public class Message {

    private User mUser;
    private Restaurant restaurant;
    private String message;

    public Message(User mUser, Restaurant restaurant, String message) {
        this.mUser = mUser;
        this.restaurant = restaurant;
        this.message = message;
    }

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String toString(){
        return mUser.getUsername() + ":  " + message;

    }

}
