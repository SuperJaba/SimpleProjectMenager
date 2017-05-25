package pl.skg.simplyprojectmenager;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by RaVxp on 16.05.2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
    }
}
