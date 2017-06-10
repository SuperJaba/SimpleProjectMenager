package pl.skg.simpleprojectmanager;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;


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

        FirebaseDatabase fireInstance = FirebaseDatabase.getInstance();
        fireInstance.setPersistenceEnabled(true);
        fireInstance.getReference().keepSynced(true);
    }

//    public static void addStep(Step step){
//        tmpStepList.add(step);
//    }
//
//    public static void clearList(){
//        tmpStepList.clear();
//    }
}
