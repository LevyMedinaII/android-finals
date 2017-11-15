package edu.ateneo.cie199.bucketdish;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by Kyle Bartido on 14/11/2017.
 */

@IgnoreExtraProperties
public class User {
    public String email;
    public String username;
    public String firstname;
    public String lastname;
    public ArrayList finishedList = new ArrayList();


    public User() {

    }

    public User(String email, String username, String firstname, String lastname, ArrayList finishedList) {
        this.email = email;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.finishedList = finishedList;
    }

    public ArrayList getFinishedList() {
        return finishedList;
    }

    public void setFinishedList(ArrayList finishedList) {
        this.finishedList = finishedList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void addRestaurant(String id){
        finishedList.add(id);
    }
}
