package edu.ateneo.cie199.bucketdish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

class InviteFriendAcitvity extends AppCompatActivity {
    ArrayList<Restaurant> inviteRestaurantList = new ArrayList<Restaurant>();
    ArrayList<User> inviteFriendsList = new ArrayList<User>();
    ArrayAdapter mRestaurantAdapter = null;
    ArrayAdapter mFriendsAdapter = null;
    Restaurant inviteRestaurant;
    ArrayList<User> invitedFriends = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);


        //TODO:POPULATE ARRAYLISTS


        mRestaurantAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_checked,inviteRestaurantList);
        mFriendsAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_checked,inviteFriendsList);

        final ListView lsvInviteRestaurant = (ListView) findViewById(R.id.lsv_invite_resto);
        final ListView lsvInviteFriend = (ListView) findViewById(R.id.lsv_invite_friends);

        lsvInviteRestaurant.setAdapter(mRestaurantAdapter);
        lsvInviteFriend.setAdapter(mFriendsAdapter);
        mRestaurantAdapter.notifyDataSetChanged();
        mFriendsAdapter.notifyDataSetChanged();

        lsvInviteRestaurant.setChoiceMode(lsvInviteRestaurant.CHOICE_MODE_SINGLE);
        lsvInviteFriend.setChoiceMode(lsvInviteFriend.CHOICE_MODE_MULTIPLE);

        Button btnInvite = (Button) findViewById(R.id.btn_invite);

        btnInvite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i=0; i<lsvInviteRestaurant.getCount(); i++){
                            CheckedTextView item = (CheckedTextView) lsvInviteRestaurant.getChildAt(i);
                            Restaurant resto = (Restaurant) lsvInviteRestaurant.getItemAtPosition(i);
                            if(item.isChecked())
                            {
                                Log.e("HISADI0","111");
                                inviteRestaurant = resto;
                                break;
                            }
                        }
                        for(int i=0; i<lsvInviteFriend.getCount(); i++){
                            CheckedTextView item = (CheckedTextView) lsvInviteFriend.getChildAt(i);
                            User friend = (User) lsvInviteFriend.getItemAtPosition(i);
                            if(item.isChecked())
                            {
                                Log.e("HISADI0","222");
                                invitedFriends.add(friend);
                            }
                        }

                        for(User friend : invitedFriends){
                            Log.e("Friend", friend.getUsername());
                        }
                    }
                }
        );

    }
}
