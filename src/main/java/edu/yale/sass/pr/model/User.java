package edu.yale.sass.pr.model;

import javax.persistence.*;
import java.util.Date;

/**
 * we probably will not use this class, we have so many person, Member, RosterPerson, ...class
 */
@Entity
@Table(name="USER")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="NAME",  nullable = true)
	private String name; 
	
	@Column(name="SURNAME",  nullable = true)
	private String surname;
	
	@Column(name="user_id",nullable=false, unique = true)
	private String userId="";
	
	@Column(name="department_info", nullable=true)
	private String departmentInfo;
	
	@Column(name="email",nullable=true)
	private String email=" ";
	
	@Column(name="is_admin", nullable=false)
	private boolean admin = false;
	

	@Column(name="active", nullable=false)
	private boolean active=true;
	
	@Column(name="created_on", nullable=false)
	private Date createdOn;
	

	public User(){
		
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
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


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getDepartmentInfo() {
		return departmentInfo;
	}


	public void setDepartmentInfo(String departmentInfo) {
		this.departmentInfo = departmentInfo;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public boolean isAdmin() {
		return admin;
	}


	public void setAdmin(boolean admin) {
		this.admin = admin;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
