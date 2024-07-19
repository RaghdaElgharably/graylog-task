package org.graylog.task.validators;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.graylog.task.helpers.FileHelper;
import org.junit.Test;

public class JsonSchemaValidatorTest {

  private static final String schemaPath = FileHelper.getFullPath("testJsonSchema.json",
      JsonSchemaValidatorTest.class);

  /**
   * Test happy scenario. Correct Json object and correct schema
   */
  @Test
  public void testValidSchema() {
    String correctJsonObject = "{\"ClientDeviceType\": \"mobile\",\"ClientIP\": "
        + "\"192.168.239.245\",\"ClientIPClass\": \"noRecord\",\"ClientStatus\": 200}";
    assertTrue(JsonSchemaValidator.isValidSchema(correctJsonObject, schemaPath));

  }

  /**
   * The json object doesn't have correct structure. It is missing the last }
   */
  @Test
  public void testInValidSchema() {
    String inCorrectJsonObject = "{\"ClientDeviceType\": \"mobile\",\"ClientIP\": "
        + "\"192.168.239.245\",\"ClientIPClass\": \"noRecord\",\"ClientStatus\": 200";
    assertFalse(JsonSchemaValidator.isValidSchema(inCorrectJsonObject, schemaPath));
  }

  /**
   * The json object doesn't contain a required field 'OriginResponseTime'
   */
  @Test
  public void testSchemaMissingARequiredAttribute() {
    String inCorrectJsonObject = "{\"ClientDeviceType\": \"mobile\",\"ClientIP\": "
        + "\"192.168.239.245\",\"ClientIPClass\": \"noRecord\"}";
    assertFalse(JsonSchemaValidator.isValidSchema(inCorrectJsonObject, schemaPath));
  }

  /**
   * Correct Json object and incorrect schema path
   */
  @Test
  public void testWrongPathForSchema() {
    String correctJsonObject = "{\"ClientDeviceType\": \"mobile\",\"ClientIP\": "
        + "\"192.168.239.245\",\"ClientIPClass\": \"noRecord\",\"ClientStatus\": 200}";
    assertFalse(JsonSchemaValidator.isValidSchema(correctJsonObject, "schemaPath"));
  }

}