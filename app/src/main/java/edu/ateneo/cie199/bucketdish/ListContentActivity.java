package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_content);
        ArrayList<Restaurant> data = new ArrayList<>();
        ArrayAdapter<Restaurant> mAdapter = new BucketDishAdapter(this, data);

        ListView listContent = (ListView) findViewById(R.id.lsv_content);
        listContent.setAdapter(mAdapter);

        Button back = (Button) findViewById(R.id.btn_back);
        Button edit = (Button) findViewById(R.id.btn_edit);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favoritesActivityIntent = new Intent(ListContentActivity.this, FavoriteListsActivity.class);
                startActivity(favoritesActivityIntent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

    }
}
