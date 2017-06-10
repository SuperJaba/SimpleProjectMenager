package pl.skg.simpleprojectmenager.model;

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

    @Override
    public String toString() {
        return stepName + ", " + sectionId + ", " + status;
    }

    public Step(String stepName, int sectionId, int status) {
        this.stepName = stepName;
        this.sectionId = sectionId; //identyfikator działu(Section.class)
        this.status = status;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
