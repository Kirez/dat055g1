package server;

import common.GamePlayer;
import common.GamePlayer.STATE;
import java.util.HashMap;
import java.util.HashSet;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/*Handles the state of a player each tick. Takes into consideration input from users*/
public class PlayerController implements GameController {

  public GamePlayer player;

  private HashMap<KeyCode, STATE> keyBinds;
  private HashSet<STATE> states; //Action-set to be performed during update

  public PlayerController(GamePlayer player) {
    this.player = player;
    keyBinds = new HashMap<>();
    states = new HashSet<>();
  }

  @Override
  public void update(double delta) {
    if (states.contains(STATE.MOVE_LEFT)) {
      player.setFaceRight(false);
      player.setPosition(player.getPosition().add(-320 * delta, 0));
    }
    if (states.contains(STATE.MOVE_RIGHT)) {
      player.setFaceRight(true);
      player.setPosition(player.getPosition().add(320 * delta, 0));
    }
    if (states.contains(STATE.JUMPING)) {
      if (player.isOnGround()) {
        player.accelerate(new Point2D(0, -400));
        player.setOnGround(false);
      }
    }
    if (states.contains(STATE.FALLING)) {
      if (!player.isOnGround()) {
        player.setPosition(player.getPosition().add(0, 1600 * delta));
      }
    }

    if (player.isActive(STATE.HITTING)) {

    }

    for (STATE state : player.getStateDurations().keySet()) {
      double duration = player.getStateDurations().get(state);
      duration -= delta;

      if (duration <= 0) {
        player.getStateDurations().remove(state);
        player.setCooldown(state, GamePlayer.PUNCH_DURATION*2);
      } else {
        player.getStateDurations().put(state, duration);
      }
    }

    for (STATE state : player.getStateCooldowns().keySet()) {
      double duration = player.getStateCooldowns().get(state);
      duration -= delta;

      if (duration <= 0) {
        player.getStateCooldowns().remove(state);
      } else {
        player.getStateCooldowns().put(state, duration);
      }
    }

    player.setPosition(player.getPosition().add(player.getVelocity().multiply(delta)));
  }

  @Override
  public void attach(GameEngine engine) {
    engine.addController(this);
  }

  public void bindKey(KeyCode code, STATE state) {
    keyBinds.put(code, state);
  }

  @Override
  public void onKeyPressed(KeyEvent event) {
    if (!states.contains(STATE.HITTING) && keyBinds.get(event.getCode()) == STATE.HITTING) {
      if (!player.isOnCooldown(STATE.HITTING) && !player.isActive(STATE.HITTING)) {
        player.activate(STATE.HITTING);
      }
    }

    if (keyBinds.containsKey(event.getCode())) {
      states.add(keyBinds.get(event.getCode()));
    }
  }

  @Override
  public void onKeyReleased(KeyEvent event) {
    if (keyBinds.containsKey(event.getCode())) {
      states.remove(keyBinds.get(event.getCode()));
    }
  }
}
