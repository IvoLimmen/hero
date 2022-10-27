package org.limmen.hero.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.limmen.hero.domain.factory.WeaponFactory;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize

public class Enemy extends Hero {

  private String hint;

  @JsonProperty
  @Override
  public void setWeapon(Weapon weapon) {
    super.setWeapon(WeaponFactory.get().byName(weapon.name()));
  }

  public String getHint() {
    return hint;
  }

  public void setHint(String hint) {
    this.hint = hint;
  }
}
