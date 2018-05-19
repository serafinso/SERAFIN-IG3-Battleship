package fr.battleship;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import serafin.solene.classes.*;
public class TestIA {
	
	public static void shoot(Player pAttack, Player p, Coordinate c1) {
		//pAttack = player who attack, p = player attacked
		//shoot at the Coordinate c1
		//print if he Hit, Destroyed or in the water
		if (p.shipAtThisCoordinate(c1)) {
			Ship s1 = p.whichShipHere(c1);
			if (s1.isHit(c1)) {
			}else {
				s1.hit(c1);
			}
		} else {
			pAttack.addCoordinateHit(c1);
		}
	}
	
	public static void main(String[] args) throws IOException {
		int tour;// which player play 
		Player ia1;
		Player ia2;
		int versionIA1 = -1;
		File f = new File("src/ai_proof.csv");
		@SuppressWarnings("resource")
		FileWriter writer = new FileWriter(f);
		writer.append("AI Name; score; AI Name2; score2\n");
		/*	
		 * 	0  = IA0
		 * 	1  = IA1
		 * 	2  = IA2
		 */
		int versionIA2 = -1;
		Coordinate c1 = null;
		
		for (int typeOfIA = 1; typeOfIA <=3; typeOfIA ++) {
			System.out.println("");
			
			switch (typeOfIA) { 
			case 1: //AI Level Beginner; X1; Level Medium; Y1 (0 vs 1)
				versionIA1 = 0;
				versionIA2 = 1;
				break;
			case 2: //AI Level Beginner;X2;Level Hard;Y2 (0 vs 2)
				versionIA1 = 1;
				versionIA2 = 2;
				break;
			default : //AI Medium;X3;Level Hard;Y3 (1 vs 2)
				versionIA1 = 0;
				versionIA2 = 2;
				break;
			}
			int ia1WinTimes = 0;
			int ia2WinTimes = 0;
			int time = 1;
			for (int j= 0; j<100; j++) {
				tour = 1;
				switch (versionIA1) {
				case 0:
					ia1 = new IA0();
					break;
				case 1:
					ia1 = new IA1();
					break;
				default:
					ia1 = new IA2();
					break;
				}
				switch (versionIA2) {
				case 0://IA0
					ia2 = new IA0();
					break;
				case 1://IA1
					ia2 = new IA1();
					break;
				default://IA2
					ia2 = new IA2();
					break;
				}
				//Player 1 put 5 Ship and then Player 2 put 5 ship
				if (time%2 ==1) {
					//enterAllShipA(ia1);
					//System.out.print 
					ia1.enterAllShip();
				
					ia2.enterAllShip();
					//enterAllShipA(ia2);
					
				}else {// Player 2 put 5 Ship and then Player 1 put 5 ship
					ia2.enterAllShip();
					//enterAllShipA(ia2);

					//enterAllShipA(ia1);
					ia1.enterAllShip();
				}
				
				while(!ia2.allShipDestroyed() && !ia1.allShipDestroyed()) {
					if (tour%2 == time%2) {
						//PLAYER 1 PLAY
						c1 = ia1.askCoordinate(ia2);
						shoot(ia1, ia2,c1);
					}else {
						//PLAYER 2 PLAY
						c1 = ia2.askCoordinate(ia1);
						shoot(ia2, ia1,c1);
					}
					tour += 1;
				}
				//Which player win 
				time +=1;
				if (ia1.allShipDestroyed()) {
					ia2WinTimes += 1;
				}
				else {
					ia1WinTimes += 1;
				}
			}
			
			
			switch (typeOfIA) {
			case 1:
				writer.append("AI Level Beginner; "+ia1WinTimes+"; Level Medium; "+ia2WinTimes +"\n");
				System.out.println("AI Level Beginner; "+ia1WinTimes+"; Level Medium; "+ia2WinTimes);
				break;
			case 2:
				writer.append("AI Level Beginner; "+ia1WinTimes+"; Level Hard; "+ia2WinTimes+"\n");
				System.out.println("AI Level Beginner; "+ia1WinTimes+"; Level Hard; "+ia2WinTimes);
				break;
			case 3:
				writer.append("AI Level Medium; "+ia1WinTimes+"; Level Hard; "+ia2WinTimes+"\n");
				System.out.println("AI Level Medium; "+ia1WinTimes+"; Level Hard; "+ia2WinTimes);
				break;
			}
		}
		writer.flush();
		writer.close();
	}
}