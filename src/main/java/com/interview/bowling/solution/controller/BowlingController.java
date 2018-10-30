package com.interview.bowling.solution.controller;

import com.interview.bowling.solution.backend.service.ScoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles all requests to compute the score.
 */
@Controller
public class BowlingController {

  private final ScoreService scoreService;

  @Autowired
  public BowlingController(ScoreService scoreService) {
    this.scoreService = scoreService;
  }

  /**
   * Computes the score given the throws
   * @param scores the scores of the throws
   * @return the score
   */
  @PostMapping(value = "/computeScore")
  public @ResponseBody Integer computeScore(@RequestParam(name = "scores") String scores) {
    String[] values = scores.split(",");

    StringBuilder stringBuilder = new StringBuilder();

    // build the desired value to be computed.
    buildScoreFromInput(values, stringBuilder);

    // if there is any excess (-) at the end of the builder, remove it since it's not needed.
    if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) == '-') {
      stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() - 1);
    }

    // trim to ensure we don't have any noise around the input.
    String input = (stringBuilder.toString().trim());

    // return the computed score for the input scores.
    return scoreService.computeScore(input);
  }

  /**
   * Builds the score in the desirable fashion (X-X-X... ).
   *
   * @param scores the scores
   * @param stringBuilder the builder
   */
  private void buildScoreFromInput(String[] scores, StringBuilder stringBuilder) {
    for (int i = 0; i < scores.length;) {
      // if it's an X, then append a single character.
      if (scores[i].equals("X")) {
        // once we hit, 10, we need to concatenate the rest without (-) like XX
        if (i < 10)
          stringBuilder.append(scores[i]).append("-");
        else
          stringBuilder.append(scores[i]);
        i++;

      } else {
        // check that we can pick two scores at a time to represent throws for a single frame (5/)
        if (i + 1 < scores.length) {
          stringBuilder.append(scores[i]).append(scores[i + 1]).append("-");
          i += 2;
        } else {
          stringBuilder.append(scores[i]);
          i++;
        }
      }
    }
  }
}
