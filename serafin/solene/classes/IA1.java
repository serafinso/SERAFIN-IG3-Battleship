package serafin.solene.classes;
import java.util.Random;

public class IA1 extends IA0{
	
	public int random10() {
		Random rd = new Random();
		return rd.nextInt(10) + 1;
	}
	
	public String randomBetween(char min, char max) {
		Random rd = new Random();
		char c = (char)(rd.nextInt(max - min + 1) + min);
		return Character.toString(c);
	}
		
	public Coordinate askCoordinate(Player o) {
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
