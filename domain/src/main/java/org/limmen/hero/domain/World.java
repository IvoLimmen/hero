package org.limmen.hero.domain;

import org.limmen.hero.command.Command;
import org.limmen.hero.command.CommandFactory;
import org.limmen.hero.command.CommandParser;
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
    CommandFactory.get().list().stream().map(Command::getName).forEach(System.out::println);
  }

  public Location getCurrentLocation() {
    return currentLocation;
  }

  public void start(PromptProvider prompt) {
    while (true) {
      String line = null;
      try {
        line = prompt.ask("?");

        CommandParser.parse(line, prompt).execute(this);

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
