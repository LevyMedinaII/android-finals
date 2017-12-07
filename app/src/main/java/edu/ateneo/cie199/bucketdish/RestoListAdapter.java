package edu.ateneo.cie199.bucketdish;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ljda0 on 07/12/2017.
 */

public class RestoListAdapter extends ArrayAdapter<Restaurant> {
    private static int DEFAULT_LAYOUT_RESOURCE = R.layout.layout_resto_list_item;
    private Context mContext = null;
    public RestoListAdapter(@NonNull Context context, ArrayList<Restaurant> list){
        super(context,DEFAULT_LAYOUT_RESOURCE,list);
        mContext = context;

        return;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Restaurant restoItem = getItem(position);

        TextView txvName = (TextView) convertView.findViewById(R.id.txv_restoname);
        CheckBox chkBucket = (CheckBox) convertView.findViewById(R.id.chk_bucket);

        txvName.setText(restoItem.getName());



        return super.getView(position, convertView, parent);
    }
}
