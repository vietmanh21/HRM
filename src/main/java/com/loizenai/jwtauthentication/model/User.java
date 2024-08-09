package com.loizenai.jwtauthentication.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        })
})
public class User{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotBlank
    @Size(min=3, max = 50)
    private String username;

    @NotBlank
    @Size(min=6, max = 100)
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
    	joinColumns = @JoinColumn(name = "user_id"), 
    	inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "valid_util", nullable = false)
    private LocalDate validUtil;

    @Column(name = "is_valid")
    private boolean isValid;

    public User() {
    }

    public User(String username, String encode) {
        this.username = username;
        this.password = encode;
    }

    public User(String username, String password, Set<Role> roles, LocalDate validUtil) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.validUtil = validUtil;
        isValid = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @Size(min = 3, max = 50) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 3, max = 50) String username) {
        this.username = username;
    }

    public @NotBlank @Size(min = 6, max = 100) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 6, max = 100) String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getValidUtil() {
        return validUtil;
    }

    public void setValidUtil(LocalDate validUtil) {
        this.validUtil = validUtil;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @PrePersist
    protected void onInsert() {
        dateCreated = LocalDate.now();
    }
}