package server;

import common.ActionCycle.CYCLE;
import common.GamePlayer;
import common.GamePlayer.ACTION;
import java.util.HashMap;
import java.util.HashSet;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/*Handles the state of a player each tick. Takes into consideration input from users*/
public class PlayerController implements GameController {

  public GamePlayer player;

  private HashMap<KeyCode, ACTION> keyBinds;
  private HashSet<ACTION> actions; //actions to be performed during update

  public PlayerController(GamePlayer player) {
    this.player = player;
    keyBinds = new HashMap<>();
    actions = new HashSet<>();
  }

  @Override
  public void update(double delta) {
    if (actions.contains(ACTION.MOVE_LEFT)) {
      player.setFaceRight(false);
      player.setPosition(player.getPosition().add(-4 * delta, 0));
    }
    if (actions.contains(ACTION.MOVE_RIGHT)) {
      player.setFaceRight(true);
      player.setPosition(player.getPosition().add(4 * delta, 0));
    }
    if (actions.contains(ACTION.JUMP)) {
      if (player.isOnGround()) {
        player.accelerate(new Point2D(0, -6));
        player.setOnGround(false);
      }
    }
    if (actions.contains(ACTION.FALL)) {
      if (!player.isOnGround()) {
        player.setPosition(player.getPosition().add(0, 2 * delta));
      }
    }
    if (player.stateKicking.isActive()) {

    }

    if (player.statePunching.isActive()) {

    }

    player.statePunching.update(delta);
    player.stateStunned.update(delta);
    player.stateKicking.update(delta);
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
    if (((!actions.contains(ACTION.HIT)) && (!actions.contains(ACTION.KICK)) && (keyBinds.get(event.getCode()) == ACTION.HIT)) || ((!actions.contains(ACTION.KICK) && (!actions.contains(ACTION.HIT))) && keyBinds.get(event.getCode()) == ACTION.KICK)) {
      if ((player.statePunching.isReady() && player.stateKicking.isReady()) && !player.stateStunned.isActive() && keyBinds.get(event.getCode()) == ACTION.HIT) {
        player.statePunching.enterCycle(CYCLE.SPOOL_UP);
      }
      else if ((player.stateKicking.isReady() && player.stateKicking.isReady()) && !player.stateStunned.isActive() && keyBinds.get(event.getCode()) == ACTION.KICK) {
        player.stateKicking.enterCycle(CYCLE.SPOOL_UP);
      }
    }
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
}
