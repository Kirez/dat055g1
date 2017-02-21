package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/*Client entry class handles switching of screens/modes*/
public class GameApplication extends Application {

  public PlayScreen playScreen;
  public MenuScreen menuScreen;
  public ConnectScreen connectScreen;
  //private GameServer gameServer;

  private AbstractScreen activeScreen;
  private Stage stage;

  public static void main(String args[]) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    stage.setTitle("TimmyFightGoGo");
    Screen screen = Screen.getPrimary();
    stage.setWidth(screen.getBounds().getMaxX());
    stage.setHeight(screen.getBounds().getMaxY());
    //stage.setFullScreen(true);

    playScreen = new PlayScreen(this);
    menuScreen = new MenuScreen(this);
    connectScreen = new ConnectScreen(this);

    setActiveScreen(menuScreen);

    // primaryStage is the stage provided by the javafx app instance
    stage.setOnCloseRequest(this::exit);
    stage.show();

    /*The following makes it so the screen pops-up in focus even when not in full screen*/
    stage.requestFocus();
    stage.setAlwaysOnTop(true);
    stage.setAlwaysOnTop(false);
  }

  public void setActiveScreen(AbstractScreen screen) {
    if (activeScreen != null) {
      activeScreen.exit();
    }
    activeScreen = screen;
    activeScreen.enter(stage);
  }

  private void exit(WindowEvent windowEvent) {
    activeScreen.exit();
    Platform.exit();
  }
}
