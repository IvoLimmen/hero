package org.limmen.hero.domain;

public class Hero {

  private String name;
  private Integer health;
  private Integer armour;
  private Weapon weapon;

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    
    private String name;
    private Integer health;
    private Integer armour;
    private Weapon weapon;

    public Builder() {    
    }
      
    Builder(String name, Integer health, Integer armour, Weapon weapon) {    
      this.name = name; 
      this.health = health; 
      this.armour = armour; 
      this.weapon = weapon;             
    }
        
    public Builder name(String name){
      this.name = name;
      return Builder.this;
    }

    public Builder health(Integer health){
      this.health = health;
      return Builder.this;
    }

    public Builder armour(Integer armour){
      this.armour = armour;
      return Builder.this;
    }

    public Builder weapon(Weapon weapon){
      this.weapon = weapon;
      return Builder.this;
    }

    public Hero build() {
        return new Hero(this);
    }
  }

  private Hero(Builder builder) {
    this.name = builder.name; 
    this.health = builder.health; 
    this.armour = builder.armour; 
    this.weapon = builder.weapon;     
  }

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
}
