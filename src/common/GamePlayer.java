package common;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The model/state for players. Handles only data
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-03-04
 */
public class GamePlayer {

  private static int DEFAULT_MAX_HP = 100;
  private static double DEFAULT_WIDTH = 1;
  private static double DEFAULT_HEIGHT = 2;
  private static Point2D DEFAULT_POSITION = new Point2D(0, 0);

  /**
   * Describes whether a player is being stunned getting stunned or recovering from stun.
   */
  public ActionCycle stateStunned;
  /**
   * Describes whether a player is going to punch, is punching or recovers from punching.
   */
  public ActionCycle statePunching;
  /**
   * Describes whether a player is going to kick, is kicking or recovers from punching.
   */
  public ActionCycle stateKicking;
  private ArrayList<Rectangle> hurtBoxes;
  private ArrayList<Rectangle> hitBoxes;
  private Point2D position;
  private Point2D velocity;
  private int maxHP;
  private int HP;
  private double width;
  private double height;
  private boolean onGround;
  private boolean faceRight;
  private Color color;

  /**
   * Creates instance of {@code GamePlayer}.
   *
   * @param position player position
   * @param velocity player velocity
   * @param maxHP player max hp
   * @param HP player hit points
   */
  private GamePlayer(Point2D position, Point2D velocity, int maxHP, int HP) {
    this.position = position;
    this.velocity = velocity;
    this.maxHP = maxHP;
    this.HP = HP;

    this.hurtBoxes = new ArrayList<>();
    this.hitBoxes = new ArrayList<>();
    faceRight = true;
  }

  /**
   * Creates instance of {@code GamePlayer} with default hp and default velocity.
   *
   * @param position player position
   * @param maxHP player max hp
   */
  public GamePlayer(Point2D position, int maxHP) {
    this(position, new Point2D(0, 0), maxHP, maxHP);
  }

  /**
   * Creates instance of {@code GamePlayer} with everything as default except position.
   *
   * @param position the position of the player
   */
  public GamePlayer(Point2D position) {
    this(position, DEFAULT_MAX_HP);
  }

  /**
   * Creates instance of {@code GamePlayer} with all defaults.
   */
  public GamePlayer() {
    this(DEFAULT_POSITION);
  }

  /**
   * gets maximum player hp.
   *
   * @return maximum player hp
   */
  public int getMaxHP() {
    return maxHP;
  }

  /**
   * gets player position.
   *
   * @return player position
   */
  public Point2D getPosition() {
    return position;
  }

  /**
   * sets player position.
   *
   * @param position the new position
   */
  public void setPosition(Point2D position) {
    this.position = position;

  }

  /**
   * Adds a hurtbox.
   *
   * @param x relative x-coordinate to player position of this hurtbox
   * @param y relative y-coordinate to player position of this hurtbox
   * @param boxwidth the width of the hurtbox
   * @param boxheight the height of the hurtbox
   */
  public void addHurtbox(double x, double y, double boxwidth, double boxheight) {
    hurtBoxes.add(new Rectangle(x, y, boxwidth, boxheight));
    System.out
        .println("Hurtbox: x:" + x + " y:" + y + " width:" + boxwidth + " height:" + boxheight);
  }

  /**
   * Adds a hitbox.
   *
   * @param x relative x-coordinate to player position of this hitbox
   * @param y relative y-coordinate to player position of this hitbox
   * @param boxwidth the width of the hitbox
   * @param boxheight the height of the hitbox
   */
  public void addHitbox(double x, double y, double boxwidth, double boxheight) {
    hitBoxes.add(new Rectangle(x, y, boxwidth, boxheight));
    System.out
        .println("Hitbox: x: " + x + " y:" + y + " width:" + boxwidth + " height:" + boxheight);
  }

  /**
   * Sets player health.
   *
   * @param health value to set player health to
   */
  public void setHealth(int health) {
    maxHP = health;
    System.out.println("Character health: " + health);
  }

  /**
   * Sets player width and height.
   *
   * @param h the new height of player
   * @param w the new width of player
   */
  public void setCharsize(double h, double w) {
    height = h;
    width = w;
    System.out.println("Character size: height" + h + "width" + w);
  }

  /**
   * Sets either stateStunned, stateKicking and statePunching based on contents of {@code type}.
   *
   * @param spool the duration of the attack's spool up time
   * @param duration the duration of the attack hitbox
   * @param cooldown the duration of the attack's cooldown
   * @param type the type of attack
   */
  public void setCycles(double spool, double duration, double cooldown, String type) {
    stateStunned = new ActionCycle(0, 0.5, 0);
    if (type == "kick") {
      stateKicking = new ActionCycle(spool, duration, cooldown);
      System.out.println("Action Cycle Kick" + spool + " " + duration + " " + cooldown);
    }
    if (type == "jab") {
      statePunching = new ActionCycle(spool, duration, cooldown);
      System.out.println("Action Cycle Jab" + spool + " " + duration + " " + cooldown);
    }
  }

