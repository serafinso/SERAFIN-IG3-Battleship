package BattleShip_SERAFIN;
public class Ship {
	private Coordinate[] coord;

	// CONSTRUCTOR
	public Ship(Coordinate startCoord, Coordinate endCoord) {
		// Supposing that the entered coordinates are corrects
		int length;
		boolean horizontal = false;
		if (startCoord.getR()==endCoord.getR()) {
			horizontal = true;
		}
		if (horizontal) {
			length = endCoord.getC().charAt(0) - startCoord.getC().charAt(0) + 1;			
		} else {
			length = endCoord.getR() - startCoord.getR() + 1;
		}

		coord = new Coordinate[length];
		for (int i = 0; i < length; i++) {
			 if (!horizontal) {//VERTICAL
				 coord[i]= new Coordinate( Character.toString(startCoord.getC().charAt(0)) + 
								 Integer.toString(startCoord.getR() + i) );
			 } else {//HORIZONTAL
				 coord[i]= new Coordinate(Character.toString((char)(startCoord.getC().charAt(0) + i)) 
			    		 + Integer.toString(startCoord.getR() ));
			}
		}
	} //END OF CONSTRUCTOR
	
	
	
	public int getLength() {
		//return the length of the current ship 
		return coord.length;
	}
	  
	public boolean isDestroyed() {
		//return true if the current ship is destroyed
		for (int i = 0; i<getLength(); i++) {
			if (coord[i].getHit()== false) {
				return false;
			}
		}
		return true;
	}
	
	public boolean wasHit() {//return true if the ship has at least one Coordinate hit
		for (int i = 0; i<getLength();i++) {
			if (coord[i].getHit()) {
				return true;
			}
		}
		return false;
	}
	
	public Coordinate wasCoordinateHit() {//return the first Coordinate hit that we found in coord[]
		for (int i = 0; i<getLength();i++) {
			if (coord[i].getHit()) {
				return coord[i];
			}
		}
		return null;
	}
	
	public boolean isHit(Coordinate c1) {//Is the Ship hit in c1 ?
		for (int i = 0; i<getLength();i++) {
			if (c1.equal(coord[i])) {
				if (coord[i].getHit()==true) {
					return true;
				}	
			}
		}
		return false;
	}

	public void hit(Coordinate c1){ //Hit the ship at the Coordinate c1 
		for (int i=0;i<getLength();i++) {
			if (c1.equal(coord[i])) {
				coord[i].setHit(true);		
			}
		}
	}
	
	public String toString() { // Print the position at which the ship is, for easy debug
		String res = "Ship";
		for (int i = 0; i < getLength(); i++ ) {
			res = res + " " + coord[i];
		}
		return res;
	}
	
	//GETTER AND SETTER
	
	public Coordinate getCoord(int i) {
		return coord[i];
	}
}