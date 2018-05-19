package serafin.solene.classes;

public class IA2 extends IA0{	
	
	public String randomACEGI() {
		String tab[]= {"A","C","E","G","I"};
		String t = randomBetween('0','4');
		int y= Integer.parseInt(t);
		return tab[y];
	}
	
	public String randomBDFHJ() {
		String tab[]= {"B","D","F","H","J"};
		String t = randomBetween('0','4');
		int y= Integer.parseInt(t);
		return tab[y];
	}
	
	public String randomImpair() {
		String tab[]= {"1","3","5","7","9"};
		String t = randomBetween('0','4');
		int y= Integer.parseInt(t);
		return tab[y];
	}
	
	public String randomPair() {
		String tab[]= {"2","4","6","8","10"};
		String t = randomBetween('0','4');
		int y= Integer.parseInt(t);
		return tab[y];
	}
	
	public boolean chassing(Player o) {
		Ship s1 = null;
		for(int i=0; i< o.getShiplist().size(); i++) {
			s1 = o.getShiplist().get(i);
			if (s1.wasHit() && !s1.isDestroyed()) {
				return true;
			}
		}
		return false;
	}
	
	public Coordinate coordinateNorth(Coordinate c_hit) {
		return new Coordinate (c_hit.getC() + Integer.toString(c_hit.getR() - 1 ));
	}
	
	public Coordinate coordinateSouth(Coordinate c_hit) {
		return new Coordinate (c_hit.getC() + Integer.toString(c_hit.getR() + 1 ));
	}
	
	public Coordinate coordinateEast(Coordinate c_hit) {
		char c = c_hit.getC().charAt(0);
		c = (char) (c + 1);
		return new Coordinate (Character.toString(c) + c_hit.getR() );
	}
	
	public Coordinate coordinateWest(Coordinate c_hit) {
		char c = c_hit.getC().charAt(0);
		c = (char) (c - 1);
		return new Coordinate (Character.toString(c) + c_hit.getR() );
	}
	
		
	public Coordinate askCoordinate(Player o) {
		if (!chassing(o)) {
			boolean find = false;
			while (!find) {
				Coordinate c1 = null;
				if (random10()%2==0) {
					c1 = new Coordinate( randomACEGI() + randomImpair());
				}else {
					c1 = new Coordinate( randomBDFHJ() + randomPair());
				}
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
		}else {
			for (int j=0; j<5;j++) {
				Ship s1 = o.getShiplist().get(j);
				if (s1.wasHit()&&!s1.isDestroyed()) {
					for (int i=0; i<s1.getLength();i++) {
						Coordinate c_hit = s1.getCoord(i);
						if (s1.isHit(c_hit)){
							Coordinate nextTo = null;
							for (int l = 0; l<4; l++) {
								switch (l) {
								case 0:
									{nextTo = coordinateSouth(c_hit);
									break;}
								case 1:
									{nextTo = coordinateEast(c_hit);
									break;}
								case 2:
									{nextTo = coordinateNorth(c_hit);
									break;}
								case 3:
									{nextTo = coordinateWest(c_hit);
									break;}
								}
								if (nextTo.isCorrect() && !coordinateHitContains(nextTo) ) {
									if (o.shipAtThisCoordinate(nextTo)) {
										Ship s2 = o.whichShipHere(nextTo);
										if (!s2.isHit(nextTo)) {
											return nextTo;}
									}else {
										return nextTo;
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
}