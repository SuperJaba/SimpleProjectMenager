package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;

class Step implements Serializable {
    private String step_name;
    private int section_id;
    private boolean started;
    private boolean finished;
}
