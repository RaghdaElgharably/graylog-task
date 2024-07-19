package org.graylog.task.services;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import org.graylog.task.helpers.FileHelper;
import org.junit.Before;
import org.junit.Test;


public class LoggingServiceTest {

  GraylogSender graylogSender;
  private LoggingService loggingService;

  @Before
  public void setUp() {
    graylogSender = mock(GraylogSender.class);
    loggingService = new LoggingService(graylogSender);
  }

  @Test
  public void testLoggingSuccessfully() throws IOException {
    String path = FileHelper.getFullPath("logs2.txt", LoggingServiceTest.class);
    String schemaPath = FileHelper.getFullPath("jsonSchema.json", LoggingService.class);
    loggingService.logFromFile(path, schemaPath);
    verify(graylogSender, times(2)).sendGelfMessage(any());
  }

  @Test
  public void testLoggingInvalidLogs() throws IOException {
    String path = FileHelper.getFullPath("logs.txt", LoggingServiceTest.class);
    String schemaPath = FileHelper.getFullPath("jsonSchema.json", LoggingService.class);
    loggingService.logFromFile(path, schemaPath);
    verify(graylogSender, times(0)).sendGelfMessage(any());
  }

  @Test
  public void testLoggingInvalidSchema() throws IOException {
    String path = FileHelper.getFullPath("logs.txt", LoggingServiceTest.class);
    String schemaPath = FileHelper.getFullPath("jsonSchema.json", LoggingServiceTest.class);
    loggingService.logFromFile(path, schemaPath);
    verify(graylogSender, times(0)).sendGelfMessage(any());
  }
}