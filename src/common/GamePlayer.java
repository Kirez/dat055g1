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
 * @version 2017-02-28
 */
public class GamePlayer {

  private static int DEFAULT_MAX_HP = 100;
  private static double DEFAULT_WIDTH = 1;
  private static double DEFAULT_HEIGHT = 2;
  private static Point2D DEFAULT_POSITION = new Point2D(0, 0);

  public ActionCycle stateStunned;
  public ActionCycle statePunching;
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

  //  Constructors
  private GamePlayer(Point2D position, Point2D velocity, int maxHP, int HP) {
    this.position = position;
    this.velocity = velocity;
    this.maxHP = maxHP;
    this.HP = HP;

    this.hurtBoxes = new ArrayList<>();
    this.hitBoxes = new ArrayList<>();
    faceRight = true;
  }

  public GamePlayer(Point2D position, int maxHP) {
    this(position, new Point2D(0, 0), maxHP, maxHP);
  }

  public GamePlayer(Point2D position) {
    this(position, DEFAULT_MAX_HP);
  }

  public GamePlayer() {
    this(DEFAULT_POSITION);
  }

  //  Getters and setters
  public int getMaxHP() {
    return maxHP;
  }

  public Point2D getPosition() {
    return position;
  }

  public void setPosition(Point2D position) {
    this.position = position;

  }

  public void addHurtbox(double x, double y, double boxwidth, double boxheight) {
    hurtBoxes.add(new Rectangle(x, y, boxwidth, boxheight));
    System.out
        .println("Hurtbox: x:" + x + " y:" + y + " width:" + boxwidth + " height:" + boxheight);
  }

  public void addHitbox(double x, double y, double boxwidth, double boxheight) {
    hitBoxes.add(new Rectangle(x, y, boxwidth, boxheight));
    System.out
        .println("Hitbox: x: " + x + " y:" + y + " width:" + boxwidth + " height:" + boxheight);
  }

  public void setHealth(int health) {
    maxHP = health;
    System.out.println("Character health: " + health);
  }

  public void setCharsize(double h, double w) {
    height = h;
    width = w;
    System.out.println("Character size: height" + h + "width" + w);
  }

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

  public Point2D getVelocity() {
    return velocity;
  }

  public void setVelocity(Point2D velocity) {
    this.velocity = velocity;
  }

  public double getHeight() {
    return height;
  }

  public double getWidth() {
    return width;
  }

  public void accelerate(Point2D delta) {
    velocity = velocity.add(delta);
  }

  public boolean isOnGround() {
    return onGround;
  }

  public void setOnGround(boolean onGround) {
    this.onGround = onGround;
  }

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

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public int getHP() {
    return HP;
  }

  public void setHP(int HP) {
    this.HP = HP;
  }

  public boolean isFaceRight() {
    return faceRight;
  }

  public void setFaceRight(boolean faceRight) {
    this.faceRight = faceRight;
  }

  public enum ACTION {
    MOVE_LEFT,
    MOVE_RIGHT,
    JUMP,
    FALL,
    HIT,
    KICK
  }
}
