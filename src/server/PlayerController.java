package server;

import common.GamePlayer;
import java.util.HashMap;
import java.util.HashSet;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PlayerController implements GameController {

  public GamePlayer player;
  private HashMap<KeyCode, ACTION> keyBinds;
  private HashSet<ACTION> actions; //Action-set to be performed during update

  public PlayerController(GamePlayer player) {
    this.player = player;
    keyBinds = new HashMap<>();
    actions = new HashSet<>();
  }

  @Override
  public void update(double delta) {
    if (actions.contains(ACTION.MOVE_LEFT)) {
      player.setPosition(player.getPosition().add(-320 * delta, 0));
    }
    if (actions.contains(ACTION.MOVE_RIGHT)) {
      player.setPosition(player.getPosition().add(320 * delta, 0));
    }
    if (actions.contains(ACTION.JUMP)) {
      if (player.isOnGround()) {
        player.accelerate(new Point2D(0, -400));
        player.setOnGround(false);
      }
    }

    player.setPosition(player.getPosition().add(player.getVelocity().multiply(delta)));
  }

  @Override
  public void attach(GameEngine engine) {
    engine.addController(this);
  }

  public void bindKey(KeyCode code, ACTION action) {
    keyBinds.put(code, action);
  }

  @Override
  public void onKeyPressed(KeyEvent event) {
    if (keyBinds.containsKey(event.getCode())) {
      actions.add(keyBinds.get(event.getCode()));
    }
  }

  @Override
  public void onKeyReleased(KeyEvent event) {
    if (keyBinds.containsKey(event.getCode())) {
      actions.remove(keyBinds.get(event.getCode()));
    }
  }

  public enum ACTION {
    MOVE_LEFT,
    MOVE_RIGHT,
    JUMP,
  }
}
