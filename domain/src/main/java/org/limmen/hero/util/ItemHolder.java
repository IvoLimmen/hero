package org.limmen.hero.util;

import java.util.List;

import org.limmen.hero.domain.Item;

public interface ItemHolder {
  
  List<Item> getItems();

  default boolean hasItems() {
    return !getItems().isEmpty();
  }

  default void addItems(List<Item> items) {
    getItems().addAll(items);
  }

  default void addItem(Item item) {
    if (item != null) {
      getItems().add(item);
    }
  }

  default Item itemByName(String name) {
    return getItems().stream()
        .filter(p -> p.getName().equalsIgnoreCase(name))
        .findFirst()
        .orElse(null);
  }

  default void removeItem(Item item) {
    var newList = getItems().stream()
        .filter(f -> !f.getName().equals(item.getName()))
        .toList();

    getItems().clear();
    getItems().addAll(newList);
  }  
}
