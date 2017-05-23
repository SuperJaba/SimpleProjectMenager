package sdacademy.pl.simpleprojectmenager.models;

/**
 * Created by RENT on 2017-05-23.
 */


public class Step {

    private Long id;
    private String name;
    private Department department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
