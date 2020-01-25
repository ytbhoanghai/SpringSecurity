package com.nguyen.security.entity;

import com.nguyen.security.clazz.ERole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer id;
    @Enumerated(value = EnumType.STRING)
    private ERole type;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role(ERole type) {
        this.type = type;
    }
}
