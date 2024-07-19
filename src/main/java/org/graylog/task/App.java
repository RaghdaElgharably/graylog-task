import java.io.IOException;
import org.graylog.task.helpers.CmdOptionsHandler;
import org.graylog.task.helpers.FileHelper;
import org.graylog.task.services.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the entry for the application. the app takes an absolute file path as input, parse it2,
 * validates it2 versus a schema and logs it2 to graylog. To start the app, you need to run the class
 * with option[-f/-- file $filePath]
 */
public class App {

  private static final Logger logger = LoggerFactory.getLogger(App.class);
  public static String jsonSchemaName = "jsonSchema.json";

  public static void main(String[] args) {
    logger.info("App starting....");
    CmdOptionsHandler cmdOptionsHandler = new CmdOptionsHandler();
    String fileName = cmdOptionsHandler.assignAndGetOptions(args);
    if (fileName != null) {
      try {
        LoggingService loggingService = new LoggingService();
        loggingService.logFromFile(fileName, FileHelper.getFullPath(jsonSchemaName, App.class));
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
    }
    logger.info("App Shutting down....");
  }


}
