package client;

import common.GameStage;
import java.util.HashSet;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import server.GameEngine;
import server.PlayerController;
import server.StageController;

public class GameScreen extends AbstractScreen {
  private Canvas canvas;
  private HashSet<GameRenderer> renderers;
  private Thread renderThread;
  private GameStage gameStage;
  private GameEngine engine;
  private Thread engineThread;
  private PlayerController playerController1;
  private StageController stageController;

  public GameScreen() {
    renderers = new HashSet<>();
    gameStage = new GameStage();
    engine = new GameEngine();
    engineThread = new Thread(engine);
    canvas = new Canvas(768, 432);

    playerController1 = new PlayerController(gameStage.getPlayer1());
    stageController = new StageController(gameStage);
    StageRenderer stageRenderer = new StageRenderer(gameStage);

    renderers.add(stageRenderer);

    renderThread = new Thread(() -> {
      while (true) {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        long before, after, delta, sleep = 0;
        long defaultSleep = 1000 / 60;

        before = System.currentTimeMillis();

        for (GameRenderer renderer : renderers) {
          renderer.render(canvas);
        }

        after = System.currentTimeMillis();
        delta = after - before;

        sleep = defaultSleep - delta;
        sleep = sleep > 0 ? sleep : 0;

        try {
          Thread.sleep(sleep);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    engine.addController(playerController1);
    engine.addController(stageController);

    getChildren().add(canvas);
  }

  public void enter(Scene scene, Group root) {
    root.getChildren().add(this);
    engineThread.start();
    renderThread.start();

    scene.setOnKeyPressed(this::onKeyPressed);
    scene.setOnKeyReleased(this::onKeyReleased);
  }

  public void exit(Scene scene, Group root) {

  }

  @Override
  public void onKeyPressed(KeyEvent event) {
    boolean state = true;

    switch (event.getCode()) {
      case LEFT:
        playerController1.leftDown = state;
        break;
      case RIGHT:
        playerController1.rightDown = state;
        break;
      case UP:
        playerController1.upDown = state;
        break;
      case P:
        engine.setPause(state);
        break;
    }
  }

  @Override
  public void onKeyReleased(KeyEvent event) {
    boolean state = false;

    switch (event.getCode()) {
      case LEFT:
        playerController1.leftDown = state;
        break;
      case RIGHT:
        playerController1.rightDown = state;
        break;
      case UP:
        playerController1.upDown = state;
        break;
      case P:
        engine.setPause(state);
        break;
    }
  }
}
