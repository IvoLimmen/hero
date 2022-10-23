package org.limmen.hero.domain;

public enum Direction {
  EAST, WEST, NORTH, SOUTH, UP, DOWN;

  public static Direction safeParse(String value) {
    try {
      return Direction.valueOf(value);
    } catch (IllegalArgumentException iae) {
      return null;
    }
  }
}
