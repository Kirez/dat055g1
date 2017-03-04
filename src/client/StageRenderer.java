package client;

import common.GameDefaults;
import common.GameStage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Handles rendering of the stage
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-28
 */
public class StageRenderer implements GameRenderer {

  private GameStage gameStage;

  /**
   * Creates an instance of the {@code StageRenderer}.
   * @param gameStage
   */
  public StageRenderer(GameStage gameStage) {
    this.gameStage = gameStage;
  }

  /**
   * Renders the Stage. GameStage is used to get the GroundLevel.
   */
  @Override
  public void render(Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    double scaleX = canvas.getWidth() / 16;
    double scaleY = canvas.getHeight() / 9;

    gc.save();
    gc.scale(scaleX, scaleY);

    // Background
    gc.setFill(GameDefaults.BACKGROUND_COLOR);
    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

    // Foreground
    gc.setFill(GameDefaults.FOREGROUND_COLOR);
    gc.fillRect(0, gameStage.getGroundLevelY(), canvas.getWidth(), canvas.getHeight());

    gc.restore();
  }
}
