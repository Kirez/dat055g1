package common;

import javafx.geometry.Point2D;

public class GameStage {

  private Point2D player1Spawn;
  private Point2D player2Spawn;

  private GamePlayer player1;
  private GamePlayer player2;

  private int groundLevelY;

  //  Constructor
  public GameStage(Point2D p1s, Point2D p2s, GamePlayer p1, GamePlayer p2, int gly) {
    player1Spawn = p1s;
    player2Spawn = p2s;
    player1 = p1;
    player2 = p2;
    groundLevelY = gly;

    player1.setPosition(player1Spawn);
    player2.setPosition(player2Spawn);
  }

  public GameStage() {
    this(new Point2D(64, 64), new Point2D(704, 64), new GamePlayer(), new GamePlayer(), 256);
  }

  //  Resets players position and HP, player is still after reset
  public void reset() {
    player1.setPosition(player1Spawn);
    player2.setPosition(player2Spawn);

    player1.setHP(player2.getMaxHP());
    player2.setHP(player2.getMaxHP());

    player1.setVelocity(new Point2D(0, 0));
    player2.setVelocity(new Point2D(0, 0));
  }

  //  Getters
  public GamePlayer getPlayer1() {
    return player1;
  }

  public GamePlayer getPlayer2() {
    return player2;
  }

  public int getGroundLevelY() {
    return groundLevelY;
  }
}
