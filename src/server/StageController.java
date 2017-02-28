package server;

import common.ActionCycle.CYCLE;
import common.GamePlayer;
import common.GamePlayer.ACTION;
import common.GameStage;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

/**
 * Handles the state of the stage each tick and each player controller within it
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-28
 */
public class StageController implements GameController {

  GameStage stage;
  int i;
  private PlayerController player1Controller;
  private PlayerController player2Controller;

  public StageController(GameStage stage) {
    this.stage = stage;
    player1Controller = new PlayerController(stage.getPlayer1());
    player2Controller = new PlayerController(stage.getPlayer2());

    // Player controls
    player1Controller.bindKey(KeyCode.A, ACTION.MOVE_LEFT);
    player1Controller.bindKey(KeyCode.W, ACTION.JUMP);
    player1Controller.bindKey(KeyCode.D, ACTION.MOVE_RIGHT);
    player1Controller.bindKey(KeyCode.S, ACTION.FALL);
    player1Controller.bindKey(KeyCode.SPACE, ACTION.HIT);
    player1Controller.bindKey(KeyCode.Q, ACTION.KICK);

    player2Controller.bindKey(KeyCode.LEFT, ACTION.MOVE_LEFT);
    player2Controller.bindKey(KeyCode.UP, ACTION.JUMP);
    player2Controller.bindKey(KeyCode.RIGHT, ACTION.MOVE_RIGHT);
    player2Controller.bindKey(KeyCode.DOWN, ACTION.FALL);
    player2Controller.bindKey(KeyCode.ENTER, ACTION.HIT);
    player2Controller.bindKey(KeyCode.CONTROL, ACTION.KICK);
  }

  @Override
  public void update(double delta) {

    double gravity = 9.82;

    GamePlayer p1 = stage.getPlayer1();
    GamePlayer p2 = stage.getPlayer2();
    Point2D p1f = p1.getPosition().add(p1.getWidth() / 2, p1.getHeight());
    Point2D p2f = p2.getPosition().add(p2.getWidth() / 2, p2.getHeight());

    if (p1f.getY() < stage.getGroundLevelY()) {
      p1.accelerate(new Point2D(0, gravity * delta));
    } else if (!p1.isOnGround()) {
      p1.setOnGround(true);
      p1.setPosition(new Point2D(p1.getPosition().getX()
          , stage.getGroundLevelY() - p1.getHeight()));
      p1.setVelocity(new Point2D(p1.getVelocity().getX(), 0));
    }

    if (p2f.getY() < stage.getGroundLevelY()) {
      p2.accelerate(new Point2D(0, gravity * delta));
    } else if (!p2.isOnGround()) {
      p2.setOnGround(true);
      p2.setPosition(new Point2D(p2.getPosition().getX()
          , stage.getGroundLevelY() - p2.getHeight()));
      p2.setVelocity(new Point2D(p2.getVelocity().getX(), 0));
    }

    if (player1Controller.player.statePunching.isActive() || player1Controller.player.stateKicking
        .isActive()) {
      for (Rectangle hurt : p2.getHurtBoxes()) {
        if (p1.getHitBox(0).getBoundsInLocal().intersects(hurt.getBoundsInParent())) {
          if (!p2.stateStunned.isActive()) {
            p2.stateStunned.enterCycle(CYCLE.ACTIVE);
            p2.setHP(p2.getHP() - 10);
            System.out.println("Player 2 is hit" + p2.getHP() + "HP");
          }
        }
      }

    }

    if (player2Controller.player.statePunching.isActive() || player2Controller.player.stateKicking
        .isActive()) {
      for (Rectangle hurt : p1.getHurtBoxes()) {
        if (p2.getHitBox(1).getBoundsInLocal().intersects(hurt.getBoundsInParent())) {
          if (!p1.stateStunned.isActive()) {
            p1.stateStunned.enterCycle(CYCLE.ACTIVE);
            p1.setHP(p1.getHP() - 10);
            System.out.println("Player 1 is hit - " + p1.getHP() + "HP");
          }
        }
      }
    }
  }

  @Override
  public void attach(GameEngine engine) {
    engine.addController(this); // Ping-pong pow!

    player1Controller.attach(engine);
    player2Controller.attach(engine);
  }

  @Override
  public void onKeyPressed(KeyEvent event) {
    player1Controller.onKeyPressed(event);
    player2Controller.onKeyPressed(event);
    //gameClient.setKeyPressed(event); //Add key to client sendlist
  }

  @Override
  public void onKeyReleased(KeyEvent event) {
    player1Controller.onKeyReleased(event);
    player2Controller.onKeyReleased(event);
    //gameClient.setKeyReleased(event); //Remove key from client sendlist
  }
}