  /**
   * Gets player velocity.
   *
   * @return player velocity
   */
  public Point2D getVelocity() {
    return velocity;
  }

  /**
   * Sets player velocity.
   *
   * @param velocity value to set velocity to
   */
  public void setVelocity(Point2D velocity) {
    this.velocity = velocity;
  }

  /**
   * Gets height of player.
   *
   * @return height of player
   */
  public double getHeight() {
    return height;
  }

  /**
   * Gets width of player.
   *
   * @return width of player
   */
  public double getWidth() {
    return width;
  }

  /**
   * Adds {@code delta} to player velocity, 'accelerating' the player by delta.
   *
   * @param delta the delta-v to accelerate player by
   */
  public void accelerate(Point2D delta) {
    velocity = velocity.add(delta);
  }

  /**
   * Answers whether player is on ground or not.
   *
   * @return true if player is on ground and false if not
   */
  public boolean isOnGround() {
    return onGround;
  }

  /**
   * Sets whether player is considered on ground or not.
   *
   * @param onGround true if on ground else false
   */
  public void setOnGround(boolean onGround) {
    this.onGround = onGround;
  }

  /**
   * Gets an ArrayList of globally positioned Rectangles representing the player's hurtboxes.
   *
   * @return the ArrayList of the hurtboxes of type Rectangle
   */
  public ArrayList<Rectangle> getHurtBoxes() {
    ArrayList<Rectangle> rectangles = new ArrayList<>();

    for (Rectangle hurtBox : hurtBoxes) {
      if (faceRight) {
        rectangles.add(new Rectangle(
            hurtBox.getX() + width / 2 - hurtBox.getWidth() / 2 + getPosition().getX(),
            hurtBox.getY() + getPosition().getY() + hurtBox.getY(), hurtBox.getWidth(),
            hurtBox.getHeight()));
      } else {
        rectangles.add(new Rectangle(
            -hurtBox.getX() + width / 2 - hurtBox.getWidth() / 2 + getPosition().getX(),
            hurtBox.getY() + getPosition().getY() + hurtBox.getY(), hurtBox.getWidth(),
            hurtBox.getHeight()));
      }
    }

    return rectangles;
  }

  /**
   * Gets a specific hitbox.
   *
   * @param hitbox index of hitbox to get
   * @return the hitbox
   */
  public Rectangle getHitBox(int hitbox) {
    ArrayList<Rectangle> rectangles = new ArrayList<>();

    for (Rectangle hitBox : hitBoxes) {
      if (faceRight) {
        rectangles.add(
            new Rectangle(hitBox.getX() + width - hitBox.getWidth() / 2 + getPosition().getX(),
                hitBox.getY() + getPosition().getY() + hitBox.getY(), hitBox.getWidth(),
                hitBox.getHeight()));
      } else {
        rectangles
            .add(new Rectangle(-hitBox.getX() - hitBox.getWidth() / 2 + getPosition().getX(),
                hitBox.getY() + getPosition().getY() + hitBox.getY(), hitBox.getWidth(),
                hitBox.getHeight()));
      }
    }

    return rectangles.get(hitbox);
  }

  /**
   * Gets the color of the player.
   *
   * @return the color
   */
  public Color getColor() {
    return color;
  }

  /**
   * Sets the color of the player.
   *
   * @param color the color to set
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * Gets player hit points.
   *
   * @return player hit points
   */
  public int getHP() {
    return HP;
  }

  /**
   * Sets player hit points.
   *
   * @param HP value to set hitpoints to
   */
  public void setHP(int HP) {
    this.HP = HP;
  }

  /**
   * Whether or not player is facing right.
   *
   * @return true if true and false if false
   */
  public boolean isFaceRight() {
    return faceRight;
  }

  /**
   * Set whether or not a player is considered to be facing right or left.
   *
   * @param faceRight true if player is to be considered facing right and false for left.
   */
  public void setFaceRight(boolean faceRight) {
    this.faceRight = faceRight;
  }

  /**
   * The types of actions a player can take.
   */
  public enum ACTION {
    MOVE_LEFT,
    MOVE_RIGHT,
    JUMP,
    FALL,
    HIT,
    KICK
  }
}
