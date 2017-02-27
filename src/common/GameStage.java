package common;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 * The model/state for a stage and all that is in it. Handles only data
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-23
 */
public class GameStage {

  private static Point2D DEFAULT_PLAYER_1_SPAWN = new Point2D(2.5, 3);
  private static Point2D DEFAULT_PLAYER_2_SPAWN = new Point2D(13.5, 3);
  private static double DEFAULT_GROUND_Y = 6;

  private Point2D player1Spawn;
  private Point2D player2Spawn;

  private GamePlayer player1;
  private GamePlayer player2;

  private double width = 16;
  private double height = 9;

  private double groundLevelY = 3;

  //  Constructor
  public GameStage(Point2D p1s, Point2D p2s, GamePlayer p1, GamePlayer p2, double gly) {
    player1Spawn = p1s;
    player2Spawn = p2s;
    player1 = p1;
    player2 = p2;
    groundLevelY = gly;

    player1.setColor(Color.RED);
    player2.setColor(Color.BLUE);

    player1.setPosition(player1Spawn);
    player2.setPosition(player2Spawn);
  }

  public GameStage() {
    this(DEFAULT_PLAYER_1_SPAWN
        , DEFAULT_PLAYER_2_SPAWN
        , new GamePlayer()
        , new GamePlayer()
        , DEFAULT_GROUND_Y);
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

  public double getGroundLevelY() {
    return groundLevelY;
  }
}
