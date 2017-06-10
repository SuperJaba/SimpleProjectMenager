package pl.skg.simplyprojectmenager;

import java.util.ArrayList;
import java.util.List;

import pl.skg.simplyprojectmenager.model.Process;
import pl.skg.simplyprojectmenager.model.Step;

/**
 * Created by RENT on 2017-06-06.
 */

public class AppContex extends Process {
//TODO ggetters
    private List<Step> listSteps;

    private String procesName;
    private String procesId;
    private String description;
    private int amount;

    private static final AppContex INSTANCE = new AppContex();

    // Private constructor prevents instantiation from other classes
    private AppContex() {
        this.listSteps = new ArrayList<>();
        this.procesName = "";
        this.procesId = "";
        this.description = "";
        this.amount = 1;
    }

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */

    public static AppContex getInstance() {
        return INSTANCE;
    }

    public void addStep(Step step) {
        listSteps.add(step);
//
    }

    public void clearList() {
        listSteps.clear();
    }

    public List<Step> getListSteps() {
        return listSteps;
    }

    public void setListSteps(List<Step> listSteps) {
        this.listSteps = listSteps;
    }

    public String getProcesName() {
        return procesName;
    }

    public void setProcesName(String procesName) {
        this.procesName = procesName;
    }

    public String getProcesId() {
        return procesId;
    }

    public void setProcesId(String procesId) {
        this.procesId = procesId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void removeItem(int position) {
        listSteps.remove(position);
    }
}

