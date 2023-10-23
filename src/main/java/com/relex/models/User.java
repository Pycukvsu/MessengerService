package com.relex.models;


import com.relex.messenger.models.Chat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "password")
    private String password;
    @Column(name = "mail")
    private String mail;
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "activity")
    private boolean activity;
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

    public User(Long id, String nickname, String name, String surname, String password, String mail, String phoneNumber, Collection<Role> roles) {
        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.mail = mail;
        this.enabled = false;
        this.phoneNumber = phoneNumber;
        this.activity = true;
        this.roles = roles;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + name + '\'' +
                ", lastName='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", roles=" + roles +
                '}';
    }
}
