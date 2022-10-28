package org.limmen.hero.domain.factory;

import java.util.ArrayList;
import java.util.List;

import org.limmen.hero.dto.Text;
import org.limmen.hero.util.JSON;

public class TextFactory {
  
  private static TextFactory INSTANCE = new TextFactory();

  private List<Text> texts = new ArrayList<>();

  private TextFactory() {
    this.texts = JSON.loadList("/texts.json", Text.class);
  }
  
  public static TextFactory get() {
    return INSTANCE;
  }

  public String byName(String name) {
    return this.texts.stream()
        .filter(f -> f.name().equalsIgnoreCase(name))
        .findFirst()
        .get()
        .description();
  }
}
