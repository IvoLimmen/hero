package org.limmen.hero.command;

import java.util.List;

import org.limmen.hero.domain.World;

public class Help extends Command {

  @Override
  public String getName() {
    return "help";
  }

  @Override
  public void execute(World world, List<String> arguments) {
    world.listCommands();
  }  
}
