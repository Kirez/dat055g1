package common;

import java.util.HashMap;

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
public class ActionCycle {

  private HashMap<CYCLE, Double> times;
  private HashMap<CYCLE, CYCLE> next;
  private double time;
  private CYCLE cycle;

  public ActionCycle(double spoolUpTime, double activeTime, double coolDownTime) {
    times = new HashMap<>();
    next = new HashMap<>();

    times.put(CYCLE.SPOOL_UP, spoolUpTime);
    times.put(CYCLE.ACTIVE, activeTime);
    times.put(CYCLE.COOL_DOWN, coolDownTime);
    times.put(CYCLE.INACTIVE, 0d);

    next.put(CYCLE.SPOOL_UP, CYCLE.ACTIVE);
    next.put(CYCLE.ACTIVE, CYCLE.COOL_DOWN);
    next.put(CYCLE.COOL_DOWN, CYCLE.INACTIVE);

    cycle = CYCLE.INACTIVE;
  }

  public boolean update(double delta) {
    if (!cycle.equals(CYCLE.INACTIVE)) {
      time -= delta;
      if (time <= 0) {
        enterCycle(next.get(cycle));
      }
    }
    return false;
  }

  public void enterCycle(CYCLE cycle) {
    this.cycle = cycle;
    time = times.get(cycle);
  }

  public boolean isActive() {
    return cycle == CYCLE.ACTIVE;
  }

  public boolean isOnCoolDown() {
    return cycle == CYCLE.COOL_DOWN;
  }

  public boolean isSpoolingUp() {
    return cycle == CYCLE.SPOOL_UP;
  }

  public boolean isReady() {
    return cycle == CYCLE.INACTIVE;
  }

  public enum CYCLE {
    SPOOL_UP,
    ACTIVE,
    COOL_DOWN,
    INACTIVE
  }
}
