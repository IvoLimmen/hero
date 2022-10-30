package org.limmen.hero.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.limmen.hero.util.ItemHolder;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
public record Location(
    String name, 
    String description, 
    List<Link> links, 
    List<Enemy> enemies, 
    List<Item> items,
    int minOccurance,
    int maxOccurance,
    boolean visited) 
    implements ItemHolder {

  public boolean canTravel(Direction direction) {
    if (direction == null) {
      return false;
    }
    return links.stream().anyMatch(p -> p.direction().equals(direction));
  }

  public void addNode(Direction direction, String locationName) {
    this.links.add(new Link(direction, locationName));
  }

  public void addEnemy(Enemy enemy) {
    this.enemies.add(enemy);
  }

  public boolean hasEnemies() {
    return !this.enemies.isEmpty();
  }

  @Override
  public List<Item> getItems() {
    return items();
  }
  
  public String newLocationName(Direction direction) {
    return this.links.stream()
        .filter(p -> p.direction().equals(direction))
        .findFirst()
        .get()
        .roomName();
  }

  public Enemy enemyByName(String name) {
    return this.enemies.stream()
        .filter(f -> f.getName().equalsIgnoreCase(name))
        .findFirst()
        .orElse(null);
  }

  public void removeEnemy(Enemy enemy) {
    var newList = this.enemies.stream()
        .filter(f -> !f.getName().equals(enemy.getName()))
        .toList();

    this.enemies.clear();
    this.enemies.addAll(newList);
  }
}
