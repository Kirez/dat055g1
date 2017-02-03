package client;

import common.GameStage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StageRenderer implements GameRenderer {

  private PlayerRenderer playerRenderer1;
  private PlayerRenderer playerRenderer2;
  private GameStage gameStage;

  //  Constructor
  public StageRenderer(GameStage gameStage) {
    this.gameStage = gameStage;
    playerRenderer1 = new PlayerRenderer(gameStage.getPlayer1());
    playerRenderer2 = new PlayerRenderer(gameStage.getPlayer2());

  }

  //  The stage has two PlayerRenderers who are added to the canvas. GameStage is used to get the GroundLevel.
  @Override
  public void render(Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();

    gc.setFill(Color.BLACK);
    gc.fillRect(0, gameStage.getGroundLevelY(), canvas.getWidth(), canvas.getHeight());

    playerRenderer1.render(canvas);
    playerRenderer2.render(canvas);
  }
}
