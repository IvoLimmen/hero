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

  public Integer hit(Hero enemy) {
    var result = Dice.d20(enemy.getArmour());

    System.out.println("D20: " + result);
    
    // we hit
    if (result.success()) {
      int value = Dice.d12(0).value();
      enemy.takeDamage(value);

      return value;
    }

    return 0;
  }

  public void takeDamage(Integer damage) {
    this.health -= damage;
  }

  public boolean isDead() {
    return this.health <= 0;
  }
}
