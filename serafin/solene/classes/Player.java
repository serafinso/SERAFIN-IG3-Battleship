package serafin.solene.classes;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player implements IA {
	private ArrayList<Coordinate> coordinateHit = new ArrayList<Coordinate>(); //Coordinate shoot by the current player
	private ArrayList<Ship> shiplist = new ArrayList<Ship>(); //List of ship of the current player 
	
	public Player(){
	}
	
	public boolean correctShip(Coordinate startCoord, Coordinate endCoord) {
		//This function is in Player class because check if the player enter a correct Ship 
		//return True if the coordinates are corrects for a Ship
		//Correct for a ship = (Good size) && (horizontal or vertical Ship) && (Not an another ship here)
		//precondition : startCoord and endCoord are corrects Coordinates
		
		if (!(startCoord.getC().equals(endCoord.getC()) ) && (startCoord.getR() != endCoord.getR()) ) {
			//correct if it is Horizontal or Vertical
			return false;
		}
		if (startCoord.getC().charAt(0) > endCoord.getC().charAt(0) 
				//Ship's coordinates in order: A1, A4 instead of A4, A1 : to avoid the problem of an array list
				//with negative size (The player can still enter a boat with the coordinates not in the order)
				|| startCoord.getR() > endCoord.getR()) {
			return false;
		}
		
		//Size between 2 and 5
		Ship s1 = new Ship(startCoord, endCoord);		
		if (s1.getLength() >5 || s1.getLength()<2) {
			return false;
		}
		//Not an another ship here
		if (alreadyShipHere(s1)) {
			return false;			
		} else {
			return true;
		}
	}
	
	public int containShipSize(int s) {
		//return the number of Ship with the size s
		int nb = 0;
		for (int i =0;i< shiplist.size();i++) {
			Ship s1 = shiplist.get(i);
			s1.getLength();
			if (shiplist.get(i).getLength()==s)
				nb += 1;
		}
		return nb;
	}
	
	public boolean shipAtThisCoordinate(Coordinate c1) {
		//return true if the current player have a ship at c1 (so that the boats do not overlap)
		for(int j=0; j<shiplist.size();j++) {//to each ..		//of the boat s2
			Ship s2 = shiplist.get(j);
			for (int k = 0; k < s2.getLength(); k++) {	//coordonate
				if (c1.equal(s2.getCoord(k))) { //if it is equal, there is a ship here 
					return true;
				}
			}
		}
		return false;			
	}
	
	public boolean alreadyShipHere(Ship s1) { 
		//return true if the current Player have an another Ship where we want to put the Ship s1 
		//different from shipAtThisCoordinate because the parameter is a Ship and not a Coordinate
		Coordinate c1 = null;
		for (int i=0; i<s1.getLength();i++) {
			c1 = s1.getCoord(i);
			if (shipAtThisCoordinate(c1)) {
				System.out.println("An another Ship is already here");
				return true;
			}
		}
		return false;
	}
	
	public Ship whichShipHere(Coordinate c1) {
		//Supposing that the current player have a ship in the Coordinate c1 because use with alreadyShipHere 
		//but to avoid problem return null if no Ship here
		//Return the Ship in the Coordinate c1
		Ship s1 = null;
		for(int j=0; j<shiplist.size();j++) {//to each ..		//of the boat s2
			Ship s2 = shiplist.get(j);
			for (int k = 0; k<s2.getLength();k++) {		//coord
				if (c1.equal(s2.getCoord(k))) { //if it is equal, 
					s1 = s2;
				}
			}
		}
		return s1;
	}
	
	public boolean allShipDestroyed() {
		//return True if all the Ship of the current player are destroyed 
		for (int i =0; i< shiplist.size(); i++) {
			Ship s1 = shiplist.get(i);
			if(!s1.isDestroyed()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean coordinateHitContains(Coordinate c1) {
		//return true if the array CoordinateHit of the current player contain c1
		for (int i =0;i< coordinateHit.size();i++) {
			if (coordinateHit.get(i).equal(c1))
				return true;
		}
		return false;
	}
	
	public Coordinate askCoordinate() { 
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
	
	public Ship askShip() {
		// ask 2 Coordinates at the user to define a ship and check if the Coordinates are corrects
		Coordinate c1 = null;
		Coordinate c2 = null;
		Ship s1 = null;
		do {
			System.out.println("Enter the first coordonate of the ship");
			c1 = askCoordinate();
			System.out.println("Enter the second coordonate of the ship ");
			c2 = askCoordinate();
			System.out.println("Invalid Ship");
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
		while (getShiplist().size()<5) {
			Ship s1;
			s1 = askShip();
			switch (s1.getLength()) {
			  case 2:{
				if (containShipSize(2)==1) {
					System.out.println("Ship with size 2 already there");
					break;
				} else {
					addShiplist(s1);
					break;
				}
			  }
			  case 4:{
				 if (containShipSize(4)==1) {
					 System.out.println("Ship with size 4 already there");
					 break;
				 } else {
					 addShiplist(s1);
				 	break;
				 }
			  }
			  case 5:{
				 if (containShipSize(5)==1) {
					 System.out.println("Ship with size 5 already there");
					 break;
				 } else {
					addShiplist(s1);
					break;
				 }
			  }
			  case 3:{
				  if (containShipSize(3)==0) {//Si il n'y a pas de cruizer alors j'ajoute un cruizer 
					  addShiplist(s1);
				  } else {
					  if (containShipSize(3)==2) {
						  System.out.println("The 2 Ships with size 3 are already there");
						  break;
					  } else {
						  addShiplist(s1);
					  }
				  }
				  break;
			  }
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
	
	public void printGamePlaceShip(){ 
		//print the game for the player when he put ship on the board game
		//~ = no Ship
		//S = Ship
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
	
	public int random10() {
		//random between 1 and 10
		Random rd = new Random();
		return rd.nextInt(10) + 1;
	}
	
	public String randomBetween(char min, char max) {
		//return a string between a char min and a char max 
		Random rd = new Random();
		char c = (char)(rd.nextInt(max - min + 1) + min);
		return Character.toString(c);
	}

	public Ship enterShipIA(int size) {
		//enter a Ship in a random position for an IA player (this function is the same for all IA
		//that is why it is in Player class)
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

	public Coordinate askCoordinateIA(Player p) {
		//this function is different for all so it will be redefine in all derive class
		return null;
	}
	
	//GETTER AND SETTER :
	
	public ArrayList<Coordinate> getCoordinateHit() {
		return coordinateHit;
	}
	
	public ArrayList<Ship> getShiplist() {
		return shiplist;
	}
	public void addShiplist(Ship s1) { //set shiplist but here we add a ship 
		shiplist.add(s1);
	}
	public void addCoordinateHit(Coordinate c1) { //set shiplist but here we add a ship 
		coordinateHit.add(c1);
	}

	@Override
	public Coordinate askCoordinate(Player p) {
		// TODO Auto-generated method stub
		return null;
	}
}	