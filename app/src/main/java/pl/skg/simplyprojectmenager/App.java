package pl.skg.simplyprojectmenager;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRefUser = database.getReference("SingletonStepList");
//
//        SingletonStepList stepListSingleton=SingletonStepList.getInstance();
//
//        Step step = new Step("lakierowanie",1,true,true);
//        Step step2 = new Step("wiercenie",2,true,true);
//        Step step3 = new Step("ciecie",3,true,true);
//
//        stepListSingleton.addStep(step);
//        stepListSingleton.addStep(step2);
//        stepListSingleton.addStep(step3);
//
//        myRefUser.setValue(stepListSingleton);

    }
}
