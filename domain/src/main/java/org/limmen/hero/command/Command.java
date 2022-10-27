package org.limmen.hero.command;

import java.util.Collections;
import java.util.List;

import org.limmen.hero.domain.World;

public abstract class Command {

  public List<String> getAliasses() {
    return Collections.emptyList();
  }
  
  public abstract String getName();

  public abstract void execute(World world, List<String> arguments);
}
