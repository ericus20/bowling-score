package com.interview.bowling.solution.controller;

import static com.interview.bowling.solution.constants.IndexControllerConstant.INDEX_URL_MAPPING;
import static com.interview.bowling.solution.constants.IndexControllerConstant.INDEX_VIEW_NAME;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The root controller for handling all root mappings.
 */
@Controller
public class IndexController {

  /**
   * The method is the root mapping.
   *
   * @return the index page.
   */
  @GetMapping(path = INDEX_URL_MAPPING)
  public String root() {
    return INDEX_VIEW_NAME;
  }

}
