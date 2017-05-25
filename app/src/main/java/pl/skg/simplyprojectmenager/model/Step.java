package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Step implements Serializable {
    private String stepName;
    private String sectionId;
    private boolean started;
    private boolean finished;


    public Step() {
    }

    public Step(String stepName, String sectionId, boolean started, boolean finished) {
        this.stepName = stepName;
        this.sectionId = sectionId;
        this.started = started;
        this.finished = finished;
    }
}
