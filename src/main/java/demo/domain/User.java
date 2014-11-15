package demo.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The persistent class for the APP_USER database table.
 * 
 */
@Entity
@Table(name="APP_USER")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="USER_ID")
	private long userId;

	@Column(name="active", nullable=false)
	private int active;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_JOINED")
	private Date dateJoined;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="SURNAME")
	private String surname;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="VERSION")
	@Version
	private Long version;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "APP_USERROLE", joinColumns = {
			@JoinColumn(name = "USER_ID", nullable = false, updatable = true) }, 
			inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", 
					nullable = false, updatable = true) })
	
	private Set<Role> roles = new HashSet<>(0);

	public User() {
		
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getActive() {
		return this.active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Date getDateJoined() {
		return this.dateJoined;
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}
 
	public void setCategories(Set<Role> roles) {
		this.roles = roles;
	}

}