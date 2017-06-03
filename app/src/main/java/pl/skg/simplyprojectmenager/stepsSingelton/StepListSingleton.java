package pl.skg.simplyprojectmenager.stepsSingelton;

/**
 * Created by RENT on 2017-06-01.
 */
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pl.skg.simplyprojectmenager.model.Step;

@Setter
@Getter
public class StepListSingleton {
    private List<Step> stepList;


    private static StepListSingleton INSTANCE;

    private StepListSingleton() {
        this.stepList=new ArrayList<>();
    }

    public static StepListSingleton getInstance() {
        if (INSTANCE == null)
            synchronized (StepListSingleton.class) {
                if (INSTANCE == null)
                    INSTANCE = new StepListSingleton();
            }




        return INSTANCE;
    }

    public void addStep(Step step){
        stepList.add(step);
    }



}

