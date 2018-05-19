package BattleShip_SERAFIN;

public class MainIA {
	
	public static void printGame(Player p, Player o){
		//o = player opponent, pAttack = player who attack
		//print the game for a player before and after pAttack attack (print where he already attack and boat he as hit)
		//~ = nothing happen here
		//1 = ShipFind
		//-1 = ShipDestroyed
		//X missile in the water
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
				if (p.coordinateHitContains(c1)) {
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
	
	public static void printGameCurrentPlayer(Player p, Player o){ 
		//o = player opponent, pAttack = player who attack
		//print the game for a player before and after pAttack attack (print where he already attack and boat he as hit)
		//S = You have a Ship here 
		//~ = nothing happen here
		//1 = ShipFind
		//-1 = ShipDestroyed
		//X missile in the water
		System.out.println("\n1 = There is a Ship Here \n~ = Water \n");
		System.out.println("     A   B   C   D   E   F   G   H   I   J  \n");
		for(int r=1; r<=10;r++ ) {//for each row
			if (r==10) {// remove the offset of 10 which has two digits
				System.out.print(" " + r +"  ");
			} else {
				System.out.print(" " + r +"   ");
			}
			for(char c='A'; c <= 'J'; c++) {//for each column
				Coordinate c1 = new Coordinate (Character.toString(c) + Integer.toString(r));
				if (p.shipAtThisCoordinate(c1) && !p.whichShipHere(c1).isHit(c1)) {
					System.out.print("S   ");
				}  else {
					if (o.coordinateHitContains(c1)) {
						System.out.print("X   ");
					} else {
						if (p.shipAtThisCoordinate(c1)) {
							Ship s1 = p.whichShipHere(c1);
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
			}
			System.out.println();
			System.out.println();
		}
	}
	
	public static void shoot(Player pAttack, Player p, Coordinate c1) {
		//pAttack = player who attack, p = player attacked
		//shoot at the Coordinate c1
		//print if he Hit, Destroyed or in the water
		
		if (p.shipAtThisCoordinate(c1)) {
			Ship s1 = p.whichShipHere(c1);
			s1.hit(c1);
			if (s1.isDestroyed()) {
				System.out.println("Destroyed !!");
				s1.hit(c1);
			} else {
				System.out.println("Hit !!");
				s1.hit(c1);
			}
		} else {
			pAttack.addCoordinateHit(c1);
			System.out.println("In the water !!");
		}
	}
	
	public static boolean endOfTheGame (Player p1, Player p2) {
		//return true if at least one player win
		//the two player can win at the same time if the two of them win at the same turn of play
		if (p1.allShipDestroyed()) {
			if (p2.allShipDestroyed()) {
				System.out.println("It's an tie");
				return true;
			} else {
				System.out.println("Congratulation Player2 win");
				return true;
			}
		}
		if (p2.allShipDestroyed()){
			System.out.println("Congratulation Player1 win");
			return true;
		}
		return false;		
	}

	public static void enterAllShipA(Player p) {
		Ship s1 = new Ship (new Coordinate ("A1"),new Coordinate("D1"));
		p.addShiplist(s1);
		s1 = new Ship (new Coordinate ("E4"),new Coordinate("E8"));
		p.addShiplist(s1);
		s1 = new Ship (new Coordinate ("B4"),new Coordinate("D4"));
		p.addShiplist(s1);
		s1 = new Ship (new Coordinate ("G6"),new Coordinate("I6"));
		p.addShiplist(s1);
		s1 = new Ship (new Coordinate ("G2"),new Coordinate("H2"));
		p.addShiplist(s1);
		p.printGamePlaceShip();
	}
	
	public static void main(String[] args) {
		int tour = 0;
		Player ia1;
		Player ia2;
		
		Coordinate c1 = null;
		
		int versionIA1 = -1; 
		/*	-1 = HUMAN
		 * 	0  = IA0
		 * 	1  = IA1
		 * 	2  = IA2
		 */
		int versionIA2 = 1;
		/*	
		 * 	-1 = HUMAN
		 * 	0  = IA0
		 * 	1  = IA1
		 * 	2  = IA2
		 */
		
		int ia1WinTimes = 0;
		int ia2WinTimes = 0;
		int time =0;
		while(time <100) {
			switch (versionIA1) {
			case -1:{//HUMAN
				ia1 = new Player();
				break;
			}case 0:{
				ia1 = new IA0();
				break;
			}case 1:{
				ia1 = new IA1();
				break;
			}default:{
				ia1 = new IA2();
				break;
			}}
			switch (versionIA2) {
			case -1 :{//HUMAN
				ia2 = new Player();
				break;
			}case 0:{//IA0
				ia2 = new IA0();
				break;
			}case 1:{//IA1
				ia2 = new IA1();
				break;
			}default:{//IA2
				ia2 = new IA2();
				break;
			}}
			System.out.println("Player 1, put 5 Ship ");
			enterAllShipA(ia1);
			//ia1.enterAllShip();
			if (versionIA1 != -1) {
				for (int i = 0; i< 50; i++) {
					System.out.println();
				}
			}
			
			System.out.println("Player 2, put 5 Ship ");
			//ia2.enterAllShip();
			enterAllShipA(ia2);
			if (versionIA2 != -1) {
				for (int i = 0; i< 50; i++) {
					System.out.println();
				}
			}
			
			while(!endOfTheGame(ia1,ia2) || tour%2==1) {
				//PLAYER 1 START
				
				System.out.println();
				System.out.println();
				System.out.println();
				
				if (versionIA1 == -1) {
					printGameCurrentPlayer(ia1,ia2);
					System.out.println("Player 1 it's your turn. Where do you want to shoot ?");
					printGame(ia1,ia2);
					System.out.println("enter the coordinates of the shots ");
					c1 = ia1.askCoordinate();
				}else {
					c1 = ia1.askCoordinate(ia2);
					System.out.println("IA shoot : here's your gameboard :");
				}
				
				shoot(ia1, ia2,c1);
				printGame(ia1,ia2);
				
				//TURN OF PLAYER 2 :
				
				System.out.println();
				System.out.println();
				System.out.println();
				
				if (versionIA2 == -1) {
					printGameCurrentPlayer(ia2, ia1);
					System.out.println("Player 2 it's your turn. Where do you want to shoot ?");
					printGame(ia2,ia1);
					System.out.println("enter the coordinates of the shots ");
					c1 = ia2.askCoordinate();
				}else {
					c1 = ia2.askCoordinate(ia1);
					System.out.println("IA shoot : here's your gameboard :");
				}
				
				
				shoot(ia2, ia1,c1);
				printGame(ia2,ia1);
			 }
			if (versionIA1 != -1 && versionIA2 != -1) {//if IA VS IA 
				time +=1;
				if (ia1.allShipDestroyed()) {
					ia2WinTimes += 1;
				}
				else {
					ia1WinTimes += 1;
				}
			}else {
				time += 100;
			}
		}
		if (versionIA1 != -1 && versionIA2 != -1) {
			System.out.println("IA with version "+versionIA1+ " win "+ia1WinTimes+ "/100 times ");
			System.out.println("IA with version "+versionIA2+ " win "+ia2WinTimes+ "/100 times ");
		}
	}
}