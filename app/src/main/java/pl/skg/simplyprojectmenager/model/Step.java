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

    public Step(String stepName, int sectionId, int status) {
        this.stepName = stepName;
        this.sectionId = sectionId; //identyfikator działu(Section.class)
        this.status = status;

    }


    @Override
    public String toString() {
        return "Step{" +
                "stepName='" + stepName + '\'' +
                ", sectionId=" + sectionId +
                ", status=" + status +
                '}';
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
