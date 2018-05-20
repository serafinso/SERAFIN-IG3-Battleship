package serafin.solene.classes;

public interface PlayerMustDo{
	public void enterAllShip();
	public Coordinate askCoordinate(Player p);
	public Coordinate printShoot(Player o);
	public void whoBegin();
	public void printScores(int playerWinTimes, int nbTimes);
}
