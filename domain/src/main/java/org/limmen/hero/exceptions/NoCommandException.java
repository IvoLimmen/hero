package org.limmen.hero.exceptions;

public class NoCommandException extends RuntimeException {

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    public Builder() {
    }

    public NoCommandException build() {
      return new NoCommandException(this);
    }
  }

  private NoCommandException(Builder builder) {
    super("Please specify a command");
  }
}
