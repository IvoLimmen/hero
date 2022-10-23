package org.limmen.hero.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

public class CommandFactory {
  
  private static CommandFactory INSTANCE = new CommandFactory();

  private List<Command> commands = new ArrayList<>();

  public CommandFactory() {

    this.commands = ServiceLoader.load(Command.class).stream()
        .map(Provider::get)
        .toList();
  }

  public static CommandFactory get() {
    return INSTANCE;
  }

  public List<Command> list() {
    return this.commands;
  }

  public Optional<Command> byName(String name) {
    return this.commands.stream()
        .filter(f -> f.getName().equalsIgnoreCase(name))
        .findFirst();
  }
}
