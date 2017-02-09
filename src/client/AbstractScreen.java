package client;

import javafx.scene.layout.Pane;

public abstract class AbstractScreen extends Pane {

  public abstract void enter();

  public abstract void exit();
}
