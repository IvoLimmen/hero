package org.limmen.hero.command;

import java.util.List;

import org.limmen.hero.domain.World;

public class Take extends Command {
  
  @Override
  public String getName() {
    return "take";
  }

  @Override
  public void execute(World world, List<String> arguments) {
    world.take(arguments);    
  }
}
