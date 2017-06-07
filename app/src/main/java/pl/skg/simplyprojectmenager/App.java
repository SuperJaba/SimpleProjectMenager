package pl.skg.simplyprojectmenager;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

import pl.skg.simplyprojectmenager.model.Step;


public class App extends Application {

//    public List<Step> getTmpStepList() {
//        return tmpStepList;
//    }
//
//    public void setTmpStepList(List<Step> tmpStepList) {
//        this.tmpStepList = tmpStepList;
//    }
//
//    private static List<Step> tmpStepList=new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);

        MySingelton mySingelton=MySingelton.getInstance();


    }

//    public static void addStep(Step step){
//        tmpStepList.add(step);
//    }
//
//    public static void clearList(){
//        tmpStepList.clear();
//    }
}
