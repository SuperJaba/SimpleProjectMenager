package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;

@Getter
public class Proces implements Serializable {
    private String process_name;
    private String proces_id;
    private String description;
    private int amount;
    private List<Step> steps;

}
