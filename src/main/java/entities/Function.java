package entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import entities.BoardxFunction;;

@Entity
public class Function {
	
	@Id
	@GeneratedValue
	private Long id; 
	private String name;
	@OneToMany(mappedBy="function")
	private List<BoardxFunction> boards;
	@OneToMany(mappedBy="function")
	List<Attribute> attributes;
	
	
	public List<BoardxFunction> getBoards() {
		return boards;
	}

	public void setBoards(List<BoardxFunction> boards) {
		this.boards = boards;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public Function() {
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
