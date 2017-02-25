package server;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * TODO: Add description
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-02-23
 */
public class GameEngine implements Runnable {

  /**
   * Desired <tt>GameEngine</tt> tickrate.
   */
  public static int DEFAULT_TPS = 100;

  /**
   * Engine state. Enabling halts the engine thread.
   */
  public boolean stop;
  /**
   * Actual <tt>GameEngine</tt> tickrate.
   */
  public double tps;
  /**
   * Desired <tt>GameEngine</tt> tickrate, is initiated to <tt>DEFAULT_TPS</tt>.
   */
  private int target_tps;
  /**
   * Used for keyboard inputs
   */
  private HashSet<GameController> controllers;
  /**
   * <tt>GameEngine</tt> state. Enabling sleeps the engine thread.
   */
  private boolean pause;

  /**
   * Initializes the <tt>GameEngine</tt> to the default values.
   */
  public GameEngine() {
    controllers = new HashSet<>();
    target_tps = DEFAULT_TPS;
    stop = true;
    tps = 0;
    pause = false;
  }

  /**
   * Adds a <tt>controller</tt> instance to the HashSet.
   *
   * @param controller The controller to be added
   * @return the <tt>GameEngine</tt> <tt>controller</tt> HashSet, with the <tt>controller</tt> added
   */
  public boolean addController(GameController controller) {
    return controllers.add(controller);
  }

  /**
   * Updates all <tt>controllers</tt> belonging to this instance of the <tt>GameEngine</tt>
   *
   * @param delta the time difference between the current and the previous tick
   */
  public void tick(double delta) {
    controllers.forEach(c -> c.update(delta));
  }

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
   * Enable the <tt>GameEngine</tt> pause state.
   *
   * @param pause the <tt>GameEngine</tt> state
   * @see #togglePause()
   * @deprecated
   */
  public void setPause(boolean pause) {
    this.pause = pause;
  }

  /**
   * Toggle the <tt>GameEngine</tt> pause state.
   */
  public void togglePause() {
    pause = !pause;
  }
}
