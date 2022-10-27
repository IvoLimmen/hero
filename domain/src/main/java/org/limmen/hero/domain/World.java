package org.limmen.hero.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.limmen.hero.command.CommandFactory;
import org.limmen.hero.command.CommandParser;
import org.limmen.hero.domain.factory.EnemyFactory;
import org.limmen.hero.domain.factory.LocationFactory;
import org.limmen.hero.exceptions.NoCommandException;
import org.limmen.hero.exceptions.UnknownCommandException;

public class World {

  private Hero hero;

  private Location currentLocation;

  public World(Hero hero, Location startLocation) {
    this.hero = hero;
    this.currentLocation = startLocation;
  }

  public Hero getHero() {
    return hero;
  }

  public void exitGame() {
    System.exit(0);
  }

  public void listDirections() {
    System.out.println("You can go to the following directions:");
    this.currentLocation.links().forEach(l -> {
      System.out.println(l.direction());
    });
  }

  public void go(Direction direction) {
    if (currentLocation.canTravel(direction)) {
      this.currentLocation = LocationFactory.get()
          .byName(currentLocation.getNewLocationName(direction));
      System.out.println("You are now at " + getCurrentLocation().name());
      listEnemies();    
    } else {
      System.out.println("You can not go in that direction.");
    }
  }

  public void showStatus() {
    System.out.println("You are " + getHero().getName());
    System.out.println("Health: " + getHero().getHealth());
    System.out.println("Armour: " + getHero().getArmour());
    System.out.println("Weapon: " + getHero().getWeapon().name() + " Damage: " + getHero().getWeapon().damage());
  }

  public void listCommands() {
    System.out.println("The following commands are available:");
    var cmds = CommandFactory.get().list().stream().toList();

    cmds.forEach(cmd -> {
      System.out.print(cmd.getName());

      if (!cmd.getAliasses().isEmpty()) {
        System.out.print(" (or: ");
        System.out.print(cmd.getAliasses().stream().collect(Collectors.joining(",")));
        System.out.print(")");
      }

      System.out.println("");
    });
  }

  public void describeLocation() {
    System.out.println("You are now at " + getCurrentLocation().name());
    System.out.println(getCurrentLocation().description());
    listDirections();
  }

  public Location getCurrentLocation() {
    return currentLocation;
  }

  public void changeLocation(List<String> arguments, PromptProvider promptProvider) {
    Direction direction = null;
    if (!arguments.isEmpty()) {
      direction = Direction.safeParse(arguments.get(0).toUpperCase());
      if (!getCurrentLocation().canTravel(direction)) {
        direction = null;
        listDirections();
      }
    }

    while (direction == null) {
      direction = Direction.safeParse(askArg(promptProvider, "Where to?").toUpperCase());
      if (direction == null || !getCurrentLocation().canTravel(direction)) {
        listDirections();
      }
    }

    go(direction);
  }

  private void listEnemies() {
    if (getCurrentLocation().hasEnemies()) {
      if (getCurrentLocation().enemies().size() == 1) {
        System.out.println("There is a " + getCurrentLocation().enemies().get(0).getName() + " here!");
      } else {
        System.out.println("There are enemies at this location!");
        getCurrentLocation().enemies().forEach(e -> {
          System.out.println(e.getName());
        });
      }
    }
  }

  public void attack(List<String> arguments, PromptProvider promptProvider) {
    Enemy enemy = null;
    if (!arguments.isEmpty()) {
      enemy = EnemyFactory.get().byName(arguments.get(0)).orElse(null);
      if (enemy == null) {
        System.out.println("No such enemy here");
      }
    }

    while (enemy == null) {
      enemy = EnemyFactory.get().byName(askArg(promptProvider, "Who?")).orElse(null);
      if (enemy == null) {
        listEnemies();
      }
    }

    int damage = getHero().hit(enemy);
    if (damage == 0) {
      System.out.println("You miss!");
    } else {
      System.out.println(String.format("You deal %d damage using your %s!", damage, getHero().getWeapon().name()));
    }

    if (enemy.isDead()) {
      System.out.println("You have killed the " + enemy.getName());
      getCurrentLocation().removeEnemy(enemy);
    }
  }

  private String askArg(PromptProvider promptProvider, String prompt) {
    return promptProvider.ask(prompt);
  }

  public void start(PromptProvider prompt) {
    while (!getHero().isDead()) {
      String line = null;
      try {
        line = prompt.ask("?");

        var parsedCommand = CommandParser.parse(line);
        parsedCommand.command().execute(this, parsedCommand.arguments(), prompt);

        if (getCurrentLocation().hasEnemies()) {
          getCurrentLocation().enemies().forEach(enemy -> {
            int damage = enemy.hit(getHero());
            if (damage == 0) {
              System.out.println(String.format("%s misses you", enemy.getName()));
            } else {
              System.out.println(String.format("%s hits you with his %s and deals %d damage!", enemy.getName(), enemy.getWeapon().name(), damage));
            }
          });
        }
      } catch (UnknownCommandException uce) {
        System.out.println(uce.getMessage());
        listCommands();
      } catch (NoCommandException nce) {
        System.out.println(nce.getMessage());
        listCommands();
      }
    }

    System.out.println("You died!");
  }
}
