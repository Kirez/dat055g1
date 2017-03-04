package client;

import javafx.scene.canvas.Canvas;

/**
 * Common interface/method to be implemented for all that is to be rendered to a game canvas
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-28
 */
public interface GameRenderer {

  /**
   * Renders model to target canvas.
   * @param canvas the render target
   */
  void render(Canvas canvas);
}
