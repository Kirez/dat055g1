package server;

import common.GamePlayer;
import common.GamePlayer.STATE;
import common.GameStage;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

/*Handles the state of the stage each tick and each player controller within it*/
public class StageController implements GameController {

  GameStage stage;

  private PlayerController player1Controller;
  private PlayerController player2Controller;
  private GameClient gameClient;
  int i;

  public StageController(GameStage stage) {
    this.stage = stage;
    player1Controller = new PlayerController(stage.getPlayer1());
    player2Controller = new PlayerController(stage.getPlayer2());

    // Player controls
    player1Controller.bindKey(KeyCode.A, STATE.MOVE_LEFT);
    player1Controller.bindKey(KeyCode.W, STATE.JUMPING);
    player1Controller.bindKey(KeyCode.D, STATE.MOVE_RIGHT);
    player1Controller.bindKey(KeyCode.S, STATE.FALLING);
    player1Controller.bindKey(KeyCode.SPACE, STATE.HITTING);

    player2Controller.bindKey(KeyCode.LEFT, STATE.MOVE_LEFT);
    player2Controller.bindKey(KeyCode.UP, STATE.JUMPING);
    player2Controller.bindKey(KeyCode.RIGHT, STATE.MOVE_RIGHT);
    player2Controller.bindKey(KeyCode.DOWN, STATE.FALLING);
    player2Controller.bindKey(KeyCode.ENTER, STATE.HITTING);

    //The client side of server-client added to the stagecontroller in order to get and send information about the stage
    gameClient = new GameClient();
    gameClient.start();
    int i = 0;
  }

  @Override
  public void update(double delta) {
    int gravity = 800;

    GamePlayer p1 = stage.getPlayer1();
    GamePlayer p2 = stage.getPlayer2();
    Point2D p1f = p1.getPosition().add(p1.getWidth() / 2, p1.getHeight());
    Point2D p2f = p2.getPosition().add(p2.getWidth() / 2, p2.getHeight());

    if (p1f.getY() < stage.getGroundLevelY()) {
      p1.accelerate(new Point2D(0, gravity * delta));
    }
    else if (!p1.isOnGround()) {
      p1.setOnGround(true);
      p1.setPosition(new Point2D(p1.getPosition().getX()
          ,stage.getGroundLevelY() - p1.getHeight()));
      p1.setVelocity(new Point2D(p1.getVelocity().getX(), 0));
    }

    if (p2f.getY() < stage.getGroundLevelY()) {
      p2.accelerate(new Point2D(0, gravity * delta));
    }
    else if (!p2.isOnGround()) {
      p2.setOnGround(true);
      p2.setPosition(new Point2D(p2.getPosition().getX()
          ,stage.getGroundLevelY() - p2.getHeight()));
      p2.setVelocity(new Point2D(p2.getVelocity().getX(), 0));
    }

    for (Rectangle hit : p1.getHitBoxes()) {
      for (Rectangle hurt : p2.getHurtBoxes()) {
        if (hit.getBoundsInParent().intersects(hurt.getBoundsInParent())) {
          if (!p2.isOnCooldown(STATE.STUNNED)) {
            p2.setCooldown(STATE.STUNNED, GamePlayer.PUNCH_DURATION);
            System.out.println("Player 2 is hit");
          }
        }
      }
    }

    for (Rectangle hit : p2.getHitBoxes()) {
      for (Rectangle hurt : p1.getHurtBoxes()) {
        if (hit.getBoundsInParent().intersects(hurt.getBoundsInParent())) {
          if (!p1.isOnCooldown(STATE.STUNNED)) {
            p1.setCooldown(STATE.STUNNED, GamePlayer.PUNCH_DURATION);
            System.out.println("Player 1 is hit");
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
    gameClient.setKeyPressed(event); //Add key to client sendlist
  }

  @Override
  public void onKeyReleased(KeyEvent event) {
    player1Controller.onKeyReleased(event);
    player2Controller.onKeyReleased(event);
    gameClient.setKeyReleased(event); //Remove key from client sendlist
  }
}
