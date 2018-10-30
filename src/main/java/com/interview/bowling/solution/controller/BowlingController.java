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
    for (int i = 0; i < values.length;) {
      if (values[i].equals("X")) {
        if (i < 10)
          stringBuilder.append(values[i]).append("-");
        else
          stringBuilder.append(values[i]);
        i++;
      } else {
        if (i + 1 < values.length) {
          stringBuilder.append(values[i]).append(values[i + 1]).append("-");
          i += 2;
        } else {
          stringBuilder.append(values[i]);
          i++;
        }
      }
    }

    if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) == '-') {
      stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() - 1);
    }

    String input = (stringBuilder.toString().trim());
    return scoreService.computeScore(input);
  }
}
