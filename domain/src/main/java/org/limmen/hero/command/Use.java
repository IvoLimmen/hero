package org.limmen.hero.command;

import java.util.List;

import org.limmen.hero.domain.World;

public class Use extends Command {

  @Override
  public String getName() {
    return "use";
  }

  @Override
  public void execute(World world, List<String> arguments) {
    world.use(arguments);    
  }
}
