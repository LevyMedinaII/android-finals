package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class ViewAll extends AppCompatActivity {

    ArrayAdapter<Restaurant> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        ArrayList<Restaurant> data = new ArrayList<>();
        mAdapter = new ArrayAdapter<Restaurant>(this, android.R.layout.simple_list_item_1,data);

        Button back = (Button) findViewById(R.id.btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchFilterIntent = new Intent(ViewAll.this, SearchFilterActivity.class);
                startActivity(searchFilterIntent);
            }
        });
    }
}
