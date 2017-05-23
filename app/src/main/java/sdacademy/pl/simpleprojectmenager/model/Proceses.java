package sdacademy.pl.simpleprojectmenager.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Proceses implements Serializable {
    private String process_ame;
    private long proces_id;
    private String description;
    private int amount;
    private List<Steps> steps;


}
