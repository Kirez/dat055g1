package client.screen;

import client.GameApplication;
import client.GameClient;
import java.io.IOException;
import javafx.event.ActionEvent;
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

/**
 * TODO: Add description
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-28
 */
public class JoinScreen implements Screen {

  private GridPane layout;
  private TextField ipAddressField;
  private TextField portField;
  private Button connectButton;
  private Label connectLabel;
  private Stage stage;
  private Scene scene;
  private Group root;
  private GameApplication owner;

  /**
   * Creates an instance of the <tt>JoinScreen</tt>.
   *
   * @param gameApplication
   */
  public JoinScreen(GameApplication gameApplication) {
    owner = gameApplication;
  }

  /**
   * Enters the screen and creates the content of it.
   *
   * @param stage
   */
  @Override
  public void enter(Stage stage) {
    this.stage = stage;
    root = new Group();
    scene = new Scene(root);
    connectLabel = new Label("Join game");
    connectLabel.setFont(Font.font(72));
    connectButton = new Button("Connect");
    ipAddressField = new TextField();
    ipAddressField.setPromptText("IP address");
    ipAddressField.setText("localhost");
    portField = new TextField();
    portField.setPromptText("Port");
    portField.setText("8022");
    layout = new GridPane();
    layout.addRow(0, connectLabel);
    layout.addRow(1, ipAddressField, portField, connectButton);
    layout.setAlignment(Pos.CENTER);

    // Enables exiting to main menu
    scene.setOnKeyPressed(this::onKeyPressed);
    scene.setOnKeyReleased(this::onKeyReleased);

    layout.setPrefWidth(stage.getWidth());
    layout.setPrefHeight(stage.getHeight());

    stage.widthProperty().addListener(l -> layout.setPrefWidth(stage.getWidth()));
    stage.heightProperty().addListener(l -> layout.setPrefHeight(stage.getHeight()));

    connectButton.setOnAction(this::onConnectButton);

    root.getChildren().add(layout);

    stage.setScene(scene);
  }

  /**
   * Exits the Screen.
   */
  @Override
  public void exit() {

  }

  /**
   * Connects to a network.
   *
   * @param event an ActionEvent from a button click.
   */
  void onConnectButton(ActionEvent event) {
    try {
      owner.networkPlayScreen.setClient(
          new GameClient(ipAddressField.getText(), Integer.parseInt(portField.getText())));
      owner.setActiveScreen(owner.networkPlayScreen);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Keeps track of the key presses. If ESC is pressed it returns to the MainMenuScreen.
   *
   * @param event a KeyEvent.
   */
  public void onKeyPressed(KeyEvent event) {
    if (event.getCode().equals(KeyCode.ESCAPE)) {
      owner.setActiveScreen(owner.mainMenuScreen);
    }
  }

  /**
   * Keeps track of Key Releases.
   *
   * @param event
   */
  public void onKeyReleased(KeyEvent event) {

  }
}
