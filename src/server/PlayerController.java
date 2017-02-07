package server;

import common.GamePlayer;

public class PlayerController implements GameController {

  public GamePlayer player;

  public boolean leftDown;
  public boolean rightDown;
  public boolean upDown;
  public boolean downDown;
  public boolean hit;

  public PlayerController(GamePlayer player) {
    this.player = player;
    leftDown = false;
    rightDown = false;
    downDown = false;
    hit = false;
  }

  @Override
  public void update(double delta) {
    if (leftDown) {
      player.setPosition(player.getPosition().add(-320 * delta, 0));
    }
    if (rightDown) {
      player.setPosition(player.getPosition().add(320 * delta, 0));
    }
    if (upDown) {
      player.setVelocity(player.getVelocity().add(0, -768 * delta));
    }
    if (downDown) {
      player.setPosition(player.getPosition().add(0, 320 * delta));
    }
    if (hit) {
      //punch();
      System.out.println("Hadouken!");
    }
    player.setPosition(player.getPosition().add(player.getVelocity().multiply(delta)));
  }
}
