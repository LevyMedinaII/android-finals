package edu.ateneo.cie199.bucketdish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewMessageActivity extends AppCompatActivity {
    Message message;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        //TODO:: GET MESSAGE FROM PREVIOUS ACTIVITY SORRY KYLE

        TextView txvMessage = (TextView) findViewById(R.id.txv_message);
        TextView txvSender = (TextView) findViewById(R.id.txv_sender);
        TextView txvResto = (TextView) findViewById(R.id.txv_resto_invite);

        String sMessage = message.getMessage();
        String sender = message.getmUser().getUsername();
        String resto = message.getRestaurant().getName();

        txvMessage.setText(sMessage);
        txvSender.setText("From " + sender);
        txvResto.setText(resto);

        Button btnAccept = (Button) findViewById(R.id.btn_accept);
        Button btnDeny = (Button) findViewById(R.id.btn_deny);

        btnAccept.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO:: DELETE MESSAGE FROM LIST

                    }
                }
        );

        btnDeny.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO:: DELETE MESSAGE??? REALLY DUNNo

                    }
                }
        );





    }
}
