package org.limmen.hero.command;

import java.util.List;

import org.limmen.hero.domain.World;

public class Exit extends Command {
  
  @Override
  public String getName() {
    return "Exit";
  }

  @Override
  public List<String> getAliasses() {
    return List.of("quit");
  }

  @Override
  public void execute(World world) {
    world.exitGame();
  }
}
