package org.limmen.hero.command;

import java.util.List;

import org.limmen.hero.domain.PromptProvider;
import org.limmen.hero.domain.World;

public abstract class Command {

  private List<String> arguments;
  private PromptProvider promptProvider;

  public abstract String getName();

  public Command inject(List<String> arguments, PromptProvider promptProvider) {
    this.arguments = arguments;
    this.promptProvider = promptProvider;
    return this;
  }

  public abstract void execute(World world);

  public List<String> getArguments() {
    return arguments;
  }

  public boolean noArgs() {
    return this.arguments.isEmpty();
  }

  public String askArg(String prompt) {
    return this.getPromptProvider().ask(prompt);
  }

  public PromptProvider getPromptProvider() {
    return promptProvider;
  }
}
