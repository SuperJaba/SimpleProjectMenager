package sdacademy.pl.simpleprojectmenager.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Sections implements Serializable {
    private int section_id;
    private String section_name;
}
