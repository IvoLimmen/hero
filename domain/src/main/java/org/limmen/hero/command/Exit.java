package org.limmen.hero.command;

import org.limmen.hero.domain.World;

public class Exit extends Command {
  
  @Override
  public String getName() {
    return "exit";
  }

  @Override
  public void execute(World world) {
    world.exitGame();
  }
}
