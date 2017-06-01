package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Proces implements Serializable {
    private String process_name;
    private long proces_id;
    private String description;
    private int amount;
    private List<Step> steps;

    public Proces() {
    }

    public Proces(String process_name, long proces_id, String description, int amount, List<Step> steps) {
        this.process_name = process_name;
        this.proces_id = proces_id;
        this.description = description;
        this.amount = amount;
        this.steps = steps;
    }
}
