package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Process implements Serializable {

    private String procesName;

    private String procesId;

    private String description;

    private int amount;

    private List<Step> steps;

    public Process() {
        steps = new ArrayList<>();
    }

    public Process(String procesName, String procesId, String description, int amount, List<Step> steps) {
        this.procesName = procesName;
        this.procesId = procesId;
        this.description = description;
        this.amount = amount;
        this.steps = steps;
    }

    //TODO remove getters
    public String getProcesName() {
        return procesName;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void setProcesId(String procesId) {
        this.procesId = procesId;
    }

    public List<Step> getSteps() {
        return steps;
    }
}
