package entities;

public class Player {
	public final static Integer PLAYER_1 = 1;
	public final static Integer PLAYER_2 = 2	;
	
	private String name;
	private Integer points;
	private Board board;
	
	
	
	public Player(){
		board = new Board();
		points = 0;
		name = "Example";
		
	}

	
	
	public Board getBoard() {
		return board;
	}



	public void setBoard(Board board) {
		this.board = board;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
	
	
}
