package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ljda0 on 09/12/2017.
 */

public class MessagesActivity extends AppCompatActivity {

    ArrayList<Message> inviteMessages = new ArrayList<Message>();
    ArrayAdapter<Message> mAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        mAdapter = new ArrayAdapter<Message>(this,android.R.layout.simple_list_item_1,  inviteMessages);

        ListView lsvInvites = (ListView) findViewById(R.id.lsv_invites);

        lsvInvites.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        lsvInvites.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent viewMessageActivity = new Intent(MessagesActivity.this, ViewMessageActivity.class);
                        viewMessageActivity.putExtra("position", position);
                        startActivity(viewMessageActivity);
                    }
                }
        );

        Button btnBack = (Button) findViewById(R.id.btn_back_home);

        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mainActivity = new Intent(MessagesActivity.this,MainActivity.class);
                        startActivity(mainActivity);
                    }
                }
        );

    }
}
