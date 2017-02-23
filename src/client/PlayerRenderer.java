package client;

import common.GamePlayer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Handles rendering of a player by reference to a canvas.
 * <p>
 * Does not render directly the render method is run from somewhere else.
 * In this case by the StageRenderer
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-20
 */
public class PlayerRenderer implements GameRenderer {

  private GamePlayer player;

  //  Constructor
  public PlayerRenderer(GamePlayer player) {
    this.player = player;
  }

  //  Renders the player (which is now a 16x16 circle(oval) inside a rectangle) on the canvas
  @Override
  public void render(Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();

    double scaleX = canvas.getWidth() / 16;
    double scaleY = canvas.getHeight() / 9;

    gc.save();
    gc.scale(scaleX, scaleY);
    gc.setLineWidth(gc.getLineWidth() / scaleX);

    gc.setFill(player.getColor());

    if (player.stateStunned.isActive()) {
      gc.setFill(Color.MAGENTA);
    } else {
      gc.setFill(player.getColor());
    }

    for (Rectangle B : player.getHurtBoxes()) {
      gc.fillRect(B.getX(), B.getY(), B.getWidth(), B.getHeight());
    }

    if (player.statePunching.isActive()) {
      gc.setFill(Color.MAGENTA);

      for (Rectangle R : player.getHitBoxes()) {
        gc.fillRect(R.getX(), R.getY(), R.getWidth(), R.getHeight());
      }
    }

    gc.setStroke(player.getColor()); // Outline color

    gc.beginPath();

    gc.rect(player.getPosition().getX(), player.getPosition().getY(), player.getWidth()
        , player.getHeight());

    gc.stroke();
    gc.setLineWidth(gc.getLineWidth() * scaleX);

    gc.restore();
  }
}

