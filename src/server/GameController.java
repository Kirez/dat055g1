package server;

import javafx.scene.input.KeyEvent;

/**
 * Common interface for anything that is to be updated by the engine each tick
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-03-04
 */
public interface GameController {

  /**
   * Update logic
   *
   * @param delta the time difference between this and the previous tick, used for scaling
   */
  void update(double delta);

  /**
   * Adds this controller to {@code engine}
   * @param engine the engine to attach this controller to
   */
  void attach(GameEngine engine);

  /**
   * To be run when a key is pressed (deprecated)
   * @param event the event that has been fired
   */
  void onKeyPressed(KeyEvent event);

  /**
   * To be run when a key is released (deprecated)
   * @param event the event that has been fired
   */
  void onKeyReleased(KeyEvent event);
}
