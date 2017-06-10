package pl.skg.simpleprojectmenager.splash;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import pl.skg.simpleprojectmenager.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        preferences.edit().remove(AppConfig.TOKEN).commit();

        try {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finish();
    }
}
