package server;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * Handles game logic updates and tick rate.
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-03-04
 */
public class GameEngine implements Runnable {

  /**
   * Desired {@code GameEngine} tickrate.
   */
  public static int DEFAULT_TPS = 100;

  /**
   * Engine state. Enabling halts the engine thread.
   */
  public boolean stop;

  /**
   * Actual {@code GameEngine} tickrate.
   */
  public double tps;

  // Desired GameEngine tickrate, is initiated to DEFAULT_TPS.
  private int target_tps;

  // Used for keyboard inputs.
  private HashSet<GameController> controllers;

  // GameEngine state. Enabling sleeps the engine thread.
  private boolean pause;

  /**
   * Creates a new instance of {@code GameEngine}.
   */
  public GameEngine() {
    controllers = new HashSet<>();
    target_tps = DEFAULT_TPS;
    stop = true;
    tps = 0;
    pause = false;
  }

  /**
   * Adds a {@code controller} instance to the HashSet.
   *
   * @param controller The controller to be added
   * @return the {@code GameEngine} {@code controller} HashSet, with the {@code controller} added
   */
  public boolean addController(GameController controller) {
    return controllers.add(controller);
  }

  /**
   * Updates all {@code controllers} belonging to this instance of the {@code GameEngine}.
   *
   * @param delta the time difference between the current and the previous tick
   */
  public void tick(double delta) {
    controllers.forEach(c -> c.update(delta));
  }

  /**
   * Main game loop. Calculate each tick, then sleep for the remaining time allocated to the tick.
   */
  @Override
  public void run() {
    stop = false;

    long before, after, delta, sleep;
    long defaultSleep = 1000000000 / target_tps;

    double doubleDelta = 0;

    while (!stop) {
      before = System.nanoTime();
      tick(doubleDelta);
      after = System.nanoTime();

      delta = after - before;

      sleep = defaultSleep - delta;
      sleep = sleep > 0 ? sleep : 0;

      sleep /= 1000 * 1000;

      try {
        Thread.sleep(sleep);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      doubleDelta = (double) TimeUnit.MILLISECONDS.convert(System.nanoTime() - before
          , TimeUnit.NANOSECONDS) / 1000d;

      tps = 1d / doubleDelta;

      if (pause) {
        while (pause) {
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  /**
   * Enable the {@code GameEngine} pause state.
   *
   * @param pause the {@code GameEngine} state
   * @see #togglePause()
   * @deprecated
   */
  public void setPause(boolean pause) {
    this.pause = pause;
  }

  /**
   * Toggle the {@code GameEngine} pause state.
   */
  public void togglePause() {
    pause = !pause;
  }
}
