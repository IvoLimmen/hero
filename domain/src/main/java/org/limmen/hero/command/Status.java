package org.limmen.hero.command;

import java.util.List;

import org.limmen.hero.domain.World;

public class Status extends Command {

  @Override
  public String getName() {
    return "status";
  }

  @Override
  public void execute(World world, List<String> arguments) {
    world.showStatus();    
  }
  
}
