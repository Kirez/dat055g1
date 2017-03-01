package client.screen;


import client.FileHandler;
import client.GameApplication;
import java.awt.Toolkit;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by karlang on 2/28/17.
 */
public class SettingsScreen implements Screen {

  static public Button rightField1;
  ;
  static public Button leftField1;
  static public Button downField1;
  static public Button jabField1;
  static public Button jumpField1;
  static public Button kickField1;
  static public Button rightField2;
  static public Button leftField2;
  static public Button downField2;
  static public Button jabField2;
  static public Button jumpField2;
  static public Button kickField2;
  static public Button selButton;
  private GridPane layout;
  private Stage stage;
  private Scene scene;
  private Group root;
  private Label controlsLabel;
  private Label playerLabel1;
  private Label playerLabel2;
  private GameApplication owner;

  public SettingsScreen(GameApplication gameApplication) {
    owner = gameApplication;
  }

  @Override
  public void enter(Stage stage) {
    this.stage = stage;
    root = new Group();
    scene = new Scene(root);
    ArrayList<String> impControls = FileHandler.importControls();

    controlsLabel = new Label("Set controls");
    playerLabel1 = new Label("Player 1");
    playerLabel2 = new Label("Player 2");
    jumpField1 = new Button(impControls.get(0));
    jumpField1.setOnAction(this::onClick);
    jumpField1.setPrefSize(100, 50);
    rightField1 = new Button(impControls.get(3));
    rightField1.setOnAction(this::onClick);
    rightField1.setPrefSize(100, 50);
    leftField1 = new Button(impControls.get(1));
    leftField1.setOnAction(this::onClick);
    leftField1.setPrefSize(100, 50);
    downField1 = new Button(impControls.get(2));
    downField1.setOnAction(this::onClick);
    downField1.setPrefSize(100, 50);
    jabField1 = new Button(impControls.get(4));
    jabField1.setOnAction(this::onClick);
    jabField1.setPrefSize(100, 50);
    kickField1 = new Button(impControls.get(5));
    kickField1.setOnAction(this::onClick);
    kickField1.setPrefSize(100, 50);
    jumpField2 = new Button(impControls.get(6));
    jumpField2.setOnAction(this::onClick);
    jumpField2.setPrefSize(100, 50);
    rightField2 = new Button(impControls.get(9));
    rightField2.setOnAction(this::onClick);
    rightField2.setPrefSize(100, 50);
    leftField2 = new Button(impControls.get(7));
    leftField2.setOnAction(this::onClick);
    leftField2.setPrefSize(100, 50);
    downField2 = new Button(impControls.get(8));
    downField2.setOnAction(this::onClick);
    downField2.setPrefSize(100, 50);
    jabField2 = new Button(impControls.get(10));
    jabField2.setOnAction(this::onClick);
    jabField2.setPrefSize(100, 50);
    kickField2 = new Button(impControls.get(11));
    kickField2.setOnAction(this::onClick);
    kickField2.setPrefSize(100, 50);
    layout = new GridPane();
    layout.setPadding(new Insets(50, 50, 50, 50));
    layout.setHgap(30);
    layout.setVgap(20);
    layout.addColumn(1, controlsLabel);
    layout.add(new Label("JUMP"), 1, 1);
    layout.add(new Label("RIGHT"), 1, 2);
    layout.add(new Label("LEFT"), 1, 3);
    layout.add(new Label("DOWN"), 1, 4);
    layout.add(new Label("JAB"), 1, 5);
    layout.add(new Label("KICK"), 1, 6);

    layout.addColumn(2, playerLabel1);
    layout.add(jumpField1, 2, 1);
    layout.add(rightField1, 2, 2);
    layout.add(leftField1, 2, 3);
    layout.add(downField1, 2, 4);
    layout.add(jabField1, 2, 5);
    layout.add(kickField1, 2, 6);

    layout.addColumn(3, playerLabel2);
    layout.add(jumpField2, 3, 1);
    layout.add(rightField2, 3, 2);
    layout.add(leftField2, 3, 3);
    layout.add(downField2, 3, 4);
    layout.add(jabField2, 3, 5);
    layout.add(kickField2, 3, 6);

    layout.setPrefWidth(stage.getWidth());
    layout.setPrefHeight(stage.getHeight());

    root.getChildren().add(layout);

    stage.setScene(scene);
  }

  @Override
  public void exit() {

  }

  void onClick(ActionEvent event) {
    if (selButton == null) {
      Button Kn = (Button) event.getSource();
      Kn.setText("Press key");
      selButton = (Button) event.getSource();
      selButton.setOnKeyPressed(this::onKeyPressed);
      selButton.setFocusTraversable(false);
    } else {
      selButton.requestFocus();
      Toolkit.getDefaultToolkit().beep();
    }
  }

  void onKeyPressed(KeyEvent event) {
    if (selButton != null) {
      selButton.setOnAction(null);
      selButton.setText(event.getCode().toString());
      selButton.setOnAction(this::onClick);
      selButton.setFocusTraversable(true);
      selButton = null;
      event.consume();
    }

  }
}
