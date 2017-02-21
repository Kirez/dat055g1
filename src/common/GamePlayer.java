package common;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*The model/state for players. Handles only data*/
public class GamePlayer {

  private static final double PUNCH_SPOOL_UP = 0.15;
  private static final double PUNCH_DURATION = 0.15;
  private static final double PUNCH_COOL_DOWN = 0.15;
  private static int DEFAULT_MAX_HP = 100;
  private static double DEFAULT_WIDTH = 1;
  private static double DEFAULT_HEIGHT = 2;
  private static Point2D DEFAULT_POSITION = new Point2D(0, 0);

  public ActionCycle stateStunned;
  public ActionCycle statePunching;
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

    width = DEFAULT_WIDTH;
    height = DEFAULT_HEIGHT;

    this.hurtBoxes = new ArrayList<>();
    this.hitBoxes = new ArrayList<>();

  //  hurtBoxes.add(new Rectangle(height * 0.0625, 0, width * 0.75, height * 0.25));
  //  hurtBoxes.add(new Rectangle(0, height * 0.125, width * 0.5, height * 0.5));
    hitBoxes.add(new Rectangle(0.75 * width, height * 0.1875, width * 0.25, height * 0.125));

    stateStunned = new ActionCycle(PUNCH_DURATION, PUNCH_DURATION * 2, 0);
    statePunching = new ActionCycle(PUNCH_SPOOL_UP, PUNCH_DURATION, PUNCH_COOL_DOWN);

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

  public void setHP(int HP) {
    this.HP = HP;
  }

  public Point2D getPosition() {
    return position;
  }

  public void setPosition(Point2D position) {
    this.position = position;

  }
  public void addHurtbox(double x, double y, double boxwidth, double boxheight ) {
    hurtBoxes.add(new Rectangle(x, y, boxwidth, boxheight));
    System.out.println(x + " " + y + " " + boxwidth + " " + boxheight);
  }
  public void addHitbox(double x,double y,double boxwidth,double boxheight) {
    hitBoxes.add(new Rectangle(x, y, boxwidth, boxheight));
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

  public ArrayList<Rectangle> getHitBoxes() {
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

    return rectangles;
  }

  public void setFaceRight(boolean faceRight) {
    this.faceRight = faceRight;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public enum ACTION {
    MOVE_LEFT,
    MOVE_RIGHT,
    JUMP,
    FALL,
    HIT
  }
}
