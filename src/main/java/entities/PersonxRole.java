package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PersonxRole {
	@Id
	@GeneratedValue
	private Long id;
	//private Role role;
	//private Person person;
	
	public PersonxRole() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	/*
	public Role getRole() {
		return role;
	}
	public void setRole(Role rol) {
		this.role = rol;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	*/
}
