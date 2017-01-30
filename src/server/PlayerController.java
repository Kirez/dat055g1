package server;

import common.GamePlayer;

public class PlayerController implements GameController {

  public GamePlayer player;

  public boolean leftDown;
  public boolean rightDown;
  public boolean upDown;

  public PlayerController(GamePlayer player) {
    this.player = player;
    leftDown = false;
    rightDown = false;
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

    player.setPosition(player.getPosition().add(player.getVelocity().multiply(delta)));
  }
}
