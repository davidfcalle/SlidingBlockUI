package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person {
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String secondName;
	private Integer age;
	
	public Person(Long id, String name, String secondName, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.secondName = secondName;
		this.age = age;
	}

	public Person(){ 
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
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}	
	
}
