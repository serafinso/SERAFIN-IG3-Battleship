package serafin.solene.classes;
import java.util.Random;

public class IA0 extends Player {
	
	public void enterAllShip(){
		//Enter all the ship for an IA player 
		for (int i=5;i>=2;i--) {
			Ship s1 = enterShipIA(i);
			addShiplist(s1);
		}
		Ship s1 = enterShipIA(3);
		addShiplist(s1);
		
	}
		
	public Coordinate askCoordinate(Player p) {
		return new Coordinate( randomBetween('A','J') + random10());	
	}
	
	public int random10() {
		Random rd = new Random();
		return rd.nextInt(10) + 1;
	}
	
	public String randomBetween(char min, char max) {
		Random rd = new Random();
		char c = (char)(rd.nextInt(max - min + 1) + min);
		return Character.toString(c);
	}
}