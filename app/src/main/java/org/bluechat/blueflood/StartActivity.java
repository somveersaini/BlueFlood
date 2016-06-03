package org.bluechat.blueflood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class StartActivity extends AppCompatActivity {

    private boolean start = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "Gasalt-Black.ttf");
        ((Button) findViewById(R.id.play)).setTypeface(typeface);
        ((Button) findViewById(R.id.setting)).setTypeface(typeface);
        Typeface typeface1 = Typeface.createFromAsset(getAssets(),
                "future.otf");
        ((TextView) findViewById(R.id.gamename)).setTypeface(typeface1);
        ((TextView) findViewById(R.id.thegame)).setTypeface(typeface1);

        Settings.init(getApplicationContext());
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            YoYo.with(Techniques.SlideInDown)
                .duration(500)
                .playOn(findViewById(R.id.play));
            YoYo.with(Techniques.SlideInDown)
                    .duration(550)
                    .playOn(findViewById(R.id.setting));
            YoYo.with(Techniques.SlideInUp)
                    .duration(600)
                    .playOn(findViewById(R.id.help));
            YoYo.with(Techniques.SlideInUp)
                    .duration(650)
                    .playOn(findViewById(R.id.about));
            YoYo.with(Techniques.SlideInUp)
                    .duration(700)
                    .playOn(findViewById(R.id.trophy));
        }
    }

    public void onResume(){
        super.onResume();
        YoYo.with(Techniques.SlideInDown)
                .duration(500)
                .playOn(findViewById(R.id.play));
        YoYo.with(Techniques.SlideInUp)
                .duration(550)
                .playOn(findViewById(R.id.setting));
        YoYo.with(Techniques.SlideInUp)
                .duration(600)
                .playOn(findViewById(R.id.help));
        YoYo.with(Techniques.SlideInUp)
                .duration(650)
                .playOn(findViewById(R.id.about));
        YoYo.with(Techniques.SlideInUp)
                .duration(700)
                .playOn(findViewById(R.id.trophy));
    }
    public void buttonhandler(View view) {
        int id = view.getId();
        Settings.buttonSound();
        switch(id) {
            case R.id.play:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.setting:
                Intent intent1 = new Intent(this, Settings.class);
                startActivity(intent1);
                break;
            case R.id.help:
                startActivity(new Intent(this, about.class));
                break;
            case R.id.about:
                startActivity(new Intent(this, help.class));
                break;
            case R.id.trophy:
                startActivity(new Intent(this, acheivements.class));
                break;
            default:
                break;
        }
    }

    public void onBackPressed() {
        if (!start) {
            setContentView(R.layout.activity_start);
        } else {
            this.finish();
        }
    }

}
