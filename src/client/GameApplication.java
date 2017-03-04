package client;

import client.screen.EndScreen;
import client.screen.JoinScreen;
import client.screen.MainMenuScreen;
import client.screen.NetworkPlayScreen;
import client.screen.PlayScreen;
import client.screen.Screen;
import client.screen.SettingsScreen;
import common.GameDefaults;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Initializes the application and handles scene switching.
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-28
 */
public class GameApplication extends Application {

  /**
   * Instance of <tt>PlayScreen</tt>.
   */
  public PlayScreen playScreen;
  /**
   * Instance of <tt>MainMenuScreen</tt>.
   */
  public MainMenuScreen mainMenuScreen;
  /**
   * Instance of <tt>JoinScreen</tt>.
   */
  public JoinScreen joinScreen;
  /**
   * Instance of <tt>NetworkPlayScreen</tt>.
   */
  public NetworkPlayScreen networkPlayScreen;
  /**
   * Instance of <tt>EndScreen</tt>.
   */
  public EndScreen endScreen;
  /**
   * Instance of <tt>SettingsScreen</tt>.
   */
  public SettingsScreen settingsScreen;

  // Current active Screen
  private Screen activeScreen;
  private Stage stage;

  /**
   * The main function for the client in <tt>GameApplication</tt>. Launches the game.
   *
   * @param args Launch parameters
   */
  public static void main(String args[]) {
    launch(args);
  }

  /**
   * Create a new instance of <tt>Stage</tt> which contains a new instance of each screen.
   *
   * @param primaryStage stage to start
   * @throws Exception general exception
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    stage.setTitle(GameDefaults.WINDOW_TITLE);
    javafx.stage.Screen screen = javafx.stage.Screen.getPrimary();
    stage.setWidth(screen.getBounds().getMaxX() / 2);
    stage.setHeight(screen.getBounds().getMaxY() / 2);
    //stage.setFullScreen(true);

    playScreen = new PlayScreen(this);
    mainMenuScreen = new MainMenuScreen(this);
    joinScreen = new JoinScreen(this);
    networkPlayScreen = new NetworkPlayScreen(this);
    endScreen = new EndScreen(this);
    settingsScreen = new SettingsScreen(this);
    setActiveScreen(mainMenuScreen);

    // primaryStage is the stage provided by the javafx app instance
    stage.setOnCloseRequest(this::exit);
    stage.show();

    /*The following makes it so the screen pops-up in focus even when not in full screen*/
    stage.requestFocus();
    stage.setAlwaysOnTop(true);
    stage.setAlwaysOnTop(false);
  }

  /**
   * Switches to <tt>screen</tt>, if possible.
   *
   * @param screen screen to switch to
   */
  public void setActiveScreen(Screen screen) {
    if (activeScreen != null) {
      activeScreen.exit();
    }
    activeScreen = screen;
    activeScreen.enter(stage);
  }

  /**
   * Closes the current screen and the <tt>GameApplication</tt>.
   *
   * @param windowEvent event invoking this function
   */
  private void exit(WindowEvent windowEvent) {
    activeScreen.exit();
    Platform.exit();
  }
}
