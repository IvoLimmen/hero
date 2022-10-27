package org.limmen.hero.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
public record Weapon (String name, Integer damage) implements Item {

  @Override
  public String getName() {
    return name();
  }
}
