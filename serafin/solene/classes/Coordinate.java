package serafin.solene.classes;

public class Coordinate {
	private String c;
	private int r;
	private boolean hit = false;
	
	public Coordinate(String ca) {
		String row ="";
		int y=0;
		this.c=Character.toString(ca.charAt(0));
		for (int i=1; i <ca.length(); i++) {
			row+= ca.charAt(i);
		}
		if (c.charAt(0)<'A' || c.charAt(0)>'J' ) {
			r=0;
			c=null;
		}
		try {
			y= Integer.parseInt(row);
		}catch(NumberFormatException e) {
			c=null;
		}
		if (c!=null) {
			r=y;
		}
		
		if (y<1 || y>10) {
			r=0;
			c=null;
		}
	}
	
	public String toString() {
		return c+r;
	}

	public boolean isCorrect() {
		//check if a Coordinate is correct 
		return !(c==null);
	}
	
	public boolean equal (Coordinate c2) {
		//return true if c2 is equal to the current Coordinate
		return this.c.equals(c2.getC()) && this.r==c2.getR();
	}
	
	//GETTER AND SETTER
	
	public String getC() {
		return c;
	}
	
	public boolean getHit() {
		return hit;
	}

	public void setHit(boolean hit) { //needed for hit a Ship
		this.hit = hit;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}
}
