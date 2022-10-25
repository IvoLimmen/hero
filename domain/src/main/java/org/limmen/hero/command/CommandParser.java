package org.limmen.hero.command;

import java.util.Arrays;

import org.limmen.hero.domain.PromptProvider;
import org.limmen.hero.exceptions.NoCommandException;
import org.limmen.hero.exceptions.UnknownCommandException;

public class CommandParser {

  public CommandParser() {
  }

  public static Command parse(String input, PromptProvider promptProvider) {
    if (input == null || input.isEmpty()) {
      throw NoCommandException.builder().build();
    }

    String[] parts = input.split("\\s+");

    var command = parts[0];
    var arguments = Arrays.asList(parts).subList(1, parts.length);

    var cmd = CommandFactory.get().byNameOrAlias(command)
        .orElseThrow(() -> { 
          throw UnknownCommandException.builder()
              .command(command)
              .build(); 
        });
        
    return cmd.inject(arguments, promptProvider);
  }
}
