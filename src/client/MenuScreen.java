package client;

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

/*Main menu where you press play, quit, options, etc*/
public class MenuScreen implements AbstractScreen {

  private GridPane layout;
  private Button playButton;
  private Button optionsButton;
  private Button exitButton;
  private Label title;
  private GameApplication owner;

  public MenuScreen(GameApplication gameApplication) {
    owner = gameApplication;
    playButton = new Button("Play");
    optionsButton = new Button("Options");
    exitButton = new Button("Exit");
    title = new Label("TimmyFightGoGo");
    title.setFont(Font.font(72));
    layout = new GridPane();
    layout.addColumn(0, title, playButton, optionsButton, exitButton);
    playButton.setOnAction(this::onPlayButton);
    exitButton.setOnAction(this::onExitButton);
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

  @Override
  public void exit() {

  }
}
