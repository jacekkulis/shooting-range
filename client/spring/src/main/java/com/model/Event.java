package com.model;

import javax.persistence.*;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @Column(name = "id")
    private Long id;
    private String title;
    private String description;
    private String typeOfGun;
    private int numberOfCompetitors;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "img")
    private byte[] img;
    
    @Column(name = "referee_id")
    private Referee referee;

    @SuppressWarnings("unused")
    private String base64;

    @Transient
    public String getBase64() {
	return this.base64 = Base64.encode(this.img);
    }

    public void setBase64(String base64) {
	this.base64 = base64;
    }
    
    
    @ManyToOne
    @JoinColumn(name = "referee_id")
    public Referee getReferee() {
	return referee;
    }

    public void setReferee(Referee referee) {
	this.referee = referee;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getTypeOfGun() {
	return typeOfGun;
    }

    public void setTypeOfGun(String typeOfGun) {
	this.typeOfGun = typeOfGun;
    }

    public int getNumberOfCompetitors() {
	return numberOfCompetitors;
    }

    public void setNumberOfCompetitors(int numberOfCompetitors) {
	this.numberOfCompetitors = numberOfCompetitors;
    }

    public byte[] getImg() {
	return img;
    }

    public void setImg(byte[] img) {
	this.img = img;
    }

    @Override
    public String toString() {
	return String.format("Event[id=%d, title='%s', typeOfGun='%s', numberOfCompetitors='%d', referee.name='%s']",
		id, title, typeOfGun, numberOfCompetitors, referee.getName());
    }

}
