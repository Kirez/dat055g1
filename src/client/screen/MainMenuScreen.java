package client.screen;

import client.GameApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Main menu where you press play, quit, options, etc
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-23
 */
public class MainMenuScreen implements Screen {

  private GridPane layout;
  private Button createGameButton;
  private Button settingsButton;
  private Button joinButton;
  private Button exitButton;
  private Label title;
  private GameApplication owner;

  public MainMenuScreen(GameApplication gameApplication) {
    owner = gameApplication;
    createGameButton = new Button("Create");
    joinButton = new Button("Join");
    settingsButton = new Button("Settings");
    exitButton = new Button("Exit");
    title = new Label("TimmyFightGoGo");
    title.setFont(Font.font(72));
    layout = new GridPane();
    layout.addColumn(0, title, createGameButton, joinButton, settingsButton, exitButton);
    createGameButton.setOnAction(this::onPlayButton);
    exitButton.setOnAction(this::onExitButton);
    joinButton.setOnAction(this::onMultiplayerButton);
    settingsButton.setOnAction(this::onSettingsButton);
    exit();
    layout.setAlignment(Pos.CENTER);
  }

  @Override
  public void enter(Stage stage) {
    Group root = new Group();
    root.getChildren().add(layout);
    Scene scene = new Scene(root);

    layout.setPrefSize(stage.getWidth(), stage.getHeight());

    stage.setScene(scene);
  }

  void onExitButton(ActionEvent event) {
    Platform.exit();
  }

  void onPlayButton(ActionEvent event) {
    owner.setActiveScreen(owner.playScreen);
  }

  void onMultiplayerButton(ActionEvent event) {
    owner.setActiveScreen(owner.joinScreen);
  }

  void onSettingsButton(ActionEvent event) {
    owner.setActiveScreen(owner.settingsScreen);
  }

  @Override
  public void exit() {

  }
}
