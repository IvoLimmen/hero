package org.limmen.hero.command;

import java.util.List;

import org.limmen.hero.domain.World;

public class Go extends Command {

  @Override
  public List<String> getAliasses() {
    return List.of("travel");
  }

  @Override
  public String getName() {
    return "go";
  }

  @Override
  public void execute(World world, List<String> arguments) {
    world.changeLocation(arguments);
  }
}
