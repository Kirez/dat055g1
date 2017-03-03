package common;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/**
 * TODO: Add description
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-28
 */
public class GameDefaults {

  public static final int SERVER_PORT = 8822;
  public static final int BIG_FONT_SIZE = 70;

  public static final String WINDOW_TITLE = "Freet Stighter: Total Combat";
  public static final String TITLE = "Total Combat";
  public static final String LOCAL_GAME_BUTTON_TEXT = "Local Game";
  public static final String JOIN_GAME_BUTTON_TEXT = "Join";
  public static final String SETTINGS_BUTTON_TEXT = "Settings";
  public static final String EXIT_BUTTON_TEXT = "Exit";

  public static final KeyCode MOVE_LEFT = KeyCode.A;
  public static final KeyCode MOVE_RIGHT = KeyCode.D;
  public static final KeyCode JUMP = KeyCode.W;
  public static final KeyCode FALL = KeyCode.S;
  public static final KeyCode HIT = KeyCode.SPACE;

  public static final Color BACKGROUND_COLOR = Color.web("002b36");
  public static final Color FOREGROUND_COLOR = Color.web("073642");
  public static final Color PLAYER_1_COLOR = Color.web("cb4b16");
  public static final Color PLAYER_2_COLOR = Color.web("268bd2");
  public static final Color HITSTUN_COLOR = Color.web("d33682");
  public static final Color HITBOX_COLOR = Color.web("859900");
}
