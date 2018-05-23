package serafin.solene.classes;

public abstract class IA extends Player {

	public IA(int num) {
		super(num);
	}
	
	public final void whoBegin() { //final because it's the same for all the IA (avoid override)
		//print scores of this IA
		System.out.println("IA begin");
	}
	
	public Ship enterShipIA(int size) {
		//enter a Ship in a random position for an IA player
		Coordinate startCoord = null;
		Coordinate endCoord = null;
		do {
			//random between HORIZONTAL and VERTICAL
			if(random10()%2==0) {//HORIZONTAL
				//the letter should be between 'A' and 'J' - size + 1
				startCoord = new Coordinate(randomBetween('A',(char) ('J'- size+1)) + Integer.toString(random10()));
				char c = startCoord.getC().charAt(0);
				c = (char) (c + size - 1);
				endCoord = new Coordinate (Character.toString(c)+ startCoord.getR());
			} else {//VERTICAL
				//the letter should be between 1 and 10- size + 1
				int max; 
				max = 10-size+1;
				startCoord = new Coordinate( randomBetween('A','J') + randomBetween ('1',(char)(max+'0')) );
				endCoord = new Coordinate( startCoord.getC() + Integer.toString(startCoord.getR()+size-1));
			}
			
		} while(!correctShip(startCoord, endCoord));
		return new Ship(startCoord, endCoord);
	}
	
	public void enterAllShip(){
		//Enter all the ship for an IA player 
		for (int i=5;i>=2;i--) {
			Ship s1 = enterShipIA(i);
			addShiplist(s1);
		}
		Ship s1 = enterShipIA(3);
		addShiplist(s1);	
	}
	
	public final Coordinate printShoot(Player o) {//final because it's the same for all the IA (avoid override)
		//Ask where to shoot and print where the IA shoot 
		Coordinate c1 = null;
		c1 = askCoordinate(o);
		System.out.print("IA shoot in " +c1+" : ");
		return c1;
	}
	
	public final void printScores(int playerWinTimes, int nbTime) { //final because it's the same for all the IA (avoid override)
		//print scores of this IA
		System.out.println("IA win "+playerWinTimes+ "/"+nbTime+ " times ");
	}
}

