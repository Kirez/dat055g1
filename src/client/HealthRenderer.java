package client;

import common.GamePlayer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class HealthRenderer implements GameRenderer{

    private GamePlayer player;
    private boolean leftBar;

    //Constructor
    public HealthRenderer(GamePlayer player, boolean leftBar){
        this.player = player;
        this.leftBar = leftBar;
    }


    @Override
    public void render(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double scaleX = canvas.getWidth () / 16;
        double scaleY = canvas.getHeight() / 9;

        gc.save();

        gc.scale(scaleX, scaleY);
        gc.setLineWidth(gc.getLineWidth() / scaleX);

        Rectangle healthBar;

        if (leftBar == true) {
            healthBar = new Rectangle(0.6, 0.3, 5.7, 0.4);
        } else {
            healthBar = new Rectangle(16-0.6-5.7, 0.3, 5.7, 0.4);
        }

        double percentage = (double) player.getHP() / player.getMaxHP();

        gc.setFill(Color.LIMEGREEN);
        if (leftBar) {
            gc.fillRect(healthBar.getX(), healthBar.getY(), healthBar.getWidth() * percentage, healthBar.getHeight());
        } else {
            gc.fillRect(healthBar.getX() + healthBar.getWidth() * (1d - percentage), healthBar.getY(), healthBar.getWidth() * percentage, healthBar.getHeight());
        }
        gc.setLineWidth(gc.getLineWidth() * scaleX);

        gc.restore();
    }
}
