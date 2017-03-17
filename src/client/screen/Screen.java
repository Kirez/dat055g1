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
 * @version 2017-03-04
 */
public interface Screen {

  /**
   * Runs when a screen becomes visible
   *
   * @param stage the stage to enter
   */
  void enter(Stage stage);

  /**
   * This method should be invoked by GameApplication before switching from this screen
   */
  void exit();
}
