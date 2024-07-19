package org.graylog.task;

import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.graylog.task.helpers.FileHelper;
import org.graylog.task.services.GraylogSender;
import org.graylog.task.services.LoggingService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * integration test that tests the happy scenario. It reads a file that has 2 logs, validates them
 * and then send them to the mock server.
 */
public class LoggingServiceIT {

  private static final String schemaPath = FileHelper.getFullPath("testJsonSchema.json",
      LoggingServiceIT.class);
  private static final String dataPath = FileHelper.getFullPath("logs2.txt",
      LoggingServiceIT.class);
  private LoggingService loggingService;

  private MockWebServer mockWebServer;

  @Before
  public void setUp() throws Exception {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
    String grayServerUrl = mockWebServer.url("/").toString();
    GraylogSender graylogSender = new GraylogSender(grayServerUrl);
    loggingService = new LoggingService(graylogSender);
  }

  @After
  public void tearDown() throws Exception {
    mockWebServer.shutdown();
  }

  @Test
  public void testLogFromFileSuccessfully() throws IOException {
    mockWebServer.enqueue(new MockResponse().setResponseCode(200));
    mockWebServer.enqueue(new MockResponse().setResponseCode(200));
    loggingService.logFromFile(dataPath, schemaPath);
    Assert.assertEquals(2, mockWebServer.getRequestCount());
  }
}
