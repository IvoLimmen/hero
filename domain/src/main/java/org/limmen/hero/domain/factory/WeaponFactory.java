package org.limmen.hero.domain.factory;

import java.util.ArrayList;
import java.util.List;

import org.limmen.hero.domain.Weapon;
import org.limmen.hero.util.JSON;

public class WeaponFactory {

  private static WeaponFactory INSTANCE = new WeaponFactory();

  private List<Weapon> weapons = new ArrayList<>();

  public WeaponFactory() {
    this.weapons = JSON.loadList("./weapons.json", Weapon.class);
  }

  public static WeaponFactory get() {
    return INSTANCE;
  }

  public Weapon byName(String name) {
    return this.weapons.stream()
        .filter(f -> f.name().equalsIgnoreCase(name))
        .findFirst()
        .get();
  }
}
