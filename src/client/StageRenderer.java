package client;

import common.GameStage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

  //  Constructor
  public StageRenderer(GameStage gameStage) {
    this.gameStage = gameStage;
  }

  //  The stage has two PlayerRenderers who are added to the canvas. GameStage is used to get the GroundLevel.
  @Override
  public void render(Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    double scaleX = canvas.getWidth() / 16;
    double scaleY = canvas.getHeight() / 9;

    gc.save();
    gc.scale(scaleX, scaleY);

    gc.setFill(Color.web("#002b36"));
    gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
    gc.setFill(Color.web("#073642"));
    gc.fillRect(0, gameStage.getGroundLevelY(), canvas.getWidth(), canvas.getHeight());

    gc.restore();
  }
}
