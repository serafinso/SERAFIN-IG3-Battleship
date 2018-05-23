package serafin.solene.classes;

public class IA0 extends IA{
	public IA0(int num) {//CONSTRUCTOR
		super(num);
	}
	
	public Coordinate askCoordinate(Player o) {
		//return a random Coordinate
		return new Coordinate( randomBetween('A','J') + random10());	
	}
}