package common;

import java.util.ArrayList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/*The model/state for players. Handles only data*/
public class GamePlayer {

  private static int DEFAULT_MAX_HP = 100;
  private static int DEFAULT_WIDTH = 64;
  private static int DEFAULT_HEIGHT = 128;
  private static Point2D DEFAULT_POSITION = new Point2D(0, 0);
  public ArrayList<Rectangle> HurtBoxes;
  public ArrayList<Rectangle> HitBoxes;
  public double intX[] = new double[3];
  public double intY[] = new double[3];
  private Point2D position;
  private Point2D velocity;
  private int maxHP;
  private int HP;
  private int width;
  private int height;
  private int i = 0;
  private boolean onGround;

  //  Constructors
  private GamePlayer(Point2D position, Point2D velocity, int maxHP, int HP) {
    this.position = position;
    this.velocity = velocity;
    this.maxHP = maxHP;
    this.HP = HP;

    width = DEFAULT_WIDTH;
    height = DEFAULT_HEIGHT;

    this.HurtBoxes = new ArrayList<>();
    this.HitBoxes = new ArrayList<>();


    intX[0] = 0;
    intX[1] = 16;
    intX[2] = 96;
    intY[0] = 0;
    intY[1] = 32;
    intY[2] = 24;
    generateHurtBoxes();
 //   generateHitBoxes();
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

  public void generateHurtBoxes() {
    Rectangle HurtBox1 = new Rectangle(getPosition().getX() + intX[0],
        getPosition().getY() + intY[0], 64, 32);

    HurtBoxes.add(HurtBox1);

    Rectangle HurtBox2 = new Rectangle(getPosition().getX() + intX[1],
        getPosition().getY() + intY[1], 32, 64);

    HurtBoxes.add(HurtBox2);

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

  public void move(Point2D delta) {
    position = position.add(delta);
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
}
