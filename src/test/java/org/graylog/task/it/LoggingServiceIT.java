package org.graylog.task.it;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.graylog.task.helpers.FileHelper;
import org.graylog.task.services.GraylogSender;
import org.graylog.task.services.LoggingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * integration test that tests the happy scenario. It reads a file that has 2 logs, validates them
 * and then send them to the mock server.
 */
@SpringBootTest
public class LoggingServiceIT {

  private static final String schemaPath = FileHelper.getFullPath("testJsonSchema.json",
      LoggingServiceIT.class);
  private static final String dataPath = FileHelper.getFullPath("logs2.txt",
      LoggingServiceIT.class);
  private LoggingService loggingService;

  private MockWebServer mockWebServer;
  @Autowired
  private GraylogSender graylogSender;

  @BeforeEach
  public void setUp() throws Exception {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
    String grayServerUrl = mockWebServer.url("/").toString();
    graylogSender.setGrayLogUrl(grayServerUrl);
    loggingService = new LoggingService(graylogSender);
  }

  @AfterEach
  public void tearDown() throws Exception {
    mockWebServer.shutdown();
  }

  @Test
  public void testLogFromFileSuccessfully() throws IOException {
    mockWebServer.enqueue(new MockResponse().setResponseCode(200));
    mockWebServer.enqueue(new MockResponse().setResponseCode(200));
    loggingService.logFromFile(dataPath, schemaPath);
    assertEquals(2, mockWebServer.getRequestCount());
  }
}
