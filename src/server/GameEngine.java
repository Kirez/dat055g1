package server;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class GameEngine implements Runnable {

  public static int DEFAULT_TPS = 100;

  public boolean stop;
  public double tps;
  private int target_tps;
  private HashSet<GameController> controllers;
  private boolean pause;

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
}
