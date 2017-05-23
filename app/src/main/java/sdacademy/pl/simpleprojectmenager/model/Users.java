package sdacademy.pl.simpleprojectmenager.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Users implements Serializable {
    private int id;
    private String user_name;
    private String email;
    private String password;
}
