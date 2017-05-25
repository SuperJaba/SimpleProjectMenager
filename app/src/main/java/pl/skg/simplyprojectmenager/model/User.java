package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {

    private int id;
    private String userName;
    private String email;
    private String password;
    private Boolean isAdmin;

    public User() {
    }

    public User(int id, String userName, String email, String password, Boolean isAdmin) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}
