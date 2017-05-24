package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String user_name;
    private String email;
    private String password;

    public User() {
    }

    public User(int id, String user_name, String email, String password) {
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.password = password;
    }
}
