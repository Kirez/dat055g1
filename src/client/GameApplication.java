package client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApplication extends Application {

  private Group root;
  private Scene scene;

  private GameScreen gameScreen;

  public static void main(String args[]) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("The MVC Game");

    root = new Group();
    scene = new Scene(root);

    gameScreen = new GameScreen();
    gameScreen.enter(scene, root);

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
