package org.limmen.hero.domain;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import org.limmen.hero.command.CommandFactory;
import org.limmen.hero.command.CommandParser;
import org.limmen.hero.domain.factory.EnemyFactory;
import org.limmen.hero.domain.factory.LocationFactory;
import org.limmen.hero.domain.factory.WeaponFactory;
import org.limmen.hero.exceptions.NoCommandException;
import org.limmen.hero.exceptions.UnknownCommandException;

public class World {

  private Hero hero;

  private Location currentLocation;

  private PrintWriter writer;

  private PromptProvider promptProvider;

  public World(PrintWriter writer, PromptProvider promptProvider, Location startLocation) {
    this.currentLocation = startLocation;
    this.writer = writer;
    this.promptProvider = promptProvider;
  }

  private void intro() {
    println("Welcome to space station X5-Y.");
  }

  private void createHero() {      
      var name = ask("What is your name?");

      var hero = new Hero();
      hero.setName(name);
      hero.setHealth(20);
      hero.setWeapon(WeaponFactory.get().byName("Lazer gun"));
      hero.setArmour(10 + Dice.d10(0).value());
      
      this.hero = hero;
  }

  public Hero getHero() {
    return hero;
  }

  public void exitGame() {
    System.exit(0);
  }

  public void listDirections() {
    println("You can go to the following directions:");
    this.currentLocation.links().forEach(l -> {
      println(l.direction());
    });
  }

  public void go(Direction direction) {
    if (currentLocation.canTravel(direction)) {
      this.currentLocation = LocationFactory.get()
          .byName(currentLocation.getNewLocationName(direction));
      println("You are now at " + getCurrentLocation().name());
      listEnemies();    
    } else {
      println("You can not go in that direction.");
    }
  }

  public void showStatus() {
    println("You are " + getHero().getName());
    println("Health: " + getHero().getHealth());
    println("Armour: " + getHero().getArmour());
    println("Weapon: " + getHero().getWeapon().name() + " Damage: " + getHero().getWeapon().damage());
  }

  public void listCommands() {
    println("The following commands are available:");
    var cmds = CommandFactory.get().list().stream().toList();

    cmds.forEach(cmd -> {
      print(cmd.getName());

      if (!cmd.getAliasses().isEmpty()) {
        print(" (or: ");
        print(cmd.getAliasses().stream().collect(Collectors.joining(",")));
        print(")");
      }

      println("");
    });
  }

  public void describeLocation() {
    println("You are now at " + getCurrentLocation().name());
    println(getCurrentLocation().description());
    listDirections();
  }

  public Location getCurrentLocation() {
    return currentLocation;
  }

  public void changeLocation(List<String> arguments) {
    Direction direction = null;
    if (!arguments.isEmpty()) {
      direction = Direction.safeParse(arguments.get(0).toUpperCase());
      if (!getCurrentLocation().canTravel(direction)) {
        direction = null;
        listDirections();
      }
    }

    while (direction == null) {
      direction = Direction.safeParse(ask("Where to?").toUpperCase());
      if (direction == null || !getCurrentLocation().canTravel(direction)) {
        listDirections();
      }
    }

    go(direction);
  }

  private void listEnemies() {
    if (getCurrentLocation().hasEnemies()) {
      if (getCurrentLocation().enemies().size() == 1) {
        println("There is a " + getCurrentLocation().enemies().get(0).getName() + " here!");
      } else {
        println("There are enemies at this location!");
        getCurrentLocation().enemies().forEach(e -> {
          println(e.getName());
        });
      }
    }
  }

  public void attack(List<String> arguments) {
    Enemy enemy = null;
    if (!arguments.isEmpty()) {
      enemy = EnemyFactory.get().byName(arguments.get(0)).orElse(null);
      if (enemy == null) {
        println("No such enemy here");
      }
    }

    while (enemy == null) {
      enemy = EnemyFactory.get().byName(ask("Who?")).orElse(null);
      if (enemy == null) {
        listEnemies();
      }
    }

    int damage = getHero().hit(enemy);
    if (damage == 0) {
      println("You miss!");
    } else {
      println(String.format("You deal %d damage using your %s!", damage, getHero().getWeapon().name()));
    }

    if (enemy.isDead()) {
      println("You have killed the " + enemy.getName());
      getCurrentLocation().removeEnemy(enemy);
    }
  }

  public void start() {
    intro();
    createHero();
    
    while (!getHero().isDead()) {
      String line = null;
      try {
        line = ask("?");

        var parsedCommand = CommandParser.parse(line);
        parsedCommand.command().execute(this, parsedCommand.arguments());

        if (getCurrentLocation().hasEnemies()) {
          getCurrentLocation().enemies().forEach(enemy -> {
            int damage = enemy.hit(getHero());
            if (damage == 0) {
              println(String.format("%s misses you", enemy.getName()));
            } else {
              println(String.format("%s hits you with his %s and deals %d damage!", enemy.getName(), enemy.getWeapon().name(), damage));
            }
          });
        }
      } catch (UnknownCommandException uce) {
        println(uce.getMessage());
        listCommands();
      } catch (NoCommandException nce) {
        println(nce.getMessage());
        listCommands();
      }
    }

    println("You died!");
  }

  private String ask(String prompt) {
    return promptProvider.ask(prompt);
  }

  private void print(String s) {
    this.writer.print(s);
    this.writer.flush();
  }

  private void println(String s) {
    this.writer.println(s);
    this.writer.flush();
  }

  private void println(Object s) {
    this.writer.println(s.toString());
    this.writer.flush();
  }
}
