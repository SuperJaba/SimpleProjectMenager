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
}
