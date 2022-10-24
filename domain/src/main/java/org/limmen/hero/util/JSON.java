package org.limmen.hero.util;

import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.limmen.hero.exceptions.DataloadingException;

public class JSON {

  private static ObjectMapper objectMapper = new ObjectMapper();

  public static <T> List<T> loadList(String file, Class<T> clazz) {
    try {
      Path resource = Path.of(clazz.getResource(file).toURI());
      return objectMapper.readValue(resource.toFile(),
          objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    } catch (Exception e) {
      throw DataloadingException.builder()
          .message("Failed to load " + file.toString())
          .throwable(e)
          .build();
    }
  }
}
