package client.screen;

import client.GameApplication;
import client.GameClient;
import client.GameRenderer;
import client.HealthRenderer;
import client.PlayerRenderer;
import client.StageRenderer;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

/**
 * The Screen used to play over networks.
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-03-04
 */
public class NetworkPlayScreen extends AnimationTimer implements Screen {

  private Canvas canvas;
  private Group root;
  private Scene scene;
  private Stage stage;
  private GameApplication owner;

  private GameRenderer stageRenderer;
  private GameRenderer player1Renderer;
  private GameRenderer player2Renderer;
  private HealthRenderer player1HealthBar;
  private HealthRenderer player2HealthBar;

  private GameClient client;
  private Thread clientThread;

  /**
   * Creates an instance of NetworkPlayScreen
   *
   * @param gameApplication the owner application
   */
  public NetworkPlayScreen(GameApplication gameApplication) {
    this.owner = gameApplication;

    canvas = new Canvas();
  }

  /**
   * Renders the game.
   *
   * @param l the current time in nanoseconds
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
   * Initializes the screen by creating instances of the renderers etc.
   */
  @Override
  public void enter(Stage stage) {
    if (client == null) {
      System.err.println("Game client was not set before entering screen");
      System.exit(-1);
    }
    this.stage = stage;

    stageRenderer = new StageRenderer(client.getGameStage());
    player1Renderer = new PlayerRenderer(client.getPlayer1());
    player2Renderer = new PlayerRenderer(client.getPlayer2());
    player1HealthBar = new HealthRenderer(client.getPlayer1(), true);
    player2HealthBar = new HealthRenderer(client.getPlayer2(), false);

    // The root element in the javafx gui stack, all sub-elements attach to this
    root = new Group();
    canvas = new Canvas(stage.getWidth(), stage.getHeight());  // The render target

    root.getChildren().add(canvas);

    // The scene where the root and all its children are displayed
    scene = new Scene(root);

    scene.setOnKeyPressed(client::onKeyPressed);
    scene.setOnKeyReleased(client::onKeyReleased);

    //Lambda linking the scene's dimensions with the canvas
    scene.widthProperty().addListener(l -> canvas.setWidth(scene.getWidth()));
    scene.heightProperty().addListener(l -> canvas.setHeight(scene.getHeight()));

    stage.setScene(scene);

    //Start animation timer aka renderer
    this.start();

    clientThread = new Thread(client);
    clientThread.start();
  }

  /**
   * Does nothing.
   */
  @Override
  public void exit() {

  }

  /**
   * Sets the Client.
   *
   * @param client the client to set
   */
  public void setClient(GameClient client) {
    this.client = client;
  }
}
