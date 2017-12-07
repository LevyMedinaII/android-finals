package edu.ateneo.cie199.bucketdish;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(DEFAULT_LAYOUT_RESOURCE, parent, false);
    }

        Restaurant restoItem = getItem(position);

        TextView txvName = (TextView) convertView.findViewById(R.id.txv_restoname);
        final CheckBox chkBucket = (CheckBox) convertView.findViewById(R.id.chk_addtolist);

        txvName.setText(restoItem.getName());

        if(chkBucket.isChecked()||chkBucket.isSelected())
        {
            chkBucket.setChecked(true);
        }
        else
        {
            chkBucket.setChecked(false);
        }

        chkBucket.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chkBucket.setChecked(true);
                }
                else
                {
                    chkBucket.setChecked(false);
                }
            }
        });

        return convertView;
    }
}
