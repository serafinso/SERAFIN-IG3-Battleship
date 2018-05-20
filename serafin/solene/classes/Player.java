package serafin.solene.classes;
import java.util.ArrayList;
import java.util.Random;

public class Player implements PlayerMustDo {
	private ArrayList<Coordinate> coordinateHit = new ArrayList<Coordinate>(); //Coordinate shoot by the current player
	private ArrayList<Ship> shiplist = new ArrayList<Ship>(); //List of ship of the current player
	private int num;
	
	public Player(int num){//CONSTRUCTOR
		this.num = num;
	}

	public boolean correctShip(Coordinate startCoord, Coordinate endCoord) {
		//This function is in Player class because check if the player enter a correct Ship 
		//return true if the coordinates are corrects for a Ship
		//Correct for a ship = (Good size) && (horizontal or vertical Ship) && (Not an another ship here)
		//precondition : startCoord and endCoord are corrects Coordinates
		
		if (!(startCoord.getC().equals(endCoord.getC()) ) && (startCoord.getR() != endCoord.getR()) ) {
			//correct if it is Horizontal or Vertical
			return false;
		}
		if (startCoord.getC().charAt(0) > endCoord.getC().charAt(0) 
				//Ship's coordinates in order: A1, A4 instead of A4, A1 : to avoid the problem of an array list
				//with negative size (The player can still enter a boat with the coordinates not in the order)
				|| startCoord.getR() > endCoord.getR()) {
			return false;
		}
		
		//Size between 2 and 5
		Ship s1 = new Ship(startCoord, endCoord);		
		if (s1.getLength() >5 || s1.getLength()<2) {
			return false;
		}
		//already ship here
		if (alreadyShipHere(s1)) {
			return false;
		}
		return true;
	}
	
	public int containShipSize(int s) {
		//return the number of Ship with the size s
		int nb = 0;
		for (int i =0;i< shiplist.size();i++) {
			Ship s1 = shiplist.get(i);
			s1.getLength();
			if (shiplist.get(i).getLength()==s)
				nb += 1;
		}
		return nb;
	}
	
	public boolean shipAtThisCoordinate(Coordinate c1) {
		//return true if the current player have a ship at c1 (so that the boats do not overlap)
		for(int j=0; j<shiplist.size();j++) {//to each ..		//of the boat s2
			Ship s2 = shiplist.get(j);
			for (int k = 0; k < s2.getLength(); k++) {	//coordonate
				if (c1.equal(s2.getCoord(k))) { //if it is equal, there is a ship here 
					return true;
				}
			}
		}
		return false;			
	}
	
	public boolean alreadyShipHere(Ship s1) { 
		//return true if the current Player have an another Ship where we want to put the Ship s1 
		//different from shipAtThisCoordinate because the parameter is a Ship and not a Coordinate
		Coordinate c1 = null;
		for (int i=0; i<s1.getLength();i++) {
			c1 = s1.getCoord(i);
			if (shipAtThisCoordinate(c1)) {
				return true;
			}
		}
		return false;
	}
	
	public Ship whichShipHere(Coordinate c1) {
		//Supposing that the current player have a ship in the Coordinate c1 because use with alreadyShipHere 
		//but to avoid problem return null if no Ship here
		//Return the Ship in the Coordinate c1
		Ship s1 = null;
		for(int j=0; j<shiplist.size();j++) {//to each ..		//of the boat s2
			Ship s2 = shiplist.get(j);
			for (int k = 0; k<s2.getLength();k++) {		//coord
				if (c1.equal(s2.getCoord(k))) { //if it is equal, 
					s1 = s2;
				}
			}
		}
		return s1;
	}
	
	public boolean allShipDestroyed() {
		//return True if all the Ship of the current player are destroyed 
		for (int i =0; i< shiplist.size(); i++) {
			Ship s1 = shiplist.get(i);
			if(!s1.isDestroyed()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean coordinateHitContains(Coordinate c1) {
		//return true if the array CoordinateHit of the current player contain c1
		for (int i =0;i< coordinateHit.size();i++) {
			if (coordinateHit.get(i).equal(c1))
				return true;
		}
		return false;
	}
	
	public int random10() {
		//random between 1 and 10
		Random rd = new Random();
		return rd.nextInt(10) + 1;
	}
	
	public String randomBetween(char min, char max) {
		//return a string between a char min and a char max 
		Random rd = new Random();
		char c = (char)(rd.nextInt(max - min + 1) + min);
		return Character.toString(c);
	}
	
	public void printGame(Player o){
		//o = player opponent
		//print the game for a player before and after pAttack attack (print where he already attack and boat he as hit)
		//~ = nothing happen here //1 = ShipFind //-1 = ShipDestroyed //X missile in the water
		System.out.println("\n1 = You Hit a Ship but it isn't destroyed\n-1 = You destroyed a Ship "
				+ "\n~ = Water \nX = You shot in the water \n");
		
		System.out.println("     A   B   C   D   E   F   G   H   I   J  ");
		for(int r=1; r<=10;r++ ) {//for each row
			if (r==10) { //align lign 10
				System.out.print(" " + r +"  ");
			} else {
				System.out.print(" " + r +"   ");
			}
			for(char c='A'; c<='J'; c++) {//for each colomn
				Coordinate c1 = new Coordinate(c + Integer.toString(r));
				if (coordinateHitContains(c1)) {
					System.out.print("X   ");
				} else {
					if (o.shipAtThisCoordinate(c1)) {
						Ship s1 = o.whichShipHere(c1);
						if (s1.isDestroyed()) {
							System.out.print("-1  ");
						} else {
							if (s1.isHit(c1)) {
								System.out.print("1   ");
							} else {
								System.out.print("~   ");
							}
						}
					} else {
						System.out.print("~   ");
					}
				}				
			}
			System.out.println();
			System.out.println();
		}
	}

	//GETTER AND SETTER :
	
	public ArrayList<Coordinate> getCoordinateHit() {
		return coordinateHit;
	}
	public ArrayList<Ship> getShiplist() {
		return shiplist;
	}
	public void addShiplist(Ship s1) { //set shipList but here we add a ship 
		shiplist.add(s1);
	}
	public void addCoordinateHit(Coordinate c1) { //set coordinateHit but here we add a coordinates
		coordinateHit.add(c1);
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public Coordinate askCoordinate(Player p) {return null;	}
	public void enterAllShip() {}
	public void whoBegin() {}
	public void printScores(int playerWinTimes, int nbTimes) {}
	public Coordinate printShoot(Player o) {return null;}
}	