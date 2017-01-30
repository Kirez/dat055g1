package server;

import common.GameStage;
import javafx.geometry.Point2D;

public class StageController implements GameController {

  GameStage stage;

  public StageController(GameStage stage) {
    this.stage = stage;
  }

  @Override
  public void update(double delta) {
    if (stage.getPlayer1().getPosition().getY() + stage.getPlayer1().getHeight() <
        stage.getGroundLevelY()) {
      stage.getPlayer1().setVelocity(stage.getPlayer1().getVelocity().add(0, 384 * delta));
    } else {
      stage.getPlayer1().setPosition(new Point2D(stage.getPlayer1().getPosition().getX(),
          stage.getGroundLevelY() - stage.getPlayer1().getHeight()));
      stage.getPlayer1().setVelocity(new Point2D(stage.getPlayer1().getVelocity().getX(), 0));
    }
  }
}
