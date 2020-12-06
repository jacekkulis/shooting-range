package com.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private String name;
    private String surname;
    private Set<Role> roles;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
	return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
	this.passwordConfirm = passwordConfirm;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getSurname() {
	return surname;
    }

    public void setSurname(String surname) {
	this.surname = surname;
    }

    /**
     * Gets the full name of the person.
     * 
     * @return The full name of the person.
     */
    @Transient
    public String getFullName() {
	StringBuilder name = new StringBuilder();

	name.append(name).append(" ").append(surname);

	return name.toString();
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
	return roles;
    }

    public void setRoles(Set<Role> roles) {
	this.roles = roles;
    }
}
