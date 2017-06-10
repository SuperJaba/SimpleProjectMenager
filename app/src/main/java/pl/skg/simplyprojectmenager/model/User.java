package pl.skg.simplyprojectmenager.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
