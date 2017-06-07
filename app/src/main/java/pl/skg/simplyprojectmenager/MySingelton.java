package pl.skg.simplyprojectmenager;

import java.util.ArrayList;
import java.util.List;

import pl.skg.simplyprojectmenager.model.Proces;
import pl.skg.simplyprojectmenager.model.Step;

/**
 * Created by RENT on 2017-06-06.
 */

public class MySingelton extends Proces{

private  List<Step> listSteps;
    private  String procesName;
    private  String procesId;
    private  String description;
    private  int amount;

    // Private constructor prevents instantiation from other classes
    private MySingelton() {
        this.listSteps=new ArrayList<>();
        this.procesName="";
        this.procesId="";
        this.description="";
        this.amount=1;
    }

    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class MySingeltonHolder {
        private static final MySingelton INSTANCE = new MySingelton();
    }

    public static MySingelton getInstance() {
        return MySingeltonHolder.INSTANCE;
    }

    public void addStep(Step step){
        listSteps.add(step);
//
    }

    public void clearList(){
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
}

