package common;

import java.util.HashMap;

/**
 * Class that describes the cycles of an action spool up, active, cool down, inactive.
 *
 * @author Alexander Andersson (alexaan)
 * @author Linus Berglund (belinus)
 * @author Erik Källberg (kalerik)
 * @author Timmy Truong (timmyt)
 * @author Karl Ängermark (karlang)
 * @version 2017-03-04
 */
public class ActionCycle {

  private HashMap<CYCLE, Double> times;
  private HashMap<CYCLE, CYCLE> next;
  private double time;
  private CYCLE cycle;

  /**
   * Creates an instance of ActionCycle.
   *
   * @param spoolUpTime time spent on spool phase
   * @param activeTime time spent on active phase
   * @param coolDownTime time spent on cool down phase
   */
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

  /**
   * Advances the cycle if time left minus {@code delta} less or equal zero cycle enters next
   * stage.
   *
   * @param delta the difference in time between the update ticks
   * @return true if next stage false if not
   */
  public boolean update(double delta) {
    if (!cycle.equals(CYCLE.INACTIVE)) {
      time -= delta;
      if (time <= 0) {
        enterCycle(next.get(cycle));
      }
    }
    return false;
  }

  /**
   * Enters into a cycle phase
   *
   * @param cycle phase to enter
   */
  public void enterCycle(CYCLE cycle) {
    this.cycle = cycle;
    time = times.get(cycle);
  }

  /**
   * Whether in active phase or not
   *
   * @return true if active phase else false
   */
  public boolean isActive() {
    return cycle == CYCLE.ACTIVE;
  }

  /**
   * Whether in cool down phase or not
   *
   * @return true if cool down phase else false
   */
  public boolean isOnCoolDown() {
    return cycle == CYCLE.COOL_DOWN;
  }

  /**
   * Whether in spool-up phase or not
   *
   * @return true if spool-up phase else false
   */
  public boolean isSpoolingUp() {
    return cycle == CYCLE.SPOOL_UP;
  }

  /**
   * Whether in this cycle is ready for next iteration this would be when inactive
   *
   * @return true if ready for next phase else false
   */
  public boolean isReady() {
    return cycle == CYCLE.INACTIVE;
  }

  /**
   * The cycle phase types
   */
  public enum CYCLE {
    SPOOL_UP,
    ACTIVE,
    COOL_DOWN,
    INACTIVE
  }
}
