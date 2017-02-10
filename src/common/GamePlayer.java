package common;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/*The model/state for players. Handles only data*/
public class GamePlayer {

  private static int DEFAULT_MAX_HP = 100;
  private static int DEFAULT_WIDTH = 64;
  private static int DEFAULT_HEIGHT = 128;
  private static Point2D DEFAULT_POSITION = new Point2D(0, 0);
  private ArrayList<Rectangle> hurtBoxes;
  private ArrayList<Rectangle> hitBoxes;
  private Point2D position;
  private Point2D velocity;
  private int maxHP;
  private int HP;
  private int width;
  private int height;
  private boolean onGround;
  private boolean faceRight;

  private HashMap<STATE, Double> stateDurations;
  private HashMap<STATE, Double> stateCooldowns;

  public static final double PUNCH_DURATION = 0.15;

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

    hurtBoxes.add(new Rectangle(0, 0, 64, 32));
    hurtBoxes.add(new Rectangle(16, 16, 32, 64));
    hitBoxes.add(new Rectangle(32, 24, 16, 16));

    stateDurations = new HashMap<>();
    stateCooldowns = new HashMap<>();
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

  public Point2D getVelocity() {
    return velocity;
  }

  public void setVelocity(Point2D velocity) {
    this.velocity = velocity;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
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
      rectangles.add(new Rectangle(hurtBox.getX() + getPosition().getX(),
          hurtBox.getY() + getPosition().getY() + hurtBox.getY(), hurtBox.getWidth(),
          hurtBox.getHeight()));
    }

    return rectangles;
  }

  public ArrayList<Rectangle> getHitBoxes() {
    ArrayList<Rectangle> rectangles = new ArrayList<>();

    if (stateDurations.containsKey(STATE.HITTING)) {
      for (Rectangle hitBox : hitBoxes) {
        if (faceRight) {
          rectangles.add(new Rectangle(hitBox.getX() + width - hitBox.getWidth() / 2 + getPosition().getX(),
              hitBox.getY() + getPosition().getY() + hitBox.getY(), hitBox.getWidth(),
              hitBox.getHeight()));
        } else {
          rectangles.add(new Rectangle(-hitBox.getX() - hitBox.getWidth() / 2+ getPosition().getX(),
              hitBox.getY() + getPosition().getY() + hitBox.getY(), hitBox.getWidth(),
              hitBox.getHeight()));
        }
      }
    }

    return rectangles;
  }

  public boolean isOnCooldown(STATE state) {
    return stateCooldowns.containsKey(state);
  }

  public boolean isActive(STATE state) {
    return stateDurations.containsKey(state);
  }

  public void activate(STATE state) {
    switch (state) {
      case HITTING:
        stateDurations.put(STATE.HITTING, PUNCH_DURATION);
        break;
    }
  }

  public void setCooldown(STATE state, double duration) {
    stateCooldowns.put(state, duration);
  }

  public void setFaceRight(boolean faceRight) {
    this.faceRight = faceRight;
  }

  public enum STATE {
    MOVE_LEFT,
    MOVE_RIGHT,
    JUMPING,
    FALLING,
    HITTING,
    STUNNED
  }

  public HashMap<STATE, Double> getStateDurations() {
    return stateDurations;
  }

  public HashMap<STATE, Double> getStateCooldowns() {
    return stateCooldowns;
  }


}
