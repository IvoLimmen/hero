package org.limmen.hero.domain.factory;

import java.util.ArrayList;
import java.util.List;

import org.limmen.hero.domain.Location;

public class LocationFactory {
  
  private static LocationFactory INSTANCE = new LocationFactory();

  private List<Location> locations = new ArrayList<>();

  private LocationFactory() {
  }

  public void set(List<Location> locations) {
    this.locations = locations;
  }
  
  public static LocationFactory get() {
    return INSTANCE;
  }

  public Location byName(String name) {
    return this.locations.stream()
        .filter(f -> f.name().equalsIgnoreCase(name))
        .findFirst()
        .get();
  }
}
