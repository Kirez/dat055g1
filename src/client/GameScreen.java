package client;

import common.GameStage;
import java.util.HashSet;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import server.GameEngine;
import server.StageController;

public class GameScreen extends AbstractScreen implements Runnable {

  private Canvas canvas;
  private HashSet<GameRenderer> renderers;
  private Thread renderThread;
  private GameStage gameStage;
  private GameEngine engine;
  private Thread engineThread;
  private StageController stageController;
  private boolean exitSignal;

  //  Constructor
  public GameScreen() {
    renderers = new HashSet<>();
    gameStage = new GameStage();
    engine = new GameEngine();
    canvas = new Canvas(768, 432);  // The render target

    // PlayerController, stageController, StageRenderer from the same gameStage
    stageController = new StageController(gameStage);
    StageRenderer stageRenderer = new StageRenderer(gameStage);

    renderers.add(stageRenderer);

    stageController.attach(engine);

    getChildren().add(canvas);
  }

  //Runnable method for render-thread
  @Override
  public void run() {
    while (!exitSignal) {
      canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

      long before, after, delta, sleep = 0;
      long defaultSleep = 1000 / 60;

      before = System.currentTimeMillis();

      //  Render all the GameRenderers on the game window and updates the game screen with 60fps frequency
      for (GameRenderer renderer : renderers) {
        renderer.render(canvas);
      }

      after = System.currentTimeMillis();
      delta = after - before;

      sleep = defaultSleep - delta;
      sleep = sleep > 0 ? sleep
          : 0;  // If sleep > 0, sleep = sleep. Otherwise sleep = 0. Sleep is never negative.

      try {
        Thread.sleep(sleep);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  //  Runs GameEngine and renderThread. What does Group do?
  @Override
  public void enter() {
    // Set signals to go
    exitSignal = false;
    engine.stop = false;

    // Create threads
    renderThread = new Thread(this);
    engineThread = new Thread(engine);

    // Run threads
    engineThread.start();
    renderThread.start();
  }

  @Override
  public void exit() {
    // Signal for threads to stop
    engine.stop = true;
    exitSignal = true;

    // Sync up with threads
    try {
      System.out.print("Closing...");
      System.out.print("renderThread...");
      renderThread.join();
      System.out.println("stopped");
      System.out.print("Closing...");
      System.out.print("engineThread...");
      engineThread.join();
      System.out.println("stopped");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void onKeyPressed(KeyEvent event) {
    stageController.onKeyPressed(event);
  }

  public void onKeyReleased(KeyEvent event) {
    stageController.onKeyReleased(event);
  }

}
