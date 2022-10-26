package org.limmen.hero.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.limmen.hero.command.CommandFactory;
import org.limmen.hero.command.CommandParser;
import org.limmen.hero.domain.factory.LocationFactory;
import org.limmen.hero.exceptions.NoCommandException;
import org.limmen.hero.exceptions.UnknownCommandException;

public class World {

  private Hero hero;

  private Location currentLocation;

  public World(Hero hero, Location startLocation) {
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
    } else {
      System.out.println("You can not go in that direction.");
    }
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
    System.out.println(getCurrentLocation().description());    
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

    if (getCurrentLocation().canTravel(direction)) {
      go(direction);
      System.out.println("You are now at " + getCurrentLocation().name());
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
  }
    
  private String askArg(PromptProvider promptProvider, String prompt) {
    return promptProvider.ask(prompt);
  }

  public void start(PromptProvider prompt) {
    while (true) {
      String line = null;
      try {
        line = prompt.ask("?");

        var parsedCommand = CommandParser.parse(line);
        parsedCommand.command().execute(this, parsedCommand.arguments(), prompt);

      } catch (UnknownCommandException uce) {
        System.out.println(uce.getMessage());
        listCommands();
      } catch (NoCommandException nce) {
        System.out.println(nce.getMessage());
        listCommands();
      }
    }
  }  
}
