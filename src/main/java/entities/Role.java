package entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import entities.PersonxRole;;

@Entity
public class Role {
	@Id
	@GeneratedValue
	private Long id; 
	private String name;
	@OneToMany
	private List<PersonxRole> roles;
	
	
	
	public List<PersonxRole> getRoles() {
		return roles;
	}

	public void setRoles(List<PersonxRole> roles) {
		this.roles = roles;
	}

	public Role() {
		super();
	}

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
	
	
	
}
