package org.limmen.hero.command;

import java.util.List;

import org.limmen.hero.domain.PromptProvider;
import org.limmen.hero.domain.World;

public class Describe extends Command {

  @Override
  public String getName() {
    return "Describe";
  }

  @Override
  public void execute(World world, List<String> arguments, PromptProvider promptProvider) {
    world.describeLocation();
  }
}
