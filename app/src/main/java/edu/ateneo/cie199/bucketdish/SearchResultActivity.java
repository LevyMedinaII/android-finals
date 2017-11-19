package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent receivedQueries = getIntent();
        TextView name = (TextView) findViewById(R.id.txv_restaurant);
        TextView price = (TextView) findViewById(R.id.txv_price);
        TextView address = (TextView) findViewById(R.id.txv_address);

        name.setText(receivedQueries.getStringExtra("name"));
        price.setText(receivedQueries.getStringExtra("price"));
        address.setText(receivedQueries.getStringExtra("location"));

        Button back = (Button) findViewById(R.id.btn_research);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(SearchResultActivity.this, SearchFilterActivity.class);
                startActivity(searchIntent);
            }
        });
    }
}
