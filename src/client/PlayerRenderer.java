package client;

import common.GamePlayer;
import common.Box;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
    gc.fillRect(player.getHurtboxes(0).getHeadBoxX(), player.getHurtboxes(0).getHeadBoxY(), player.getHurtboxes(0).getBoxWidth(), player.getHurtboxes(0).getBoxHeight());
    gc.fillRect(player.getHurtboxes(1).getBodyBoxX(), player.getHurtboxes(1).getBodyBoxY(), player.getHurtboxes(1).getBoxWidth(), player.getHurtboxes(1).getBoxHeight());
   // gc.setStroke(Color.RED);

    //gc.beginPath();

   // gc.rect(player.getPosition().getX(), player.getPosition().getY(), player.getWidth()
     //   , player.getHeight());

//    gc.stroke();
  }
}
