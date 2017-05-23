package sdacademy.pl.simpleprojectmenager.model;

import java.io.Serializable;

class Steps implements Serializable {
    private String step_name;
    private int amount;
    private int section_id;
    private boolean started;
    private boolean finished;
}
