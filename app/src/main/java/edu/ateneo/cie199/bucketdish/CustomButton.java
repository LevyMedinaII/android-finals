package edu.ateneo.cie199.bucketdish;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by ljda0 on 18/11/2017.
 */

public class CustomButton extends android.support.v7.widget.AppCompatButton {


    public CustomButton(Context context) {
        super(context);
        setButton();
    }
    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setButton();
    }
    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setButton();
    }

    private void setButton() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/smilingcats.otf");
        setTypeface(font, Typeface.NORMAL);
        setTextColor(Color.WHITE);
        setBackgroundColor(Color.rgb(255,128,0));
    }
}
