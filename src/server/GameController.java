package server;

import javafx.scene.input.KeyEvent;

/*Common interface for anything that is to be updated by the engine each tick*/
public interface GameController {

  void update(double delta);

  void attach(GameEngine engine);

  void onKeyPressed(KeyEvent event);

  void onKeyReleased(KeyEvent event);
}
