package serafin.solene.classes;

import java.util.Scanner;

public class Human extends Player{
	public Human(int num) {//CONSTRUCTOR
		super(num);
	}
	
	public void whoBegin() {
		//Execute only if the current player begin
		System.out.println("Player "+getNum()+" begin");
	}

	public void printShipIn() {
		//print the ship that the player has already placed on the game board
		if (containShipSize(5)==1) {
			System.out.println("Carrier taille 5 : 1/1");
		} else {
			System.out.println("Carrier taille 5 : 0/1");
		}
		if (containShipSize(4)==1) {
			System.out.println("Battleship taille 4 : 1/1");
		} else {
			System.out.println("Battleship taille 4 : 0/1");
		}
		if (containShipSize(3)==1) {
			System.out.println("Cruizer taille 3 : 1/1");
		} else {
			System.out.println("Cruizer taille 3 : 0/1");
		}
		if (containShipSize(3)==2) {
			System.out.println("Submarine taille 3 : 1/1");
		} else {
			System.out.println("Submarine taille 3 : 0/1");
		}
		if (containShipSize(2)==1) {
			System.out.println("Destroyer taille 2 : 1/1");
		} else {
			System.out.println("Destroyer taille 2 : 0/1");
		}
	}
	
	public void printGamePlaceShip(){
		//different from printGameCurentPlayer because this function doesn't need player opponent informations 
		//and less control needed to print (less if) 
		//print the game for the player when he put ship on the board game
		//~ = no Ship //S = Ship 
		System.out.println("\n1 = There is a Ship Here \n~ = Water \n");
		System.out.println("      A   B   C   D   E   F   G   H   I   J  \n");
		for(int r=1; r<=10;r++ ) {//for each row
			if (r==10) {// remove the offset of 10 which has two digits
				System.out.print(" " + r +"  ");
			} else {
				System.out.print(" " + r +"   ");
			}
			for(char c='A'; c <= 'J'; c++) {//for each column
				Coordinate c1 = new Coordinate (Character.toString(c) + Integer.toString(r));
				if (shipAtThisCoordinate(c1)) {
					System.out.print(" S  " );
				} else {
					System.out.print(" ~  " );
				}
			}
			System.out.println();
			System.out.println();
		}
	}
	
	public Ship askShip() {
		// ask 2 Coordinates at the user to define a ship and check if the Coordinates are corrects
		Coordinate c1 = null;
		Coordinate c2 = null;
		Ship s1 = null;
		int l=0;
		do {
			if (l>0) {
				System.out.println("Invalid Ship");
			}
			l=l+1;
			System.out.println("Enter the first coordonate of the ship");
			c1 = askCoordinate(new Player(0));
			System.out.println("Enter the second coordonate of the ship ");
			c2 = askCoordinate(new Player(0));
			
			} while(!correctShip(c1,c2) && !correctShip(c2, c1)); // (The player can still enter a boat with the coordinates not in the order)
		if (correctShip(c1,c2)) {
			s1 = new Ship(c1,c2);
		} else {
			s1 = new Ship(c2,c1);
		}
		return s1;	
	}
	
	public void enterAllShip() {
		//Enter all the ship in the shiplist of the current player and check the lengths
		//check the size of a ship and if an other ship of this size is not already on the board game
		printShipIn();
		printGamePlaceShip();
		System.out.println("Player "+getNum()+", put 5 Ship ");
		while (getShiplist().size()<5) {
			Ship s1;
			s1 = askShip();
			switch (s1.getLength()) {
			  case 2:
				if (containShipSize(2)==1) {
					System.out.println("Ship with size 2 already there");
				} else {
					addShiplist(s1);
				}
				break;
			  case 4:
				 if (containShipSize(4)==1) {
					 System.out.println("Ship with size 4 already there");
				 } else {
					 addShiplist(s1);
				 }
				 break;
			  case 5:
				 if (containShipSize(5)==1) {
					 System.out.println("Ship with size 5 already there");
				 } else {
					addShiplist(s1);
				 }
				 break;
			  case 3:
				  if (containShipSize(3)==0) {//Si il n'y a pas de cruizer alors j'ajoute un cruizer 
					  addShiplist(s1);
				  } else {
					  if (containShipSize(3)==2) {
						  System.out.println("The 2 Ships with size 3 are already there");
					  } else {
						  addShiplist(s1);
					  }
				  }
				  break;
			}
			printShipIn();
			printGamePlaceShip();
		}
		//In order to hide the boat from the opponent
		System.out.println("All your Ship are in the board game. Press enter to continue");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		for (int i=0; i<40; i++) {
			System.out.println();
		}
	}	
	
	public void printGameCurrentPlayer(Player o){ 
		//o = player opponent, pAttack = player who attack
		//print the game for a player before and after pAttack attack (print where he already attack and boat he as hit)
		//S = You have a Ship here //~ = nothing happen here//1 = ShipFind//-1 = ShipDestroyed//X missile in the water
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
				if (shipAtThisCoordinate(c1) && !whichShipHere(c1).isHit(c1)) {
					System.out.print("S   ");
				}  else {
					if (o.coordinateHitContains(c1)) {
						System.out.print("X   ");
					} else {
						if (shipAtThisCoordinate(c1)) {
							Ship s1 = whichShipHere(c1);
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
	
	public Coordinate askCoordinate(Player o) { 
		//ask to the user to enter one Coordinate while the Coordinate enter is correct 
		boolean continu = true;
		Coordinate c1 = null;
		while (continu) {			
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			String str = sc.nextLine();
			if (!str.isEmpty()) {
				c1 = new Coordinate (str);
				if (c1.isCorrect()) {
					continu = false;
				}
				else {
					System.out.println("Invalid coordonate ");
				}
			}
			else {
				System.out.println("Invalid coordonate ");
			}
		}
		return c1;
	}
	
	public Coordinate printShoot(Player o) {
		//function that print the gameBoards and ask to the player to enter a coordinate to shoot
		System.out.println("Player "+ getNum()+" it's your turn, here's your game Board :");
		printGameCurrentPlayer(o);
		System.out.println("Player "+getNum()+", here's your opponent gameboard, where do you want to shoot ?");
		printGame(o);
		System.out.println("enter the coordinates of the shots ");
		return askCoordinate(o);
	}	
	
	
	public void printScores(int playerWinTimes, int nbTime) {
		//print the scores of the current player
		System.out.println("\nPlayer "+getNum()+" win "+playerWinTimes+ "/"+nbTime+ " times ");
	}
}