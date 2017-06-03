package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Step implements Serializable {
    private String step_name;
    private int section_id;
    private boolean started;
    private boolean finished;

    public Step() {
    }

    public Step(String step_name, int section_id, boolean started, boolean finished) {
        this.step_name = step_name;
        this.section_id = section_id;
        this.started = started;
        this.finished = finished;
    }

    @Override
    public String toString() {
        return step_name+", "+section_id+", "+started+", "+finished+", ";
    }

//    public String getStep_name() {
//        return step_name;
//    }
//
//    public void setStep_name(String step_name) {
//        this.step_name = step_name;
//    }
//
//    public int getSection_id() {
//        return section_id;
//    }
//
//    public void setSection_id(int section_id) {
//        this.section_id = section_id;
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
