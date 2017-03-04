package server;

import static javafx.scene.input.KeyCode.getKeyCode;

import client.FileHandler;
import common.ActionCycle.CYCLE;
import common.GamePlayer;
import common.GamePlayer.ACTION;
import common.GameStage;
import java.util.ArrayList;
import javafx.geometry.Point2D;
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
  private PlayerController player1Controller;
  private PlayerController player2Controller;

  /**
   * Creates a new instance of <tt>StageController</tt>, gets the controls and creates instances
   * of PlayerController.
   *
   * @param stage
   */
  public StageController(GameStage stage) {
    this.stage = stage;
    player1Controller = new PlayerController(stage.getPlayer1());
    player2Controller = new PlayerController(stage.getPlayer2());
    getControls();
  }

  /**
   * Updates the current events in the game.
   *
   * @param delta the time difference between this and the previous tick, used for scaling
   */
  @Override
  public void update(double delta) {

    double gravity = 9.82;

    GamePlayer p1 = stage.getPlayer1();
    GamePlayer p2 = stage.getPlayer2();
    Point2D p1f = p1.getPosition().add(p1.getWidth() / 2, p1.getHeight());
    Point2D p2f = p2.getPosition().add(p2.getWidth() / 2, p2.getHeight());

    if (p1.getHP() < 0) {
      p1.setHP(0);
    }
    if (p2.getHP() < 0) {
      p2.setHP(0);
    }

    double kbMultiplier1 = ((double) p2.getMaxHP() / (p2.getHP() + 1)) / 1.5;
    double kbMultiplier2 = ((double) p1.getMaxHP() / (p1.getHP() + 1)) / 1.5;

    //Set player 1 as not on ground if above ground level
    if (p1.getPosition().getY() + p1.getHeight() < stage.getGroundLevelY()) {
      p1.setOnGround(false);
    }

    //Set player 2 as not on ground if above ground level
    if (p2.getPosition().getY() + p2.getHeight() < stage.getGroundLevelY()) {
      p2.setOnGround(false);
    }

    //Apply air resistance + eventual ground friction to reduce player 1 x-velocity
    if (p1.isOnGround()) {
      p1.accelerate(new Point2D(p1.getVelocity().multiply(-5 * delta).getX(), 0));
    } else {
      p1.accelerate(new Point2D(p1.getVelocity().multiply(-2.5 * delta).getX(), 0));
    }

    //Apply air resistance + eventual ground friction to reduce player 2 x-velocity
    if (p2.isOnGround()) {
      p2.accelerate(new Point2D(p2.getVelocity().multiply(-5 * delta).getX(), 0));
    } else {
      p2.accelerate(new Point2D(p2.getVelocity().multiply(-2.5 * delta).getX(), 0));
    }

    //Apply gravity acceleration if not on ground and handle ground collision
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

    //Stage wall collision
    if (p1f.getX() + p1.getWidth() / 2 > 16) {
      p1.setPosition(new Point2D(16 - p1.getWidth(), p1.getPosition().getY()));
      p1.setVelocity(new Point2D(p1.getVelocity().getX() * -1, p1.getVelocity().getY()));
    } else if (p1f.getX() - p1.getWidth() / 2 < 0) {
      p1.setPosition(new Point2D(0, p1.getPosition().getY()));
      p1.setVelocity(new Point2D(p1.getVelocity().getX() * -1, p1.getVelocity().getY()));
    }

    if (p2f.getX() + p2.getWidth() / 2 > 16) {
      p2.setPosition(new Point2D(16 - p2.getWidth(), p2.getPosition().getY()));
      p2.setVelocity(new Point2D(p2.getVelocity().getX() * -1, p2.getVelocity().getY()));
    } else if (p2f.getX() - p2.getWidth() / 2 < 0) {
      p2.setPosition(new Point2D(0, p2.getPosition().getY()));
      p2.setVelocity(new Point2D(p2.getVelocity().getX() * -1, p2.getVelocity().getY()));
    }

    //Stage ceiling collision
    if (p1.getPosition().getY() < 0) {
      p1.setPosition(new Point2D(p1.getPosition().getX(), 0));
      p1.setVelocity(new Point2D(p1.getVelocity().getX(), p1.getVelocity().getY() * -1));
    }
    if (p2.getPosition().getY() < 0) {
      p2.setPosition(new Point2D(p2.getPosition().getX(), 0));
      p2.setVelocity(new Point2D(p2.getVelocity().getX(), p2.getVelocity().getY() * -1));
    }
    //Checks HitBox/HurtBox collisions
    if (player1Controller.player.statePunching.isActive()) {
      for (Rectangle hurt : p2.getHurtBoxes()) {
        if (p1.getHitBox(0).getBoundsInParent().intersects(hurt.getBoundsInParent())) {
          if (!p2.stateStunned.isActive()) {
            p2.stateStunned.enterCycle(CYCLE.ACTIVE);
            p2.setHP(p2.getHP() - 10);
            System.out.println("Player 2 is hit - " + p2.getHP() + "HP");
            if (p1.isFaceRight()) {
              p2.accelerate(new Point2D(15, -3).multiply(kbMultiplier1));
            } else {
              p2.accelerate(new Point2D(-15, -3).multiply(kbMultiplier1));
            }
          }
        }
      }
    }
    //Checks HitBox/HurtBox collisions
    if (player1Controller.player.stateKicking.isActive()) {
      for (Rectangle hurt : p2.getHurtBoxes()) {
        if (p1.getHitBox(1).getBoundsInParent().intersects(hurt.getBoundsInParent())) {
          if (!p2.stateStunned.isActive()) {
            p2.stateStunned.enterCycle(CYCLE.ACTIVE);
            p2.setHP(p2.getHP() - 20);
            System.out.println("Player 2 is hit - " + p2.getHP() + "HP");
            if (p1.isFaceRight()) {
              p2.accelerate(new Point2D(20, -10).multiply(kbMultiplier1));
            } else {
              p2.accelerate(new Point2D(-20, -10).multiply(kbMultiplier1));
            }
          }
        }
      }
    }
    //Checks HitBox/HurtBox collisions
    if (player2Controller.player.statePunching.isActive()) {
      for (Rectangle hurt : p1.getHurtBoxes()) {
        if (p2.getHitBox(0).getBoundsInParent().intersects(hurt.getBoundsInParent())) {
          if (!p1.stateStunned.isActive()) {
            p1.stateStunned.enterCycle(CYCLE.ACTIVE);
            p1.setHP(p1.getHP() - 10);
            System.out.println("Player 1 is hit - " + p1.getHP() + "HP");
            if (p2.isFaceRight()) {
              p1.accelerate(new Point2D(15, -3).multiply(kbMultiplier2));
            } else {
              p1.accelerate(new Point2D(-15, -3).multiply(kbMultiplier2));
            }
          }
        }
      }
    }
    //Checks HitBox/HurtBox collisions
    if (player2Controller.player.stateKicking.isActive()) {
      for (Rectangle hurt : p1.getHurtBoxes()) {
        if (p2.getHitBox(1).getBoundsInParent().intersects(hurt.getBoundsInParent())) {
          if (!p1.stateStunned.isActive()) {
            p1.stateStunned.enterCycle(CYCLE.ACTIVE);
            p1.setHP(p1.getHP() - 20);
            System.out.println("Player 1 is hit - " + p1.getHP() + "HP");
            if (p2.isFaceRight()) {
              p1.accelerate(new Point2D(20, -10).multiply(kbMultiplier2));
            } else {
              p1.accelerate(new Point2D(-20, -10).multiply(kbMultiplier2));
            }
          }
        }
      }
    }
  }

  /**
   * Attaches The <tt>StageController</tt> and the two <tt>PlayerControllers</tt> to the
   * <tt>GameEngine</tt>.
   *
   * @param engine the engine to attach this controller to
   */
  @Override
  public void attach(GameEngine engine) {
    engine.addController(this); // Ping-pong pow!

    player1Controller.attach(engine);
    player2Controller.attach(engine);
  }

  /**
   * Keeps track of the pressed keys.
   *
   * @param event the event that has been fired
   */
  @Override
  public void onKeyPressed(KeyEvent event) {
    player1Controller.onKeyPressed(event);
    player2Controller.onKeyPressed(event);
    //gameClient.setKeyPressed(event); //Add key to client sendlist
  }

  /**
   * Keeps track of the released keys.
   * @param event the event that has been fired
   */
  @Override
  public void onKeyReleased(KeyEvent event) {
    player1Controller.onKeyReleased(event);
    player2Controller.onKeyReleased(event);
    //gameClient.setKeyReleased(event); //Remove key from client sendlist
  }

  /**
   * Gets the current controls from the Settings.
   */
  public void getControls() {
    ArrayList<String> impControls = FileHandler.importControls();
    for (int i = 0; i < 12; i++) {
      if (impControls.get(i).equals("CONTROL")) {
        impControls.set(i, "Ctrl");
      } else {
        impControls.set(i,
            impControls.get(i).substring(0, 1).toUpperCase() + impControls.get(i).substring(1)
                .toLowerCase());
      }
    }
    // Player controls
    player1Controller.bindKey(getKeyCode(impControls.get(0)), ACTION.JUMP);
    player1Controller.bindKey(getKeyCode(impControls.get(1)), ACTION.MOVE_LEFT);
    player1Controller.bindKey(getKeyCode(impControls.get(3)), ACTION.MOVE_RIGHT);
    player1Controller.bindKey(getKeyCode(impControls.get(2)), ACTION.FALL);
    player1Controller.bindKey(getKeyCode(impControls.get(4)), ACTION.HIT);
    player1Controller.bindKey(getKeyCode(impControls.get(5)), ACTION.KICK);

    player2Controller.bindKey(getKeyCode(impControls.get(6)), ACTION.JUMP);
    player2Controller.bindKey(getKeyCode(impControls.get(7)), ACTION.MOVE_LEFT);
    player2Controller.bindKey(getKeyCode(impControls.get(9)), ACTION.MOVE_RIGHT);
    player2Controller.bindKey(getKeyCode(impControls.get(8)), ACTION.FALL);
    player2Controller.bindKey(getKeyCode(impControls.get(10)), ACTION.HIT);
    player2Controller.bindKey(getKeyCode(impControls.get(11)), ACTION.KICK);
  }
}
