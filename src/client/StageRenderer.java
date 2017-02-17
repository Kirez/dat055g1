package client;

import common.GameStage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/* Handles rendering of the stage and all that is in it
*  In this case it calls the respective renderer for each player
*  This in turn is asked to render by the PlayScreen's render thread */
public class StageRenderer implements GameRenderer {

  private PlayerRenderer playerRenderer1;
  private PlayerRenderer playerRenderer2;
  private GameStage gameStage;

  //  Constructor
  public StageRenderer(GameStage gameStage) {
    this.gameStage = gameStage;
    playerRenderer1 = new PlayerRenderer(gameStage.getPlayer1());
    playerRenderer2 = new PlayerRenderer(gameStage.getPlayer2());

    playerRenderer1.color = Color.RED;
    playerRenderer2.color = Color.BLUE;
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
