package com.interview.bowling.solution.constants.index;

/**
 * This class holds all constants used by the Index Controller.
 *
 * @author Eric Opoku
 */
public abstract class IndexControllerConstant {


  private IndexControllerConstant() {
    throw new AssertionError("Non Instantiable");
  }

  /**
   * URL Mapping Constants.
   */
  public static final String INDEX_URL_MAPPING = "/";

  /**
   * View Name Constants.
   */
  public static final String INDEX_VIEW_NAME = "index";
}
