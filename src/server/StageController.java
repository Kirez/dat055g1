package server;

import static javafx.scene.input.KeyCode.getKeyCode;

import client.FileHandler;
import client.GameApplication;
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
 * @version 2017-02-23
 */
public class StageController implements GameController {

  GameStage stage;
  int i;
  private PlayerController player1Controller;
  private PlayerController player2Controller;
  private GameApplication owner;


  public StageController(GameStage stage) {
    this.stage = stage;
    player1Controller = new PlayerController(stage.getPlayer1());
    player2Controller = new PlayerController(stage.getPlayer2());
    ArrayList<String> impControls = FileHandler.importControls();
    for (int i = 0; i < 12; i++) {
      if (impControls.get(i).equals("CONTROL")) {
        impControls.set(i, "Ctrl");
      } else {
        impControls.set(i,
            impControls.get(i).substring(0, 1).toUpperCase() + impControls.get(i).substring(1)
                .toLowerCase());
      }

      System.out.println("IMP: " + impControls.get(i));
    }

    // Player controls
    System.out.println(
        "Vad är det här ens? -> " + getKeyCode(impControls.get(0)) + "Och vad fab är detta? ->"
            + getKeyCode(impControls.get(7)));
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
        if (p1.getHitBox(0).getBoundsInParent().intersects(hurt.getBoundsInParent())) {
          if (!p2.stateStunned.isActive()) {
            p2.stateStunned.enterCycle(CYCLE.ACTIVE);
            p2.setHP(p2.getHP() - 10);
            System.out.println("Player 2 is hit - " + p2.getHP() + "HP");
          }
        }
      }

    }

    if (player2Controller.player.statePunching.isActive() || player2Controller.player.stateKicking
        .isActive()) {
      for (Rectangle hurt : p1.getHurtBoxes()) {
        if (p2.getHitBox(1).getBoundsInParent().intersects(hurt.getBoundsInParent())) {
          if (!p1.stateStunned.isActive()) {
            p1.stateStunned.enterCycle(CYCLE.ACTIVE);
            p1.setHP(p1.getHP() - 10);
            System.out.println("Player 1 is hit - " + p1.getHP() + "HP");
          }
        }
      }
    }
    if (p1.getHP() == 0 || p2.getHP() == 0) {
      /*if(p1.getHP() == 0){
        System.out.println("Winner p2!");
        System.exit(0);
      }
      else if(p2.getHP() == 0){
        System.out.println("Winner p1!");
        System.exit(0);
      }*/
      // stop(engine)

      owner.setActiveScreen(owner.endScreen);
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

  public void stop(GameEngine engine) {
    engine.shutdown();
  }
}
