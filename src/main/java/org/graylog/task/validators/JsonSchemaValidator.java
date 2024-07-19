package org.graylog.task.validators;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonSchemaValidator {

  private static final Logger logger = LoggerFactory.getLogger(JsonSchemaValidator.class);

  /**
   * @param jsonString the string of the json object to be validated
   * @param schemaName the name of the schema that exists in the resources folder.
   * @return if the json string is valid or not.
   */
  public static boolean isValidSchema(String jsonString, String schemaName) {
    try (InputStream inputStream = new FileInputStream(schemaName)) {
      try {
        JSONObject jsonSchema = new JSONObject(new JSONTokener(inputStream));
        Schema schema = SchemaLoader.load(jsonSchema);
        JSONObject jsonObject = new JSONObject(new JSONTokener(jsonString));
        schema.validate(jsonObject);
        return true;
      } catch (ValidationException | JSONException e) {
        logger.error(" The JSON object: {} is in valid. The error message is {}. ",
            jsonString, e.getMessage());
      }
    } catch (IOException e) {
      logger.error("The json Schema couldn't be loaded and an exception is thrown: {}",
          e.toString());
    }
    return false;
  }
}
