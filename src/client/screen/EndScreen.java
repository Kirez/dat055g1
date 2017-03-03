package client.screen;

import client.GameApplication;
import client.HealthRenderer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * EndScreen with the options to have a rematch, etc.
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-28
 */
public class EndScreen implements Screen {

  private GridPane layout;
  private Button menuButton;
  private Button rematchButton;
  private Button exitButton;
  private Label winner;
  private GameApplication owner;
  private String winningPlayer;
  public EndScreen(GameApplication gameApplication) {
    owner = gameApplication;
    menuButton = new Button("Main Menu");
    rematchButton = new Button("Rematch");
    exitButton = new Button("Exit");
    winner = new Label();
    winner.setFont(Font.font(48));
    layout = new GridPane();
    layout.addColumn(0, winner, rematchButton, menuButton, exitButton);
    rematchButton.setOnAction(this::onRematchButton);
    menuButton.setOnAction(this::onMenuButton);
    exitButton.setOnAction(this::onExitButton);
    layout.setAlignment(Pos.CENTER);
  }

  @Override
  public void enter(Stage stage) {
    Group root = new Group();
    root.getChildren().add(layout);
    Scene scene = new Scene(root);
    layout.setPrefSize(stage.getWidth(), stage.getHeight());
    winner.setText("Winner: " + PlayScreen.getWinner());
    stage.setScene(scene);
    scene.setOnKeyPressed(this::onKeyPressed);
  }

  void onRematchButton(ActionEvent event) {
    owner.setActiveScreen(owner.playScreen);
  }

  void onMenuButton(ActionEvent event) {
    owner.setActiveScreen(owner.mainMenuScreen);
  }
  public void onKeyPressed(KeyEvent event) {
    if (event.getCode().equals(KeyCode.ESCAPE)) {
      owner.setActiveScreen(owner.mainMenuScreen);
    }
  }
  void onExitButton(ActionEvent event) {
    Platform.exit();
  }

  @Override
  public void exit() {

  }
}
