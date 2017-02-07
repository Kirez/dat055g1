package client;

import common.GamePlayer;
import common.Box;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import server.PlayerController;

public class PlayerRenderer implements GameRenderer {

  private GamePlayer player;

  public PlayerRenderer(GamePlayer player) {
    this.player = player;
  }

  @Override
  public void render(Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.RED);
    gc.fillOval(player.getPosition().getX(), player.getPosition().getY(), 16, 16);
    gc.fillRect(player.getHurtboxes(0).getHeadBoxX(), player.getHurtboxes(0).getHeadBoxY(),
        player.getHurtboxes(0).getBoxWidth(), player.getHurtboxes(0).getBoxHeight());
    gc.fillRect(player.getHurtboxes(1).getBodyBoxX(), player.getHurtboxes(1).getBodyBoxY(),
        player.getHurtboxes(1).getBoxWidth(), player.getHurtboxes(1).getBoxHeight());

 /*   if (PlayerController.hit == true) {
      gc.setFill(Color.YELLOW);
      gc.fillRect(player.getHitboxes(0).getPunchX(), player.getHitboxes(0).getPunchY(),
          player.getHitboxes(0).getBoxWidth(), player.getHitboxes(0).getBoxHeight());
    } else
      gc.setFill(Color.WHITE);
      gc.fillRect(player.getHitboxes(0).getPunchX(), player.getHitboxes(0).getPunchY(),
          player.getHitboxes(0).getBoxWidth(), player.getHitboxes(0).getBoxHeight());
    }*/
  }
}

