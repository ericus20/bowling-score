package com.interview.bowling.solution.enums;

/**
 * Measures the outcome for each roll.
 *
 * @author Eric Opoku
 */
public enum FrameOutcome {
  /**
   * Tag a frame outcome as strike.
   * That is knocked down 10 balls on first throw
   */
  STRIKE,

  /**
   * Tag a frame outcome as spare.
   * That is knocked down 10 balls on second throw
   */
  SPARE,

  /**
   * Tag a frame outcome as open.
   * That is knocked down x balls with two throws throw
   */
  OPEN_FRAME,

  /**
   * Tag a frame outcome as bonus.
   * That is knocked down 10 balls on first throw on 10th frame
   */
  BONUS_FRAME
}
