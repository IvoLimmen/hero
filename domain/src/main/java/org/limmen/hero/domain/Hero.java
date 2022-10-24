package org.limmen.hero.domain;

public class Hero {

  private String name;
  private Integer health;
  private Integer armour;
  private Weapon weapon;

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
    return weapon;
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
}
