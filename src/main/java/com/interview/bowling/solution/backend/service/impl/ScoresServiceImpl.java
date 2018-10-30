package com.interview.bowling.solution.backend.service.impl;

import com.interview.bowling.solution.backend.persistence.pojo.Frame;
import com.interview.bowling.solution.backend.service.ScoreService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ScoresServiceImpl implements ScoreService {

  /**
   * The application logger
   */
  private static final Logger LOG = LoggerFactory.getLogger(ScoresServiceImpl.class);
  /**
   * Compute the score for the given throws
   *
   * @param string the scores from each frame
   * @return sum of scores from frames
   */
  @Override
  public Integer computeScore(String string) {
    // split input into array of single scores
    String[] singleScores = string.split("-");
    int sum = 0;

    try {
      int lastIndex;

      // prepare frames to be generated
      Frame[] frame = new Frame[singleScores.length];
      // if the score is exactly 10, then there is no bonus score
      if (singleScores.length == 10) {
        lastIndex = 9;
      } else {
        lastIndex = singleScores.length - 1;
      }
      // check for the special frame since it doesn't have any other frames afterwards.
      frame[lastIndex] = new Frame(singleScores[lastIndex], null, lastIndex);

      // for the rest of frames, prepare each frame with the appropriate score
      for (int i = lastIndex - 1; i >= 0; i--) {
        frame[i] = new Frame(singleScores[i], frame[i + 1], i);
      }

      // compute the final score by going through each frame and summing the scores up.
      for (Frame aFrame : frame) {
        sum += aFrame.getScore();
      }

      // if the frame is with a bonus, then make correction for the computed accounted for in bonus
      if (frame.length == 11 && frame[9].isStrike()) {
        sum -= 20;
      } else if (frame.length == 11 && frame[9].isSpare()) {
        sum -= 5;
      }
    } catch (Exception e) {
      LOG.error("There was an error computing the score ", e);
    }

    return sum;
  }
}
