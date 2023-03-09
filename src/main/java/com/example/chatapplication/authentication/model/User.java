package com.example.chatapplication.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_db")
public class User {

    @Id
    @NotEmpty(message = "Username is mandatory")
    @Size(min = 5, max = 15, message = "Min 5, max 15")
    private String username;

    @NotEmpty(message = "Password is mandatory")
    @Size(min = 8, message = "At least 8 characters")
    private String password;

    @NotEmpty(message = "Name is mandatory")
    private String name;

    @Email(message = "Invalid email")
    @NotEmpty(message = "Email is mandatory")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    private boolean isOnline;

    public void addRoles(Collection<Role> roles) {
        this.roles.addAll(roles);
    }
}
