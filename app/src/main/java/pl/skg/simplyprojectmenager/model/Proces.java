package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Proces implements Serializable {
    private String procesName;
    private long procesId;
    private String description;
    private int amount;
    private List<Step> steps;

    public Proces() {
    }

    public Proces(String procesName, long procesId, String description, int amount, List<Step> steps) {
        this.procesName = procesName;
        this.procesId = procesId;
        this.description = description;
        this.amount = amount;
        this.steps = steps;
    }
}
