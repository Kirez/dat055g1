package common;

import client.FileHandler;
import javafx.geometry.Point2D;

/**
 * Model class for GameStage
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-03-04
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

  private double groundLevelY = 6;

  /**
   * Creates an instance of GameStage.
   *
   * @param p1s player 1 spawn position
   * @param p2s player 2 spawn position
   * @param p1 player 1 reference
   * @param p2 player 2 reference
   * @param gly ground level y
   */
  public GameStage(Point2D p1s, Point2D p2s, GamePlayer p1, GamePlayer p2, double gly) {
    player1Spawn = p1s;
    player2Spawn = p2s;
    player1 = p1;
    player2 = p2;
    groundLevelY = gly;
    FileHandler.importCharacters(p1);
    FileHandler.importCharacters(p2);
    player1.setColor(GameDefaults.PLAYER_1_COLOR);
    player2.setColor(GameDefaults.PLAYER_2_COLOR);

    player1.setPosition(player1Spawn);
    player2.setPosition(player2Spawn);
  }

  /**
   * Creates an instance of GameStage.
   */
  public GameStage() {
    this(DEFAULT_PLAYER_1_SPAWN
        , DEFAULT_PLAYER_2_SPAWN
        , new GamePlayer()
        , new GamePlayer()
        , DEFAULT_GROUND_Y);
  }

  /**
   * Resets player position, hit points, velocity and sets on ground to false since player spawns in
   * air.
   */
  public void reset() {
    player1.setPosition(player1Spawn);
    player2.setPosition(player2Spawn);

    player1.setHP(player2.getMaxHP());
    player2.setHP(player2.getMaxHP());

    player1.setVelocity(new Point2D(0, 0));
    player2.setVelocity(new Point2D(0, 0));

    player1.setOnGround(false);
    player2.setOnGround(false);
  }

  /**
   * Gets player 1
   *
   * @return player 1
   */
  public GamePlayer getPlayer1() {
    return player1;
  }

  /**
   * Gets player 2
   *
   * @return player 2
   */
  public GamePlayer getPlayer2() {
    return player2;
  }

  /**
   * Gets y-coordinate of ground
   *
   * @return the y-coordinate of the ground
   */
  public double getGroundLevelY() {
    return groundLevelY;
  }
}
