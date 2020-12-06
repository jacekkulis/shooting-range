package com.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "referee")
public class Referee {
    @Id
    @Column(name = "id")
    private Long id;
    private String name;
    private String surname;
    private String title;
    
    @Transient
    private String fullName;

    private Set<Event> events;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
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

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    @OneToMany(mappedBy = "referee", cascade = CascadeType.ALL)
    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
    
 
    @Transient
    public String getFullName() {
	StringBuilder fullName = new StringBuilder();

	fullName.append(name).append(" ").append(surname);

	return fullName.toString();
    }
}
