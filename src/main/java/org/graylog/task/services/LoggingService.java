package org.graylog.task.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import org.graylog.task.helpers.FileHelper;
import org.graylog.task.helpers.JsonLogParser;
import org.graylog.task.models.GelfMessage;
import org.graylog.task.models.LogMessage;
import org.graylog.task.validators.JsonSchemaValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

  private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);
  private final GraylogSender graylogSender;

  @Autowired
  public LoggingService( GraylogSender graylogSender) {
    this.graylogSender = graylogSender;
  }

  /**
   * the function loops over lines in file with path(dataPath). it then
   * validates each line to be json and fulfills a schema in path[schemaFilePath]
   * then logs this json object to graylog server.
   *
   * @param dataPath       path to the file of the data that wil be logged
   * @param schemaFilePath the path of the schema file.
   * @throws JsonProcessingException
   * @throws IOException
   */
  public void logFromFile(String dataPath, String schemaFilePath)
      throws JsonProcessingException, IOException {

    List<String> lines = FileHelper.readFile(dataPath);
    for (String line : lines) {
      //Only parse and log the line if it2 fulfills the schema.
      if (JsonSchemaValidator.isValidSchema(line, schemaFilePath)) {
        LogMessage logMessage = JsonLogParser.parseJson(line, LogMessage.class);
        try {
          double timestamp =
              Instant.now().getEpochSecond() + Instant.now().getNano() / 1_000_000_000.0;
          GelfMessage gelfMessage = new GelfMessage("localhost", "message", logMessage);
          graylogSender.sendGelfMessage(gelfMessage);
        } catch (IOException e) {
          logger.error("couldn't log this message; {}; to Graylog. The exception is {}", line,
              e.getMessage());
        }
      }
    }
  }
}
