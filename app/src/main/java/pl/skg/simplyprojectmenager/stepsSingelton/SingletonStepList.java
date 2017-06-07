//package pl.skg.simplyprojectmenager.stepsSingelton;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import pl.skg.simplyprojectmenager.model.Step;
//
///**
// * Created by k.czechowski83@gmail.com on 2017-06-02.
// */
//
//public class SingletonStepList {
//
//    private List<Step> stepList;
//
//
//    private static SingletonStepList instance;
//
//    private SingletonStepList(){
//        this.stepList=new ArrayList<>();
//    }
//
//    public static SingletonStepList getInstance(){
//        if(instance == null){
//            synchronized (SingletonStepList.class) {
//                if(instance == null){
//                    instance = new SingletonStepList();
//                }
//            }
//        }
//        return instance;
//    }
//
//    public List<Step> getStepList() {
//        return stepList;
//    }
//
//    public void setStepList(List<Step> stepList) {
//        this.stepList = stepList;
//    }
//
//    public void addStep(Step step){
//        stepList.add(step);
//    }
//
//
//
//}