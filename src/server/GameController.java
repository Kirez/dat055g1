package server;

import javafx.scene.input.KeyEvent;

public interface GameController {

  void update(double delta);

  void attach(GameEngine engine);

  void onKeyPressed(KeyEvent event);

  void onKeyReleased(KeyEvent event);
}
