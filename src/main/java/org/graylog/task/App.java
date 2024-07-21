package org.graylog.task;

import java.io.IOException;
import org.graylog.task.helpers.CmdOptionsHandler;
import org.graylog.task.helpers.FileHelper;
import org.graylog.task.services.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the entry for the application. the app takes an absolute file path as input, parse it2,
 * validates it2 versus a schema and logs it2 to graylog. To start the app, you need to run the class
 * with option[-f/-- file $filePath]
 */

@SpringBootApplication
public class App  implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(App.class);
  public static String jsonSchemaName = "jsonSchema.json";

  private final LoggingService loggingService;
  private final CmdOptionsHandler cmdOptionsHandler;

  @Autowired
  public App(LoggingService loggingService, CmdOptionsHandler cmdOptionsHandler) {
    this.loggingService = loggingService;
    this.cmdOptionsHandler = cmdOptionsHandler;
  }

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Override
  public void run(String[] args) {
    logger.info("App starting....");
    String fileName = cmdOptionsHandler.assignAndGetOptions(args);
    if (fileName != null) {
      try {
        loggingService.logFromFile(fileName, FileHelper.getFullPath(jsonSchemaName, App.class));
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
    }
    logger.info("App Shutting down....");
  }

}
