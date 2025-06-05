package org.itsci.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority, Comparable<Authority> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", columnDefinition = "VARCHAR(30)", nullable = false, unique = true)
    private EAuthorityType roleName;
    @Column(name = "description")
    private String description;

    public Authority() {
    }

    public Authority(EAuthorityType roleName) {
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EAuthorityType getRoleName() {
        return roleName;
    }

    public void setRoleName(EAuthorityType roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getAuthority();
    }

    @Override
    public String getAuthority() {
        return this.roleName.toString();
    }

    @Override
    public int compareTo(Authority o) {
        return this.roleName.compareTo(o.roleName);
    }
}
