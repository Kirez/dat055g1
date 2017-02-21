package client;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ConnectScreen implements AbstractScreen {

  private GridPane layout;
  private TextField ipAddressField;
  private Button connectButton;
  private Label connectLabel;
  private Stage stage;
  private Scene scene;
  private Group root;
  private GameApplication owner;

  public ConnectScreen(GameApplication gameApplication) {
    owner = gameApplication;
  }

  @Override
  public void enter(Stage stage) {
    this.stage = stage;
    root = new Group();
    scene = new Scene(root);
    connectLabel = new Label("Play over Network!");
    connectLabel.setFont(Font.font(72));
    connectButton = new Button("Connect");
    ipAddressField = new TextField();
    ipAddressField.setPromptText("ip address");
    layout = new GridPane();
    layout.addRow(0, connectLabel);
    layout.addRow(1, ipAddressField, connectButton);
    layout.setAlignment(Pos.CENTER);

    layout.setPrefWidth(stage.getWidth());
    layout.setPrefHeight(stage.getHeight());

    stage.widthProperty().addListener(l -> layout.setPrefWidth(stage.getWidth()));
    stage.heightProperty().addListener(l -> layout.setPrefHeight(stage.getHeight()));

    root.getChildren().add(layout);

    stage.setScene(scene);
  }

  @Override
  public void exit() {

  }
}
