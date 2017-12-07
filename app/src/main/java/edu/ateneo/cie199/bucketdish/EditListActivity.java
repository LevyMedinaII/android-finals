package edu.ateneo.cie199.bucketdish;

import android.app.usage.NetworkStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditListActivity extends AppCompatActivity {

    ArrayList<Restaurant> bucketDish = new ArrayList<>();
    ArrayList<Restaurant> selected = new ArrayList<>();
    private  ArrayAdapter<Restaurant> mAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);
        final BucketdishApplication app = (BucketdishApplication) getApplication();


        bucketDish = app.getBucketList();

        mAdapter = new ArrayAdapter<Restaurant>(this,android.R.layout.simple_list_item_checked,bucketDish);

        final ListView lsvList = (ListView) findViewById(R.id.lsv_edit_list);
        lsvList.setChoiceMode(lsvList.CHOICE_MODE_MULTIPLE);
        lsvList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        Button btnAdd = (Button) findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("HISADI0","ASDASLD");
                        for(int i=0; i<lsvList.getCount(); i++){
                            CheckedTextView item = (CheckedTextView) lsvList.getChildAt(i);
                            Restaurant resto = (Restaurant) lsvList.getItemAtPosition(i);
                            if(item.isChecked())
                            {
                                Log.e("HISADI0","111");
                                selected.add(resto);
                            }
                        }

                        for(Restaurant resto : selected){
                            Log.e("S",resto.getName());
                        }
                    }
                }

        );


    }
}
