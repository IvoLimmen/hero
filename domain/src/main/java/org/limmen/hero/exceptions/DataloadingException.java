package org.limmen.hero.exceptions;

public class DataloadingException extends RuntimeException {

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Throwable throwable;
    private String message;

    public Builder() {
    }

    Builder(Throwable throwable, String message) {
      this.throwable = throwable;
      this.message = message;
    }

    public Builder throwable(Throwable throwable) {
      this.throwable = throwable;
      return Builder.this;
    }

    public Builder message(String message) {
      this.message = message;
      return Builder.this;
    }

    public DataloadingException build() {
      return new DataloadingException(this);
    }
  }

  private DataloadingException(Builder builder) {
    super(builder.message, builder.throwable);
  }
}
