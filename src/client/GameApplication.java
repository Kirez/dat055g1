package client;

import common.GameStage;
import java.util.HashSet;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import server.GameEngine;
import server.PlayerController;
import server.StageController;

public class GameApplication extends Application {

  private Canvas canvas;
  private Group root;
  private Scene scene;

  private HashSet<GameRenderer> renderers;

  public static void main(String args[]) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("The MVC Game");

    root = new Group();
    scene = new Scene(root);
    canvas = new Canvas(768, 432);
    GameStage stage = new GameStage();
    renderers = new HashSet<>();

    PlayerController playerController1 = new PlayerController(stage.getPlayer1());
    StageController stageController = new StageController(stage);
    StageRenderer stageRenderer = new StageRenderer(stage);

    renderers.add(stageRenderer);

    Thread renderThread = new Thread(() -> {
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

    root.getChildren().add(canvas);

    scene.setOnKeyPressed(eh -> {
      boolean state = true;

      switch (eh.getCode()) {
        case LEFT:
          playerController1.leftDown = state;
          break;
        case RIGHT:
          playerController1.rightDown = state;
          break;
        case UP:
          playerController1.upDown = state;
          break;
      }
    });

    scene.setOnKeyReleased(eh -> {
      boolean state = false;

      switch (eh.getCode()) {
        case LEFT:
          playerController1.leftDown = state;
          break;
        case RIGHT:
          playerController1.rightDown = state;
          break;
        case UP:
          playerController1.upDown = state;
          break;
      }
    });

    GameEngine engine = new GameEngine();
    engine.addController(playerController1);
    engine.addController(stageController);

    Thread engineThread = new Thread(engine);

    engineThread.start();
    renderThread.start();

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
