package client.screen;

import client.GameApplication;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import server.GameServer;

public class CreateScreen implements Screen {

  private GridPane layout;
  private TextField portField;
  private Button createButton;
  private Label createLabel;
  private Stage stage;
  private Scene scene;
  private Group root;
  private GameApplication owner;

  public CreateScreen(GameApplication gameApplication) {
    owner = gameApplication;
  }

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

    layout.setPrefWidth(stage.getWidth());
    layout.setPrefHeight(stage.getHeight());

    stage.widthProperty().addListener(l -> layout.setPrefWidth(stage.getWidth()));
    stage.heightProperty().addListener(l -> layout.setPrefHeight(stage.getHeight()));

    root.getChildren().add(layout);

    stage.setScene(scene);
  }

  void onCreateButton() {
    try {
      GameServer server = new GameServer(Integer.parseInt(portField.getText()));
    } catch (IOException e) {
      System.err.println("Could not create server!");
    }

  }

  @Override
  public void exit() {

  }
}
