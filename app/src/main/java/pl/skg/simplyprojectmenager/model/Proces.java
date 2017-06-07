package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Proces implements Serializable {
    private String procesName;
    private String procesId;
    private String description;
    private int amount;
    private List<Step> steps;

    public Proces() {
        steps=new ArrayList<>();
    }

    public Proces(String procesName, String procesId, String description, int amount, List<Step> steps) {
        this.procesName = procesName;
        this.procesId = procesId;
        this.description = description;
        this.amount = amount;
        this.steps = steps;
    }
}
