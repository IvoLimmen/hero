package org.limmen.hero.domain;

public class Statistics {
  private int health;
  private int steps;
  
  public void update(Hero hero) {
    this.steps++;
    this.health = hero.getHealth();
  }

  public int getHealth() {
    return health;
  }

  public int getSteps() {
    return steps;
  }
}
