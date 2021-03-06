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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.about);

        Typeface typeface1 = Typeface.createFromAsset(getAssets(),
                "future.otf");
        Typeface typeface2 = Typeface.createFromAsset(getAssets(),
                "Gasalt-Black.ttf");
        ((TextView) findViewById(R.id.about1)).setTypeface(typeface1);
        ((TextView) findViewById(R.id.about2)).setTypeface(typeface2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
