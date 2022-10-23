package org.limmen.hero.exceptions;

public class UnknownCommandException extends RuntimeException {

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String command;

    public Builder() {
    }

    public Builder command(String command) {
      this.command = command;
      return Builder.this;
    }

    public UnknownCommandException build() {
      return new UnknownCommandException(this);
    }
  }

  private UnknownCommandException(Builder builder) {
    super(String.format("I did not understood %s", builder.command));
  }
}
