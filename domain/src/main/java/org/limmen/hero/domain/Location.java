package org.limmen.hero.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
public record Location(String name, String description, List<Link> links, List<Enemy> enemies) {

  public boolean canTravel(Direction direction) {
    if (direction == null) {
      return false;
    }
    return links.stream().anyMatch(p -> p.direction().equals(direction));
  }

  public boolean hasEnemies() {
    return !this.enemies.isEmpty();
  }

  public String getNewLocationName(Direction direction) {
    return this.links.stream()
        .filter(p -> p.direction().equals(direction))
        .findFirst()
        .get()
        .roomName();
  }
}
