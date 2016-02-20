package entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Board {
	@Id
	@GeneratedValue
	private Long id; 
	private String mac;
	private String name;
	@OneToMany(mappedBy="board")
	private List<BoardxFunction> boardxFunction;
	@OneToMany(mappedBy="board")
	private List<Session> sessions;
	@ManyToOne
	private Person user;
	
	public Person getUser() {
		return user;
	}


	public void setUser(Person user) {
		this.user = user;
	}


	public List<Session> getSessions() {
		return sessions;
	}


	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}


	public Board() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getMac() {
		return mac;
	}


	public void setMac(String mac) {
		this.mac = mac;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<BoardxFunction> getBoardxFunction() {
		return boardxFunction;
	}


	public void setBoardxFunction(List<BoardxFunction> boardxFunction) {
		this.boardxFunction = boardxFunction;
	}
	
	

	
	
}
