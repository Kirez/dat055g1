package client.screen;

import client.GameApplication;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import server.GameServer;

/**
 * Screen used to host online games.
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-03-04
 */
public class CreateScreen implements Screen {

  private GridPane layout;
  private TextField portField;
  private Button createButton;
  private Label createLabel;
  private Stage stage;
  private Scene scene;
  private Group root;
  private GameApplication owner;

  /**
   * Creates an instance of the {@code CreateScreen}.
   *
   * @param gameApplication the Application is used to change between screens
   */
  public CreateScreen(GameApplication gameApplication) {
    owner = gameApplication;
  }

  /**
   * Enters the screen and creates the content of it.
   *
   * @param stage the stage of the screen
   */
  @Override
  public void enter(Stage stage) {
    this.stage = stage;
    root = new Group();
    scene = new Scene(root);
    createLabel = new Label("Host a game!");
    createLabel.setFont(Font.font(72));
    createButton = new Button("Connect");
    portField = new TextField();
    portField.setText("8822");
    layout = new GridPane();
    layout.addRow(0, createLabel);
    layout.addRow(1, portField, createButton);
    layout.setAlignment(Pos.CENTER);

    // Enables exiting to main menu
    scene.setOnKeyPressed(this::onKeyPressed);
    scene.setOnKeyReleased(this::onKeyReleased);

    layout.setPrefWidth(stage.getWidth());
    layout.setPrefHeight(stage.getHeight());

    stage.widthProperty().addListener(l -> layout.setPrefWidth(stage.getWidth()));
    stage.heightProperty().addListener(l -> layout.setPrefHeight(stage.getHeight()));

    root.getChildren().add(layout);

    stage.setScene(scene);
  }

  /**
   * This method should be invoked by GameApplication before switching from this screen
   */
  @Override
  public void exit() {

  }

  /**
   * Creates a game server.
   */
  void onCreateButton() {
    try {
      GameServer server = new GameServer(Integer.parseInt(portField.getText()));
    } catch (IOException e) {
      System.err.println("Could not create server!");
    }

  }

  /**
   * Keeps track of key presses. Returns to Main menu when ESCAPE is pressed.
   *
   * @param event a KeyEvent
   */
  public void onKeyPressed(KeyEvent event) {
    if (event.getCode().equals(KeyCode.ESCAPE)) {
      owner.setActiveScreen(owner.mainMenuScreen);
    }
  }

  /**
   * Keeps track of Key releases.
   *
   * @param event a KeyEvent
   */
  public void onKeyReleased(KeyEvent event) {

  }
}
