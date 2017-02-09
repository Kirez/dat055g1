package common;

import javafx.geometry.Point2D;

public class GamePlayer {

  private static int DEFAULT_MAX_HP = 100;
  private static int DEFAULT_WIDTH = 64;
  private static int DEFAULT_HEIGHT = 128;
  private static Point2D DEFAULT_POSITION = new Point2D(0, 0);

  private Point2D position;
  private Point2D velocity;

  private int maxHP;
  private int HP;

  private int width;
  private int height;
  private int i = 0;

  public Box[] Hurtboxes = new Box[5];
  public Box[] punchBox = new Box[5];

  private boolean onGround;

  //  Constructors
  private GamePlayer(Point2D position, Point2D velocity, int maxHP, int HP) {
    this.position = position;
    this.velocity = velocity;
    this.maxHP = maxHP;
    this.HP = HP;

    width = DEFAULT_WIDTH;
    height = DEFAULT_HEIGHT;
    generateHurtBoxes();
    generateHitBoxes();
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

  public void generateHurtBoxes () {
    Hurtboxes[0] = new Box(getPosition().getX(), getPosition().getY(), 64, 32, false, this);
    Hurtboxes[1] = new Box(getPosition().getX(), getPosition().getY(), 32, 64, false, this);
  }
  public Box getHurtboxes(int i) {
    return Hurtboxes[i];
  }

  public void generateHitBoxes () {
    punchBox[0] = new Box (getPosition().getX(), getPosition().getY(), 16, 16, true, this);
  }
  public Box getHitboxes(int i) {
    return punchBox[i];
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
