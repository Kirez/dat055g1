package client.screen;

import client.GameApplication;
import common.GameDefaults;
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
 * @version 2017-03-04
 */
public class MainMenuScreen implements Screen {

  private GridPane layout;
  private Button localGameButton;
  private Button settingsButton;
  private Button joinButton;
  private Button exitButton;
  private Label title;
  private GameApplication owner;

  /**
   * Creates an instance of the {@code MainMenuScreen}.
   *
   * @param gameApplication is used to change between screens
   */

  public MainMenuScreen(GameApplication gameApplication) {
    owner = gameApplication;

    localGameButton = new Button(GameDefaults.LOCAL_GAME_BUTTON_TEXT);
    joinButton = new Button(GameDefaults.JOIN_GAME_BUTTON_TEXT);
    settingsButton = new Button(GameDefaults.SETTINGS_BUTTON_TEXT);
    exitButton = new Button(GameDefaults.EXIT_BUTTON_TEXT);

    title = new Label(GameDefaults.TITLE);
    title.setFont(Font.font(GameDefaults.BIG_FONT_SIZE));

    layout = new GridPane();
    layout.addColumn(0, title, localGameButton, joinButton, settingsButton, exitButton);

    localGameButton.setOnAction(this::onLocalGameButton);
    exitButton.setOnAction(this::onExitButton);
    joinButton.setOnAction(this::onJoinGameButton);
    settingsButton.setOnAction(this::onSettingsButton);

    layout.setAlignment(Pos.CENTER);
  }

  @Override
  public void enter(Stage stage) {
    Group root = new Group();
    root.getChildren().add(layout);
    Scene scene = new Scene(root);

    layout.setPrefSize(stage.getWidth(), stage.getHeight());
    stage.widthProperty().addListener(l -> layout.setPrefWidth(stage.getWidth()));
    stage.heightProperty().addListener(l -> layout.setPrefHeight(stage.getHeight()));

    stage.setScene(scene);
  }

  /**
   * Exits the game.
   *
   * @param event an ActionEvent from clicking the exit button.
   */
  void onExitButton(ActionEvent event) {
    Platform.exit();
  }

  /**
   * Sets the active screen to the PlayScreen.
   *
   * @param event an ActionEvent from clicking a button.
   */
  void onLocalGameButton(ActionEvent event) {
    owner.setActiveScreen(owner.playScreen);
  }

  /**
   * Sets the active screen to the JoinScreen.
   *
   * @param event an ActionEvent from clicking a button.
   */
  void onJoinGameButton(ActionEvent event) {
    owner.setActiveScreen(owner.joinScreen);
  }

  /**
   * Sets the Active to the SettingsScreen.
   *
   * @param event an ActionEvent from clicking a button.
   */
  void onSettingsButton(ActionEvent event) {
    owner.setActiveScreen(owner.settingsScreen);
  }

  /**
   * This method should be invoked by GameApplication before switching from this screen
   */
  @Override
  public void exit() {

  }
}
