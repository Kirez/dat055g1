package server;

import common.GamePlayer;
import common.GameStage;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import server.PlayerController.ACTION;

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
    player1Controller.bindKey(KeyCode.A, ACTION.MOVE_LEFT);
    player1Controller.bindKey(KeyCode.W, ACTION.JUMP);
    player1Controller.bindKey(KeyCode.D, ACTION.MOVE_RIGHT);
    player1Controller.bindKey(KeyCode.S, ACTION.FALL);
    player1Controller.bindKey(KeyCode.SPACE, ACTION.HIT);

    player2Controller.bindKey(KeyCode.LEFT, ACTION.MOVE_LEFT);
    player2Controller.bindKey(KeyCode.UP, ACTION.JUMP);
    player2Controller.bindKey(KeyCode.RIGHT, ACTION.MOVE_RIGHT);
    player2Controller.bindKey(KeyCode.DOWN, ACTION.FALL);
    player2Controller.bindKey(KeyCode.ENTER, ACTION.HIT);

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

    for (Rectangle B: p1.HurtBoxes) {
      B.setX(p1.getPosition().getX() + p1.intX[i]);
      B.setY(p1.getPosition().getY() + p1.intY[i]);
      i++;
    }

    for (Rectangle B: p1.HitBoxes) {
      B.setY(p1.getPosition().getY() + p1.intY[2]);
      B.setX(p1.getPosition().getX() + p1.intX[2]);

    }
    i=0;
    for (Rectangle B: p2.HurtBoxes) {
      B.setX(p2.getPosition().getX() + p1.intX[i]);
      B.setY(p2.getPosition().getY() + p1.intY[i]);
    i++;
    }
    i=0;
    for (Rectangle B: p2.HitBoxes) {
      B.setY(p2.getPosition().getY() + p1.intY[2]);
      B.setX(p2.getPosition().getX() - p1.intX[2]);
    }
    for (Rectangle hub : p1.HurtBoxes) {
      Bounds bound1 = hub.getBoundsInParent();
      for (Rectangle hib: p2.HitBoxes) {
        Bounds bound2 = hib.getBoundsInParent();
        if (bound1.intersects(bound2)) {
          System.out.println("Rickity rekt");
        }
      }
    }
    for (Rectangle hub : p2.HurtBoxes) {
      Bounds bound1 = hub.getBoundsInParent();
      for (Rectangle hib: p1.HitBoxes) {
        Bounds bound2 = hib.getBoundsInParent();
        if (bound1.intersects(bound2)) {
          System.out.println("Mortily Morted");
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
