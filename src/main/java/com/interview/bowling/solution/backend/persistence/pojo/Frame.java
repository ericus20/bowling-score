package com.interview.bowling.solution.backend.persistence.pojo;

/**
 * This class handles the operation relating to a single frame.
 */
public class Frame {

  private int[] singleThrow;
  private boolean bonusFrame;
  private Frame nextFrame;
  private boolean strike;
  private boolean spare;

  /**
   * Creates a frame with the given options.
   *
   * @param score the score for this frame
   * @param nextFrame the pointer to the next frame
   * @param frameIndex the index of this frame
   */
  public Frame (String score, Frame nextFrame, int frameIndex) {
    singleThrow = new int[2];

    // if the frame is the last one then set bonus frame to true
    if (frameIndex == 10) {
      setBonusFrame();
      computeScoreForBonusFrame(score);
    } else {
      setNextFrame(nextFrame);
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
      strike = true;

    } else {
      singleThrow[0] = Integer.parseInt(Character.toString(score.charAt(0)));

      // check that the score is a spare like "5/" or "8/"
      if (score.length() > 1) {
        if (score.charAt(1) == '/') {
          spare = true;
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

  private int getNextThrowScore() {
    return singleThrow[0];
  }

  private int getNextTwoThrowsScore() {
    if (isStrike()) {
      return 10 + nextFrame.getNextThrowScore();
    }
    return singleThrow[0] + singleThrow[1];
  }

  /**
   * Computes the score for this frame
   * If strike, then current frame + next two throw's scores
   * If spare, then current frame + next throw's score.
   *
   * Otherwise it's the score of the throw.
   *
   * @return the score
   */
  public int getScore() {
    int sum = 0;
    for (int aSingleThrow : singleThrow) {
      sum += aSingleThrow;
    }
    if (!isBonusFrame()) {
      if (isStrike()) {
        sum += getNextFrame().getNextTwoThrowsScore();
      } else if (isSpare()) {
        sum += getNextFrame().getNextThrowScore();
      }
    }
    return sum;
  }

  private boolean isBonusFrame() {
    return bonusFrame;
  }

  private void setBonusFrame() {
    this.bonusFrame = true;
  }

  private void setNextFrame(Frame nextFrame) {
    this.nextFrame = nextFrame;
  }

  public boolean isStrike() {
    return strike;
  }

  public boolean isSpare() {
    return spare;
  }

  private Frame getNextFrame() {
    return nextFrame;
  }
}
