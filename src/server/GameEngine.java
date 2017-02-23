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
   * Desired tickrate
   */
  public static int DEFAULT_TPS = 100;

  /**
   * Engine state. Enabling halts the engine thread.
   */
  public boolean stop;
  /**
   * Actual tickrate
   */
  public double tps;
  /**
   * Desired tickrate, is initiated to DEFAULT_TPS
   */
  private int target_tps;
  /**
   * Used for keyboard inputs
   */
  private HashSet<GameController> controllers;
  /**
   * Engine state. Enabling sleeps the engine thread.
   */
  private boolean pause;

  /**
   * TODO: Continue here
   */
  public GameEngine() {
    controllers = new HashSet<>();
    target_tps = DEFAULT_TPS;
    stop = true;
    tps = 0;
    pause = false;
  }

  public boolean addController(GameController controller) {
    return controllers.add(controller);
  }

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

  public void setPause(boolean pause) {
    this.pause = pause;
  }

  public void togglePause() {
    pause = !pause;
  }
}
