package client;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public abstract class AbstractScreen extends Pane {
  public abstract void enter(Scene scene, Group root);
  public abstract void exit(Scene scene, Group root);
  public abstract void onKeyPressed(KeyEvent event);
  public abstract void onKeyReleased(KeyEvent event);
}
