package pl.skg.simplyprojectmenager;

import java.util.ArrayList;
import java.util.List;

import pl.skg.simplyprojectmenager.model.Proces;
import pl.skg.simplyprojectmenager.model.Step;

/**
 * Created by RENT on 2017-06-06.
 */

public class MySingelton extends Proces{


    private Proces proces;

    public Proces getProces() {
        return proces;
    }

    public void setProces(Proces proces) {
        this.proces = proces;
    }


    // Private constructor prevents instantiation from other classes
    private MySingelton() {
        proces=new Proces();
    }

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class MySingeltonHolder {
        private static final MySingelton INSTANCE = new MySingelton();
    }

    public static MySingelton getInstance() {
        return MySingeltonHolder.INSTANCE;
    }

    public void addStep(Step step){
        List<Step> tmpList=proces.getSteps();
        tmpList.add(step);
        proces.setSteps(tmpList);
//        proces.getSteps().add(step);
    }

    public void clearList(){
        proces.getSteps().clear();
    }
}

