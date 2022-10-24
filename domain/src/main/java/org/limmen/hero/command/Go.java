package org.limmen.hero.command;

import org.limmen.hero.domain.Direction;
import org.limmen.hero.domain.World;

public class Go extends Command {

  @Override
  public String getName() {
    return "Go";
  }

  @Override
  public void execute(World world) {
    Direction direction = null;
    if (!noArgs()) {
      direction = Direction.safeParse(getArguments().get(0).toUpperCase());
      if (!world.getCurrentLocation().canTravel(direction)) {
        direction = null;
        world.listDirections();
      }
    }

    while (direction == null) {
      direction = Direction.safeParse(askArg("Where to?").toUpperCase());
      if (direction == null || !world.getCurrentLocation().canTravel(direction)) {
        world.listDirections();
      }
    }

    if (world.getCurrentLocation().canTravel(direction)) {
      world.go(direction);
      System.out.println("You are now at " + world.getCurrentLocation().name());
      if (world.getCurrentLocation().hasEnemies()) {
        if (world.getCurrentLocation().enemies().size() == 1) {
          System.out.println("There is a " + world.getCurrentLocation().enemies().get(0).getName() + " here!");
        } else {
          System.out.println("There are enemies at this location!");
          world.getCurrentLocation().enemies().forEach(e -> {
            System.out.println(e.getName());
          });
        }
      }
    }
  }
}
