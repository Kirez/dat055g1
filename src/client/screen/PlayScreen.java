package client.screen;

import client.GameApplication;
import client.GameRenderer;
import client.HealthRenderer;
import client.PlayerRenderer;
import client.StageRenderer;
import common.GameStage;
import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import server.GameEngine;
import server.StageController;

/**
 * The 'play' screen handles the 'play' state
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-28
 */
public class PlayScreen extends AnimationTimer implements Screen {

  public static IntegerProperty intProperty1 = new SimpleIntegerProperty(100);
  GridPane layout = new GridPane();
  private boolean gameOver;
  private Canvas canvas;
  private GameStage gameStage;
  private GameEngine engine;
  private Thread engineThread;
  private StageController stageController;
  private Group root;
  private Scene scene;
  private Stage stage;
  private GameApplication owner;
  private static boolean player1wins;
  final ChangeListener changeListener = new ChangeListener() {
    /**
     * Keeps track of the health of the players.
     *
     * @param observableValue
     * @param oldValue
     * @param newValue
     */
    @Override
    public void changed(ObservableValue observableValue, Object oldValue,
        Object newValue) {
      //   System.out.println(Integer.parseInt(newValue.toString()));
      if (player1HealthBar.getHealth() < player2HealthBar.getHealth()) {
        player1wins = false;
      } else {
        player1wins = true;
      }
      if (Integer.parseInt(newValue.toString()) <= 0) {
        onGameEnd();
        intProperty1.removeListener(changeListener);
      }
    }

  };
  private GameRenderer stageRenderer;
  private GameRenderer player1Renderer;
  private GameRenderer player2Renderer;
  private HealthRenderer player1HealthBar;
  private HealthRenderer player2HealthBar;
  private Object l;

  /**
   * Creates an instance of the {@code PlayScreen}.
   *
   * @param gameApplication
   */
  public PlayScreen(GameApplication gameApplication) {
    gameOver = false;

    owner = gameApplication;
    gameStage = new GameStage();
    engine = new GameEngine();

    // PlayerController, stageController, StageRenderer from the same gameStage
    stageController = new StageController(gameStage);
    stageRenderer = new StageRenderer(gameStage);
    player1Renderer = new PlayerRenderer(gameStage.getPlayer1());
    player2Renderer = new PlayerRenderer(gameStage.getPlayer2());
    player1HealthBar = new HealthRenderer(gameStage.getPlayer1(), true);
    player2HealthBar = new HealthRenderer(gameStage.getPlayer2(), false);
    stageController.attach(engine);
  }

  /**
   * Handles the rendering of the {@code PlayScreen}.
   *
   * @param l
   */
  @Override
  public void handle(long l) {
    canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    stageRenderer.render(canvas);
    player1HealthBar.render(canvas);
    player2HealthBar.render(canvas);

    player1Renderer.render(canvas);
    player2Renderer.render(canvas);
  }

  /**
   * Enters the {@code PlayScreen}.
   *
   * @param stage the stage
   */
  @Override
  public void enter(Stage stage) {
    this.stage = stage;
    gameStage = new GameStage();
    engine = new GameEngine();

    // PlayerController, stageController, StageRenderer from the same gameStage
    stageController = new StageController(gameStage);
    stageRenderer = new StageRenderer(gameStage);
    player1Renderer = new PlayerRenderer(gameStage.getPlayer1());
    player2Renderer = new PlayerRenderer(gameStage.getPlayer2());
    player1HealthBar = new HealthRenderer(gameStage.getPlayer1(), true);
    player2HealthBar = new HealthRenderer(gameStage.getPlayer2(), false);
    stageController.attach(engine);

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
    intProperty1.addListener(changeListener);

    stage.setScene(scene);
    stageController.getControls();
    engine.stop = false;

    // Create threads
    engineThread = new Thread(engine);

    // Run thread
    engineThread.start();

    //Start animation timer aka renderer
    this.start();
  }

  /**
   * Exits the game.
   */
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

  /**
   * Keeps track of pressed keys. If P is pressed the game is paused, F11 sets fullscreen and
   * ESC exits to main menu.
   *
   * @param event a KeyEvent
   */
  private void onKeyPressed(KeyEvent event) {
    switch (event.getCode()) {
      case P:
        engine.togglePause();
        break;
      case F11:
        stage.setFullScreen(!stage.isFullScreen());
        break;
      case ESCAPE:
        this.exit();
        owner.setActiveScreen(owner.mainMenuScreen);
        break;
    }
    stageController.onKeyPressed(event);
  }

  /**
   * Keeps track of released keys.
   *
   * @param event a KeyEvent.
   */
  private void onKeyReleased(KeyEvent event) {
    stageController.onKeyReleased(event);
  }

  /**
   *Exits the game and sets the screen to the {@code EndScreen}.
   */
  public void onGameEnd() {
    this.exit();
    owner.setActiveScreen(owner.endScreen);
  }

  /**
   * Returns a String with the winner.
   *
   * @return the winning player
   */
  public static String getWinner() {
    if (player1wins == true) {
      return ("Player 1");
    } else {
      return ("Player 2");
    }
  }
}