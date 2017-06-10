package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Section implements Serializable {

    private int sectionId;

    private String sectionName;

    private List<User> userList;

    public Section() {
    }

    public Section(int sectionId, String section_name, List<User> userList) {
        this.sectionId = sectionId;
        this.sectionName = section_name;
        this.userList = userList;
    }
}
