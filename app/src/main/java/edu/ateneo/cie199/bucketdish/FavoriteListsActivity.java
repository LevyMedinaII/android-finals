package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListsActivity extends AppCompatActivity {

    ArrayList<List<Restaurant>> favoriteLists = new ArrayList<List<Restaurant>>();
    ArrayAdapter<List<Restaurant>> mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_lists);

        mAdapter = new ArrayAdapter<List<Restaurant>>(this,android.R.layout.simple_list_item_1, favoriteLists);
        final ListView lsvFavoriteList = (ListView) findViewById(R.id.lsv_favorite_list);
        lsvFavoriteList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        lsvFavoriteList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent editListActivity = new Intent(FavoriteListsActivity.this, EditListActivity.class);

                        editListActivity.putExtra("listnumber", position);
                        editListActivity.putExtra("listname", lsvFavoriteList.getItemAtPosition(position).toString());

                        startActivity(editListActivity);
                    }
                }
        );

        Button btnAddNewList = (Button) findViewById(R.id.btn_add_list);
        btnAddNewList.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newListActivity = new Intent(FavoriteListsActivity.this,NewListActivity.class);
                        startActivity(newListActivity);
                    }
                }
        );

        Button btnBack = (Button) findViewById(R.id.btn_home);
        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mainActivity = new Intent(FavoriteListsActivity.this, MainActivity.class);

                        startActivity(mainActivity);
                    }
                }
        );

    }
}
