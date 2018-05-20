package serafin.solene;

import serafin.solene.classes.*;
import java.util.Scanner;

public class Battleship {
	
	public static void shoot(Player p, Player o, Coordinate c1) {
		//p = player who attack, p = opponent
		//shoot at the Coordinate c1
		//print if he Hit, Destroyed or in the water
		
		if (o.shipAtThisCoordinate(c1)) {
			Ship s1 = o.whichShipHere(c1);
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
			if (p.coordinateHitContains(c1)) {
				System.out.println("You already shot here, and you missed");
			}else {
				p.addCoordinateHit(c1);
				System.out.println("In the water !!");
			}
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
	
	public static void main(String[] args) {
		//VARIABLE DECLARATION :
		
		int tour = 1;// which player play 
		Player ia1 = new Player(1);
		Player ia2 = new Player(2);
		boolean continu;
		Coordinate c1 = null;
		String playAgain = "";
		int whoBegin = 0; //decide who begin the first time 
		int ia1WinTimes = 0;// counter of victories of player 1
		int ia2WinTimes = 0;// counter of victories of player 2
		int time = 1;// counter of number of Game
		int nbTime =1;//Number of time you want to play (if IA versus IA then nbTime = 100)
		int versionIA2 = -1; //	-1 = HUMAN,	0  = IA0, 1  = IA1, 2  = IA2
		
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
			ia1 = new Human(1);
			//INITIALIZATION OF PLAYER 2 :
			switch (typeOfGame) {
			case 1 :
				ia2 = new Human(2);
				break;
			case 2: 
				switch (versionIA2) {
				case 0:
					ia2 = new IA0(2);
					break;
				case 1:
					ia2 = new IA1(2);
					break;
				case 2:
					ia2 = new IA2(2);
					break;
				}
				break;
			}
			
			if (tour%2 == time%2) { //print who begin
				ia1.whoBegin();
			}else {
				ia2.whoBegin();
			}
			
			//Player 1 put 5 Ship and then Player 2 put 5 ship
			if (time%2 ==1) {
				ia1.enterAllShip();
				ia2.enterAllShip();
			}else {// Player 2 put 5 Ship and then Player 1 put 5 ship
				ia2.enterAllShip();
				ia1.enterAllShip();
			}
			
			
			while(!endOfTheGame(ia1,ia2)) {//GAME
				if (tour%2 == time%2) {
					//PLAYER 1 PLAY
					c1 = ia1.printShoot(ia2);
					shoot(ia1, ia2,c1);
				}else {
					//PLAYER 2 PLAY
					c1 = ia2.printShoot(ia1);
					shoot(ia2, ia1,c1);
				}
				tour += 1;
			}
			
			time +=1;
			//increase scores and print final game
			if (ia1.allShipDestroyed()) {
				ia2WinTimes += 1;
			}
			else {
				ia1WinTimes += 1;
			}
			//Print scores 
			ia1.printScores(ia1WinTimes, nbTime);
			ia2.printScores(ia2WinTimes, nbTime);
			
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
				ia1.printScores(ia1WinTimes, nbTime);
				ia2.printScores(ia2WinTimes, nbTime);
				
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