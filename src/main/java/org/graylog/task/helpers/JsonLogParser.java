package org.graylog.task.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonLogParser {

  /**
   * the function, takes a string and returns an object
   *
   * @param jsonString the input object written as json
   * @param valueType  the class that the json is mapped to
   * @param <T>
   * @return object of valueType class
   * @throws JsonProcessingException
   */
  public static <T> T parseJson(String jsonString, Class<T> valueType)
      throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(jsonString, valueType);
  }
}
