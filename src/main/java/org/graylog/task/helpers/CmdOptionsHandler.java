package org.graylog.task.helpers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CmdOptionsHandler {

  private static final Logger logger = LoggerFactory.getLogger(CmdOptionsHandler.class);

  /**
   * This class ensures that the args has -f/--file option and validates that the file's path
   * exists
   *
   * @param args
   * @return
   */
  public String assignAndGetOptions(String[] args) {
    Options options = new Options();

    // Add option for message
    options.addOption("f", "file", true, "Path to the file of the data");

    // Parse command-line arguments
    org.apache.commons.cli.CommandLineParser parser = new DefaultParser();
    CommandLine cmd = null;
    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      logger.error("Error parsing command-line arguments: {}.", e.getMessage());
    }
    // Check if the message option is provided
    if (cmd != null && cmd.hasOption("f")) {
      String fileName = cmd.getOptionValue("f");
      logger.info("Input file path: {}", fileName);
      if (fileExists(fileName)) {
        return fileName;
      } else {
        logger.error("Input file {} does not exist.", fileName);
      }
    } else {
      logger.error("Missing required argument: -f/--file");
    }
    return null;
  }

  /**
   * Checks if the file exists
   *
   * @param filePathStr the path to the file
   * @return either a file exists or not
   */
  private boolean fileExists(String filePathStr) {
    Path filePath = Paths.get(filePathStr);
    return filePath.isAbsolute() && Files.exists(filePath);
  }
}
