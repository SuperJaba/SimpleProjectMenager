package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;
import java.util.List;


public class Section implements Serializable {
    private int section_id;
    private String section_name;
    private List<User> userList;

    public Section() {
    }

    public Section(int section_id, String section_name, List<User> userList) {
        this.section_id = section_id;
        this.section_name = section_name;
        this.userList = userList;
    }
}
