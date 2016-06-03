package org.bluechat.blueflood;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.Switch;
import android.widget.TextView;


/**
 * Created by Samsaini on 12/05/2016.
 */
public class about extends Activity{
    private static final String TAG = "HelpActivity" ;
    public static boolean sfx;
    public static int theme;
    public static boolean next = false;


    Switch  ssfx;
    SharedPreferences settings;
    SoundPool sp;
    int  btnbtn, scs, sce;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setup the window
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.about);

        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "halo.TTF");
        ((TextView) findViewById(R.id.about1)).setTypeface(typeface);
        ((TextView) findViewById(R.id.about2)).setTypeface(typeface);
        ((TextView) findViewById(R.id.developer)).setTypeface(typeface);
        ((TextView) findViewById(R.id.email)).setTypeface(typeface);
        ((TextView) findViewById(R.id.cont)).setTypeface(typeface);
        ((TextView) findViewById(R.id.fb)).setTypeface(typeface);
        sp = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        btnbtn = sp.load(this, R.raw.btnbtn, 1);
        scs = sp.load(this, R.raw.btnbtn, 1);


        settings = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
