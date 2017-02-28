package client.screen;

import client.GameApplication;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EndScreen implements Screen{

  private GameApplication owner;

  public EndScreen(GameApplication gameApplication) {
    owner = gameApplication;
  }

  @Override
  public void enter(Stage stage) {
    Scene scene = new Scene(new Group());
    stage.setScene(scene);
  }

  @Override
  public void exit() {

  }
}
