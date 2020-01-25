package com.nguyen.security.entity;

import com.nguyen.security.clazz.EStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(exclude = "roles")
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    @Enumerated(value = EnumType.STRING)
    private EStatus status;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    private List<Role> roles;

    public User(String password, String username, String firstName, String lastName, String email, EStatus status) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
