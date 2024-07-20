package org.graylog.task.services;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import org.graylog.task.helpers.FileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class LoggingServiceTest {

  private GraylogSender graylogSender;
  private LoggingService loggingService;

  @BeforeEach
  public void setUp() {
    graylogSender = mock(GraylogSender.class);
    loggingService = new LoggingService(graylogSender);
  }

  /**
   * tests that a file is read correct and the sendmessage func is called as many times
   * as there are lines in the file
   * @throws IOException
   */
  @Test
  public void testLoggingSuccessfully() throws IOException {
    String path = FileHelper.getFullPath("logs2.txt", LoggingServiceTest.class);
    String schemaPath = FileHelper.getFullPath("jsonSchema.json", LoggingService.class);
    loggingService.logFromFile(path, schemaPath);
    verify(graylogSender, times(2)).sendGelfMessage(any());
  }

  /**
   * trying to log wrong json objects. make sure that no messages was going to be sent.
   * @throws IOException
   */
  @Test
  public void testLoggingInvalidLogs() throws IOException {
    String path = FileHelper.getFullPath("logs.txt", LoggingServiceTest.class);
//    read the schema in the main folder
    String schemaPath = FileHelper.getFullPath("jsonSchema.json", LoggingService.class);
    loggingService.logFromFile(path, schemaPath);
    verify(graylogSender, times(0)).sendGelfMessage(any());
  }

  /**
   * trying to log json objects but validating them with the wrong schema. make sure that no messages was going to be sent.
   * @throws IOException
   */
  @Test
  public void testLoggingInvalidSchema() throws IOException {
    String path = FileHelper.getFullPath("logs.txt", LoggingServiceTest.class);
//    read the schema that is in the test folder
    String schemaPath = FileHelper.getFullPath("jsonSchema.json", LoggingServiceTest.class);
    loggingService.logFromFile(path, schemaPath);
    verify(graylogSender, times(0)).sendGelfMessage(any());
  }
}