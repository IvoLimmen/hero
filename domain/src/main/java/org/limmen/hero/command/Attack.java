package org.limmen.hero.command;

import java.util.List;

import org.limmen.hero.domain.World;

public class Attack extends Command {

  @Override
  public List<String> getAliasses() {
    return List.of("hit");
  }

  @Override
  public String getName() {
    return "attack";
  }

  @Override
  public void execute(World world, List<String> arguments) {
    world.attack(arguments);    
  }
}
