package serafin.solene.classes;

public interface PlayerMustDo{
	public void whoBegin();
	public void enterAllShip();
	public Coordinate askCoordinate(Player p);
	public Coordinate printShoot(Player o); 
	public void printScores(int playerWinTimes, int nbTimes);
}
