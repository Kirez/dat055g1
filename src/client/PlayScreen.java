package client;

import common.GameStage;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import server.GameEngine;
import server.StageController;

/*The 'play' screen handles the 'play' state*/
public class PlayScreen extends AnimationTimer implements AbstractScreen {

  private Canvas canvas;
  private GameStage gameStage;
  private GameEngine engine;
  private Thread engineThread;
  private StageController stageController;
  private Group root;
  private Scene scene;
  private Stage stage;
  private GameApplication owner;

  private GameRenderer stageRenderer;
  private GameRenderer player1Renderer;
  private GameRenderer player2Renderer;

  //  Constructor
  public PlayScreen(GameApplication gameApplication) {
    owner = gameApplication;
    gameStage = new GameStage();
    engine = new GameEngine();

    // PlayerController, stageController, StageRenderer from the same gameStage
    stageController = new StageController(gameStage);

    stageRenderer = new StageRenderer(gameStage);
    player1Renderer = new PlayerRenderer(gameStage.getPlayer1());
    player2Renderer = new PlayerRenderer(gameStage.getPlayer2());

    stageController.attach(engine);
  }

  @Override
  public void handle(long l) {
    canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    stageRenderer.render(canvas);
    player1Renderer.render(canvas);
    player2Renderer.render(canvas);
  }

  //  Runs GameEngine and renderThread.
  @Override
  public void enter(Stage stage) {
    this.stage = stage;

    // The root element in the javafx gui stack, all sub-elements attach to this
    root = new Group();
    canvas = new Canvas(stage.getWidth(), stage.getHeight());  // The render target

    root.getChildren().add(canvas);

    // The scene where the root and all its children are displayed
    scene = new Scene(root);
    scene.setOnKeyPressed(this::onKeyPressed);
    scene.setOnKeyReleased(this::onKeyReleased);

    //Lambda linking the scenes dimensions with the canvas
    scene.widthProperty().addListener(l -> canvas.setWidth(scene.getWidth()));
    scene.heightProperty().addListener(l -> canvas.setHeight(scene.getHeight()));

    stage.setScene(scene);

    engine.stop = false;

    // Create threads
    engineThread = new Thread(engine);

    // Run thread
    engineThread.start();

    //Start animation timer aka renderer
    this.start();
  }

  @Override
  public void exit() {
    // Signal for threads to stop
    engine.stop = true;

    // Sync up with threads
    try {
      System.out.print("Closing...");
      System.out.print("engineThread...");
      engineThread.join();
      System.out.println("stopped");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void onKeyPressed(KeyEvent event) {
    switch (event.getCode()) {
      case P:
        engine.togglePause();
        break;
      case F11:
        stage.setFullScreen(!stage.isFullScreen());
        break;
    }
    stageController.onKeyPressed(event);
  }

  public void onKeyReleased(KeyEvent event) {
    stageController.onKeyReleased(event);
  }

}
