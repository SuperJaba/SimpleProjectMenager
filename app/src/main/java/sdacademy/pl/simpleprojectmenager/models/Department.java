package sdacademy.pl.simpleprojectmenager.models;

import java.util.List;

/**
 * Created by RENT on 2017-05-23.
 */

class Department {

    private Long id;
    private String departmenytName;
    private List<Employee> employeeList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmenytName() {
        return departmenytName;
    }

    public void setDepartmenytName(String departmenytName) {
        this.departmenytName = departmenytName;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
