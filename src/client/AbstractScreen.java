package client;

import javafx.stage.Stage;

/*Common code for each screen*/
public interface AbstractScreen {

  /*Run when a screen becomes visible*/
  void enter(Stage stage);

  /*Run when a screen becomes invisible*/
  void exit();
}
