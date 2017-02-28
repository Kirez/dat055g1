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
 * @version 2017-02-28
 */
public interface GameController {

  void update(double delta);

  void attach(GameEngine engine);

  void onKeyPressed(KeyEvent event);

  void onKeyReleased(KeyEvent event);
}
