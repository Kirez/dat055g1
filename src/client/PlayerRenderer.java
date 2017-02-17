package client;

import common.GamePlayer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/* Handles rendering of a player by reference to a canvas
*  Does not render directly the render method is run from somewhere else
*  In this case by the StageRenderer */
public class PlayerRenderer implements GameRenderer {

  public Color color;
  private GamePlayer player;

  //  Constructor
  public PlayerRenderer(GamePlayer player) {
    this.player = player;
    color = Color.RED;
  }

  //  Renders the player (which is now a 16x16 circle(oval) inside a rectangle) on the canvas
  @Override
  public void render(Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setFill(color);

    if (player.stateStunned.isActive()) {
      gc.setFill(Color.MAGENTA);
    } else {
      gc.setFill(Color.RED);
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

    gc.setStroke(color); // Outline color

    gc.beginPath();

    gc.rect(player.getPosition().getX(), player.getPosition().getY(), player.getWidth()
        , player.getHeight());

    gc.stroke();
  }
}

