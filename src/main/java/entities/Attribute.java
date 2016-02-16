package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Attribute {
	@Id
	@GeneratedValue
	private Long id; 
	private String type;
	private Boolean input;
	public Attribute() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getInput() {
		return input;
	}
	public void setInput(Boolean input) {
		this.input = input;
	}
	
	
}
