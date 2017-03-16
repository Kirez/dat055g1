package client;

import common.GameDefaults;
import common.GamePlayer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
 * @version 2017-03-16
 */
public class PlayerRenderer implements GameRenderer {

  private GamePlayer player;

  /**
   * Crates an instance of {@code PlayerRenderer}.
   *
   * @param player The player being rendered
   */
  public PlayerRenderer(GamePlayer player) {
    this.player = player;
  }

  /**
   * Renders the player and its accompanying attributes.
   *
   * @param canvas javafx canvas to render to
   */
  @Override
  public void render(Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();

    double scaleX = canvas.getWidth() / 16;
    double scaleY = canvas.getHeight() / 9;

    gc.save();
    gc.scale(scaleX, scaleY);
    gc.setLineWidth(gc.getLineWidth() / scaleX);

    gc.setFill(player.getColor());

    this.renderPlayer(gc);

    gc.setLineWidth(gc.getLineWidth() * scaleX);
    gc.restore();
  }

  private void renderPlayer(GraphicsContext gc) {
    // TODO: Maybe do case analysis before invoking functions?
    renderStunned(gc);
    renderHurtBoxes(gc);
    renderSpoolUp(gc);
    renderHitBoxes(gc);
    renderCoolDown(gc);
  }

  private void renderStunned(GraphicsContext gc) {
    if (player.stateStunned.isActive()) {
      gc.setFill(GameDefaults.HITSTUN_COLOR);
    } else {
      gc.setFill(player.getColor());
    }
  }

  private void renderHurtBoxes(GraphicsContext gc) {
    for (Rectangle B : player.getHurtBoxes()) {
      gc.fillRect(B.getX(), B.getY(), B.getWidth(), B.getHeight());
    }
  }

  private void renderSpoolUp(GraphicsContext gc) {
    // PUNCH
    if (player.statePunching.isSpoolingUp()) {
      gc.setFill(player.getColor());
      if (player.isFaceRight()) {
        gc.fillRect(
            player.getPosition().getX() + 0.5,
            player.getHitBox(0).getY(),
            ((player.getHitBox(0).getWidth()
                + player.getHitBox(0).getX())
                - player.getPosition().getX()
                - 0.505) * 0.33,
            player.getHitBox(0).getHeight());
      } else {
        gc.fillRect(
            player.getPosition().getX() + (0.5 - player.getHitBox(0).getWidth()) / 2,
            player.getHitBox(0).getY(),
            ((-player.getHitBox(0).getWidth()
                - player.getHitBox(0).getX())
                + player.getPosition().getX() + 0.505) * 0.33,
            player.getHitBox(0).getHeight());
      }
    }

    // KICK
    if (player.stateKicking.isSpoolingUp()) {
      if (player.isFaceRight()) {
        gc.setFill(GameDefaults.BACKGROUND_COLOR);
        gc.fillRect(
            player.getPosition().getX() + 0.52,
            player.getPosition().getY() + 1.5,
            0.25,
            0.505);
        gc.setFill(player.getColor());
        gc.fillRect(
            player.getPosition().getX() + 0.52,
            player.getPosition().getY() + 1.5,
            1.5 / 2,
            0.2);
      } else {
        gc.setFill(GameDefaults.BACKGROUND_COLOR);
        gc.fillRect(
            player.getPosition().getX() + 0.27,
            player.getPosition().getY() + 1.5,
            0.25,
            0.505);
        gc.setFill(player.getColor());
        gc.fillRect(
            player.getPosition().getX() - 0.3,
            player.getHitBox(1).getY(),
            1.5 / 2,
            0.2);
      }
    }
  }

