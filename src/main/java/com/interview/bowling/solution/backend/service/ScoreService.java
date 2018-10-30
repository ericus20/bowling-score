package com.interview.bowling.solution.backend.service;

/**
 * Compute the score for the given throws and return back to the user.
 */
public interface ScoreService {

  /**
   * Compute the score for the given throws
   *
   * @param string the scores from each frame
   * @return sum of scores from frames
   */
   Integer computeScore(String string);
}
