package sdacademy.pl.simpleprojectmenager.models;

import java.util.List;

/**
 * Created by RENT on 2017-05-23.
 */

public class Proces {


    private Long id;
    private List<Step> stepList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }
}
