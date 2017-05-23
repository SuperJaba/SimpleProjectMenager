package sdacademy.pl.simpleprojectmenager.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
class Steps implements Serializable {
    private String step_name;
    private int section_id;
    private boolean started;
    private boolean finished;
}
