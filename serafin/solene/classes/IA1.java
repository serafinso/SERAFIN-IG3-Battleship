package serafin.solene.classes;

public class IA1 extends IA{	
	
	public IA1(int num) {//CONSTRUCTOR
		super(num);
	}

	public Coordinate askCoordinate(Player o) {
		//return a random Coordinate not already hit 
		boolean find = false;
		Coordinate c1 =null;
		while (!find) {
			c1 = new Coordinate( randomBetween('A','J') + Integer.toString(random10()));
			if(!coordinateHitContains(c1)) {
				if (o.shipAtThisCoordinate(c1)) {
					Ship s2 = o.whichShipHere(c1);
					if (!s2.isHit(c1)) {
						find = true;
						return c1;}
				}else {
					find = true;
					return c1;
				}
			}
		}
		return c1;
	}
}