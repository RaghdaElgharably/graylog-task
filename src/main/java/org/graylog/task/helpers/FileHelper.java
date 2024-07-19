package org.graylog.task.helpers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHelper {

  private static final Logger logger = LoggerFactory.getLogger(FileHelper.class);

  /**
   * The function reads the content of the file and return a list of lines
   *
   * @param filePathStr path for the file to be read.
   * @return list of Strings. Each line in the file is a string.
   * @throws IOException if a problem happened while the file is being read.
   */
  public static List<String> readFile(String filePathStr) throws IOException {
    Path filePath = Paths.get(filePathStr);
    try {
      return Files.readAllLines(filePath);
    } catch (IOException e) {
      logger.error("Failed to read the file: {}.", filePath);
      throw e;
    }
  }

  /**
   * @param name of te file
   * @return the full path of the file from resources' folder
   */
  public static <T> String getFullPath(String name, Class<T> fileHelperClass) {
    ClassLoader classLoader = fileHelperClass.getClassLoader();

    // Get the URL of the resource
    URL resource = classLoader.getResource(name);

    if (resource == null) {
      throw new IllegalArgumentException("File not found!");
    } else {
      // Convert the URL to a File object
      File file = new File(resource.getFile());

      // Get the absolute path
      return file.getAbsolutePath();
    }
  }

}
