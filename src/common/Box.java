package common;


import javafx.geometry.BoundingBox;

public class Box extends BoundingBox {

  private double x;
  private double y;
  private double width;
  private double height;
  private boolean boxType;
  private GamePlayer player;

  public Box(double x,
      double y,
      double width,
      double height,
      boolean boxType,
      GamePlayer player)
  {
    super(x,y, width, height );
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.boxType = boxType;
    this.player = player;
  }
  public int getHeadBoxX() {
    return (int) player.getPosition().getX();
  }
  public int getHeadBoxY() {return (int) player.getPosition().getY();}

  public int getBodyBoxX() {
    return (int) player.getPosition().getX() + 16;
  }
  public int getBodyBoxY() {return (int) player.getPosition().getY() + 32;}
  public int getBoxWidth() {
    return (int) width;
  }
  public int getBoxHeight() {
    return (int) height;
  }

  public int getPunchX() {
    return (int) player.getPosition().getX() + 64;
  }
  public int getPunchY() {return (int) player.getPosition().getY() + 32;}
}