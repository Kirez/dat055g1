package client;

import javafx.scene.layout.Pane;

/*Common code for each screen*/
public abstract class AbstractScreen extends Pane {

  /*Run when a screen becomes visible*/
  public abstract void enter();

  /*Run when a screen becomes invisible*/
  public abstract void exit();
}
