package com.interview.bowling.solution.backend.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ScoreServiceTest {

  @Autowired
  private ScoreService scoreService;

  @Test
  public void computeScoreOnStrike() {
    String score = "X-X-X-X-X-X-X-X-X-X-XX";
    assertEquals(Integer.valueOf(300), scoreService.computeScore(score));
  }

  @Test
  public void computeScoreOnOpenFrame() {
    String score = "45-54-36-27-09-63-81-18-90-72";
    assertEquals(Integer.valueOf(90), scoreService.computeScore(score));
  }

  @Test
  public void computeScoreOnSpare() {
    String score = "5/-5/-5/-5/-5/-5/-5/-5/-5/-5/-5";
    assertEquals(Integer.valueOf(150), scoreService.computeScore(score));
  }

  @Test
  public void computeScoreOnOpenFrameAndSpare() {
    String score = "45-54-36-27-09-63-81-18-90-7/-5";
    assertEquals(Integer.valueOf(96), scoreService.computeScore(score));
  }
}
