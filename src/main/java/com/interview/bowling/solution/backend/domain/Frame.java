package com.interview.bowling.solution.backend.domain;

import com.interview.bowling.solution.enums.FrameOutcome;

/**
 * This class handles the operation relating to a single frame.
 */
public class Frame {

  private int[] singleThrow;
  private Frame nextFrame;
  // holds the current outcome for the throws that occurred in this frame. Strike, Spare, etc.
  private FrameOutcome frameOutcome;

  /**
   * Creates a frame with the given options.
   *
   * @param score      the score for this frame
   * @param nextFrame  the pointer to the next frame
   * @param frameIndex the index of this frame
   */
  public Frame(String score, Frame nextFrame, int frameIndex) {
    singleThrow = new int[2];

    // if the frame is the last one then set bonus frame to true
    if (frameIndex == 10) {
      // set as a bonus
      setFrameOutcome(FrameOutcome.BONUS_FRAME);
      // compute the score for the bonus frame
      computeScoreForBonusFrame(score);
    } else {
      // set the next frame
      setNextFrame(nextFrame);
      // compute the score for open frame
      setFrameOutcome(FrameOutcome.OPEN_FRAME);
      // compute the frame's score.
      computeFrameScore(score);
    }
  }

  /**
   * Computes the frame score given the score. example "45", "5/", "X".
   *
   * @param score the score
   */
  private void computeFrameScore(String score) {
    if (score.charAt(0) == 'X') {
      singleThrow[0] = 10;
      singleThrow[1] = 0;
      // set as a strike
      setFrameOutcome(FrameOutcome.STRIKE);

    } else {
      singleThrow[0] = Integer.parseInt(Character.toString(score.charAt(0)));

      // check that the score is a spare like "5/" or "8/"
      if (score.length() > 1) {
        if (score.charAt(1) == '/') {
          // set as a spare
          setFrameOutcome(FrameOutcome.SPARE);
          singleThrow[1] = 10 - singleThrow[0];
        } else {
          singleThrow[1] = Integer.parseInt(Character.toString(score.charAt(1)));
        }
      }
    }
  }

  /**
   * Computes the score for the bonus frame. all scores has to be 10 if strike.
   *
   * @param score the score
   */
  private void computeScoreForBonusFrame(String score) {
    for (int i = 0; i < score.length(); i++) {
      if (score.charAt(i) == 'X') {
        singleThrow[i] = 10;
      } else if (score.charAt(i) == '/') {
        singleThrow[i] = 10 - singleThrow[i - 1];
      } else {
        singleThrow[i] = Integer.parseInt(Character.toString(score.charAt(i)));
      }
    }
  }

  /**
   * The the next throw's score which will be used on special cases. Strike and Spare.
   *
   * @return the score
   */
  private int getNextThrowScore() {
    return singleThrow[0];
  }

  /**
   * The the next two throw's score which will be used on special cases. Strike and Spare.
   *
   * @return the score
   */
  private int getNextTwoThrowsScore() {
    if (getFrameOutcome() == FrameOutcome.STRIKE) {
      return 10 + nextFrame.getNextThrowScore();
    }
    return singleThrow[0] + singleThrow[1];
  }

  /**
   * Computes the score for this frame
   * If strike, then current frame + next two throw's scores
   * If spare, then current frame + next throw's score.
   * <p>
   * Otherwise it's the score of the throw.
   *
   * @return the score
   */
  public int getScore() {
    int sum = 0;
    for (int aSingleThrow : singleThrow) {
      sum += aSingleThrow;
    }

    // switch between frames to set the appropriate frame.
    switch (getFrameOutcome()) {
      case STRIKE:
        // if it's a strike, get the next two scores of throws.
        sum = sum + getNextFrame().getNextTwoThrowsScore();
        break;
      case SPARE:
        // if it's a spate, we need just the next throw to add to the current score.
        sum = sum + getNextFrame().getNextThrowScore();
        break;
      default:
        break;
    }

    // return sum
    return sum;
  }

  private Frame getNextFrame() {
    return nextFrame;
  }

  private void setNextFrame(Frame nextFrame) {
    this.nextFrame = nextFrame;
  }

  public FrameOutcome getFrameOutcome() {
    return frameOutcome;
  }

  private void setFrameOutcome(FrameOutcome frameOutcome) {
    this.frameOutcome = frameOutcome;
  }
}
