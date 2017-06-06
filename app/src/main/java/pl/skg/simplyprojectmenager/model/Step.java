package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Step implements Serializable {

    private String stepName;
    private int sectionId;
    private int status; //3 positions (0-not started, 1-doing, 2-finished)

    public Step() {
    }

    public Step(String stepName, int sectionId, int status) {
        this.stepName = stepName;
        this.sectionId = sectionId; //identyfikator działu(Section.class)
        this.status = status;

    }
}