  private void renderHitBoxes(GraphicsContext gc) {
    // PUNCH
    if (player.statePunching.isActive()) {
      gc.setFill(GameDefaults.HITBOX_COLOR);
      gc.fillRect(
          player.getHitBox(0).getX(),
          player.getHitBox(0).getY(),
          player.getHitBox(0).getWidth(),
          player.getHitBox(0).getHeight());
      gc.setFill(player.getColor());
      if (player.isFaceRight()) {
        gc.fillRect(
            player.getPosition().getX() + 0.5,
            player.getHitBox(0).getY(),
            ((player.getHitBox(0).getX())
                - player.getPosition().getX()
                - 0.505),
            player.getHitBox(0).getHeight());
      } else {
        gc.fillRect(
            player.getHitBox(0).getX() + player.getHitBox(0).getWidth(),
            player.getHitBox(0).getY(),
            (-player.getHitBox(0).getWidth()
                - player.getHitBox(0).getX())
                + player.getPosition().getX()
                + 0.5,
            player.getHitBox(0).getHeight());
      }
    }

    // KICK
    if (player.stateKicking.isActive()) {
      gc.setFill(GameDefaults.HITBOX_COLOR);
      gc.fillRect(
          player.getHitBox(1).getX(),
          player.getHitBox(1).getY(),
          player.getHitBox(1).getWidth(),
          player.getHitBox(1).getHeight());
      gc.setFill(GameDefaults.BACKGROUND_COLOR);
      if (player.isFaceRight()) {
        gc.fillRect(
            player.getPosition().getX() + 0.52,
            player.getPosition().getY() + 1.5,
            0.25,
            0.505);
        gc.setFill(player.getColor());
        gc.fillRect(
            player.getPosition().getX() + 0.52,
            player.getPosition().getY() + 1.5,
            player.getHitBox(1).getX()
                + player.getHitBox(1).getWidth()
                - player.getPosition().getX()
                - 0.52,
            0.2);
      } else {
        gc.fillRect(
            player.getPosition().getX() + 0.27,
            player.getPosition().getY() + 1.5,
            0.25,
            0.505);
        gc.setFill(player.getColor());
        gc.fillRect(
            player.getHitBox(1).getX(),
            player.getHitBox(1).getY(),
            player.getPosition().getX() - player.getHitBox(1).getX() + 0.45,
            0.2);
      }
    }
  }

  private void renderCoolDown(GraphicsContext gc) {
    // PUNCH
    if (player.statePunching.isOnCoolDown()) {
      gc.setFill(player.getColor());
      if (player.isFaceRight()) {
        gc.fillRect(
            player.getPosition().getX() + 0.5,
            player.getHitBox(0).getY(),
            ((player.getHitBox(0).getWidth()
                + player.getHitBox(0).getX())
                - player.getPosition().getX() - 0.505) * 0.66,
            player.getHitBox(0).getHeight());
      } else {
        gc.fillRect(
            player.getPosition().getX() + (0.5 - player.getHitBox(0).getWidth()) / 2,
            player.getHitBox(0).getY(),
            ((-player.getHitBox(0).getWidth()
                - player.getHitBox(0).getX())
                + player.getPosition().getX() + 0.505) * 0.66,
            player.getHitBox(0).getHeight());
      }
    }

    // KICK
    if (player.stateKicking.isOnCoolDown()) {
      if (player.isFaceRight()) {
        gc.setFill(GameDefaults.BACKGROUND_COLOR);
        gc.fillRect(
            player.getPosition().getX() + 0.52,
            player.getPosition().getY() + 1.5,
            0.25,
            0.505);
        gc.setFill(player.getColor());
        gc.fillRect(
            player.getPosition().getX() + 0.52,
            player.getPosition().getY() + 1.5,
            1.5 / 2,
            0.2);
      } else {
        gc.setFill(GameDefaults.BACKGROUND_COLOR);
        gc.fillRect(
            player.getPosition().getX() + 0.27,
            player.getPosition().getY() + 1.5,
            0.25,
            0.505);
        gc.setFill(player.getColor());
        gc.fillRect(
            player.getPosition().getX() - 0.3,
            player.getHitBox(1).getY(),
            1.5 / 2,
            0.2);
      }
    }
  }
}
