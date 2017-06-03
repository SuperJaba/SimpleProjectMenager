package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Step implements Serializable {
    private String stepName;
    private int sectionId;
    private boolean started;
    private boolean finished;

    public Step() {
    }

    public Step(String stepName, int sectionId, boolean started, boolean finished) {
        this.stepName = stepName;
        this.sectionId = sectionId;
        this.started = started;
        this.finished = finished;
    }

    @Override
    public String toString() {
        return stepName +", "+ sectionId +", "+started+", "+finished+", ";
    }

//    public String getStepName() {
//        return stepName;
//    }
//
//    public void setStepName(String stepName) {
//        this.stepName = stepName;
//    }
//
//    public int getSectionId() {
//        return sectionId;
//    }
//
//    public void setSectionId(int sectionId) {
//        this.sectionId = sectionId;
//    }
//
//    public boolean isStarted() {
//        return started;
//    }
//
//    public void setStarted(boolean started) {
//        this.started = started;
//    }
//
//    public boolean isFinished() {
//        return finished;
//    }
//
//    public void setFinished(boolean finished) {
//        this.finished = finished;
//    }
}
