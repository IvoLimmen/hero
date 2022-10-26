package org.limmen.hero.domain.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.limmen.hero.domain.Enemy;
import org.limmen.hero.util.JSON;

public class EnemyFactory {
  private static EnemyFactory INSTANCE = new EnemyFactory();

  private List<Enemy> enemies = new ArrayList<>();

  public EnemyFactory() {
    this.enemies = JSON.loadList("/enemies.json", Enemy.class);    
  }

  public static EnemyFactory get() {
    return INSTANCE;
  }

  public Optional<Enemy> byName(String name) {
    return this.enemies.stream()
        .filter(f -> f.getName().equalsIgnoreCase(name))
        .findFirst();
  }

}