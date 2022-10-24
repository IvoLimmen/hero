package org.limmen.hero.command;

import org.limmen.hero.domain.World;

public class Describe extends Command {

  @Override
  public String getName() {
    return "Describe";
  }

  @Override
  public void execute(World world) {
    world.describeLocation();
  }
}
