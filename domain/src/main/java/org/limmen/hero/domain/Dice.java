package org.limmen.hero.domain;

import java.util.Random;

public class Dice {
  
  private static Random rnd = new Random();

  public static DiceThrow d20(int minimum) {
    return dice(20, minimum);
  }

  public static DiceThrow d12(int minimum) {
    return dice(12, minimum);
  }

  private static DiceThrow dice(int max, int min) {
    int value = rnd.nextInt(max + 1);
    return new DiceThrow(max, min, value, value > min);
  }
}