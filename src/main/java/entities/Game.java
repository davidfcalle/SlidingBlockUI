package entities;

public class Game {
	
	public Player p1;
	public Player p2;
	
	
	public Game(){
		p1 = new Player();
		p2 = new Player();
		p1.setName("David");
		p2.setName("Silva");
		p1.setPoints( 0 );
		p2.setPoints( 1 );
		
	}
	


	public Player getP1() {
		return p1;
	}


	public void setP1(Player p1) {
		this.p1 = p1;
	}


	public Player getP2() {
		return p2;
	}


	public void setP2(Player p2) {
		this.p2 = p2;
	}	

}
