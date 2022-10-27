package org.limmen.hero.domain;

import java.util.ArrayList;
import java.util.List;

import org.limmen.hero.domain.factory.WeaponFactory;
import org.limmen.hero.util.ItemHolder;

public class Hero implements ItemHolder {

  private String name;
  private Integer health;
  private Integer armour;
  private Weapon weapon;
  private Weapon fallbackWeapon = WeaponFactory.get().byName("Hands");

  private List<Item> items = new ArrayList<>();

  public Integer getArmour() {
    return armour;
  }
  
  public Integer getHealth() {
    return health;
  }

  public String getName() {
    return name;
  }

  public Weapon getWeapon() {
    if (weapon == null) {
      return fallbackWeapon;
    }
    return weapon;
  }

  public Weapon getFallbackWeapon() {
    return fallbackWeapon;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setArmour(Integer armour) {
    this.armour = armour;
  }

  public void setHealth(Integer health) {
    this.health = health;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setWeapon(Weapon weapon) {
    this.weapon = weapon;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public String getWeaponName() {
    return getWeapon().getName();
  }

  public Integer hit(Hero enemy) {
    var result = Dice.d20(enemy.getArmour());    
    
    // we hit
    if (result.success()) {      
      int value = Dice.dice(getWeapon().damage(), 0).value();
      enemy.takeDamage(value);

      return value;
    }

    return 0;
  }

  public void takeDamage(Integer damage) {
    this.health -= damage;
  }

  public boolean hasWeapon() {
    return this.weapon != null;
  }
  
  public boolean isDead() {
    return this.health <= 0;
  }
}
