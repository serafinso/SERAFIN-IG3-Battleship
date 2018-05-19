package serafin.solene;

import serafin.solene.classes.*;
import java.util.Scanner;

public class Battleship {
	
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
		System.out.println("\n 1 = Your Ship is Hit but it isn't destroyed \n-1 = Your Ship is destroyed \n ~ = Water "
				+ "\n X = Opponent shot in the water \n S = You have a Ship not hit \n");
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
			if (s1.isHit(c1)) {
				System.out.println("Already Hit");
			}else {
				s1.hit(c1);
				if (s1.isDestroyed()) {
					System.out.println("Destroyed !!");
					s1.hit(c1);
				} else {
					System.out.println("Hit !!");
					s1.hit(c1);
				}	
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
			System.out.println("\n Congratulation Player2 win this time");
			return true;
		} else {
			if (p2.allShipDestroyed()){
				System.out.println("\n Congratulation Player1 win this time");
				return true;
		}}
		return false;		
	}

	public static void enterAllShipA(Player p) {
		Ship s1 = new Ship (new Coordinate ("A1"),new Coordinate("D1"));
		p.addShiplist(s1);
		/*s1 = new Ship (new Coordinate ("E4"),new Coordinate("E8"));
		p.addShiplist(s1);
		s1 = new Ship (new Coordinate ("B4"),new Coordinate("D4"));
		p.addShiplist(s1);
		s1 = new Ship (new Coordinate ("G6"),new Coordinate("I6"));
		p.addShiplist(s1);
		s1 = new Ship (new Coordinate ("G2"),new Coordinate("H2"));
		p.addShiplist(s1);
		p.printGamePlaceShip();*/
	}
	
	public static void main(String[] args) {
		//VARIABLE DECLARATION :
		
		int tour = 1;// which player play 
		Player ia1 = new Player();
		Player ia2 = new Player();
		boolean continu;
		Coordinate c1 = null;
		String playAgain = "";
		int whoBegin = 0;
		int ia1WinTimes = 0;
		int ia2WinTimes = 0;
		int time = 1;
		int nbTime =1;//Number of time you want to play (if IA versus IA then nbTime = 100)
		int versionIA2 = -1;
		/*	
		 * 	-1 = HUMAN
		 * 	0  = IA0
		 * 	1  = IA1
		 * 	2  = IA2
		 */
		int typeOfGame = 1;
		// typeOfGame = 1 --> HUMAN VS HUMAN 
		// typeOfGame = 2 --> HUMAN VS IA
		
		//Let the user choose the type of Game 
		continu = true;
		while (continu) {
			System.out.println("Which type of game do you want ? "
					+ "\n 1 : HUMAN VS HUMAN \n 2 : HUMAN VS IA");
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			String str = sc.nextLine();
			if ( !str.equals("1") && !str.equals("2")) {
				System.out.println("Not 1 or 2");
			}
			else {
				typeOfGame = Integer.parseInt(str);
				continu = false;
			}
		}
		
		//Let the user choose the type of Player2 : human, IA0, IA1, IA2
		if (typeOfGame == 2) {
			continu = true;
			while (continu) {
				System.out.println("Which type of player do you want for player 2 ? "
						+ "\n 0 for IA0 \n 1 for IA1 \n 2 for IA2");
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
				String str = sc.nextLine();
				if ( !str.equals("0") && !str.equals("1") && !str.equals("2")) {
					System.out.println("Not -1 or 0 or 1 or 2");
				}
				else {
					versionIA2 = Integer.parseInt(str);
					continu = false ;
				}
			}
		}
		
		//if HUMAN VS IA : a one-in-two chance IA begin
		if (typeOfGame == 2 && time == 1) {
			 whoBegin = ia2.random10()%2; //whoBegin == 0 (Player 2 begin) 	
			 						  // || whoBegin == 1 (Player 1 begin) 
		}
		
		while(time <= nbTime) {
			//if whoBegin==0 then tour should be initialize with 0 else with 1
			if (whoBegin == 0 && typeOfGame ==2) {
				tour = 0;
			}else {
				tour = 1;
			}
			ia1 = new Player();
			//INITIALIZATION OF PLAYER 2 :
			switch (typeOfGame) {
			case 1 :
				ia2 = new Player();
				break;
			case 2: 
				switch (versionIA2) {
				case 0:
					ia2 = new IA0();
					break;
				case 1:
					ia2 = new IA1();
					break;
				case 2:
					ia2 = new IA2();
					break;
				}
				break;
			}
			
			
			if (tour%2 == time%2) {
				System.out.println("Player 1 begin");
			}else {
				if (typeOfGame == 1) {
					System.out.println("Player 2 begin");
				}else {
					System.out.println("IA begin");
				}
			}
			
			//Player 1 put 5 Ship and then Player 2 put 5 ship
			if (time%2 ==1) {
				System.out.println("Player 1, put 5 Ship ");
				enterAllShipA(ia1);
				//ia1.enterAllShip();
				
				if (typeOfGame == 2) {
					//ia2.enterAllShip();
					enterAllShipA(ia2);
				}else {
					System.out.println("Player 2, put 5 Ship ");
					//ia2.enterAllShip();
					enterAllShipA(ia2);
				}
				
			}else {// Player 2 put 5 Ship and then Player 1 put 5 ship
				if (typeOfGame == 2) {
					//ia2.enterAllShip();
					enterAllShipA(ia2);
				}else {
					System.out.println("Player 2, put 5 Ship ");
					//ia2.enterAllShip();
					enterAllShipA(ia2);
				}
				
				System.out.println("Player 1, put 5 Ship ");
				enterAllShipA(ia1);
				//ia1.enterAllShip();
				
			}
			
			
			while(!endOfTheGame(ia1,ia2)) {
				if (tour%2 == time%2) {
					//PLAYER 1 PLAY
					System.out.println("\nPlayer 1 it's your turn, here's your game Board :");
					printGameCurrentPlayer(ia1,ia2);
					System.out.println("Player 1, here's your opponent gameboard, where do you want to shoot ?");
					
					printGame(ia1,ia2);
					System.out.println("enter the coordinates of the shots ");
					c1 = ia1.askCoordinate();
					shoot(ia1, ia2,c1);
				}else {
						//PLAYER 2 PLAY
						if (typeOfGame == 1) {
							System.out.println("Player 2 it's your turn, here's your game Board :");
							printGameCurrentPlayer(ia2,ia1);
							System.out.println("Player 2, here's your opponent gameboard, where do you want to shoot ?");
							
							printGame(ia2,ia1);
							System.out.println("enter the coordinates of the shots ");
							c1 = ia2.askCoordinate();
							shoot(ia2, ia1,c1);
						}else {
							c1 = ia2.askCoordinate(ia1);
							System.out.print("IA shoot in " +c1+" :");
							shoot(ia2, ia1,c1);
						}
				}
				tour += 1;
			}
			
			time +=1;
			if (ia1.allShipDestroyed()) {
				ia2WinTimes += 1;
			}
			else {
				ia1WinTimes += 1;
			}
			
			System.out.println("\nPlayer 1 win "+ia1WinTimes+ "/"+nbTime+ " times ");
			if (typeOfGame == 1) {
				System.out.println("Player 2 win "+ia2WinTimes+ "/"+nbTime+ " times ");
			}else {
				System.out.println("IA with version "+versionIA2+ " win "+ia2WinTimes+ "/"+nbTime+ " times ");
			}
			//ASK TO THE PLAYER(S) IF THEY WANT TO PLAY AGAIN :
			continu = true;
			while (continu) {
				System.out.println("\nDo you want to play again ? (y/n)");
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
				String str = sc.nextLine();
				if ( !str.equals("y") && !str.equals("n")) {
					System.out.println("Not y or n");
				}
				else {
					playAgain = str;
					continu = false;
				}
			}
			if (playAgain.equals("y")) {
				nbTime += 1;
			}else {
				System.out.println("Final results :");
				System.out.println("\nPlayer 1 win "+ia1WinTimes+ "/"+nbTime+ " times ");
				if (typeOfGame == 1) {
					System.out.println("Player 2 win "+ia2WinTimes+ "/"+nbTime+ " times ");
				}else {
					System.out.println("IA with version "+versionIA2+ " win "+ia2WinTimes+ "/"+nbTime+ " times ");
				}
				if (ia1WinTimes < ia2WinTimes) {
					System.out.println("Congratulation to player 2 !!");
				}else {
					if (ia1WinTimes == ia2WinTimes) {
						System.out.println("It's a tie, congratulation to both players !!");
					}else {
						System.out.println("Congratulation to player 1 !!");
					}
				}
			}
		}
	}
}