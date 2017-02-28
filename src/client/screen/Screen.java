package client.screen;

import javafx.stage.Stage;

/**
 * Common code for each screen
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-23
 */

public interface Screen {

  /*Run when a screen becomes visible*/
  void enter(Stage stage);

  /*Run when a screen becomes invisible*/
  void exit();
}
